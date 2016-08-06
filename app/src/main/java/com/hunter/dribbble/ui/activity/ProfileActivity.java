package com.hunter.dribbble.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.ui.fragment.ProfileDetailFragment;
import com.hunter.dribbble.ui.fragment.ProfileFollowersFragment;
import com.hunter.dribbble.ui.fragment.ProfileShotsFragment;
import com.hunter.dribbble.utils.StatusBarCompat;
import com.hunter.library.base.BasePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class ProfileActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private static final String[] TAB_TITLES = {"简介", "作品", "粉丝"};

    @BindView(R.id.drawee_profile_avatar)
    SimpleDraweeView        mDraweeProfileAvatar;
    @BindView(R.id.toolbar_profile)
    Toolbar                 mToolbarProfile;
    @BindView(R.id.collapsing_profile)
    CollapsingToolbarLayout mCollapsingProfile;
    @BindView(R.id.tab_profile)
    TabLayout               mTabProfile;
    @BindView(R.id.app_bar_profile)
    AppBarLayout            mAppBarProfile;
    @BindView(R.id.pager_profile)
    ViewPager               mPagerProfile;
    @BindView(R.id.drawee_profile_avatar_blurry)
    ImageView               mDraweeAvatarBlurry;

    private UserEntity mUserEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        mUserEntity = (UserEntity) getIntent().getSerializableExtra(AppConstants.EXTRA_USER_ENTITY);
        init();
    }

    private void init() {
        setSupportActionBar(mToolbarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarProfile.setOnMenuItemClickListener(this);
        mToolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initUserInfo();
        initPager();
    }

    private void initUserInfo() {
        mDraweeProfileAvatar.setImageURI(mUserEntity.getAvatarUrl());
        Blurry.with(ProfileActivity.this)
              .radius(25)
              .sampling(4)
              .async()
              .animate()
              .capture(mDraweeProfileAvatar)
              .into(mDraweeAvatarBlurry);
    }

    private void initPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ProfileDetailFragment.newInstance(mUserEntity));
        fragments.add(ProfileShotsFragment.newInstance(mUserEntity.getId() + ""));
        fragments.add(ProfileFollowersFragment.newInstance(mUserEntity.getId() + ""));
        BasePagerAdapter<Fragment> adapter = new BasePagerAdapter<>(getSupportFragmentManager(),
                                                                    fragments,
                                                                    Arrays.asList(TAB_TITLES));
        mPagerProfile.setAdapter(adapter);
        for (String tabTitle : TAB_TITLES) {
            mTabProfile.addTab(mTabProfile.newTab().setText(tabTitle));
        }
        mTabProfile.setupWithViewPager(mPagerProfile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
