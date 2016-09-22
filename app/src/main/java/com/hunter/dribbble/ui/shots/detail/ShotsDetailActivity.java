package com.hunter.dribbble.ui.shots.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.shots.detail.comments.ShotsCommentsFragment;
import com.hunter.dribbble.ui.shots.detail.des.ShotsDesFragment;
import com.hunter.dribbble.utils.ImageUrlUtils;
import com.hunter.dribbble.utils.StatusBarCompat;
import com.hunter.dribbble.widget.FrescoImageProgressBar;
import com.hunter.lib.base.BasePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShotsDetailActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    private static final String[] TAB_TITLES = {"简介", "评论"};

    @BindView(R.id.drawee_shots_detail_image)
    SimpleDraweeView     mDraweeShotsImage;
    @BindView(R.id.tab_shots_detail)
    TabLayout            mTabShots;
    @BindView(R.id.pager_shots_detail)
    ViewPager            mPagerShots;
    @BindView(R.id.toolbar_shots_detail)
    Toolbar              mToolbarShots;
    @BindView(R.id.fab_shots_detail_play)
    FloatingActionButton mFabShotsDetailPlay;

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

        if (mShotsEntity.isAnimated()) {
            mDraweeShotsImage.setImageURI(mShotsEntity.getImages().getNormal());
            mFabShotsDetailPlay.setVisibility(View.VISIBLE);
        } else {
            mDraweeShotsImage.setImageURI(ImageUrlUtils.getImageUrl(mShotsEntity.getImages()));
            mFabShotsDetailPlay.setVisibility(View.GONE);
        }

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

    @OnClick(R.id.fab_shots_detail_play)
    void playGif(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("正在使用数据流量访问图片，是否继续？")
               .setNegativeButton("取消", null)
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       mDraweeShotsImage.getHierarchy()
                                        .setProgressBarImage(new FrescoImageProgressBar(ShotsDetailActivity.this));
                       Uri uri = Uri.parse(ImageUrlUtils.getImageUrl(mShotsEntity.getImages()));
                       DraweeController controller = Fresco.newDraweeControllerBuilder()
                                                           .setUri(uri)
                                                           .setAutoPlayAnimations(true)
                                                           .build();
                       mDraweeShotsImage.setController(controller);
                       mFabShotsDetailPlay.hide();
                   }
               })
               .setTitle("提示")
               .show();
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
                    if (Math.abs(x - mPressX) < Math.abs(y - mPressY)) mPagerShots.setEnabled(false);
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
