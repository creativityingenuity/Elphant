package com.gxs.elphant.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.gxs.elphant.R;
import com.gxs.elphant.base.BaseActivity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.util.SharedPreferencesUtil;

import butterknife.Bind;

public class NewDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView mToolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    WebView mWebview;
    @Bind(R.id.loading)
    ProgressBar mLoading;
    @Bind(R.id.fl_wv_content)
    FrameLayout fl_Content;

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        mToolbarTitle.setVisibility(View.GONE);
        mToolbar.setTitle("");
        boolean themeTag = SharedPreferencesUtil.getBoolean(this, Constant.ThemeSwitch, false);
        int color = Color.parseColor(themeTag ? "#979797" : "#00ACFF");
        mToolbar.setBackgroundColor(color);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setMax(100);
        mWebview = new WebView(getApplicationContext());
        fl_Content.addView(mWebview);
        initWebView();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_newdetail;
    }

    private void initWebView() {
        String url = getIntent().getStringExtra(Constant.NEWDETAIL);
        XLog.e(url);
        mWebview.loadUrl(url);
        //获取webview设置
        WebSettings settings = mWebview.getSettings();
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //设置是否显示放大缩小网页的按钮(wap网页不支持)
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 设置是否支持双击放大(wap网页不支持)
        settings.setUseWideViewPort(true);
        // 设置是否支持android和网页中js代码的互调
        settings.setJavaScriptEnabled(true);

        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100) {
                    mLoading.setProgress(newProgress);
                }else {
                    mLoading.setVisibility(View.GONE);
                }
            }
        });

        //监听网页的加载操作
        mWebview.setWebViewClient(new WebViewClient() {
            //设置不用系统浏览器打开,直接显示在当前Webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //激活WebView为活跃状态，能正常执行网页的响应
        mWebview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        mWebview.onPause();
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //销毁webview
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
