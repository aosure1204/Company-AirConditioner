package com.wd.airdemo;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wd.airdemo.module.BaseCallBack;
import com.wd.airdemo.module.CarBusCallBack;
import com.wd.airdemo.module.FinalCanbus;
import com.wd.airdemo.module.FinalRemoteModule;
import com.wd.airdemo.module.RemoteTools;
import com.wd.ms.ITaskBinder;
import com.wd.ms.tools.MSTools;

public class MyServer extends Service {
    BaseCallBack callBack;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("airserver create");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1,new Notification()); //这个id不要和应用内的其他同志id一样，不行就写 int.maxValue()        //context.startForeground(SERVICE_ID, builder.getNotification());
        }
        ITaskBinder module = MSTools.getInstance().getModule(FinalRemoteModule.MODULE_CARBUS);
        RemoteTools.setTaskBinder(module);
        callBack = new CarBusCallBack();
        regFlags();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("airserver destroy");
        unregFlags();
        RemoteTools.setTaskBinder(null);
    }

    private void regFlags() {
        for (int i = 0; i < FinalCanbus.U_MAX; i++)
            RemoteTools.register(callBack, i, 1);
    }

    private void unregFlags() {
        for (int i = 0; i < FinalCanbus.U_MAX; i++)
            RemoteTools.unregister(callBack, i);
    }
}
