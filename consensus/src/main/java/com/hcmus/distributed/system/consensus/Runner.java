package com.hcmus.distributed.system.consensus;

import com.hcmus.distributed.system.consensus.client.Client;
import com.hcmus.distributed.system.consensus.config.Config;
import com.hcmus.distributed.system.consensus.config.ProcessInfo;
import com.hcmus.distributed.system.consensus.entity.SpringApplicationContext;
import com.hcmus.distributed.system.consensus.server.impl.BroadcastServiceImpl;
import com.hcmus.distributed.system.consensus.server.writer.StableWriter;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    public static void main(String args[]) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.register(Client.class);
        context.refresh();
        SpringApplicationContext.setSharedApplicationContext(context);
        ProcessInfo processInfo = SpringApplicationContext.getBean(ProcessInfo.class);
        StableWriter stableWriter = SpringApplicationContext.getBean(StableWriter.class);
        LOGGER.info("{}, {}, {}, {}", processInfo.getPid(), processInfo.getPort(), processInfo.getPortServers(), processInfo.getMapTimeDelay());
        Server server = ServerBuilder.forPort(processInfo.getPort())
                                    .addService(new BroadcastServiceImpl(processInfo, stableWriter,processInfo.getStubMap(), processInfo.getNumberConfirm())).build();
        LOGGER.info("Start listen connection");
        server.start();
        server.awaitTermination();
    }
}
