package com.hunter.dribbble.ui.shots;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.utils.ImageDownloadUtils;
import com.hunter.dribbble.utils.glide.GlideUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageWatcherActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    public static final String EXTRA_IS_ANIMATED = "extra_is_animated";
    public static final String EXTRA_IMAGE_TITLE = "extra_image_title";

    @BindView(R.id.photo_watch_image)
    PhotoView mPhoto;

    private String mImageUrl;
    private String mTitle;
    private boolean mIsAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_watcher);
        ButterKnife.bind(this);

        mTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mIsAnimated = getIntent().getBooleanExtra(EXTRA_IS_ANIMATED, false);
        if (mIsAnimated) GlideUtils.setGif(this, mImageUrl, mPhoto);
        else GlideUtils.setImageWithThumb(this, mImageUrl, mPhoto);

        PhotoViewAttacher attacher = new PhotoViewAttacher(mPhoto);
        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }

            @Override
            public void onOutsidePhotoTap() {
                finish();
            }
        });

    }

    @OnClick(R.id.iv_image_watcher_download)
    void downloadImage() {
        RxPermissions.getInstance(this)
                     .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                     .subscribe(new Action1<Boolean>() {
                         @Override
                         public void call(Boolean granted) {
                             if (granted) ImageDownloadUtils.downloadImage(ImageWatcherActivity.this, mImageUrl, mTitle,
                                     mIsAnimated);
                             else showToast("获取权限失败，请重试");
                         }
                     });
    }

    @OnClick(R.id.iv_image_watcher_rotation)
    void rotationImage() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}
