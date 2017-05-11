package com.gxs.elphant;

import android.app.Application;
import android.content.Context;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.gxs.elphant.util.HttpClientUtil;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public class Elephant extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化Xlog
        XLog.init(LogLevel.ALL, new LogConfiguration.Builder().b().build());
        //初始化httpclient
        HttpClientUtil.init(this,"http://v.juhe.cn/");

    }
    public static Context getContext() {
        return mContext;
    }
}
