package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ThreeFragment extends Fragment {
    private Button button1;
    private WebView wv_fanChart2, wv_fanChart3;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View   view =inflater.inflate(R.layout.fragment_three, container, false);

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
        Button add=(Button)getActivity().findViewById(R.id.historyInformation);
        add.setOnClickListener(new historyListener());
    }

    class historyListener implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent();
            intent.setClass(getActivity(), threeDownActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
