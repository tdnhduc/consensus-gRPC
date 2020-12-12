package com.hcmus.distributed.system.consensus.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser {
    public static Date parseCurrentTime(long currentTime) {
        return new Date(currentTime);
    }
}
