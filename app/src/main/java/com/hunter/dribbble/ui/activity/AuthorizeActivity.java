package com.hunter.dribbble.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.ui.base.BaseWebActivity;
import com.hunter.library.util.LogUtils;

public class AuthorizeActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected WebViewClient getWebViewClient() {

        return new MyWebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogUtils.d(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("code")) {
                    String code = url.replace(ApiConstants.Url.REDIRECT_URL + "?code=", "")
                                     .replace("&state=hunter", "");
                    Intent intent = new Intent();
                    intent.putExtra(AppConstants.EXTRA_AUTHORIZE_CODE, code);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    view.loadUrl(url);
                }

                return true;
            }
        };
    }
}
