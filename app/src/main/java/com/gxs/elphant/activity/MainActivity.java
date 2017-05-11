package com.gxs.elphant.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.elvishew.xlog.XLog;
import com.gxs.elphant.R;
import com.gxs.elphant.adapter.HomeViewPagerAdapter;
import com.gxs.elphant.base.BaseActivity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.util.SharedPreferencesUtil;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.gxs.elphant.R.id.toolbar;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_PHONE = 100;
    private static final int REQUEST_CODE = 1001;
    @Bind(toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigationview)
    NavigationView mNavigationview;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerlayout;
    @Bind(R.id.toolbar_title)
    TextView mToolbarTitle;
    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabStrip;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;

    private SwitchCompat mThemeSwitch;
    private HomeViewPagerAdapter adapter;
    private boolean themeTag;
    private int color;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initView() {
        initToolBar();
        initViewPager();
    }

    private void initViewPager() {
        if (adapter == null) {
            adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
            mViewpager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        //初始化标签联动效果
        initPagerSlidingTabStrip();
    }

    private void initPagerSlidingTabStrip() {
        //设置滑块变量
        mTabStrip.setIndicatorHeight(5);
        //颜色
        mTabStrip.setIndicatorColor(color);
        //分割线程
        mTabStrip.setUnderlineHeight(2);
        mTabStrip.setUnderlineColor(color);
        //设置Tab背景透明
        mTabStrip.setTabBackground(android.R.color.transparent);
        //设置一下初始化后的ViewPager
        mTabStrip.setViewPager(mViewpager);
    }

    private void initToolBar() {
        //初始化ToolBar
        mToolbar.setTitle("");
        themeTag = SharedPreferencesUtil.getBoolean(this, Constant.ThemeSwitch, false);
        color = Color.parseColor(themeTag ? "#979797" : "#00ACFF");
        mToolbar.setBackgroundColor(color);
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText("大象");
        //初始化DrawLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerlayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        //给侧拉菜单设置拖动监听
        mDrawerlayout.addDrawerListener(toggle);
        //1.把返回图标替换 2.把旋转特效的箭头跟抽屉关联
        toggle.syncState();

        //初始化NavigationView侧拉菜单 设置条目点击监听
        mNavigationview.setNavigationItemSelectedListener(this);

        //主题变色
        MenuItem item = mNavigationview.getMenu().findItem(R.id.nav_changetheme);
        mThemeSwitch = (SwitchCompat) MenuItemCompat.getActionView(item).findViewById(R.id.view_switch);
        XLog.i(mThemeSwitch.isChecked() + "waimian");

        mThemeSwitch.setChecked(themeTag);
        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                XLog.i(isChecked + "");
                SharedPreferencesUtil.saveBoolean(MainActivity.this, Constant.ThemeSwitch, isChecked);
                mThemeSwitch.setChecked(isChecked);
                int color = -1;
                if (isChecked) {
                    mTheme = R.style.GreyTheme;
                } else {
                    mTheme = R.style.AppTheme;
                }
                reload();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //关于我界面
            startActivity(new Intent(this, AboutMeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_setting) {
            //设置
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_discussion) {
            //弹出一个loading  最新
            showUpdateDialog();
        } else if (id == R.id.nav_exit) {
            startActivity(new Intent(this, AboutMeActivity.class));
        }
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private int i = -1;
    /**
     * 显示更新dialog
     */
    private void showUpdateDialog() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i){
                    case 0:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            public void onFinish() {
                i = -1;
                pDialog.setTitleText("已是最新版本")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();
    }

    //3.重新加载Activity
    protected void reload() {
        //实现activity无动画跳转
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//取消Activity切换动画
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
