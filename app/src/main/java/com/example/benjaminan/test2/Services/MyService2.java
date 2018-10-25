package com.example.benjaminan.test2.Services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.benjaminan.test2.EventBus.EventUtil;
import com.example.benjaminan.test2.LockScreenActivity;
import com.example.benjaminan.test2.MainActivity;
import com.example.benjaminan.test2.R;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import Http.HttpUtlis;

public class MyService2 extends Service {
    private AlarmManager manager;
    private PendingIntent pi;
    DeviceScreenListener listener = new DeviceScreenListener(this);
    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Date on, off;
    private long temp;
    long currentTime;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, LockScreenActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("防沉迷时间管理系统")
                .setContentText("惩罚模式")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(2, notification);
        EventBus.getDefault().post(new EventUtil(UID));
    }

    private String UID;
    private String url;
    private String response;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UID = intent.getStringExtra("UID");
        EventBus.getDefault().post(new EventUtil(UID));
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.register(new DeviceScreenListener.ScreenStateListener() {
                    @Override
                    public void onScreenOn() {
                        //long currentTime = System.currentTimeMillis();

                    }

                    @Override
                    public void onScreenOff() {
                        if(on != null) {
                            currentTime = System.currentTimeMillis();
                            off = new Date(currentTime);
                            SimpleDateFormat fYear = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat fTime = new SimpleDateFormat("HHmmss");
                            String year = fYear.format(off);
                            String offTime = fTime.format(off);
                            String onTime = fTime.format(on);
                            Long between = (off.getTime() - on.getTime() ) / 1000;
                            String second = between.toString();
                            url = "http://123.207.36.58/insertUsing.php?UID=" + UID + "&AID=0" + "&date=" + year + "&start_time=" + onTime + "&end_time=" + offTime + "&appname=none&apptime=none&keyword=using_time&value=" + second;
                            new AsyncTask<String, Float, String>(){
                                @Override
                                protected String doInBackground(String... params) {
                                    HttpUtlis http = new HttpUtlis();
                                    return http.getRequest(params[0],"utf-8");
                                }
                            }.execute(url);
                            //HttpUtlis http = new HttpUtlis();
                            //response = http.getRequest(url, "utf-8");
                            Handler handler2=new Handler(Looper.getMainLooper());  // 这里是得到主界面程序的Looper
                            handler2.post(new Runnable(){
                                public void run(){
                                    Toast.makeText(getApplicationContext(), response + " : " + url, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
//123.207.36.58/insertUsing.php?UID=1009171740&AID=0&date=2018-10-23&start_time=202000&end_time=203000&appname=none&apptimr=none&keyword=using_time&value=10
                    @Override
                    public void onUserPresent() {
                        on = new Date(temp = System.currentTimeMillis());

                        //String url = "http://123.207.36.58/login.php?UID=" + UID + "&password=" + md5Psw;
                    }
                });
            }
        }).start();
        manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int halfHour = 1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+halfHour;
        Intent i = new Intent(this,Alarm.class);
        pi = PendingIntent.getService(this,0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }

}
