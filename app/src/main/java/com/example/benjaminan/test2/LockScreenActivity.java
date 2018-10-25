package com.example.benjaminan.test2;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.benjaminan.test2.AntiAddiction.AdminReceiver;
import com.example.benjaminan.test2.AntiAddiction.BaseActivity;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_MULTI_PROCESS;

public class LockScreenActivity extends BaseActivity implements View.OnClickListener{

    protected static TextView Lock;
    private TextView Lock2;
    protected static RelativeLayout LockR;


    private AssetManager mgr;
    private Typeface typeface;

    protected static Long startTLock;
    protected static Long endTLock;
    protected static SharedPreferences pref;
    protected static SharedPreferences.Editor editor;
    private DevicePolicyManager policyManager;
    private  ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        pref=getSharedPreferences("data",MODE_MULTI_PROCESS);
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, AdminReceiver.class);

        //final Long TempEndTLock=LockScreenActivity.pref.getLong("tempendt",0);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }//隐藏原始标题

        Lock = findViewById(R.id.Lock);
        Lock2 = findViewById(R.id.Lock2);
        Lock.setOnClickListener(this);
        mgr = getAssets();
        typeface = Typeface.createFromAsset(mgr,"fonts/qi.ttf");

        Lock.setTypeface(typeface);
        Lock2.setTypeface(typeface);

        // mgr=getAssets();
        //typeface= Typeface.createFromAsset(mgr,"fonts/qi.ttf");
        //lock.setTypeface(typeface);

        Intent data=new Intent(LockScreenActivity.this, LoginActivity.class);
        startActivity(data);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Lock:
                Intent startIntent = new Intent(this, AntiAddictionActivity.class);
                startActivity(startIntent);


                break;

            default:
                break;
        }
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


}