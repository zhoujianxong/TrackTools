package com.example.tracktools.utils;

import android.content.Context;

import com.example.tracktools.base.BaseApplication;

public class CommonUtils {
    public static int sp2px(float spValue) {
        final float fontScale = BaseApplication.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
