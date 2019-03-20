package com.wd.ms;

import android.os.RemoteException;


public class IRemoteModuleProxy extends IRemoteModule.Stub {
    private static IRemoteModuleProxy INSTANCE = new IRemoteModuleProxy();
    private IRemoteModule remote;

    public static IRemoteModuleProxy getInstance(){
        return INSTANCE;
    }

    public void setRemoteModule(IRemoteModule iRemoteModule) {
        this.remote = iRemoteModule;
    }


    public ITaskBinder getModuleByCode(int code){
        if(remote != null){
            try {
                return remote.getModule(code);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ITaskBinder getModule(int code) throws RemoteException {
//        switch (code) {
//            case FinalRemoteModule.MODULE_MAIN:
//                return ModuleMain.getObj();
//            case FinalRemoteModule.MODULE_BT:
//                return ModuleBt.getObj();
//            case FinalRemoteModule.MODULE_TV:
//                return ModuleTV.getObj();
//            case FinalRemoteModule.MODULE_RADIO:
//                return ModuleRadio.getObj();
//        }
        return null;
    }
}
