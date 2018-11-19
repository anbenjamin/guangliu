package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.benjaminan.test2.Schedule.*;
import com.example.benjaminan.test2.R;
import com.example.benjaminan.test2.Schedule.Schedule;

public class FourFragment extends Fragment {
    SQLiteHelper helper;
    private ScheduleAdapter listAdapter;
    private ListView List;
    private ArrayList<Schedule> listData;
    private Calendar calendar;
    int now_year,now_month,now_day;
    private boolean isGetData = false;
    public FourFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_four, container, false);
//        helper=new SQLiteHelper(this,"schedule.db",null,1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    refresh();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
        Button add=(Button)getActivity().findViewById(R.id.add);
        Button look=(Button)getActivity().findViewById(R.id.look);
        calendar=Calendar.getInstance();
        now_year=calendar.get(Calendar.YEAR);
        now_month=calendar.get(Calendar.MONTH)+1;
        now_day=calendar.get(Calendar.DAY_OF_MONTH);
        helper = new SQLiteHelper(getActivity(), "schedule", null, 1);
        List=(ListView)getActivity().findViewById(R.id.listview);
//        listData=new ArrayList<Schedule>();
//        List<Schedule> list=helper.queryScheduleNow(now_year,now_month,now_day);
        listData=helper.queryScheduleNow(now_year,now_month,now_day);
        listAdapter= new ScheduleAdapter(listData,this.getActivity());
        listAdapter.setList(listData);
        listAdapter.notifyDataSetChanged();
        List.setAdapter(listAdapter);
        List.setOnItemClickListener(new showdetail());
        add.setOnClickListener(new AddSchedule());
        look.setOnClickListener(new LookSchedule());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端

        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) { //   进入当前Fragment
        if (enter && !isGetData) { isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            refresh();
        } else { isGetData = false;
        } return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    @Override
    public void onResume() {
        if (!isGetData) { //   这里可以做网络请求或者需要的数据刷新操作
            refresh();
            isGetData = true;
        }
        super.onResume();
    }

    private void refresh() {
        Button add=(Button)getActivity().findViewById(R.id.add);
        Button look=(Button)getActivity().findViewById(R.id.look);
//        Toast.makeText(getActivity(), now_day+"日"+now_month+"月"+now_year+"年", Toast.LENGTH_SHORT).show();
        calendar=Calendar.getInstance();
        now_year=calendar.get(Calendar.YEAR);
        now_month=calendar.get(Calendar.MONTH)+1;
        now_day=calendar.get(Calendar.DAY_OF_MONTH);
        helper = new SQLiteHelper(getActivity(), "schedule", null, 1);
        List=(ListView)getActivity().findViewById(R.id.listview);
//        listData=new ArrayList<Schedule>();
//        List<Schedule> list=helper.queryScheduleNow(now_year,now_month,now_day);
        listData=helper.queryScheduleNow(now_year,now_month,now_day);
        listAdapter= new ScheduleAdapter(listData,this.getActivity());
//        listAdapter.setList(listData);
        listAdapter.notifyDataSetChanged();
        List.setAdapter(listAdapter);
        List.setOnItemClickListener(new showdetail());
        add.setOnClickListener(new AddSchedule());
        look.setOnClickListener(new LookSchedule());
    }
    class showdetail implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent();
            intent.setClass(getActivity(),showDetailActivity.class);
            Bundle bundle=new Bundle();
            Schedule schedule=listData.get(position);
            bundle.putInt("Po",position);
//            Toast.makeText(getActivity(),"位置"+position,Toast.LENGTH_SHORT).show();
            int Sid=schedule.getSid();
            bundle.putInt("Sid",Sid);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    class AddSchedule implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), addScheduleActivity.class);
            getActivity().startActivity(intent);
        }
    }
    class LookSchedule implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(getActivity(),lookScheduleActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
