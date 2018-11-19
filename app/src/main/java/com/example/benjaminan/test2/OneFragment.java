package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.view.animation.Animation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.benjaminan.test2.EventBus.EventUID;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Http.HttpUtlis;
import butterknife.ButterKnife;

public class OneFragment extends Fragment {

    private int usingTime[] = {0,0,0,0,0,0,0,1};
    private String UID;
    private WebView wv_fanChart;
    TextView utility_time, main5_background_12, main5_background_52;
    private boolean isGetData = false;

    public OneFragment() {
        // Required empty public constructor
    }

    private ImageButton anti;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);

        //注冊EventBus
        EventBus.getDefault().register(this);

        /*------------------TextView utility_time（今日使用时长数据）------------------*/
        utility_time = (TextView) view.findViewById(R.id.utility_time);
        utility_time.setText("0M");
        /*----------------------------------------------------------------------------*/

        /*----------------TextView main5_background_12（查看次数数据）----------------*/
        main5_background_12 = (TextView) view.findViewById(R.id.main5_background_12);
        main5_background_12.setText("0");
        /*---------------------------------------------------------------------------*/

        /*----------------TextView main5_background_32（查看间隔数据）----------------*/
        TextView main5_background_32 = (TextView) view.findViewById(R.id.main5_background_32);
        main5_background_32.setText("6min");
        /*---------------------------------------------------------------------------*/

        /*----------------TextView main5_background_52（单次最长数据）----------------*/
        main5_background_52 = (TextView) view.findViewById(R.id.main5_background_52);
        main5_background_52.setText("0m");
        /*---------------------------------------------------------------------------*/

        /*-----------------TextView main6_background_2（显示当前日期）-----------------*/
        TextView main6_background_2 = (TextView) view.findViewById(R.id.main6_background_2);
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        main6_background_2.setText(simpleDateFormat.format(date));
        /*----------------------------------------------------------------------------*/

        anti = view.findViewById(R.id.main8);
        anti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), AntiAddictionActivity.class));
            }
        });

        wv_fanChart = (WebView) view.findViewById(R.id.wv_fanChart);
        WebSettings settings = wv_fanChart.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        wv_fanChart.setBackgroundColor(0);
        //wv_fanChart.setBackgroundColor(0); // 设置背景色
        //wv_fanChart.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        wv_fanChart.loadUrl("file:///android_asset/barChart.html");
        // "#2dc9d7"
        wv_fanChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:drawBarChart('[0, 0, 0, 0, 0, 0, 0, 0, 0]','[\"0點\", \"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]')");
            }
        });

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端
            //Log.e("onHiddenChanged",UID );
       }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(EventUID event){
        UID = event.getMsg();
        initData();
        Log.e("1", UID );
        setWebView();
    }

    private void setWebView() {
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
                Log.e("getCounter",temp);
                return temp;
            }

            @Override
            protected void onPostExecute(String response) {
                String[] aa = response.split("\\.");
                for(int i = 0; i < 8; ++i)
                    usingTime[i] = Integer.valueOf(aa[i]);
                wv_fanChart.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        String temp1 = "javascript:drawBarChart('[" + String.valueOf(usingTime[0])+ ", " + String.valueOf(usingTime[1])+ ", "
                                + String.valueOf(usingTime[2])+ ", " + String.valueOf(usingTime[3])+ ", "
                                + String.valueOf(usingTime[4])+ ", " + String.valueOf(usingTime[5])+ ", "
                                + String.valueOf(usingTime[6])+ ", " + String.valueOf(usingTime[7])
                                + "]','[\"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]')";
                        Log.e("count1",temp1);
                        System.out.print(usingTime[5]);
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
        String url0 = "http://123.207.36.58/searchUsing.php?type=time&date=";
        String url10 = "http://123.207.36.58/searchUsing.php?type=getMax&date=";
        String url1 = "http://123.207.36.58/searchUsing.php?type=counter&date=";
        String url2 = "&start_time=";
        String url3 = "&end_time=";
        String url4 = "&UID=" + UID;


        String url = url0 + fYear.format(start) + url2 + "000000" + url3 + "235959" + url4;
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                Log.e("getAllUsing",temp);
                int min = Integer.valueOf(temp);
                if(min%60 != 0)
                    min = min/60 + 1;
                else
                    min /= 60;
                return String.valueOf(min);
            }
            @Override
            protected void onPostExecute(String response) {
                utility_time.setText(response + "min");
                super.onPostExecute(response);
            }
        }.execute(url);

        url = url1 + fYear.format(start) + url2 + "000000" + url3 + "235959" + url4;
        new AsyncTask<String, Float, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpUtlis http = new HttpUtlis();
                String temp = http.getRequest(params[0], "utf-8");
                Log.e("getAllCounter",temp);
                return temp;
            }
            @Override
            protected void onPostExecute(String response) {
                main5_background_12.setText(response);
                super.onPostExecute(response);
            }
        }.execute(url);

        url = url10 + fYear.format(start) + url2 + "000000" + url3 + "235959" + url4;
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
                main5_background_52.setText(response + "min");
                super.onPostExecute(response);
            }
        }.execute(url);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) { //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            if(UID != null){

                Log.e("2", UID );
                initData();
                setWebView();
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
                Log.e("3", UID );
                setWebView();
            }
            isGetData = true;
        }
        super.onResume();
    }

    private static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}

