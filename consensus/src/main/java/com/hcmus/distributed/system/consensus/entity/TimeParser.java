package com.hcmus.distributed.system.consensus.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
    public static Date parseCurrentTime(long currentTime) {
        return new Date(currentTime);
    }

    public static String parseTimeFromMsg(long timeSend) {
        return new StringBuilder().append(sdf.format(timeSend)).toString();
    }

    public static String parseInfoToStableStorage(long time, String pidActual, String pidExpect) {
        return new StringBuilder().append(sdf.format(new Date(time))).append("| PID_NEED_CONFIRM: ").append(pidActual)
                                         .append("| PID_EXPECT: ").append(pidExpect).append(System.lineSeparator()).toString();
    }
}
