package com.example.tracktools.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableLong;
import androidx.databinding.ViewDataBinding;

import com.blankj.ALog;
import com.example.tracktools.R;
import com.example.tracktools.utils.DensityUtil;
import com.example.tracktools.utils.PermissionsUtils;
import com.example.tracktools.utils.rx.RxBus;
import com.example.tracktools.utils.rx.RxBusCode;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.tracktools.utils.ButtonUtils.isFastDoubleClick;
/**
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseActivity<V extends ViewDataBinding, I extends Integer> extends RxAppCompatActivity {
    private String TAG = "BaseActivity";
    protected V binding;
    protected I layoutId;
    private CompositeDisposable mCompositeDisposable;
    protected BaseActivity mActivity;
    public int loadingCount = 0;
    private Dialog dialogLimit;//限制使用弹窗
    /***
     * 是否是展开状态
     */
    protected boolean isShowFloatWindow = false;
    /**
     * 解除限制倒计时（秒）
     **/
    ObservableLong mSecond = new ObservableLong(0);

    /**友盟统计的页面名称，可自定义**/
    protected String activityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        layoutId = createLayoutId();
        mActivity = this;
        setDensity();
        binding = DataBindingUtil.setContentView(this, layoutId);
        setWindow();
        if (binding != null) {
//            binding.getRoot().setBackgroundResource(R.drawable.bg_rc_write);
            //设置进入动画
//        binding.getRoot().startAnimation(AnimUtil.getScaleAnimRL());
        }
        activityName=mActivity.getClass().getName();
    }

    private void setDensity() {
        //获取设置的配置信息
        Configuration mConfiguration = this.getResources().getConfiguration();
        //获取屏幕方向
        int orientation = mConfiguration.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            DensityUtil.setDensity(getApplication(), this, 600);
        } else {
            DensityUtil.setDensity(getApplication(), this, 960);
        }
    }

    private void setWindow() {
        Window window = getWindow();
        //如果是5.0  以上  全部状态栏 透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#00000000"));
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //设置相应的  设计图  dp  比率
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //"横屏"
            DensityUtil.setDensity(getApplication(), this, 960);
        } else {
            // "竖屏"
            DensityUtil.setDensity(getApplication(), this, 600);
        }
    }

    /**
     * 初始化
     */
    protected abstract I createLayoutId();

    /**
     * 获取
     *
     * @param colorId
     */
    public int getBaseColor(int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //去掉activity跳转动画
        overridePendingTransition(0, 0);
//        if (Settings.canDrawOverlays(getApplicationContext()) && isShowFloatWindow) {
//            FloatWindowUtil.getInstance(BaseApplication.context).dismissWindow();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    /**
     * 添加activity里的订阅者 对订阅者统一管理,避免内存泄漏
     *
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解绑
     */
    public void unsubscribe() {
        if (this.mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            this.mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    /**
     * Activity跳转
     *
     * @param clz 要跳转的Activity的类名
     */
    public void startActivity(Class clz,int...id) {
        int ids=-0x10;
        if(id!=null&&id.length>0){
            ids=id[0];
        }
        if (isFastDoubleClick(ids)) {
            ALog.e("重复跳转不执行");
            return;
        }
        startActivity(new Intent(this, clz));
    }


    /**
     * Activity携带数据的跳转
     *
     * @param clz    要跳转的Activity的类名
     * @param bundle bundle
     */
    public void startActivity(Class clz, Bundle bundle,int...id) {
        int ids=-0x10;
        if(id!=null&&id.length>0){
            ids=id[0];
        }
        if (isFastDoubleClick(ids)) {
            ALog.e("重复跳转不执行");
            return;
        }
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onStop = true;
        Log.v(TAG, mActivity.getClass().getSimpleName() + "-onStop()");
    }

    /**
     * 当前界面是否是onStop状态，如息屏、跳转了新的界面。本界面都不应再接收弹窗消息
     **/
    private boolean onStop = false;
}
