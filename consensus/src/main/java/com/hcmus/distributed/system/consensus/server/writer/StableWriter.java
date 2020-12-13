package com.hcmus.distributed.system.consensus.server.writer;

import com.hcmus.distributed.system.consensus.entity.TimeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;



public class StableWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(StableWriter.class);

    private static FileWriter writer;


    public StableWriter(FileWriter writer) {
        this.writer = writer;
    }

    public FileWriter getWriter() {
        return writer;
    }

    public void setWriter(FileWriter writer) {
        this.writer = writer;
    }

    public void writeToStableFile(String content) throws IOException {
        writer.write(content);
        LOGGER.info("Successfully write to stable at {}", TimeParser.parseCurrentTime(System.currentTimeMillis()));
    }
}
