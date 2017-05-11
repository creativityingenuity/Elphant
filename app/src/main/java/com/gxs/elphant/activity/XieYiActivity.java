package com.gxs.elphant.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gxs.elphant.R;
import com.gxs.elphant.base.BaseActivity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.util.SharedPreferencesUtil;

import butterknife.Bind;

public class XieYiActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Override
    protected void initData() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    protected void initView() {
        mToolbar.setTitle("隐私协议");
        boolean themeTag = SharedPreferencesUtil.getBoolean(this, Constant.ThemeSwitch, false);
        int color = Color.parseColor(themeTag ? "#979797" : "#00ACFF");
        mToolbar.setBackgroundColor(color);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_xie_yi;
    }
}
