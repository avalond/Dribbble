package com.hunter.dribbble.ui.shots.detail;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPActivity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.shots.WatchImageActivity;
import com.hunter.dribbble.ui.shots.detail.comments.ShotsCommentsFragment;
import com.hunter.dribbble.ui.shots.detail.des.ShotsDesFragment;
import com.hunter.dribbble.utils.FileUtils;
import com.hunter.dribbble.utils.ImageUrlUtils;
import com.hunter.dribbble.utils.StatusBarCompat;
import com.hunter.dribbble.utils.glide.GlideUtils;
import com.hunter.dribbble.widget.ProportionImageView;
import com.hunter.lib.base.BasePagerAdapter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.hunter.dribbble.utils.FileUtils.getExternalBaseDir;

public class ShotsDetailActivity extends BaseMVPActivity<ShotsDetailPresenter, ShotsDetailModel> implements
        ShotsDetailContract.View, Toolbar.OnMenuItemClickListener {

    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 100;

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
    @BindView(R.id.fab_shots_detail_play)
    FloatingActionButton mFabShotsDetailPlay;

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
        if (mShotsEntity.isAnimated()) {
            GlideUtils.setGif(this, mShotsEntity.getImages().getHidpi(), mPivShotsImage);
            mFabShotsDetailPlay.setVisibility(View.VISIBLE);
        } else {
            GlideUtils.setImageWithThumb(this, ImageUrlUtils.getImageUrl(mShotsEntity.getImages()), mPivShotsImage);
            mFabShotsDetailPlay.setVisibility(View.GONE);
        }
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
        Intent intent = new Intent(this, WatchImageActivity.class);
        intent.putExtra(WatchImageActivity.EXTRA_IMAGE_URL, ImageUrlUtils.getImageUrl(mShotsEntity.getImages()));
        intent.putExtra(WatchImageActivity.EXTRA_IS_ANIMATED, mShotsEntity.isAnimated());
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
                downloadPicture();
                break;

            case R.id.menu_set_wallpaper:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) requestPermission();
                else setWallpager();
                break;
        }
        return true;
    }

    private void downloadPicture() {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                try {
                    subscriber.onNext(Glide.with(ShotsDetailActivity.this)
                                           .load(mShotsEntity.getImages().getHidpi())
                                           .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                           .get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<File>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                showToast("下载失败，请重试");
            }

            @Override
            public void onNext(File file) {
                String suffix = mShotsEntity.isAnimated() ? ".gif" : ".png";
                File saveFile = FileUtils.saveToExternalCustomDir(FileUtils.getBytesFromFile(file),
                                                                  AppConstants.EXTERNAL_FILE_ROOT,
                                                                  mShotsEntity.getTitle() + suffix);
                if (saveFile != null)
                    showToastForStrongWithAction("保存至", saveFile.getPath().replace(getExternalBaseDir(), ""),
                                                 new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                     }
                                                 });
                else showToast("下载失败，请重试");
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(saveFile)));
            }
        });
    }

    private void requestPermission() {
        AndPermission.with(this)
                     .requestCode(REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
                     .permission(Manifest.permission.SET_WALLPAPER)
                     .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionListener);
    }

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
                setWallpager();
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            }
        }
    };

    private void setWallpager() {
        final Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Glide.with(this).load(mShotsEntity.getImages().getHidpi()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resource, null, null));
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("mimeType", "image/*");
                startActivity(Intent.createChooser(intent, "设为壁纸"));
            }
        });

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
