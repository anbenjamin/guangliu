package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/10/3.
 */
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
import details.DetailInfo;
import details.DetailInfoAdapter;

import static android.content.Context.USAGE_STATS_SERVICE;

public class ThreeFragment extends Fragment {
    @BindView(R.id.detail_recycler_view)
    RecyclerView mRecyclerview;

    private DetailInfoAdapter mAdapter;

    private SwipeRefreshLayout swipeRefresh;

    private List<DetailInfo> detailList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);

        initDetails();

        //mRecyclerview = (RecyclerView) view.findViewById(R.id.detail_recycler_view);

        mAdapter = new DetailInfoAdapter(detailList);
        //设置布局管理器
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        mRecyclerview.setAdapter(mAdapter);
        //添加分割线
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //mAdapter.setOnItemClickLitener(this);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.detail_swip_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {refreshDetails();}
        });

        return view;
    }

    void initDetails(){
        Calendar calendar = Calendar.getInstance();
        Context context = getActivity().getApplicationContext();
        PackageManager pm = context.getPackageManager();
        for( int i = 0 ; i < 5 ; i++){
            DetailInfo temp = new DetailInfo();
            calendar.add(Calendar.DATE,-1);
            temp.setCalendar(calendar);
            detailList.add(temp);
        }
    }

    private void refreshDetails(){
        boolean flag = false;
        detailList.clear();
        Calendar calendar = Calendar.getInstance();
        Context context = getActivity().getApplicationContext();
        PackageManager pm = context.getPackageManager();
        for( int i = 0 ; i < 5 ; i++){
            DetailInfo temp = new DetailInfo();
            calendar.add(Calendar.DATE,-1);
            temp.setCalendar(calendar);
            detailList.add(temp);

            flag = true;
        }
        if(flag)
            mAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
}

