package com.hcmus.distributed.system.consensus.config;

import com.hcmus.distributed.system.consensus.exception.InvalidConfig;
import com.hcmus.distributed.system.grpc.ConsensusServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProcessInfo {
    private final String pid;
    private final int port;
    private final int initialDelay;
    private ArrayList<Integer> portServers = new ArrayList<>();
    private Map<String, Integer> mapTimeDelay = new HashMap<>();
    private Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap = new HashMap<>();
    private final boolean isByzantine;
    private final int numberConfirm;
    private final int fixRated;

    public ProcessInfo(String pid, int port, String isByzantine, String initialDelay, String fixRated, String numberConfirm,
                       String timeDelayRequestNodes, String pidServers, String portServers) throws InvalidConfig {
        this.pid = pid;
        this.port = port;
        this.initialDelay = Integer.valueOf(initialDelay);
        this.isByzantine = !isByzantine.equals("0");
        this.numberConfirm = Integer.valueOf(numberConfirm);
        this.fixRated = Integer.valueOf(fixRated);
        this.parseEntity(portServers, pidServers, timeDelayRequestNodes);
        this.buildStubs(pidServers, this.portServers);
    }

    private void buildStubs(String pidServers, ArrayList<Integer> ports) throws InvalidConfig {
        ArrayList<String> pids = new ArrayList<>(Arrays.asList(pidServers.split(",")));
        pids.remove(this.pid);
        if(pids.size() != ports.size()) {
            throw new InvalidConfig("size pid and port is not comparable");
        }

        for(int i = 0; i < pids.size(); i++) {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", ports.get(i)).usePlaintext()
                        .build();
                ConsensusServiceGrpc.ConsensusServiceBlockingStub stub = ConsensusServiceGrpc.newBlockingStub(managedChannel);
                this.stubMap.put(pids.get(i), stub);
            } catch (Exception e) {
                e.printStackTrace();
                i--;
            }
        }
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
            throw new InvalidConfig("length pid and delay time not comparable: list pid = " + listPid.length + ", list delay time = " + listDelayTime.length);
        } else {
            int length = listPid.length;
            for(int i = 0; i < length; i++) {
                this.mapTimeDelay.put(listPid[i], Integer.valueOf(listDelayTime[i]));
            }
        }
    }

    public String getPid() {
        return pid;
    }

    public int getPort() {
        return port;
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

    public Map<String, Integer> getMapTimeDelay() {
        return mapTimeDelay;
    }

    public void setMapTimeDelay(Map<String, Integer> mapTimeDelay) {
        this.mapTimeDelay = mapTimeDelay;
    }

    public void setPortServer(ArrayList<Integer> portServers) {
        this.portServers = portServers;
    }

    public int getInitialDelay() {
        return initialDelay;
    }

    public int getNumberConfirm() {
        return numberConfirm;
    }

    public Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> getStubMap() {
        return stubMap;
    }

    public void setStubMap(Map<String, ConsensusServiceGrpc.ConsensusServiceBlockingStub> stubMap) {
        this.stubMap = stubMap;
    }

    public int getFixRated() {
        return fixRated;
    }
}
