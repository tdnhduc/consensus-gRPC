package com.hcmus.distributed.system.consensus.client;

import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.grpc.BaseRequest;
import com.hcmus.distributed.system.grpc.BroadcastRequest;
import com.hcmus.distributed.system.grpc.ConsensusServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private ProcessInfo processInfo;
    private Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap = new HashMap<>();

    public Client(ProcessInfo processInfo) {
        this.processInfo = processInfo;
        this.stubMap = processInfo.getStubMap();
    }

    @Scheduled(fixedRateString = "${fixRated}", initialDelayString = "${initial.delay}")
    public void sendRequest() {
        if(this.processInfo.isByzantine()) {
            // TODO : if this is node is byzantine
        } else {
            BaseRequest request = BaseRequest.newBuilder().setPid(this.processInfo.getPid())
                                            .setTimeSend(System.currentTimeMillis())
                                            .build();
            int index = 0;
            for(String pid : this.stubMap.keySet()) {
                LOGGER.info("Send msg to node {}...", pid);
                ConsensusServiceGrpc.ConsensusServiceBlockingStub stub = this.stubMap.get(pid);
                try{
                    stub.schedule(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index++;
            }
            LOGGER.info("Finish broadcast to total {} nodes", index);
        }
    }
}
