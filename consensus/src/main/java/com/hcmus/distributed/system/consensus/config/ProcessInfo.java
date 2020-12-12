package com.hcmus.distributed.system.consensus.config;

import com.hcmus.distributed.system.consensus.exception.InvalidConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProcessInfo {
    private String pid;
    private int port;
    private int initialDelay;
    private ArrayList<Integer> portServers = new ArrayList<>();
    private Map<Integer, Integer> mapTimeDelay = new HashMap<>();
    private boolean isByzantine;

    public ProcessInfo(String pid, int port, String isByzantine, String initialDelay,
                       String timeDelayRequestNodes, String pidServers, String portServers) throws InvalidConfig {
        this.pid = pid;
        this.port = port;
        this.initialDelay = Integer.valueOf(initialDelay);
        this.isByzantine = !isByzantine.equals("0");
        this.parseEntity(portServers, pidServers, timeDelayRequestNodes);
    }

    private void parseEntity(String portServers, String pidServers, String listTimeDelay) throws InvalidConfig {
        this.parseListPorts(portServers);
        this.parseListTimeDelay(pidServers, listTimeDelay);
    }

    private void parseListPorts(String portServers) {
        for(String portAnotherServer : portServers.split(",")) {
            if(!portAnotherServer.equals(String.valueOf(port))) {
                this.portServers.add(Integer.valueOf(portAnotherServer));
            }
        }
    }

    private void parseListTimeDelay(String pidServers, String delayTimes) throws InvalidConfig {
        String[] listPid = pidServers.split(",");
        String[] listDelayTime = delayTimes.split(",");
        if(listPid.length != listDelayTime.length) {
            throw new InvalidConfig("length pid and delay time not comparable: listpid = " + listPid.length + ", list delay time = " + listDelayTime.length);
        } else {
            int length = listPid.length;
            for(int i = 0; i < length; i++) {
                this.mapTimeDelay.put(Integer.valueOf(listPid[i]), Integer.valueOf(listDelayTime[i]));
            }
        }
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ArrayList<Integer> getPortServers() {
        return portServers;
    }

    public void setPortServers(ArrayList<Integer> portServers) {
        this.portServers = portServers;
    }

    public boolean isByzantine() {
        return isByzantine;
    }

    public void setByzantine(boolean byzantine) {
        isByzantine = byzantine;
    }

    public Map<Integer, Integer> getMapTimeDelay() {
        return mapTimeDelay;
    }

    public void setMapTimeDelay(Map<Integer, Integer> mapTimeDelay) {
        this.mapTimeDelay = mapTimeDelay;
    }

    public void setPortServer(ArrayList<Integer> portServers) {
        this.portServers = portServers;
    }
}
