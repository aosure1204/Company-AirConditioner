package com.wd.airdemo.util;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

public final class UiNotifyEvent implements Runnable {

    public static final Handler HANDLER_UI = new Handler(Looper.getMainLooper());

    private ArrayList<IUiNotify> mUiNotifies = new ArrayList<IUiNotify>();
    private int mUpdateCode;

    public UiNotifyEvent() {}

    public UiNotifyEvent(int updateCode) { mUpdateCode = updateCode; }

    public int getUpdateCode() { return mUpdateCode; }

    public void setUpdateCode(int updateCode) { mUpdateCode = updateCode; }

    public synchronized int size() {
        return mUiNotifies.size();
    }

    /**
     * in ui thread
     */
    public synchronized void addNotify(IUiNotify notify) {
        if (notify == null) {
            return;
        }
        if (!mUiNotifies.contains(notify)) {
            mUiNotifies.add(notify);
        }
    }

    /**
     * in ui thread
     */
    public synchronized void addNotify(IUiNotify notify, int onNotify) {
        if (notify == null) {
            return;
        }
        if (!mUiNotifies.contains(notify)) {
            mUiNotifies.add(notify);
        }
        if (onNotify == 1) {
            notify.onNotify(mUpdateCode, null, null, null);
        }
    }

    public synchronized void removeNotify(IUiNotify notify) {
        if (notify == null) {
            return;
        }
        mUiNotifies.remove(notify);
    }

    public synchronized void clearNotifies() {
        mUiNotifies.clear();
    }

    public synchronized void onNotify() {
        if (mUiNotifies.size() > 0) {
            HANDLER_UI.post(this);
        }
    }

    public synchronized void onNotify(int[] ints, float[] flts, String[] strs) {
        if (mUiNotifies.size() > 0) {
            HANDLER_UI.post(new NofityData(ints, flts, strs));
        }
    }

    private class NofityData implements Runnable {
        public int[] 	ints;
        public float[]	flts;
        public String[] strs;

        public NofityData(int[] ints, float[] flts, String[] strs) {
            this.ints = ints;
            this.flts = flts;
            this.strs = strs;
        }

        @Override
        public void run() {
            synchronized (UiNotifyEvent.this) {
                for (IUiNotify notify : mUiNotifies) {
                    notify.onNotify(mUpdateCode, ints, flts, strs);
                }
            }
        }
    }

    @Override
    public synchronized void run() {
        for (IUiNotify notify : mUiNotifies) {
            notify.onNotify(mUpdateCode, null, null, null);
        }
    }
}