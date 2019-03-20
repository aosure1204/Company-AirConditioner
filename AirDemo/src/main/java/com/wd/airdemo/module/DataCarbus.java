package com.wd.airdemo.module;

import com.wd.airdemo.util.UiNotifyEvent;

public class DataCarbus {
    public static int[] DATA = new int[FinalCanbus.U_MAX];
    public static UiNotifyEvent[] NOTIFY = new UiNotifyEvent[FinalCanbus.U_MAX];

    static {
        for (int i = 0; i < NOTIFY.length; i++) {
            NOTIFY[i] = new UiNotifyEvent();
            NOTIFY[i].setUpdateCode(i);
        }
    }
    public static String sFactorySn = "";
}
