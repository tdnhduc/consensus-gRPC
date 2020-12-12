package com.hcmus.distributed.system.consensus.server.impl;


import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.grpc.VotingRequest;
import com.hcmus.distributed.system.grpc.VotingResponse;
import com.hcmus.distributed.system.grpc.VotingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class VotingServiceImpl extends VotingServiceGrpc.VotingServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingServiceImpl.class);

    ProcessInfo processInfo;
    private Map<String, StreamObserver<VotingResponse>> channelMap = new HashMap<>();
    public VotingServiceImpl(ProcessInfo processInfo) {
        this.processInfo = processInfo;
    }

    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessInfo processInfo) {
        this.processInfo = processInfo;
    }

    @Override
    public void vote(VotingRequest request, StreamObserver<VotingResponse> responseObserver) {
        LOGGER.info("Receive msg broadcast from {}, with time {}", request.getPid(), request.getTimeSend());
        LOGGER.info("Start broadcast for all node...");
        VotingResponse response = VotingResponse.newBuilder().setMsg("Receive msg from {}" + request.getPid())
                                                             .setPid(this.processInfo.getPid()).build();
        for(StreamObserver<VotingResponse> streamObserver: channelMap.values()) {
            streamObserver.onNext(response);
            streamObserver.onCompleted();
        }

        LOGGER.info("Finish broadcast");
        channelMap.put(request.getPid(), responseObserver);
    }

}
