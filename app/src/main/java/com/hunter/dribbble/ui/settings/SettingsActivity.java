package com.hunter.dribbble.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.dribbble.App;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.ui.user.login.LoginActivity;
import com.hunter.dribbble.utils.UserInfoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar_settings)
    Toolbar mToolbarSettings;
    @BindView(R.id.switch_settings_net_gif)
    SwitchCompat mSwitchNetGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initToolbar();
        initSwitchNet();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarSettings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarSettings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSwitchNet() {
        mSwitchNetGIF.setChecked(App.getAppConfig().getShowGIFValue());
    }

    /**
     * 使用2G/3G/4G网络浏览动图
     */
    @OnCheckedChanged(R.id.switch_settings_net_gif)
    void setShowGIF(SwitchCompat switchButton, boolean isChecked) {
        App.getAppConfig().setShowGIF(isChecked);
    }

    /**
     * 图片缓存大小
     */
    @OnClick(R.id.rl_settings_cache_size)
    void setCacheSize() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("取消", null);
        View view = LayoutInflater.from(this)
                                  .inflate(R.layout.dialog_cache_size, (ViewGroup) findViewById(R.id.dialog_cache_size),
                                          false);
        builder.setView(view);

        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.input_dialog_cache_size);
        textInputLayout.setHint("设置图片缓存上限");

        builder.show();
    }

    @OnClick(R.id.tv_settings_logout)
    void logout() {
        UserInfoUtils.clearUserInfo(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
