package com.example.benjaminan.test2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private Long middleT;
    private AlarmManager manager;
    private PendingIntent pi;
    public MyService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();


    class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate executed");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("防沉迷时间管理系统")
                .setContentText("惩罚模式")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand executed");
        final Long TempEndT = LockScreenActivity.pref.getLong("tempendt",0);

        new Thread(new Runnable() {
            @Override
            public void run() {

                    AntiAddictionActivity.policyManager.lockNow(); //立即锁屏
                    //finish();

                middleT= System.currentTimeMillis();
                if (middleT>=TempEndT){
                    stopSelf();
                    StopAlarm(); 
                }


            }
        }).start();
        manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int halfHour=5*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+halfHour;
        Intent i=new Intent(this,MyService.class);
        pi= PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
    public void activeManage() {
        //启动设备管理 - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, AntiAddictionActivity.componentName);
        //描述
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活锁屏功能");

        startActivity(intent);
    }
    public void StopAlarm(){
        manager.cancel(pi);
        LockScreenActivity.editor.putLong("tempendt",0);
        LockScreenActivity.editor.apply();
    }
}

