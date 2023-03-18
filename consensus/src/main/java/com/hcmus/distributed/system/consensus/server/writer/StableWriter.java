package com.hcmus.distributed.system.consensus.server.writer;

import com.hcmus.distributed.system.consensus.entity.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
@ComponentScan
public class StableWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(StableWriter.class);
    private String path;

    public StableWriter(String path) {
        this.path = path;
    }

    public void writeToStableFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(this.path, true);
        fileWriter.write(content);
        fileWriter.close();
    }
}
