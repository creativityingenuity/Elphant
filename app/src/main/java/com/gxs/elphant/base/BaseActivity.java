package com.gxs.elphant.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gxs.elphant.Elephant;

import butterknife.ButterKnife;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected static int mTheme = -1;
    protected Elephant elephant ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mTheme!=-1){
            setTheme(mTheme);
        }
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        elephant = (Elephant) getApplication();
        elephant.addActivity(this);
        //初始化界面控件
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        elephant.removeActivity(this);
    }

    protected abstract void initData();

    protected abstract void initView();
    /**
     * 设置布局ID
     * @return
     */
    protected abstract int setLayoutId();
}
