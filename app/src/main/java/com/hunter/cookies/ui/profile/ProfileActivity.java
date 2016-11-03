package com.hunter.cookies.ui.profile;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hunter.cookies.R;
import com.hunter.cookies.entity.UserEntity;
import com.hunter.cookies.ui.profile.detail.ProfileDetailFragment;
import com.hunter.cookies.ui.profile.followers.ProfileFollowersFragment;
import com.hunter.cookies.ui.profile.shots.ProfileShotsFragment;
import com.hunter.cookies.utils.StatusBarCompat;
import com.hunter.cookies.utils.glide.GlideUtils;
import com.hunter.cookies.widget.ProportionImageView;
import com.hunter.lib.base.BasePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public static final String EXTRA_USER_ENTITY = "extra_user_entity";

    private static final String[] TAB_TITLES = {"简介", "作品", "粉丝"};

    @BindView(R.id.iv_profile_avatar)
    ImageView mIvProfileAvatar;
    @BindView(R.id.toolbar_profile)
    Toolbar mToolbarProfile;
    @BindView(R.id.collapsing_profile)
    CollapsingToolbarLayout mCollapsingProfile;
    @BindView(R.id.tab_profile)
    TabLayout mTabProfile;
    @BindView(R.id.app_bar_profile)
    AppBarLayout mAppBarProfile;
    @BindView(R.id.pager_profile)
    ViewPager mPagerProfile;
    @BindView(R.id.piv_profile_avatar_background)
    ProportionImageView mAvatarBackground;
    @BindView(R.id.tv_profile_user_nickname)
    TextView mTvUserNickname;
    @BindView(R.id.tv_profile_user_bio)
    TextView mTvUserBio;
    @BindView(R.id.ll_profile_avatar)
    View mLayoutProfileAvatar;

    private UserEntity mUserEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        mUserEntity = (UserEntity) getIntent().getSerializableExtra(EXTRA_USER_ENTITY);

        initToolbar();
        initUserInfo();
        initPager();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarProfile.setOnMenuItemClickListener(this);
        mToolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAppBarProfile.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mLayoutProfileAvatar.scrollTo(0, -verticalOffset / 2);
            }
        });
    }

    private void initUserInfo() {
        GlideUtils.setAvatar(this, mUserEntity.getAvatarUrl(), mIvProfileAvatar);
        Glide.with(this)
             .load(mUserEntity.getAvatarUrl())
             .placeholder(R.drawable.shape_grey)
             .centerCrop()
             .into(mAvatarBackground);

        mTvUserNickname.setText(mUserEntity.getName());
        HtmlFormatUtils.Html2String(mTvUserBio, mUserEntity.getBio());
    }

    private void initPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ProfileDetailFragment.newInstance(mUserEntity));
        fragments.add(ProfileShotsFragment.newInstance(mUserEntity.getId() + ""));
        fragments.add(ProfileFollowersFragment.newInstance(mUserEntity.getId() + ""));
        BasePagerAdapter<Fragment> adapter = new BasePagerAdapter<>(getSupportFragmentManager(), fragments,
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
