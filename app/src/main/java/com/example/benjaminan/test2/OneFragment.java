package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;

public class OneFragment extends Fragment {


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);

                /*------------------TextView utility_time（今日使用时长数据）------------------*/
        TextView utility_time = (TextView) view.findViewById(R.id.utility_time);
        utility_time.setText("13M");
        /*----------------------------------------------------------------------------*/

        /*----------------TextView main5_background_12（查看次数数据）----------------*/
        TextView main5_background_12 = (TextView) view.findViewById(R.id.main5_background_12);
        main5_background_12.setText("5");
        /*---------------------------------------------------------------------------*/

        /*----------------TextView main5_background_32（查看间隔数据）----------------*/
        TextView main5_background_32 = (TextView) view.findViewById(R.id.main5_background_32);
        main5_background_32.setText("7m");
        /*---------------------------------------------------------------------------*/

        /*----------------TextView main5_background_52（单次最长数据）----------------*/
        TextView main5_background_52 = (TextView) view.findViewById(R.id.main5_background_52);
        main5_background_52.setText("41m");
        /*---------------------------------------------------------------------------*/

        /*-----------------TextView main6_background_2（显示当前日期）-----------------*/
        TextView main6_background_2 = (TextView) view.findViewById(R.id.main6_background_2);
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        main6_background_2.setText(simpleDateFormat.format(date));
        /*----------------------------------------------------------------------------*/

        WebView wv_fanChart = (WebView) view.findViewById(R.id.wv_fanChart);
        wv_fanChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:drawBarChart('[45, 23, 23, 10, 25, 60, 5, 30, 12]','[\"0點\", \"3點\", \"6點\", \"9點\", \"12點\", \"15點\", \"18點\", \"21點\", \"24點\"]')");
            }
        });
        WebSettings settings = wv_fanChart.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        wv_fanChart.setBackgroundColor(0);
        //wv_fanChart.setBackgroundColor(0); // 设置背景色
        //wv_fanChart.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        wv_fanChart.loadUrl("file:///android_asset/barChart.html");
        // "#2dc9d7"

        return view;
    }

}

