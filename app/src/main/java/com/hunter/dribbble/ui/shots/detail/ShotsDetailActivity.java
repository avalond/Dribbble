package com.hunter.dribbble.ui.shots.detail;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPActivity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.shots.ImageWatcherActivity;
import com.hunter.dribbble.ui.shots.detail.comments.ShotsCommentsFragment;
import com.hunter.dribbble.ui.shots.detail.des.ShotsDesFragment;
import com.hunter.dribbble.utils.ImageDownloadUtils;
import com.hunter.dribbble.utils.ImageUrlUtils;
import com.hunter.dribbble.utils.StatusBarCompat;
import com.hunter.dribbble.utils.glide.GlideUtils;
import com.hunter.dribbble.widget.ProportionImageView;
import com.hunter.lib.base.BasePagerAdapter;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ShotsDetailActivity extends BaseMVPActivity<ShotsDetailPresenter, ShotsDetailModel> implements
        ShotsDetailContract.View, Toolbar.OnMenuItemClickListener {

    public static final String EXTRA_SHOTS_ENTITY = "extra_shots_entity";
    public static final String EXTRA_IS_FROM_SEARCH = "extra_is_from_search";

    private static final String[] TAB_TITLES = {"简介", "评论"};

    @BindView(R.id.piv_shots_detail_image)
    ProportionImageView mPivShotsImage;
    @BindView(R.id.tab_shots_detail)
    TabLayout mTabShots;
    @BindView(R.id.pager_shots_detail)
    ViewPager mPagerShots;
    @BindView(R.id.toolbar_shots_detail)
    Toolbar mToolbarShots;

    private ShotsEntity mShotsEntity;

    private float mPressX;
    private float mPressY;
    private boolean mIsVerticalMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shots_detail);
        ButterKnife.bind(this);

        initToolbar();
        for (String tabTitle : TAB_TITLES)
            mTabShots.addTab(mTabShots.newTab().setText(tabTitle));

        Intent intent = getIntent();
        mShotsEntity = (ShotsEntity) intent.getSerializableExtra(EXTRA_SHOTS_ENTITY);
        boolean isFromSearch = intent.getBooleanExtra(EXTRA_IS_FROM_SEARCH, false);
        if (isFromSearch) {
            showImage();
            mPresenter.getShotsDetail(mShotsEntity.getId());
        } else {
            showImage();
            initPager();
        }
    }

    private void initToolbar() {
        StatusBarCompat.translucentStatusBar(this);
        setSupportActionBar(mToolbarShots);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarShots.setOnMenuItemClickListener(this);
        mToolbarShots.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showImage() {
        if (mShotsEntity.isAnimated()) GlideUtils.setGif(this, mShotsEntity.getImages().getHidpi(), mPivShotsImage);
        else GlideUtils.setImageWithThumb(this, ImageUrlUtils.getImageUrl(mShotsEntity.getImages()), mPivShotsImage);

    }

    private void initPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ShotsDesFragment.newInstance(mShotsEntity));
        fragments.add(ShotsCommentsFragment.newInstance(mShotsEntity));
        BasePagerAdapter<Fragment> adapter = new BasePagerAdapter<>(getSupportFragmentManager(), fragments,
                Arrays.asList(TAB_TITLES));
        mPagerShots.setAdapter(adapter);
        mTabShots.setupWithViewPager(mPagerShots);
    }

    @OnClick(R.id.piv_shots_detail_image)
    void toWatchImage() {
        Intent intent = new Intent(this, ImageWatcherActivity.class);
        intent.putExtra(ImageWatcherActivity.EXTRA_IMAGE_URL, ImageUrlUtils.getImageUrl(mShotsEntity.getImages()));
        intent.putExtra(ImageWatcherActivity.EXTRA_IS_ANIMATED, mShotsEntity.isAnimated());
        intent.putExtra(ImageWatcherActivity.EXTRA_IMAGE_TITLE, mShotsEntity.getTitle());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shots_detail, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, mShotsEntity.getHtmlUrl());
                startActivity(Intent.createChooser(intent, "分享到"));
                break;

            case R.id.menu_open_on_browser:

                break;

            case R.id.menu_download:
                RxPermissions.getInstance(this)
                             .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                             .subscribe(new Action1<Boolean>() {
                                 @Override
                                 public void call(Boolean granted) {
                                     if (granted)
                                         ImageDownloadUtils.downloadImage(ShotsDetailActivity.this, mShotsEntity);
                                     else showToast("获取权限失败，请重试");
                                 }
                             });
                break;

        }
        return true;
    }

    /**
     * 处理水平方向与垂直方向的滑动冲突
     */
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
                    double differ = Math.sqrt(differX * differX + differY * differY);
                    int angle = Math.round((float) (Math.asin(differY / differ) / Math.PI * 180));
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

    @Override
    public void getShotsDetailOnSuccess(ShotsEntity data) {
        mShotsEntity = data;
        initPager();
    }
}