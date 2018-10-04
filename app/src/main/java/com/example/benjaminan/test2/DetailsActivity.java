package com.example.benjaminan.test2;

import android.support.v7.app.AppCompatActivity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import details.AppInfo;
import details.AppInfoAdapter;

import static android.content.Context.USAGE_STATS_SERVICE;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerview;

    private AppInfoAdapter mAdapter;

    private SwipeRefreshLayout swipeRefresh;

    private List<AppInfo> appList = new ArrayList<>();

    private boolean sysFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(sysFlag){
                    sysFlag = false;
                    refreshApps();
                    Toast.makeText(getApplicationContext(),"已隐藏系统应用",Toast.LENGTH_SHORT).show();
                }
                else{
                    sysFlag = true;
                    refreshApps();
                    Toast.makeText(getApplicationContext(),"已显示系统应用",Toast.LENGTH_SHORT).show();
                }
            }
        });

        initApps();

        mRecyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AppInfoAdapter(appList);
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        mRecyclerview.setAdapter(mAdapter);
        //添加分割线
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //mAdapter.setOnItemClickLitener(this);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swip_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {refreshApps();}
        });
    }

    void initApps(){
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY,-24);
        Calendar endCal = Calendar.getInstance();
        UsageStatsManager manager = (UsageStatsManager) this.getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,beginCal.getTimeInMillis(),endCal.getTimeInMillis());

        if(stats.size() == 0) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                try {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                } catch (Exception e) {
                    Toast.makeText(this, "无法开启允许查看使用情况的应用界面", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
        }
        else{
            for(UsageStats usageStates :stats){
                usageStates.getPackageName();
                usageStates.getTotalTimeInForeground();
            }
            Context context = getApplicationContext();
            PackageManager pm = context.getPackageManager();
            for( int i = 0 ; i < stats.size() ; i++){
                AppInfo temp = new AppInfo(stats.get(i).getPackageName());
                try{
                    ApplicationInfo info = pm.getApplicationInfo(temp.getPackageName(),0);
                    /**
                     * 筛选出系统应用
                     */
                    if((info.flags & ApplicationInfo.FLAG_SYSTEM) > 0 && !sysFlag)
                        continue;
                    temp.setIcon(info.loadIcon(pm));
                    temp.setAppName(info.loadLabel(pm).toString());
                    temp.setTime(stats.get(i).getTotalTimeInForeground());
                    appList.add(temp);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(appList);
        }
    }

    private void refreshApps(){
        boolean flag = false;
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY,-24);
        Calendar endCal = Calendar.getInstance();
        UsageStatsManager manager = (UsageStatsManager)this.getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,beginCal.getTimeInMillis(),endCal.getTimeInMillis());

        if(stats.size() == 0) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                try {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "无法开启允许查看使用情况的应用界面", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
        }
        else{
            for(UsageStats usageStates :stats){
                usageStates.getPackageName();
                usageStates.getTotalTimeInForeground();
            }
            Context context = getApplicationContext();
            PackageManager pm = context.getPackageManager();
            appList.clear();
            for( int i = 0 ; i < stats.size() ; i++){
                AppInfo temp = new AppInfo(stats.get(i).getPackageName());
                try{
                    ApplicationInfo info = pm.getApplicationInfo(temp.getPackageName(),0);
                    if((info.flags & ApplicationInfo.FLAG_SYSTEM) > 0 && !sysFlag)
                        continue;
                    temp.setIcon(info.loadIcon(pm));
                    temp.setAppName(info.loadLabel(pm).toString());
                    temp.setTime(stats.get(i).getTotalTimeInForeground());
                    appList.add(temp);
                    flag = true;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(appList);
        }
        if(flag)
            mAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
}