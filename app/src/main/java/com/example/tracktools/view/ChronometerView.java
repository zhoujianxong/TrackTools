package com.example.tracktools.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.tracktools.R;
import com.example.tracktools.base.BaseApplication;
import com.example.tracktools.utils.CommonUtils;

public class ChronometerView extends RelativeLayout {

    private Chronometer chronometer;
    private ImageView imageView;
    private ObjectAnimator rotate;

    public ChronometerView(Context context) {
        super(context);
        init();
    }

    public ChronometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChronometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChronometerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        chronometer = new Chronometer(getContext());
        chronometer.setTextColor(ContextCompat.getColor(getContext(),R.color.colorTime));
        chronometer.setTextSize(CommonUtils.sp2px(6));
        addView(chronometer);
        imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.time_bg);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(imageView);
        ((LayoutParams) chronometer.getLayoutParams()).addRule(CENTER_IN_PARENT);
        LayoutParams imageParams= ((LayoutParams) imageView.getLayoutParams());
        imageParams.height= CommonUtils.sp2px(45);
        imageParams.width=CommonUtils.sp2px(45);
        imageParams.addRule(CENTER_IN_PARENT);
        //旋转
        rotate = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f).setDuration(5000);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(ValueAnimator.INFINITE);


    }

    public void start(){
        chronometer.start();
        rotate.start();
    }

    public void stop(){
        chronometer.stop();
        rotate.cancel();
    }



}
