package com.example.benjaminan.test2.AntiAddiction;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.benjaminan.test2.R;

/**
 * Created by T on 2017/12/17.
 */

public class TopLayout extends LinearLayout {

    public TopLayout(Context context){
        super(context,null);
    }
    public TopLayout(final Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.top,this);

    }

}
