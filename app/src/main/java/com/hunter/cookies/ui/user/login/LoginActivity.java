package com.hunter.cookies.ui.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hunter.cookies.App;
import com.hunter.cookies.R;
import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.base.mvp.BaseMVPActivity;
import com.hunter.cookies.entity.TokenEntity;
import com.hunter.cookies.entity.UserEntity;
import com.hunter.cookies.utils.UrlUtils;
import com.hunter.cookies.utils.UserInfoUtils;
import com.hunter.lib.LibConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMVPActivity<LoginPresenter, LoginModel> implements LoginContract.View {

    public static final String EXTRA_AUTHORIZE_CODE = "extra_authorize_code";

    public static final int REQUEST_CODE_AUTHORIZE = 1;

    @BindView(R.id.tv_login_welcome)
    TextView mTvWelcome;
    @BindView(R.id.btn_to_authorize_web)
    Button mBtnLogin;
    @BindView(R.id.progress_login)
    ProgressBar mProgressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_to_authorize_web)
    void toAuthorizeWeb() {
        Intent intent = new Intent(this, AuthorizeActivity.class);
        intent.putExtra(LibConstants.EXTRA_WEB_LOAD_URL, UrlUtils.getAuthorizeUrl());
        startActivityForResult(intent, REQUEST_CODE_AUTHORIZE);
    }

    @OnClick(R.id.ibtn_login_close)
    void close() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_AUTHORIZE) {
            mPresenter.getToken(data.getStringExtra(EXTRA_AUTHORIZE_CODE));
            mProgressLogin.setVisibility(View.VISIBLE);
            mProgressLogin.setIndeterminate(true);
            mBtnLogin.setVisibility(View.GONE);
            mTvWelcome.setText("欢迎回来");
        }
    }

    @Override
    public void getTokenOnSuccess(TokenEntity entity) {
        App.getAppConfig().setToken(entity.getAccessToken());
        ApiClient.resetApiClient();
        mPresenter.getUserInfo();
    }

    @Override
    public void getTokenOnFail(String msg) {
        mBtnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void getUserInfoOnSuccess(UserEntity entity) {
        UserInfoUtils.setUserInfo(this, entity);
        setResult(RESULT_OK);
        finish();
    }
}
