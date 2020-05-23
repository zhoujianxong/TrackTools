package com.example.tracktools.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.ALog;
import com.example.tracktools.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseApplication extends Application implements Thread.UncaughtExceptionHandler, Application.ActivityLifecycleCallbacks {
    public static String FILE_PATH_PREFIX;
    public static Context context;
    private List<Activity> activities = new ArrayList<>();

    public static BaseApplication getInstance() {
        return application;
    }
    private static BaseApplication application;
    public static BaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        application = this;
        FILE_PATH_PREFIX = getFilesDir().getAbsolutePath();
        registerActivityLifecycleCallbacks(this);
        setSmartRefreshLayout();
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        //CrashHandler.handleCrash(e, "crash.log");
        Iterator<Activity> iterable = activities.iterator();
        while (iterable.hasNext()) {
            iterable.next().finish();
        }
        System.exit(0);
//        Util.restartApp(this);
    }
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activities.remove(activity);
    }


    /**
     * finish指定的activity
     * @param activityName
     */
    public void finishActivity(Class... activityName) {
        if(activityName==null||activities==null||activities.isEmpty()){
            return;
        }
        for (int j = 0; j <activities.size() ; j++) {
            Activity activity=activities.get(j);
            for (int i=0;i<activityName.length;i++){
                if(activityName[i].getSimpleName().equals(activity.getClass().getSimpleName())) {
                    if (!activity.isFinishing())
                    {
                        ALog.v(activity.getClass().getSimpleName()+" finish!!!");
                        activity.finish();
                        activities.remove(activity);
                        j--;
                    }
                }
            }
        }
    }

    /**
     *设置SmartRefreshLayout默认的header Footer样式，需要在布局生成之前设置，建议代码放在 Application 中
     */
    private  void setSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
                header.setPrimaryColorId(R.color.ui_activity_bg);
                header.setAccentColorId( R.color.ui_gray);
                header.setTextSizeTime(13);
                header.setTextSizeTitle(15);
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableLoadMoreWhenContentNotFull(true);//内容不满一页时候启用加载更多
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setTextSizeTitle(15);
                footer.setBackgroundResource(android.R.color.transparent);
                footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });
    }
}
