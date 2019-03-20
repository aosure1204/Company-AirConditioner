package com.wd.ms.tools;

import android.content.Context;

import com.wd.ms.IRemoteModule;
import com.wd.ms.IRemoteModuleProxy;
import com.wd.ms.ITaskBinder;

public class MSTools {

    public interface IConnectListener {
        void onSuccess();

        void onFailed();
    }

    private IConnectListener mListener;
    private static MSTools INSTANCE = new MSTools();
    private IRemoteModule remote;
    private Context mCtx;
    private m obj;

    public static MSTools getInstance() {
        return INSTANCE;
    }

    public void init(Context c, IConnectListener lis) {
        mCtx = c;
        this.mListener = lis;
        if (obj == null) {
            obj = new m(c);
            obj.setListener(lis);
        }
        obj.run();
    }

    public void disconnect() {
        if (obj != null) {
            obj.disconnect();
        }
    }

    protected void setRemote(IRemoteModule remote) {
        System.out.println("mstool " + remote);
        this.remote = remote;
        IRemoteModuleProxy.getInstance().setRemoteModule(remote);
    }

    public ITaskBinder getModule(int module) {
        try {
            ITaskBinder moduleByCode = IRemoteModuleProxy.getInstance().getModuleByCode(module);
//            ITaskBinder moduleByCode = remote.getModule(module);
            System.out.println("mstools: getmodule:" + moduleByCode);
            return moduleByCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
