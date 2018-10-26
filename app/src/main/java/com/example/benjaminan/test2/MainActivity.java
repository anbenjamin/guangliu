package com.example.benjaminan.test2;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminan.test2.Services.MyService2;
import com.example.benjaminan.test2.settingPages.ContactSettingActivity;
import com.example.benjaminan.test2.settingPages.PrivacySettingActivity;
import com.example.benjaminan.test2.settingPages.UseSettingActivity;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private String UID;

//    String[] titles = new String[]{"微信", "通讯录", "发现", "我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        //getSupportActionBar().hide();
        UID = getIntent().getStringExtra("UID");

        Toast.makeText(MainActivity.this, "已將UID：" + UID + "傳入屏幕監聽服務", Toast.LENGTH_SHORT).show();

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

        Switch switch1 = findViewById(R.id.setting_checkbox1);
        Switch switch2 = findViewById(R.id.setting_checkbox2);
        Switch switch3 = findViewById(R.id.setting_checkbox3);
        Button data_button1 = findViewById(R.id.data_button1);
        Button data_button2 = findViewById(R.id.data_button2);
        Button support_button1 = findViewById(R.id.support_button1);
        Button support_button2 = findViewById(R.id.support_button2);
        Button support_button3 = findViewById(R.id.support_button3);
        Button support_button4 = findViewById(R.id.support_button4);
        Button support_button6 = findViewById(R.id.support_button6);
        RelativeLayout layout = findViewById(R.id.setting5);
        Button feedback_button1 = findViewById(R.id.feedback_button1);
        Button feedback_button2 = findViewById(R.id.feedback_button2);
        switch1.setChecked(true);
        switch2.setChecked(true);
        switch3.setChecked(false);

        /**
         * 已经实现的功能
         */

        /**
         * 隐私条款
         */
        support_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrivacySettingActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 用户条款
         */
        support_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UseSettingActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 联系我们
         */
        support_button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactSettingActivity.class);
                startActivity(intent);
            }
        });


        /**
         * 未上线功能
         */

        /**
         * 导出数据
         */
        data_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 备份和恢复
         */
        data_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 发送反馈信息
         */
        feedback_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 求打分求分享
         */
        feedback_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 恢复购买
         */
        support_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 常见问题
         */
        support_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 版本
         */
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
        });

        Intent screenListener = new Intent(MainActivity.this, MyService2.class);
        screenListener.putExtra("UID",UID);
        startService(screenListener);
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
                String temp = "share test(AN).";
                showShare(temp);
                break;
            default:
        }
        return true;
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
        oks.show(this);
    }
}
