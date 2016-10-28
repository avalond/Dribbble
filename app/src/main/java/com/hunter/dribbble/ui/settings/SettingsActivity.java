package com.hunter.dribbble.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.TextView;

import com.hunter.dribbble.App;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.ui.user.login.LoginActivity;
import com.hunter.dribbble.utils.FileCacheUtils;
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
    @BindView(R.id.tv_settings_cache_size)
    TextView mTvCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initToolbar();
        initSwitchNet();
        initCache();
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
     * context.getApplicationContext().getCacheDir()
     * context.getApplicationContext().getExternalCacheDir()
     */
    private void initCache() {
        mTvCacheSize.setText(FileCacheUtils.getTotalCacheSize(this));
    }

    @OnCheckedChanged(R.id.switch_settings_net_gif)
    void setShowGIF(SwitchCompat switchButton, boolean isChecked) {
        App.getAppConfig().setShowGIF(isChecked);
    }

    @OnClick(R.id.item_settings_clean_cache)
    void cleanCache() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("确定清除缓存吗？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileCacheUtils.clearAllCache(SettingsActivity.this);
                showToast("缓存已清除");
                mTvCacheSize.setText(FileCacheUtils.getTotalCacheSize(SettingsActivity.this));
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    @OnClick(R.id.tv_settings_logout)
    void logout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("确定退出当前账号吗？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cleanLocalInfo();
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    private void cleanLocalInfo() {
        /* 删除本地用户数据 */
        UserInfoUtils.clearUserInfo(this);

        /* 删除网页表单 */
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        /* 删除本地 token */
        App.getAppConfig().setToken("");

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
