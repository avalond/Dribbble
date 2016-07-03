package com.hunter.dribbble.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hunter.dribbble.App;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.TokenEntity;
import com.hunter.dribbble.mvp.token.TokenPresenter;
import com.hunter.dribbble.mvp.token.TokenView;
import com.hunter.dribbble.ui.base.BaseMvpActivity;
import com.hunter.library.LibConstants;
import com.hunter.library.util.SPUtils;
import com.hunter.library.util.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<TokenPresenter> implements TokenView {

    @BindView(R.id.btn_login)
    Button      mBtnLogin;
    @BindView(R.id.progress_login)
    ProgressBar mProgressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    protected TokenPresenter createPresenter() {
        return new TokenPresenter(this);
    }

    @OnClick(R.id.btn_login)
    void getToken() {
        Map<String, String> map = new HashMap<>();
        map.put(ApiConstants.ParamKey.CLIENT_ID, ApiConstants.ParamValue.CLIENT_ID);
        map.put(ApiConstants.ParamKey.REDIRECT_URI, ApiConstants.ParamValue.REDIRECT_URI);
        map.put(ApiConstants.ParamKey.SCOPE, ApiConstants.ParamValue.SCOPE);
        map.put(ApiConstants.ParamKey.STATE, ApiConstants.ParamValue.STATE);

        Intent intent = new Intent(this, AuthorizeActivity.class);
        intent.putExtra(LibConstants.EXTRA_WEB_LOAD_URL,
                        UrlUtils.formatToUrl(ApiConstants.Url.OAUTH_URL + ApiConstants.Path.AUTHORIZE, map));
        startActivityForResult(intent, AppConstants.REQUEST_CODE_AUTHORIZE);
    }

    @OnClick(R.id.ibtn_login_close)
    void close() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AppConstants.REQUEST_CODE_AUTHORIZE) {
            mPresenter.getToken(data.getStringExtra(AppConstants.EXTRA_AUTHORIZE_CODE));
        }
    }

    @Override
    public void onSuccess(TokenEntity entity) {
        String token = entity.getAccessToken();
        SPUtils.put(this, AppConstants.SP_KEE_ACCESS_TOKEN, token);
        App.getInstance().setToken(token);
        ApiClient.cleanApiClient();

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onStarted() {
        mProgressLogin.setVisibility(View.VISIBLE);
        mProgressLogin.setIndeterminate(true);
        mBtnLogin.setVisibility(View.GONE);
    }

    @Override
    public void onFinished() {
        mProgressLogin.setVisibility(View.GONE);
        mProgressLogin.setIndeterminate(false);
    }

    @Override
    public void onFailed(String msg) {
        super.onFailed(msg);
        mBtnLogin.setVisibility(View.VISIBLE);
    }
}
