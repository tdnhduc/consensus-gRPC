package com.hcmus.distributed.system.consensus.server.writer;

import com.hcmus.distributed.system.consensus.entity.SpringApplicationContext;
import com.hcmus.distributed.system.consensus.entity.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
public class LocalWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(StableWriter.class);

    @Value("local.direction")
    private static String localDirection;
    private static LocalWriter instance;
    private static FileWriter writer;


    public static LocalWriter getInstance() {
        if (instance == null) {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(LocalWriter.class);
            context.refresh();
            SpringApplicationContext.setSharedApplicationContext(context);
            return new LocalWriter(SpringApplicationContext.getBean(FileWriter.class));
        } else {
            return instance;
        }
    }

    private LocalWriter(FileWriter writer) {
        this.writer = writer;
    }

    @Bean
    FileWriter file() throws IOException {
        FileWriter myFile = new FileWriter(localDirection);
        return myFile;
    }

    public static String getLocalDirection() {
        return localDirection;
    }

    public static void setLocalDirection(String localDirection) {
        LocalWriter.localDirection = localDirection;
    }

    public static void setInstance(LocalWriter instance) {
        LocalWriter.instance = instance;
    }

    public static FileWriter getWriter() {
        return writer;
    }

    public static void setWriter(FileWriter writer) {
        LocalWriter.writer = writer;
    }

    public static void writeToLocalFile(String content) throws IOException {
        writer.write(content);
        LOGGER.info("Successfully write to local at {}", TimeParser.parseCurrentTime(System.currentTimeMillis()));
    }
}
