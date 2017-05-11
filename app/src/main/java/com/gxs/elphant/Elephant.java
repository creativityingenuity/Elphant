package com.gxs.elphant;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.gxs.elphant.base.BaseActivity;
import com.gxs.elphant.util.HttpClientUtil;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

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
        init_Tinker();
    }
    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }
    /**
     * 初始化TInker
     */
    private void init_Tinker() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
           ApplicationLike tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3);
            // 获取当前的补丁版本
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());

            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }

    public static Context getContext() {
        return mContext;
    }

    private List<BaseActivity> activities = new ArrayList<>();
    public void addActivity(BaseActivity activity){
        if(!activities.contains(activity)){
            activities.add(activity);
        }
    }

    public void removeActivity(BaseActivity activity){
        activities.remove(activity);
    }

    public void exit(){
        if(activities!=null&&activities.size()>0) {
            for (BaseActivity baseActivity : activities) {
                baseActivity.finish();
            }
        }
    }
}
