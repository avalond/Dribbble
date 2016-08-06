package com.hunter.dribbble.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.base.BaseActivity;
import com.hunter.dribbble.ui.fragment.ShotsCommentsFragment;
import com.hunter.dribbble.ui.fragment.ShotsDesFragment;
import com.hunter.dribbble.utils.CheckImageUrlUtils;
import com.hunter.dribbble.utils.StatusBarCompat;
import com.hunter.library.base.BasePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotsDetailActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    private static final String[] TAB_TITLES = {"简介", "评论"};

    @BindView(R.id.drawee_shots_detail_image)
    SimpleDraweeView mDraweeShotsImage;
    @BindView(R.id.tab_shots_detail)
    TabLayout        mTabShots;
    @BindView(R.id.pager_shots_detail)
    ViewPager        mPagerShots;
    @BindView(R.id.toolbar_shots_detail)
    Toolbar          mToolbarShots;
    @BindView(R.id.app_bar_shots)
    AppBarLayout     mAppBarShots;

    private ShotsEntity mShotsEntity;

    private float   mPressX;
    private float   mPressY;
    private boolean mIsVerticalMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots_detail);
        ButterKnife.bind(this);
        StatusBarCompat.translucentStatusBar(this);
        mShotsEntity = (ShotsEntity) getIntent().getSerializableExtra(AppConstants.EXTRA_SHOTS_ENTITY);
        init();
    }

    private void init() {
        setSupportActionBar(mToolbarShots);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarShots.setOnMenuItemClickListener(this);
        mToolbarShots.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDraweeShotsImage.setImageURI(CheckImageUrlUtils.checkImageUrl(mShotsEntity.getImages()));
        initPager();
    }

    private void initPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ShotsDesFragment.newInstance(mShotsEntity));
        fragments.add(ShotsCommentsFragment.newInstance(mShotsEntity));
        BasePagerAdapter<Fragment> adapter = new BasePagerAdapter<>(getSupportFragmentManager(),
                                                                    fragments,
                                                                    Arrays.asList(TAB_TITLES));
        mPagerShots.setAdapter(adapter);
        for (String tabTitle : TAB_TITLES) {
            mTabShots.addTab(mTabShots.newTab().setText(tabTitle));
        }
        mTabShots.setupWithViewPager(mPagerShots);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shots_detail, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open_on_browser:

                break;

            case R.id.menu_download:

                break;

            case R.id.menu_set_wallpaper:

                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressX = x;
                mPressY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mIsVerticalMove) {

                    float differX = Math.abs(x - mPressX);
                    float differY = Math.abs(y - mPressY);
                    double differZ = Math.sqrt(differX * differX + differY * differY);
                    int angle = Math.round((float) (Math.asin(differY / differZ) / Math.PI * 180));

                    mIsVerticalMove = angle > 45;
                    if (mIsVerticalMove) {
                        mPagerShots.setEnabled(false);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mIsVerticalMove) {
                    mPagerShots.setEnabled(true);
                    mIsVerticalMove = false;
                }

                break;
        }

        return super.dispatchTouchEvent(event);
    }
}
