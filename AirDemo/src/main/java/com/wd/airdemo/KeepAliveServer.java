package com.wd.airdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class KeepAliveServer extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
