package com.gxs.elphant.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.elvishew.xlog.XLog;
import com.gxs.elphant.R;
import com.gxs.elphant.adapter.SplashViewPagerAdapter;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.util.SharedPreferencesUtil;
import com.gxs.elphant.widget.MyVideoView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.iv_splash_bg)
    ImageView iv_Bg;
    @Bind(R.id.vp_splash)
    ViewPager mViewPager;
    @Bind(R.id.ll_points)
    LinearLayout ll_Points;
    @Bind(R.id.btn_splash_enter)
    Button btn_Enter;
    @Bind(R.id.rl_splash_vp)
    RelativeLayout rl_content;
    private List<MyVideoView> videoViews;
    private int prePos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        boolean isfirstenter = SharedPreferencesUtil.getBoolean(this, Constant.ISFIRSTENTER, true);
        //设置美女图片是否显示
        iv_Bg.setVisibility(isfirstenter ? View.GONE : View.VISIBLE);
        rl_content.setVisibility(isfirstenter ? View.VISIBLE : View.GONE);
        if (isfirstenter) {
            //第一次进入 显示guid引导
            showGuid();
        } else {
            //不是第一次进入
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 3000);
        }
    }

    /**
     * 显示引导界面，几个小视频
     */
    private void showGuid() {
        //初始化集合数据
        int[] videos = {R.raw.splash_1, R.raw.splash_2, R.raw.splash_3, R.raw.splash_4};
        videoViews = new ArrayList<>();
        MyVideoView mVideoView;
        ImageView mPoint;
        for (int i = 0; i < videos.length; i++) {
            mVideoView = new MyVideoView(this);
            //如果你想对res/raw和assets文件写的动作，那就要得到相应uri，
            //android.resource://工程的包名/R.raw.XX
            mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + videos[i]));
            videoViews.add(mVideoView);
            //设置点
            mPoint = new ImageView(this);
            mPoint.setImageResource(R.mipmap.black);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(13, 13);
            params.leftMargin = 13;//设置间距
            mPoint.setLayoutParams(params);
            ll_Points.addView(mPoint);
        }
        SplashViewPagerAdapter mAdapter = new SplashViewPagerAdapter(videoViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
        //默认设置第一个页面播放
        videoViews.get(0).setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //当视频开始准备好的时候，开始播放视频
                videoViews.get(0).start();
            }
        });
        //设置第一点显示白色
        ((ImageView) ll_Points.getChildAt(0)).setImageResource(R.mipmap.white);
        btn_Enter.setOnClickListener(this);
    }

    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            //设置按钮是否显示
            btn_Enter.setVisibility(position == videoViews.size() - 1 ? View.VISIBLE : View.GONE);
            //这个非常麻烦，
//            setVideoView(position);
            if (prePos != position) {
                //停止播放前一个
                videoViews.get(prePos).stopPlayback();
                final MyVideoView curr_video = videoViews.get(position);
                //当视频开始准备好的时候，开始播放视频
                curr_video.start();
            }
            //设置前一个点为黑色
            ((ImageView) ll_Points.getChildAt(prePos)).setImageResource(R.mipmap.black);
            //设置当前点
            ((ImageView) ll_Points.getChildAt(position)).setImageResource(R.mipmap.white);
            //设置前一个点
            prePos = position;
        }

        //2界面移动的百分比
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    };

    /**
     * 当页面被选中的时候，设置VideoView
     *
     * @param position
     */
    private void setVideoView(int position) {
        if (position == prePos) {
            return;
        }
        //停止播放前一个
        VideoView pre_video = videoViews.get(prePos);
        try {
            Method method = pre_video.getClass().getDeclaredMethod("release", boolean.class);
            method.setAccessible(true);
            method.invoke(pre_video, true);
            XLog.i("释放了一下");
            //获取当前videoview
            final MyVideoView curr_video = videoViews.get(position);
            curr_video.start();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入主界面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //进入主界面
        startActivity(new Intent(this, MainActivity.class));
        SharedPreferencesUtil.saveBoolean(this, Constant.ISFIRSTENTER, false);
        finish();
    }
}
