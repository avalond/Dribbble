package com.hunter.cookies.ui.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.base.BaseWebActivity;

public class AuthorizeActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected WebViewClient getWebViewClient() {
        return new MyWebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("code")) {
                    String code = url.replace(ApiConstants.Url.REDIRECT_URL + "?code=", "")
                                     .replace("&state=hunter", "");
                    Intent intent = new Intent();
                    intent.putExtra(LoginActivity.EXTRA_AUTHORIZE_CODE, code);
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
