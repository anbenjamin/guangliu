package com.example.benjaminan.test2;

/**
 * Created by BenjaminAn on 2018/9/26.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import myControls.CircleSeekBar;

public class FiveFragment extends Fragment {

    public FiveFragment() {
        // Required empty public constructor
    }

    private CircleSeekBar mCircleProgress1;
    private CircleSeekBar mCircleProgress2;
    public TextView spareTime;
    public TextView open1;  //设置额度后面的开关控制
    public RelativeLayout seniorSetting;
    int mSeletedIndex;  //从numberpicker即时间额度中选择的值确定后传给这个变量
    String txt1;
    int used=10;
    int total=20;
    private String textString;

    private String[] numbers = {"10分钟","20分钟","30分钟","40分钟","50分钟",
            "1h","70分钟","80分钟","90分钟","100分钟","110分钟","2h",
            "130分钟","140分钟","150分钟", "160分钟","170分钟","3h",
            "190分钟","200分钟","210分钟","220分钟", "230分钟","4h","250分钟",
            "260分钟","270分钟","280分钟","290分钟","5h","310分钟","320分钟",
            "330分钟","340分钟","350分钟", "6h","370分钟","380分钟",
            "390分钟","400分钟","410分钟","7h"};
    //设置需要显示的内容数组
    private NumberPicker numberPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        ButterKnife.bind(this, view);

        mCircleProgress2 = (CircleSeekBar) view.findViewById(R.id.progress2);
        spareTime = (TextView)view.findViewById(R.id.spareTime);
        seniorSetting = view.findViewById(R.id.seniorSetting);
        open1 = view.findViewById(R.id.open1);
        View layout1 = view.findViewById(R.id.limitedTime);
        used=10;     //已用时间，暂设为规定值，需要从后台获取
        total=10;       //可用时间，即设置额度的值
        textString=used+" "+"/"+" "+total;
        spareTime.setText(textString);

        float x=(float) 1.0*used/total*100;
        mCircleProgress2.setProgress(x);

        seniorSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SeniorWarningActivity.class);
                startActivity(intent);
            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder7 = new AlertDialog.Builder(getActivity());
                builder7.setTitle("选择额度");
                builder7.setIcon(R.drawable.choose);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.number_picker, null);
                numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
                //设置不可编辑
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                //数据颜色的渐变
                LinearGradient linearGradient = new LinearGradient(0, 0, 50, 50,
                        getResources().getIntArray(R.array.color1), null,
                        Shader.TileMode.MIRROR);
                //
                numberPicker.setDisplayedValues(numbers);
                //设置最大最小值
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(numbers.length);
                //设置默认的位置
                numberPicker.setValue(1);

                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mSeletedIndex = newVal;
                    }
                });


                builder7.setView(view);
                builder7.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                total=mSeletedIndex*10;
                                textString=used+" "+"/"+" "+total;
                                spareTime.setText(textString);
                                mCircleProgress2.setProgress((float) used/total*100);
                                open1.setText(total+"min");

                            }
                        });
                builder7.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Toast.makeText(getActivity(), "you choosed cancel", Toast.LENGTH_LONG).show();
                            }
                        });
                builder7.create();
                builder7.show();

            }
        });

        return view;
    }
}
