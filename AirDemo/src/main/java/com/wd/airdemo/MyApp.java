package com.wd.airdemo;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.wd.ms.tools.MSTools;
import com.wd.airdemo.module.AirModule;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MSTools.getInstance().init(this, new MSTools.IConnectListener() {
            @Override
            public void onSuccess() {
                AirModule.Init();
            }

            @Override
            public void onFailed() {
            }
        });
    }

}
