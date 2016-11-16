package com.hunter.cookies.ui.settings;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hunter.cookies.R;
import com.hunter.cookies.base.BaseActivity;
import com.hunter.cookies.entity.LibraryEntity;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_about_version)
    TextView mTvVersion;
    @BindView(R.id.toolbar_about)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_about)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.app_bar_about)
    AppBarLayout mAppBar;
    @BindView(R.id.tv_about_contact)
    TextView mTvContact;
    @BindView(R.id.rv_about_library)
    RecyclerView mRvLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initLibrary();
    }

    private void initLibrary() {
        final List<LibraryEntity> datas = new ArrayList<>();
        datas.add(new LibraryEntity("Retrofit", "Square", "Apache License, Version 2.0",
                                    "https://github.com/square/retrofit"));
        datas.add(new LibraryEntity("OkHttp", "Square", "Apache License, Version 2.0",
                                    "https://github.com/square/okhttp"));
        datas.add(new LibraryEntity("RxJava", "ReactiveX", "Apache License, Version 2.0",
                                    "https://github.com/ReactiveX/RxJava"));
        datas.add(new LibraryEntity("ButterKnife", "Jake Wharton", "Apache License, Version 2.0",
                                    "https://github.com/JakeWharton/butterknife"));
        datas.add(new LibraryEntity("MaterialDrawer", "Mike Penz", "Apache License, Version 2.0",
                                    "https://github.com/mikepenz/MaterialDrawer"));
        datas.add(new LibraryEntity("EventBus", "GreenRobot", "Apache License, Version 2.0",
                                    "https://github.com/greenrobot/EventBus"));
        datas.add(new LibraryEntity("StatusBarUtil", "Jaeger", "Apache License, Version 2.0",
                                    "https://github.com/laobie/StatusBarUtil"));
        datas.add(new LibraryEntity("PhotoView", "Chris Banes", "Apache License, Version 2.0",
                                    "https://github.com/chrisbanes/PhotoView"));
        datas.add(new LibraryEntity("Jsoup", "Jonathan Hedley", "", "https://github.com/jhy/jsoup"));
        datas.add(new LibraryEntity("RxPermissions", "Thomas Bruyelle", "",
                                    "https://github.com/tbruyelle/RxPermissions"));
        AboutLibraryAdapter adapter = new AboutLibraryAdapter(datas);
        mRvLibrary.setLayoutManager(new LinearLayoutManager(this));
        mRvLibrary.setAdapter(adapter);
        mRvLibrary.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.LIST_VERTICAL));
    }
}
