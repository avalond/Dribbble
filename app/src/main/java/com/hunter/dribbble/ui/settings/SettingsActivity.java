package com.hunter.dribbble.ui.settings;

import android.os.Bundle;

import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initToolbar();
    }

    private void initToolbar() {
    }
}
