package com.hunter.dribbble.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
    @BindView(R.id.switch_settings_net)
    SwitchCompat mSwitchNet;
    @BindView(R.id.switch_settings_net_png)
    SwitchCompat mSwitchNetPng;
    @BindView(R.id.switch_settings_net_gif)
    SwitchCompat mSwitchNetGif;

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

    }

    @OnCheckedChanged({R.id.switch_settings_net, R.id.switch_settings_net_png, R.id.switch_settings_net_gif})
    void netControl(SwitchCompat switchButton, boolean isChecked) {
        switch (switchButton.getId()) {
            case R.id.switch_settings_net:
                mSwitchNetPng.setChecked(isChecked);
                mSwitchNetGif.setChecked(isChecked);
                break;
            case R.id.switch_settings_net_png:

                break;
            case R.id.switch_settings_net_gif:

                break;
        }
    }

    @OnClick(R.id.tv_settings_logout)
    void logout() {
        UserInfoUtils.clearUserInfo(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
