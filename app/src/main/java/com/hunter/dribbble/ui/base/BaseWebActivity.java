package com.hunter.dribbble.ui.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hunter.dribbble.R;
import com.hunter.library.LibConstants;

public class BaseWebActivity extends BaseActivity {

    protected Toolbar     mToolbar;
    protected ProgressBar mProgressWeb;
    protected WebView     mWebView;

    protected String mWebTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);

        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        mWebTitle = getIntent().getStringExtra(LibConstants.EXTRA_WEB_TITLE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_web);
        setSupportActionBar(mToolbar);
        if (!TextUtils.isEmpty(mWebTitle)) mToolbar.setTitle(mWebTitle);
        mToolbar.setNavigationIcon(R.drawable.ic_vector_close);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebView() {
        mProgressWeb = (ProgressBar) findViewById(R.id.progress_web);
        mWebView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**
         * 设置布局方式
         *
         * NORMAL：          正常显示，没有渲染变化
         * SINGLE_COLUMN：   把所有内容放到 WebView 组件等宽的一列中，这个是强制的，会把网页都挤变形
         * NARROW_COLUMNS：  可能的话，使所有列的宽度不超过屏幕宽度
         */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        mWebView.setWebChromeClient(getWebChromeClient());
        mWebView.setWebViewClient(getWebViewClient());

        mWebView.loadUrl(getIntent().getStringExtra(LibConstants.EXTRA_WEB_LOAD_URL));
    }

    protected WebChromeClient getWebChromeClient() {
        return new MyWebChromeClient();
    }

    protected WebViewClient getWebViewClient() {
        return new MyWebViewClient();
    }

    protected class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressWeb.setVisibility(View.GONE);
            } else {
                if (mProgressWeb.getVisibility() == View.GONE) {
                    mProgressWeb.setVisibility(View.VISIBLE);
                }
                mProgressWeb.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (TextUtils.isEmpty(mWebTitle)) {
                mToolbar.setTitle(title);
            }
        }
    }

    protected class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            mToolbar.setTitle(view.getTitle());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
