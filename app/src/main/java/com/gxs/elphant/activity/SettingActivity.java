package com.gxs.elphant.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxs.elphant.R;
import com.gxs.elphant.base.BaseActivity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.util.SharedPreferencesUtil;

import butterknife.Bind;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_fankui)
    TextView tvFankui;
    @Bind(R.id.tv_get_open_source)
    TextView tvGetOpenSource;
    @Bind(R.id.tv_isOpenTinker)
    TextView tvIsOpenTinker;
    @Bind(R.id.clear_cache_ll)
    LinearLayout ll_OpenTinker;
    @Bind(R.id.tv_xieyi)
    TextView tvXieyi;
    @Bind(R.id.tv_exit)
    TextView tvExit;
    @Bind(R.id.version_tv)
    TextView versionTv;

    @Override
    protected void initData() {
        tvFankui.setOnClickListener(this);
        tvGetOpenSource.setOnClickListener(this);
        ll_OpenTinker.setOnClickListener(this);
        tvXieyi.setOnClickListener(this);
        tvExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO: 2017/5/11  TTTTTTTTTTTTTTTTTTTTTTTTTTTT
        switch (v.getId()){
            case R.id.tv_fankui:
                break;
        }
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("设置");
        boolean themeTag = SharedPreferencesUtil.getBoolean(this, Constant.ThemeSwitch, false);
        int color = Color.parseColor(themeTag ? "#979797" : "#00ACFF");
        mToolbar.setBackgroundColor(color);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
