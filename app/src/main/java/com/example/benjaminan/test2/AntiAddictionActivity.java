package com.example.benjaminan.test2;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminan.test2.AntiAddiction.AdminReceiver;
import com.example.benjaminan.test2.AntiAddiction.BaseActivity;
import com.example.benjaminan.test2.AntiAddiction.HomeReceiver;

public class AntiAddictionActivity extends BaseActivity implements View.OnClickListener{

    protected static DevicePolicyManager policyManager;
    protected static ComponentName componentName;
    protected static Long startT;
    protected static Long endT;
    private EditText lockTime;
    private int locktime=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, AdminReceiver.class);
        HomeReceiver innerReceiver = new HomeReceiver();//注册广播
        IntentFilter intentFilter1 = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(innerReceiver, intentFilter1);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }//隐藏原始标题

        setContentView(R.layout.activity_anti_addiction);
        //LockScreenActivity.Lock=findViewById(R.id.Lock);
        lockTime=findViewById(R.id.lockTime);

        TextView startService = findViewById(R.id.start_service);
        TextView stopService =  findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:


                Long TempEndB=LockScreenActivity.pref.getLong("tempendt",0);
                if(TempEndB>0){
                    Intent startIntent = new Intent(this, MyService.class);
                    startService(startIntent); // 启动服务
                }
                else{
                    locktime=Integer.parseInt(lockTime.getText().toString());
                    if(locktime==0){
                        Toast.makeText(AntiAddictionActivity.this, "re", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        startT=System.currentTimeMillis();
                        endT=startT+1000*3600*locktime;
                        LockScreenActivity.editor.putLong("tempendt",endT);
                        LockScreenActivity.editor.apply();

                        if (AntiAddictionActivity.policyManager.isAdminActive(AntiAddictionActivity.componentName)) {
                            Intent startIntent = new Intent(this, MyService.class);
                            startService(startIntent); // 启动服务
                            // AntiAdditionActivity.policyManager.lockNow(); //立即锁屏
                            //finish();
                        } else {
                            activeManage();
                            // finish();
                        }
                    }


                }

                /////

                // 启动服务



                break;
            case R.id.stop_service:
                //startT=System.currentTimeMillis();
                Long TempEndC=LockScreenActivity.pref.getLong("tempendtC",0);
                if(TempEndC>0){
                    Intent intent2=new Intent(this,LimitedScreenActivity.class);
                    startActivity(intent2);
                }
                else{
                    locktime=Integer.parseInt(lockTime.getText().toString());
                    if(locktime==0){

                        Toast.makeText(AntiAddictionActivity.this, "re", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startT=System.currentTimeMillis();
                        endT=startT+1000*3600*locktime;
                        LockScreenActivity.editor.putLong("tempendtC",endT);
                        LockScreenActivity.editor.apply();
                        Intent intent2=new Intent(this,LimitedScreenActivity.class);
                        startActivity(intent2);
                    }
                }

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



    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            new AlertDialog.Builder(AntiAdditionActivity.this).setTitle("提示")
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setMessage("确定要退出吗?")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AntiAdditionActivity.this.finish();
                        }})
                    .setNegativeButton("取消", null)
                    .create().show();
            return false;
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {
            // 监控菜单键
            Toast.makeText(AntiAdditionActivity.this, "Menu", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return super.onKeyDown(keyCode, event);
    }*/
}

