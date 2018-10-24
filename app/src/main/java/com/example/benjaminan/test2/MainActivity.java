package com.example.benjaminan.test2;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView  item_index, item_diary, item_find, item_exercese,item_me;
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fouthFragmen;
    private FiveFragment fiveFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private DrawerLayout mDrawerLayout;

//    String[] titles = new String[]{"微信", "通讯录", "发现", "我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.title_background_1);
        }
        initViews();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(5);//ViewPager的缓存为5帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        item_index.setTextColor(Color.parseColor("#66CDAA"));

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
        item_index = (TextView) findViewById(R.id.item_index);
        item_diary = (TextView) findViewById(R.id.item_diary);
        item_find = (TextView) findViewById(R.id.item_find);
        item_exercese=(TextView)findViewById(R.id.item_exercese) ;
        item_me = (TextView) findViewById(R.id.item_me);

        item_index.setOnClickListener(this);
        item_diary.setOnClickListener(this);
        item_find.setOnClickListener(this);
        item_me.setOnClickListener(this);
        item_exercese.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fouthFragmen = new FourFragment();
        fiveFragment=new FiveFragment();
        //给FragmentList添加数据
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fouthFragmen);
        mFragmentList.add(fiveFragment);
    }

    /**
     * 点击底部Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_index:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_diary:
                vp.setCurrentItem(1, true);
                break;
            case R.id.item_find:
                vp.setCurrentItem(2, true);
                break;
            case R.id.item_exercese:
                vp.setCurrentItem(3, true);
                break;
            case R.id.item_me:
                vp.setCurrentItem(4,true);
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
            item_index.setBackgroundResource(R.drawable.xiaorong_pressed);
            item_diary.setBackgroundResource(R.drawable.diary_normal);
            item_find.setBackgroundResource(R.drawable.find_normal);
            item_exercese.setBackgroundResource(R.drawable.exercise_normal);
            item_me.setBackgroundResource(R.drawable.me_normal);
        } else if (position == 1) {
            item_diary.setBackgroundResource(R.drawable.diary_pressed);
            item_index.setBackgroundResource(R.drawable.xiaorong_normal);
            item_find.setBackgroundResource(R.drawable.find_normal);
            item_exercese.setBackgroundResource(R.drawable.exercise_normal);
            item_me.setBackgroundResource(R.drawable.me_normal);
        } else if (position == 2) {
            item_find.setBackgroundResource(R.drawable.find_pressed);
            item_index.setBackgroundResource(R.drawable.xiaorong_normal);
            item_diary.setBackgroundResource(R.drawable.diary_normal);
            item_exercese.setBackgroundResource(R.drawable.exercise_normal);
            item_me.setBackgroundResource(R.drawable.me_normal);
        } else if (position == 3) {
            item_exercese.setBackgroundResource(R.drawable.exercise_pressed);
            item_me.setBackgroundResource(R.drawable.me_normal);
            item_index.setBackgroundResource(R.drawable.xiaorong_normal);
            item_diary.setBackgroundResource(R.drawable.diary_normal);
            item_find.setBackgroundResource(R.drawable.find_normal);
        }else if (position==4){
            item_me.setBackgroundResource(R.drawable.me_pressed);
            item_index.setBackgroundResource(R.drawable.xiaorong_normal);
            item_diary.setBackgroundResource(R.drawable.diary_normal);
            item_exercese.setBackgroundResource(R.drawable.exercise_normal);
            item_find.setBackgroundResource(R.drawable.find_normal);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.share:
                /*----------放宋静的分享的响应事件----------*/
                break;
            default:
        }
        return true;
    }
}
