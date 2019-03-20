package com.wd.airdemo;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.wd.ms.tools.MSTools;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MSTools.getInstance().init(this, new MSTools.IConnectListener() {
            @Override
            public void onSuccess() {
                openSelfServer(true);
            }

            @Override
            public void onFailed() {
                openSelfServer(false);
            }
        });
    }

    private void openSelfServer(boolean on) {
        Intent ii = new Intent(this, MyServer.class);
        if (on){
            // 启动服务的地方
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 startForegroundService(ii);
            } else {
                startService(ii);
            }
        }
        else
            stopService(ii);
    }

}
