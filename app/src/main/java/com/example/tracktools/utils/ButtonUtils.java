package com.example.tracktools.utils;

import android.util.Log;

/**
 * Created by yzh 2017年11月16日10:40:34
 */
public class ButtonUtils {
    private static long lastClickTime = 0;
    private static long DIFF = 500;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于500，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于500，则认为是多次无效点击
     * @param buttonId 根据控件的id 来判断
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            Log.v("isFastDoubleClick", buttonId+" 短时间内事件多次触发");
            lastClickTime = time;
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }

}