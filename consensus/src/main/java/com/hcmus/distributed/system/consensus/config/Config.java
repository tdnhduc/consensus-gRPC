package com.hcmus.distributed.system.consensus.config;

import com.hcmus.distributed.system.consensus.exception.InvalidConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executor;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {
    @Value("${pid}")
    private String pid;

    @Value("${port}")
    private String port;

    @Value("${ports.server}")
    private String portServers;

    @Value("${initial.delay}")
    private String initialDelay;

    @Value("${time.delay.all.server}")
    private String timeDelayAllServer;

    @Value("${byzantine}")
    private String byzantine;

    @Value("${pid.servers}")
    private String pidServers;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPortServers() {
        return portServers;
    }

    public void setPortServers(String portServers) {
        this.portServers = portServers;
    }

    public String getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(String initialDelay) {
        this.initialDelay = initialDelay;
    }

    public String getByzantine() {
        return byzantine;
    }

    public void setByzantine(String byzantine) {
        this.byzantine = byzantine;
    }

    public String getTimeDelayAllServer() {
        return timeDelayAllServer;
    }

    public void setTimeDelayAllServer(String timeDelayAllServer) {
        this.timeDelayAllServer = timeDelayAllServer;
    }

    public String getPidServers() {
        return pidServers;
    }

    public void setPidServers(String pidServers) {
        this.pidServers = pidServers;
    }

    @Bean
    public ProcessInfo processInfo() throws InvalidConfig {
        return new ProcessInfo(pid, Integer.valueOf(port), byzantine, initialDelay, timeDelayAllServer, pidServers, portServers);
    }

    @Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
}
