package com.wd.airdemo.module;

import android.os.RemoteException;
import android.widget.Switch;

import com.wd.ms.ITaskCallback;

public abstract class BaseCallBack extends ITaskCallback.Stub {
    
    @Override
    public void update(int updateCode, int[] ints, float[] flts, String[] strs) throws RemoteException {
        update(updateCode, ints, strs);
    }

    protected abstract void update(int updateCode, int[] ints, String[] strs);
}
