package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/10/3.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import cn.sharesdk.onekeyshare.OnekeyShare;
import details.DetailInfo;
import details.DetailInfoAdapter;

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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.share);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            String temp = "share test(AN).";
            showShare(temp);
            }
        });

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

    /**
     * 将content 发送
     * @param content
     * */
    private void showShare(String content) {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("分享内容"+content);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getActivity());
    }
}

