package com.hcmus.distributed.system.consensus.client;

import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.grpc.VotingRequest;
import com.hcmus.distributed.system.grpc.VotingResponse;
import com.hcmus.distributed.system.grpc.VotingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableAsync
@EnableScheduling
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private ProcessInfo processInfo;
    private Map<Integer, VotingServiceGrpc.VotingServiceBlockingStub> stubMap = new HashMap<>();

    public Client(ProcessInfo processInfo) {
        this.processInfo = processInfo;
        for(Integer portAnotherServer : this.processInfo.getPortServers()) {
            try {
                ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", portAnotherServer)
                        .usePlaintext().build();
                VotingServiceGrpc.VotingServiceBlockingStub stub = VotingServiceGrpc.newBlockingStub(channel);
                stubMap.put(portAnotherServer, stub);
            } catch (Exception e) {
                LOGGER.info("Fail to connect server localhost:{}", portAnotherServer);
            }
        }
    }

    @Async
    @Scheduled(fixedRateString = "${fixRated}", initialDelayString = "${initial.delay}")
    public void sendRequest() {
        if(this.processInfo.isByzantine()) {
            // TODO : if this is node is byzantine
        } else {
            VotingRequest request = VotingRequest.newBuilder().setPid(this.processInfo.getPid())
                                                            .setTimeSend(System.currentTimeMillis())
                                                            .build();
            int index = 0;
            LOGGER.info("Prepare broadcast to {} server....", this.processInfo.getPortServers().size());
            for(VotingServiceGrpc.VotingServiceBlockingStub stub : stubMap.values()) {
                try{
                    VotingResponse response = stub.vote(request);
                    LOGGER.info("Receive response from {}", request.getPid());
                    index++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("Finish broadcast to {} node", index);
        }
    }
}
