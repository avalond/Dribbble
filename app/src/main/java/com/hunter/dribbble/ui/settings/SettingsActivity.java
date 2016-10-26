package com.hunter.dribbble.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.ui.user.login.LoginActivity;
import com.hunter.dribbble.utils.UserInfoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar_settings)
    Toolbar mToolbarSettings;
    @BindView(R.id.tv_settings_download_dir)
    TextView mTvDownloadDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initToolbar();
        initDownloadDir();
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

    private void initDownloadDir() {
        mTvDownloadDir.setText("根目录/" + AppConstants.EXTERNAL_FILE_ROOT);
    }

    @OnClick(R.id.tv_settings_logout)
    void logout() {
        UserInfoUtils.clearUserInfo(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
