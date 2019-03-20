package com.wd.airdemo.module;

import android.os.RemoteException;

import com.wd.ms.ITaskBinder;
import com.wd.ms.ITaskCallback;

public class RemoteTools {
    private static ITaskBinder mModule;

    public static void setTaskBinder(ITaskBinder module) {
        mModule = module;
    }

    public static void register(ITaskCallback cb, int code, int update) {
        if (mModule == null) return;
        try {
            mModule.registerCallback(cb, code, update);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public static void unregister(ITaskCallback cb, int code) {
        if (mModule == null) return;
        try {
            mModule.unregisterCallback(cb, code);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void cmd(int cmd, int val) {
        cmd(cmd, new int[]{val}, null, null);
    }

    public static void cmd(int cmd, int val1, int val2) {
        cmd(cmd, new int[]{val1, val2}, null, null);
    }

    public static void cmd(int cmd, String str) {
        cmd(cmd, null, null, new String[]{str});
    }

    public static void cmd(int cmd, int[] ints, float[] flts, String[] strs) {
        if (mModule == null) return;
        System.out.println("cmd " + cmd +"-"+ (ints != null ? (ints[0]):0));
        try {
            mModule.cmd(cmd, ints, flts, strs);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
