package com.hunter.cookies.ui.settings;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hunter.cookies.R;
import com.hunter.cookies.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.rv_about)
    RecyclerView mRvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initList();
    }

    private void initList() {
        mRvAbout.setLayoutManager(new LinearLayoutManager(this));
        AboutDesAdapter adapter = new AboutDesAdapter(new ArrayList<String>());
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_about_help, adapter.getHeaderView(this), false);
        adapter.addHeaderView(headerView);
        mRvAbout.setAdapter(adapter);
    }
}
