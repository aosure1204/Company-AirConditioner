package com.wd.ms.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wd.airdemo.module.FinalRemoteModule;
import com.wd.ms.IRemoteModule;
import com.wd.ms.ITaskBinder;


class m implements Runnable, ServiceConnection {

    private Context mCtx;
    private boolean isConnect = false;
    private boolean isForceDisconnect = false;
    private Handler mH = new Handler();
    private MSTools.IConnectListener listener;

    public m(Context context) {
        this.mCtx = context;
    }

    @Override
    public void run() {
        if (!isConnect && !isForceDisconnect)
            connect();
    }


    public void setListener(MSTools.IConnectListener lis) {
        this.listener = lis;
    }
    private void connect() {
        if (mCtx != null) {
            isForceDisconnect = false;
            Intent ii = new Intent("com.wd.ms");
            ii.setPackage("com.wd.ms");
            mCtx.bindService(ii, this, Context.BIND_AUTO_CREATE);

            Log.i("mstool", "connect ms... ");
        }

        mH.postDelayed(this, 3000);
    }


    public void disconnect() {
        isForceDisconnect = true;
        mCtx.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i("mstool", "onServiceConnected ok !");
        isConnect = true;
        IRemoteModule iRemoteModule = IRemoteModule.Stub.asInterface(service);
        MSTools.getInstance().setRemote(iRemoteModule);
        if (listener != null) listener.onSuccess();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

        isConnect = false;
        MSTools.getInstance().setRemote(null);
        if (isForceDisconnect) return;
        mH.postDelayed(this, 1000);
        if (listener != null) listener.onFailed();
    }

}
