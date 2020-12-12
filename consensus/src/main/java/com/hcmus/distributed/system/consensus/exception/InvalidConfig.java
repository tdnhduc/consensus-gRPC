package com.hcmus.distributed.system.consensus.exception;

public class InvalidConfig extends Exception{
    public InvalidConfig() { super();}

    public InvalidConfig(String msg) {
        super(msg);
    }

    public InvalidConfig(String msg, Throwable e) {
        super(msg, e);
    }
}
