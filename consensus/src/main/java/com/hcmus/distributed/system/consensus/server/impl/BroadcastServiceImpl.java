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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
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
        LOGGER.info("[BROADCAST] receive broad cast confirm from pid = {}, node need confirm = {}, is this node legal = {}",
                                                                                request.getBaseRequest().getPid(),
                                                                                request.getPidNeedConfirm(),
                                                                                request.getIsLegal());
    }

    @Override
    public void schedule(BaseRequest request, StreamObserver<BaseResponse> responseObserver) {
        LOGGER.info("[SCHEDULE] receive one schedule request from {}, time send {}", request.getPid(), TimeParser.parseTimeFromMsg(request.getTimeSend()));

        BaseResponse response = BaseResponse.newBuilder().setAck(true)
                                            .setPid(this.processInfo.getPid())
                                            .setTimeReceive(System.currentTimeMillis()).build();
        BroadcastRequest.Builder builder = this.broadRequestBuilder(request.getPid());
        if(this.validateScheduleRequest(request)) {
            builder.setIsLegal(true);
            this.pushIntoConfirmMap(request);
        } else {
            builder.setIsLegal(false);
        }
        this.doBroadCast(builder.build());

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    private boolean validateScheduleRequest(BaseRequest request) {
        return true;
    }

    private void pushIntoConfirmMap(BaseRequest request) {
        //TODO validate schedule request
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
        for (ConsensusServiceGrpc.ConsensusServiceBlockingStub stub : this.stubMap.values()) {
            try {
                stub.broadcast(request);
            } catch (StatusRuntimeException e) {
                LOGGER.debug("DEADLINE EXCEED");
            }
        }
    }
}
