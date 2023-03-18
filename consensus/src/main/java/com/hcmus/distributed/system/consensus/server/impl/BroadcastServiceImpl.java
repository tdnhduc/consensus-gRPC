package com.hcmus.distributed.system.consensus.server.impl;

import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.consensus.entity.TimeParser;
import com.hcmus.distributed.system.consensus.exception.InvalidConfig;
import com.hcmus.distributed.system.consensus.server.writer.StableWriter;
import com.hcmus.distributed.system.grpc.*;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BroadcastServiceImpl extends ConsensusServiceGrpc.ConsensusServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastServiceImpl.class);
    private final ProcessInfo processInfo;
    private final int minConfirm;
    private final StableWriter stableWriter;
    private final Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap;
    private Map<String, AtomicInteger> confirmMap = new ConcurrentHashMap<>();

    public BroadcastServiceImpl(ProcessInfo processInfo, StableWriter stableWriter, Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap, int minConfirm) {
        this.processInfo = processInfo;
        this.stableWriter = stableWriter;
        this.minConfirm = minConfirm;
        this.stubMap = stubMap;
    }

    @Override
    public void broadcast(BroadcastRequest request, StreamObserver<BroadcastResponse> responseObserver) {
        LOGGER.info("[BROADCAST][RECEIVE] broad cast confirm from pid = {}, node need confirm = {}, is this node legal = {}",
                                                                                request.getBaseRequest().getPid(),
                                                                                request.getPidNeedConfirm(),
                                                                                request.getIsLegal());
        String pidExpect = this.calculatePid(request.getBaseRequest().getTimeSend(), request.getPidNeedConfirm());
        calculateQuantumConfirm(request, pidExpect);
        BaseResponse baseResponse = BaseResponse.newBuilder().setTimeReceive(System.currentTimeMillis())
                                                            .setPid(this.processInfo.getPid())
                                                            .setAck(true).build();
        BroadcastResponse response = BroadcastResponse.newBuilder().setBaseResponse(baseResponse).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void schedule(BaseRequest request, StreamObserver<BaseResponse> responseObserver) {
        LOGGER.info("[SCHEDULE] receive one schedule request from {}, time send {}", request.getPid(), TimeParser.parseTimeFromMsg(request.getTimeSend()));

        BaseResponse response = BaseResponse.newBuilder().setAck(true)
                                            .setPid(this.processInfo.getPid())
                                            .setTimeReceive(System.currentTimeMillis()).build();
        BroadcastRequest.Builder builder = this.broadRequestBuilder(request.getPid());
        String pidExpect = this.calculatePid(request.getTimeSend(), request.getPid());
        if(this.validateScheduleRequest(request, pidExpect)) {
            builder.setIsLegal(true);
            this.pushIntoConfirmMap(request);
        } else {
            builder.setIsLegal(false);
        }
        if (this.processInfo.isByzantine()) {
            builder.setIsLegal(new Random().nextBoolean());
        }

        this.doBroadCast(builder.build());

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    private boolean validateScheduleRequest(BaseRequest request, String pidExpect) {
        if (pidExpect.isEmpty()) {
            LOGGER.error("Cannot calculate pid expect from request!!!");
            return false;
        }
        if (pidExpect.equals(request.getPid())) {
            LOGGER.info("[VALID] schedule request pid = {}, pidExpect = {}", request.getPid(), pidExpect);
            return true;
        } else {
            LOGGER.info("[INVALID] schedule request pid = {}, pidExpect = {}", request.getPid(), pidExpect);
            return false;
        }
    }

    private String calculatePid(long timeSend, String pidReq) {
        Date date = TimeParser.parseCurrentTime(timeSend);
        long seconds = (date.getMinutes() * 60 + date.getSeconds()) * 1000;
        
        while(seconds >= 15000) {
            if( seconds >= this.processInfo.getFixRated() )
                seconds -= this.processInfo.getFixRated();
            else
                seconds -= 15000;
        }

        String pidExpect = "";
        seconds += this.processInfo.getMapTimeDelay().get(pidReq);
        for(String pid : this.processInfo.getMapTimeDelay().keySet()) {
            if (seconds >= this.processInfo.getMapTimeDelay().get(pid)) {
                pidExpect = pid;
            } else {
                break;
            }
        }
        return pidExpect;
    }

    private void pushIntoConfirmMap(BaseRequest request) {
        AtomicInteger numbConfirm = this.confirmMap.get(request.getPid());
        if (numbConfirm == null) {
            numbConfirm = new AtomicInteger(0);
        }
        numbConfirm.incrementAndGet();
        this.confirmMap.put(request.getPid(), numbConfirm);
    }

    private BroadcastRequest.Builder broadRequestBuilder(String pid) {
        BaseRequest baseRequest = BaseRequest.newBuilder().setPid(this.processInfo.getPid())
                                                            .setTimeSend(System.currentTimeMillis()).build();
        return BroadcastRequest.newBuilder().setBaseRequest(baseRequest).setPidNeedConfirm(pid);
    }

    private void doBroadCast(BroadcastRequest request) {
        int index = 0;
        for (ConsensusServiceGrpc.ConsensusServiceBlockingStub stub : this.stubMap.values()) {
            try {
                stub.withDeadlineAfter(3L, TimeUnit.SECONDS).broadcast(request);
                index ++;
            } catch (StatusRuntimeException e) {
                LOGGER.debug("DEADLINE EXCEED");
            }
        }
        LOGGER.info("[BROADCAST][SEND] total broadcast to {} nodes", index);
    }

    private synchronized void calculateQuantumConfirm (BroadcastRequest request, String pidExpect) {
        if (request.getIsLegal()) {
            AtomicInteger confirm = this.confirmMap.get(request.getPidNeedConfirm());
            if (confirm == null) {
                confirm = new AtomicInteger(1);
            } else {
                if (confirm.incrementAndGet() >= this.processInfo.getNumberConfirm()) {
                    LOGGER.info("[CONFIRM] enough confirm, write to stable storage by pid {}", request.getPidNeedConfirm());
                    try {
                        this.stableWriter.writeToStableFile(TimeParser.parseInfoToStableStorage(System.currentTimeMillis(), pidExpect, request.getPidNeedConfirm()));
                    } catch (IOException e) {
                        LOGGER.error("Cannot write to stable storage");
                    }
                    this.confirmMap.remove(request.getPidNeedConfirm());
                    return;
                }
            }
            this.confirmMap.put(request.getPidNeedConfirm(), confirm);
            LOGGER.debug("increase confirm of node {} by one", request.getPidNeedConfirm());
        } else {
            LOGGER.debug("Not enough confirm, wait in {} milliseconds to maybe enough confirm", this.processInfo.getFixRated());
        }
    }
}
