package com.example.benjaminan.test2.AntiAddiction;

/**
 * Created by BenjaminAn on 2018/10/24.
 */

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.benjaminan.test2.MainActivity;

public class HomeReceiver extends BroadcastReceiver {
    static public final String SYSTEM_DIALOG_REASON_KEY = "reason";
    static public final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    static public final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context arg0, Intent arg1) {


        String action = arg1.getAction();
        //按下Home键会发送ACTION_CLOSE_SYSTEM_DIALOGS的广播
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = arg1.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    Log.d("ddd","按了home");
                    for(int j=0;j<10;j++){
                        Intent intent=new Intent(arg0,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pendingIntent =
                                PendingIntent.getActivity(arg0, 0, intent, 0);
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                        arg0.startActivity(intent);
                    }
                }
                else  if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    Log.d("ddd","按了多任务键");
                    Intent intent2=new Intent(arg0,MainActivity.class);
                    arg0.startActivity(intent2);
                }
            }
        }

    }
}