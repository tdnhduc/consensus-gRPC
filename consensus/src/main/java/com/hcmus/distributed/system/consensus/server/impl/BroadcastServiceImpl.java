package com.hcmus.distributed.system.consensus.server.impl;

import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.consensus.entity.TimeParser;
import com.hcmus.distributed.system.consensus.exception.InvalidConfig;
import com.hcmus.distributed.system.consensus.server.writer.StableWriter;
import com.hcmus.distributed.system.grpc.*;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
    private Map<String, AtomicBoolean> isSendConfirmMap = new ConcurrentHashMap<>();

    public BroadcastServiceImpl(ProcessInfo processInfo, StableWriter stableWriter, Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap, int minConfirm) {
        this.processInfo = processInfo;
        this.stableWriter = stableWriter;
        this.minConfirm = minConfirm;
        this.stubMap = stubMap;
    }

    @Override
    public void broadcast(BroadcastRequest request, StreamObserver<BroadcastResponse> responseObserver) {
        super.broadcast(request, responseObserver);
    }

    @Override
    public void schedule(BaseRequest request, StreamObserver<BaseResponse> responseObserver) {
        super.schedule(request, responseObserver);
    }
}
