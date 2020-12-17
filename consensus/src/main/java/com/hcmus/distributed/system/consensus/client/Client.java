package com.hcmus.distributed.system.consensus.client;

import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.grpc.BaseRequest;
import com.hcmus.distributed.system.grpc.ConsensusServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
@EnableScheduling
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private ProcessInfo processInfo;
    private Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap = new HashMap<>();

    public Client(ProcessInfo processInfo) {
        this.processInfo = processInfo;
        this.stubMap = processInfo.getStubMap();
    }

    @Async
    @Scheduled(fixedRateString = "${fixRated}", initialDelayString = "${initial.delay}")
    public void sendRequest() {
        BaseRequest request = BaseRequest.newBuilder().setPid(this.processInfo.getPid())
                .setTimeSend(System.currentTimeMillis())
                .build();
        boolean isSend = false;
        if(this.processInfo.isByzantine()) {
            isSend = new Random().nextBoolean();
        }
        if(isSend) {

            int index = 0;
            for(String pid : this.stubMap.keySet()) {
                LOGGER.info("Send msg to node {}...", pid);
                ConsensusServiceGrpc.ConsensusServiceBlockingStub stub = this.stubMap.get(pid);
                try{
                    stub.withDeadlineAfter(3L, TimeUnit.SECONDS).schedule(request);
                } catch (Exception e) {
                    LOGGER.debug("TIME EXCEED AT CLIENT");
                }
                index++;
            }
            LOGGER.info("Finish send schedule msg to total {} nodes", index);
        }
    }
}
