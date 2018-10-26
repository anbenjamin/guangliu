package com.example.benjaminan.test2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class threeDownActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView week,month,all;
    private ViewPager vp;
    private threeDownWeekActivity threedownweek;
    private threeDownMonthActivity threedownmonth;
    private threeDownAllActivity threedownall;
    private List<Fragment> threeFragmentList = new ArrayList<Fragment>();
    private threeDownActivity.FragmentAdapter threeFragmentAdapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //去除工具栏
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_three_down);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        initViews();

        threeFragmentAdapter = new threeDownActivity.FragmentAdapter(this.getSupportFragmentManager(), threeFragmentList);
        vp.setOffscreenPageLimit(3);//ViewPager的缓存为5帧
        vp.setAdapter(threeFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        week.setTextColor(Color.parseColor("#66CDAA"));

        //ViewPager的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                /*此方法在页面被选中时调用*/
//                title.setText(titles[position]);
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }
    /**
     * 初始化布局View
     */
    private void initViews() {
        week = (TextView) findViewById(R.id.week);
        month = (TextView) findViewById(R.id.month);
        all = (TextView) findViewById(R.id.all);

        week.setOnClickListener(this);
        month.setOnClickListener(this);
        all.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        threedownweek = new threeDownWeekActivity();
        threedownmonth = new threeDownMonthActivity();
        threedownall = new threeDownAllActivity();
        //给FragmentList添加数据
        threeFragmentList.add(threedownweek);
        threeFragmentList.add(threedownmonth);
        threeFragmentList.add(threedownall);
    }

    /**
     * 点击底部Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.week:
                vp.setCurrentItem(0, true);
                break;
            case R.id.month:
                vp.setCurrentItem(1, true);
                break;
            case R.id.all:
                vp.setCurrentItem(2, true);
                break;
        }
    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */
    private void changeTextColor(int position) {
        if (position == 0) {
            week.setBackgroundResource(R.drawable.xiaorong_pressed);
            month.setBackgroundResource(R.drawable.diary_normal);
            all.setBackgroundResource(R.drawable.find_normal);
        } else if (position == 1) {
            month.setBackgroundResource(R.drawable.diary_pressed);
            week.setBackgroundResource(R.drawable.xiaorong_normal);
            all.setBackgroundResource(R.drawable.find_normal);
        } else if (position == 2) {
            all.setBackgroundResource(R.drawable.find_pressed);
            week.setBackgroundResource(R.drawable.xiaorong_normal);
            month.setBackgroundResource(R.drawable.diary_normal);
        }
    }
}
