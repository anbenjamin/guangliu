package details;

/**
 * Created by BenjaminAn on 2018/10/3.
 */

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class AppInfo implements Comparable<AppInfo>{

    private String appName;
    private String packageName;
    private Drawable icon;
    private long time = 0;

    public AppInfo(){}
    public AppInfo(String packageName){this.packageName = packageName;}
    public void setAppName(String appName){this.appName = appName;}
    public void setPackageName(String packageName){this.packageName = packageName;}
    public void setIcon(Drawable icon){this.icon = icon;}
    public void setTime(long time){this.time = time;}

    public String getAppName(){
        if(appName == null)
            return "";
        else
            return appName;
    }
    public String getPackageName(){
        if(packageName == null)
            return "";
        else
            return packageName;
    }
    public long getTime(){return time;}

    public Drawable getIcon(){return icon;}

    @Override
    public int compareTo(@NonNull AppInfo s) {
        if(this.time >= s.getTime()){
            return -1;
        }
        return 1;
    }
}


