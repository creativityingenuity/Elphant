package com.gxs.elphant.util;

import android.content.Context;

import com.gxs.elphant.adapter.ProgressSubscriber;
import com.gxs.elphant.api.APIService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 网络请求框架工具类，配置了rx+ok+retrofit
 */
public class HttpClientUtil {
    private HttpClientUtil() {
        initOkhttp();
        initRetrofit();
        //初始化retrofit接口
        if (mApiService == null) {
            mApiService = mRetrofit.create(APIService.class);
        }
    }

    private volatile static HttpClientUtil mInstance = null;

    public static HttpClientUtil getInstance() {
        if (mInstance == null) {
            synchronized (HttpClientUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpClientUtil();
                }
            }
        }
        return mInstance;
    }

    //服务器地址
    private static String BASE_URL = "";
    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient = null;
    private APIService mApiService;
    //默认最大缓存时间4周
    private int maxCacheTime = 60 * 60 * 24 * 28;
    private static Context mContext;

    public static void init(Context context, String baseUrl) {
        mContext = context;
        BASE_URL = baseUrl;
    }

    /**
     * 设置最大缓存时间
     *
     * @param maxCacheTime
     */
    public void setMaxCacheTime(int maxCacheTime) {
        this.maxCacheTime = maxCacheTime;
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private void initOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //缓存处理
        Cache cache = new Cache(
                new File(mContext.getExternalCacheDir(), "httpCache"),//缓存路径
                1024 * 1024 * 50//缓存大小50M
        );
        //添加拦截器 控制缓存的过期时间
        Interceptor cacheInterceptor = new Interceptor() {
            /**
             * 1.在拦截器内做Request拦截操作，在每个请求发出前，判断一下网络状况，如果没问题继续访问，如果有问题，则设置从本地缓存中读取
             * 2.接下来是设置Response，先判断网络，网络好的时候，移除header后添加cache失效时间为0小时，网络未连接的情况下设置缓存时间为4周
             */
            @Override
            public Response intercept(Chain chain) throws IOException {
                //1.拦截request，在每个请求发出前，进行拦截，然后判断是否有网，若么有网，直接走缓存
                Request request = chain.request();
                //如果没有网络,直接走缓存
                if (!NetUtils.checkNet(mContext)) {//判断网络连接状况
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)//CacheControl类，负责缓存策略的管理
                            .build();
                }
                //2.设置Response
                Response response = chain.proceed(request);
                if (NetUtils.checkNet(mContext)) {
                    int maxAge = 0;
                    //有网时 设置缓存超时时间0小时
                    response.newBuilder()
                            //max-age 控制缓存的最大生命时间
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    //无网时 设置超时为4周
                    response.newBuilder()
                            //max-stale 这个是控制缓存的过时时间
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxCacheTime)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }

        };
        builder.cache(cache);
        builder.interceptors().add(cacheInterceptor);//添加本地缓存拦截器，用来拦截本地缓存
        builder.networkInterceptors().add(cacheInterceptor);//添加网络拦截器，用来拦截网络数据

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        //错误重连
        builder.retryOnConnectionFailure(true);

        //以上设置结束，才能build(),不然设置白搭
        mOkHttpClient = builder.build();
    }

    /**
     * 获取接口
     *
     * @return
     */
    public APIService getApiService() {
        if (mApiService == null && mRetrofit != null) {
            mApiService = mRetrofit.create(APIService.class);
        }
        return mApiService;
    }

    public <T> void getServiceData(Observable<T> o, final ProgressSubscriber<T> p) {
        o
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示进度条
                        p.preTask();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p);
    }

}
