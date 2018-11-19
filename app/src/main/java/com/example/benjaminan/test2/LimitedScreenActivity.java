package com.example.benjaminan.test2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminan.test2.AntiAddiction.ActivityCollector;
import com.example.benjaminan.test2.AntiAddiction.BaseActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LimitedScreenActivity extends BaseActivity {

    public static final String TAG = "Lancher";
    private TextView GuangLT1;
    private TextView GuangLT2;

    public static final int DISABLE_EXPAND = 0x00010000;//4.2以上的整形标识
    public static final int DISABLE_EXPAND_LOW = 0x00000001;//4.2以下的整形标识
    public static final int DISABLE_NONE = 0x00000000;//取消StatusBar所有disable属性，即还原到最最原始状态

    // static SharedPreferences pref;
    // static SharedPreferences.Editor editor;

    static AssetManager mgr;
    static Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited_screen);
        //editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        //pref=getSharedPreferences("data",MODE_MULTI_PROCESS);

        banStatusBar();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }//隐藏原始标题

        //加载app应用。
        loadApps();
        GridView gridView = (GridView) findViewById(R.id.apps_list);
        //设置默认适配器。
        mContent = getApplicationContext();
        mResources = getResources();
        gridView.setAdapter(new AppsAdapter());

        //

        gridView.setOnItemClickListener(clickListener);


        GuangLT1=findViewById(R.id.GuangLT1);
        GuangLT2=findViewById(R.id.GuangLT2);
        GuangLT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Long TempEndA=LockScreenActivity.pref.getLong("tempendtC",0);
                if(TempEndA==0){
                    ActivityCollector.finishAll();
                }
                else{
                    long middleTM=System.currentTimeMillis();
                    float remain=(TempEndA-middleTM)/1000/60;
                    int remain2=(int)remain;

                    Toast.makeText(LimitedScreenActivity.this, "剩余"+remain2+"分钟", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mgr=getAssets();
        typeface= Typeface.createFromAsset(mgr,"fonts/qi.ttf");

        GuangLT2.setTypeface(typeface);

    }

    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ResolveInfo info = apps.get(i);
            //该应用的包名
            String pkg = info.activityInfo.packageName;//GAIDONG
            //应用的主activity类
            String cls = info.activityInfo.name;//GAIDONG
            ComponentName componet = new ComponentName(pkg, cls);
            Intent intent = new Intent();
            intent.setComponent(componet);
            startActivity(intent);
        }
    };

    protected List<ResolveInfo> apps = new ArrayList<ResolveInfo>();
    private List<ResolveInfo> apps2 = new ArrayList<ResolveInfo>();
    private Resources mResources;
    private Context mContent;

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        new ImageView(LimitedScreenActivity.this);

        PackageManager packageManager = getPackageManager();
        apps2 = packageManager.queryIntentActivities(mainIntent, 0);
        if (apps2 != null) {
            for (int position= 0; position < apps2.size(); ++position) {
                ResolveInfo appInfo = apps2.get(position);
                String pkgName = appInfo.activityInfo.packageName;
                if(pkgName.equals("com.taobao.taobao"))
                    continue;
                PackageInfo mPackageInfo = null;
                try {
                    mPackageInfo = packageManager.getPackageInfo(pkgName, 0);
                }catch (PackageManager.NameNotFoundException e){

                }
                if(mPackageInfo != null)
                    if ((mPackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0)
                        apps.add(appInfo);

                //Log.v(TAG, resolveInfo.toString());
                /*if ((resolveInfo.activityInfo.packageName=="相机"||
                        resolveInfo.activityInfo.packageName=="信息"||
                        resolveInfo.activityInfo.packageName=="拨号")) {
                    apps.add(resolveInfo);
                }*/
            }
        }
    }

    public class AppsAdapter extends BaseAdapter {

        public AppsAdapter() {
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int i) {
            return apps.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ResolveInfo info = apps.get(i);

            View convertView = LayoutInflater.from(mContent).inflate(R.layout.text_img_view, null);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            TextView text = (TextView) convertView.findViewById(R.id.text);
            //设置文字和图片。
            text.setText(info.loadLabel(getPackageManager()));

            image.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));//改动

            // convertView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            //使用dp进行参数设置。进行分辨率适配。
            convertView.setLayoutParams(new GridView.LayoutParams(
                    (int) mResources.getDimension(R.dimen.app_width),
                    (int) mResources.getDimension(R.dimen.app_height)));
            //返回一个图文混合。
            return convertView;
        }
    }
    @Override
    public void onBackPressed() {

    }

    private void unBanStatusBar() {//利用反射解除状态栏禁止下拉
        Object service = getSystemService("statusbar");
        Log.e("unBanStatusBar", service.toString());
        try {
            Class<?> statusBarManager = Class.forName
                    ("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod("disable", int.class);
            expand.invoke(service, DISABLE_NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatusBarDisable(int disable_status) {//调用statusBar的disable方法
        Object service = getSystemService("statusbar");
        Log.e("setStatusBarDisable", service.toString());
        try {
            Class<?> statusBarManager = Class.forName
                    ("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod("disable", int.class);
            expand.invoke(service, disable_status);
        } catch (Exception e) {
            unBanStatusBar();
            e.printStackTrace();
        }
    }

    private void banStatusBar() {//禁止statusbar下拉，适配了高低版本
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        Log.e("banStatusBar", String.valueOf(currentApiVersion));
        if (currentApiVersion <= 16) {
            setStatusBarDisable(DISABLE_EXPAND_LOW);
        } else {
            setStatusBarDisable(DISABLE_EXPAND);
        }
    }
}
