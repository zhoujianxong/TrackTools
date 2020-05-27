package com.example.tracktools;

import android.app.Activity;

import com.blankj.ALog;
import com.example.tracktools.base.BaseApplication;

import java.util.ArrayList;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initALog();
    }

    public void initALog() {
        ALog.Config config = ALog.init(this)
                // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(BuildConfig.DEBUG)
                // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(true)
                // 设置 log 全局标签，默认为空
                .setGlobalTag(null)
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true)
                // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(false)
                // 当自定义路径为空时，写入应用的 /cache/log/ 目录中
                .setDir("")
                // 当文件前缀为空时，默认为 "alog"，即写入文件为 "alog-MM-dd.txt"
                .setFilePrefix("")
                // 输出日志是否带边框开关，默认开
                .setBorderSwitch(true)
                // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setSingleTagSwitch(false)
                // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setConsoleFilter(ALog.V)
                // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(ALog.V)
                // log 栈深度，默认为 1
                .setStackDeep(1)
                // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setStackOffset(0)
                // 设置日志可保留天数，默认为 -1 表示无限时长
                .setSaveDays(3)
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new ALog.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "ALog Formatter ArrayList { " + list.toString() + " }";
                    }
                });
        ALog.d(config.toString());
    }
}
