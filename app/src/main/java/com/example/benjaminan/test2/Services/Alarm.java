package com.example.benjaminan.test2.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by BenjaminAn on 2018/10/26.
 */

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyService2.class);
        context.startService(i);
    }
}

