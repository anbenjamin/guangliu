package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.benjaminan.test2.EventBus.EventUID;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Http.HttpUtlis;

public class ThreeFragment extends Fragment {
    private Button button1;
    private WebView wv_fanChart2, wv_fanChart3;
    private TextView statistic1,statistic2,statistic3;
    private String UID;
    private int usingTime[] = {0,0,0,0,0,0,0,1};
    private int usingCount[] = {0,0,0,0,0,0,0,1};
    private boolean isGetData = false;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View   view =inflater.inflate(R.layout.fragment_three, container, false);

        statistic1 = view.findViewById(R.id.three_up_one);
        statistic2 = view.findViewById(R.id.three_up_two);
        statistic3 = view.findViewById(R.id.three_up_three);

        //注冊EventBus
        EventBus.getDefault().register(this);

        wv_fanChart2 = (WebView) view.findViewById(R.id.wv_fanChart2);
        WebSettings settings2 = wv_fanChart2.getSettings();
        settings2.setJavaScriptEnabled(true);
        settings2.setJavaScriptCanOpenWindowsAutomatically(true);
        wv_fanChart2.setBackgroundColor(0);
        //wv_fanChart.setBackgroundColor(0); // 设置背景色
        //wv_fanChart.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        wv_fanChart2.loadUrl("file:///android_asset/barChart.html");
        // "#2dc9d7"
        wv_fanChart2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:drawBarChart('[0, 0, 0, 0, 0, 0, 0, 0, 0]','[\"0點\", \"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]')");
            }
        });

        wv_fanChart3 = (WebView) view.findViewById(R.id.wv_fanChart3);
        WebSettings settings3 = wv_fanChart3.getSettings();
        settings3.setJavaScriptEnabled(true);
        settings3.setJavaScriptCanOpenWindowsAutomatically(true);
        wv_fanChart3.setBackgroundColor(0);
        //wv_fanChart.setBackgroundColor(0); // 设置背景色
        //wv_fanChart.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        wv_fanChart3.loadUrl("file:///android_asset/barChart.html");
        // "#2dc9d7"
        wv_fanChart3.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:drawBarChart('[0, 0, 0, 0, 0, 0, 0, 0, 0]','[\"0點\", \"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]')");
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Button add=(Button)getActivity().findViewById(R.id.historyInformation);
        //add.setOnClickListener(new historyListener());
    }

    class historyListener implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent();
            intent.setClass(getActivity(), threeDownActivity.class);
            getActivity().startActivity(intent);
        }
    }

    @Subscribe
    public void onEventMainThread(EventUID event){
        UID = event.getMsg();
        setWebView1();
        setWebView2();
        initData();
    }

    private void setWebView1() {
        String url1 = "http://123.207.36.58/searchUsing.php?type=time&date=";
        String url2 = "&start_time=";
        String url3 = "&end_time=";
        String url4 = "&UID=" + UID;
        long currentTime = System.currentTimeMillis();
        Date start = new Date(currentTime);
        SimpleDateFormat fYear = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fTime = new SimpleDateFormat("HHmmss");
        start.setSeconds(0);
        start.setMinutes(0);
        //start.setHours(i * 3);
        String url = url1 + fYear.format(start) ;//+ url2 + fTime.format(start);
        if (start.getHours() == 21) {
            start.setSeconds(59);
            start.setMinutes(59);
            start.setHours(23);
        }
        else
            start.setHours(start.getHours() + 3);
        url +=  url4;// + url3 + fTime.format(start);
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                return String.valueOf(temp);
            }

            @Override
            protected void onPostExecute(String response) {
                String[] aa = response.split("\\.");
                for(int i = 0; i < 8; ++i) {
                    usingTime[i] = Integer.valueOf(aa[i]);
                    if(usingTime[i]%60 != 0)
                        usingTime[i] = usingTime[i]/60 + 1;
                    else
                        usingTime[i] /= 60;
                }
                wv_fanChart2.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        String temp1 = "javascript:drawBarChart('[" + String.valueOf(usingTime[0])+ ", " + String.valueOf(usingTime[1])+ ", "
                                + String.valueOf(usingTime[2])+ ", " + String.valueOf(usingTime[3])+ ", "
                                + String.valueOf(usingTime[4])+ ", " + String.valueOf(usingTime[5])+ ", "
                                + String.valueOf(usingTime[6])+ ", " + String.valueOf(usingTime[7])
                                + "]','[\"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]','單位：分鐘')";
                        Log.e("count1",temp1);
                        view.loadUrl(temp1);
                    }
                });
                super.onPostExecute(response);
            }
        }.execute(url);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端

        }
    }

    private void setWebView2() {
        String url1 = "http://123.207.36.58/searchUsing.php?type=counter&date=";
        String url2 = "&start_time=";
        String url3 = "&end_time=";
        String url4 = "&UID=" + UID;
        long currentTime = System.currentTimeMillis();
        Date start = new Date(currentTime);
        SimpleDateFormat fYear = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fTime = new SimpleDateFormat("HHmmss");
        start.setSeconds(0);
        start.setMinutes(0);
        //start.setHours(i * 3);
        String url = url1 + fYear.format(start) ;//+ url2 + fTime.format(start);
        if (start.getHours() == 21) {
            start.setSeconds(59);
            start.setMinutes(59);
            start.setHours(23);
        }
        else
            start.setHours(start.getHours() + 3);
        url +=  url4;// + url3 + fTime.format(start);
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                return temp;
            }

            @Override
            protected void onPostExecute(String response) {
                String[] aa = response.split("\\.");
                for(int i = 0; i < 8; ++i)
                    usingCount[i] = Integer.valueOf(aa[i]);
                wv_fanChart3.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        String temp1 = "javascript:drawBarChart('[" + String.valueOf(usingCount[0])+ ", " + String.valueOf(usingCount[1])+ ", "
                                + String.valueOf(usingCount[2])+ ", " + String.valueOf(usingCount[3])+ ", "
                                + String.valueOf(usingCount[4])+ ", " + String.valueOf(usingCount[5])+ ", "
                                + String.valueOf(usingCount[6])+ ", " + String.valueOf(usingCount[7])
                                + "]','[\"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]')";
                        Log.e("count1",temp1);
                        view.loadUrl(temp1);
                    }
                });
                super.onPostExecute(response);
            }
        }.execute(url);
    }

    private void initData(){
        long currentTime = System.currentTimeMillis();
        Date start = new Date(currentTime);
        SimpleDateFormat fTime = new SimpleDateFormat("HHmmss");
        SimpleDateFormat fYear = new SimpleDateFormat("yyyy-MM-dd");
        String url1 = "http://123.207.36.58/searchStatistic.php?type=max_count&date=&UID=" + UID;
        String url2 = "http://123.207.36.58/searchStatistic.php?type=min_count&date=&UID=" + UID;
        String url3 = "http://123.207.36.58/searchStatistic.php?type=time_date&UID=" + UID + "&date=";

        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                Log.e("getMaxCount",temp);
                int min = Integer.valueOf(temp);
                //if(min%60 != 0)
                //    min = min/60 + 1;
                //else
                //    min /= 60;
                return String.valueOf(min);
            }
            @Override
            protected void onPostExecute(String response) {
                statistic1.setText("您查看手机最多的那天查看了" + response + "次");
                super.onPostExecute(response);
            }
        }.execute(url1);

        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                Log.e("getMinCount",temp);
                return temp;
            }
            @Override
            protected void onPostExecute(String response) {
                statistic2.setText("您查看手机最少的那天查看了" + response + "次");
                super.onPostExecute(response);
            }
        }.execute(url2);

        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日期减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        url3 += date.format(lastDay);
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                Log.e("getMaxTime",temp);
                int min = Integer.valueOf(temp);
                if(min%60 != 0)
                    min = min/60 + 1;
                else
                    min /= 60;
                return String.valueOf(min);
            }
            @Override
            protected void onPostExecute(String response) {
                Log.e("getMaxTime",response);
                statistic3.setText("您昨天使用手机" + response + "分钟");
                super.onPostExecute(response);
            }
        }.execute(url3);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) { //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            if(UID != null){
                initData();
            }
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    @Override
    public void onResume() {
        if (!isGetData) { //   这里可以做网络请求或者需要的数据刷新操作
            if(UID != null){
                initData();
            }
            isGetData = true;
        }
        super.onResume();
    }
}
