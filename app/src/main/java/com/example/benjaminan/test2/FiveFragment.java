package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminan.test2.settingPages.ContactSettingActivity;
import com.example.benjaminan.test2.settingPages.PrivacySettingActivity;
import com.example.benjaminan.test2.settingPages.UseSettingActivity;

import butterknife.ButterKnife;

public class FiveFragment extends Fragment {
    public FiveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        ButterKnife.bind(this, view);

        Switch switch1 =  view.findViewById(R.id.setting_checkbox1);
        Switch switch2 =  view.findViewById(R.id.setting_checkbox2);
        Switch switch3 =  view.findViewById(R.id.setting_checkbox3);
        Button data_button1 = view.findViewById(R.id.data_button1);
        Button data_button2 = view.findViewById(R.id.data_button2);
        Button support_button1 = view.findViewById(R.id.support_button1);
        Button support_button2 = view.findViewById(R.id.support_button2);
        Button support_button3 = view.findViewById(R.id.support_button3);
        Button support_button4 = view.findViewById(R.id.support_button4);
        Button support_button6 = view.findViewById(R.id.support_button6);
        RelativeLayout layout = view.findViewById(R.id.setting5);
        Button feedback_button1 = view.findViewById(R.id.feedback_button1);
        Button feedback_button2 = view.findViewById(R.id.feedback_button2);
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
                Intent intent = new Intent(getActivity(), PrivacySettingActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 用户条款
         */
        support_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UseSettingActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 联系我们
         */
        support_button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactSettingActivity.class);
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
                Toast.makeText(getActivity(), "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 备份和恢复
         */
        data_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 发送反馈信息
         */
        feedback_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 求打分求分享
         */
        feedback_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 恢复购买
         */
        support_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 常见问题
         */
        support_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能还未上线", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 版本
         */
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
        });



        /**
         * 返回实现
         */

        return view;
    }
}
