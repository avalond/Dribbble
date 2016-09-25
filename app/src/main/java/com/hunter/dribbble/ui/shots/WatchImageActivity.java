package com.hunter.dribbble.ui.shots;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.dribbble.R;
import com.hunter.dribbble.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class WatchImageActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_URL   = "extra_image_url";
    public static final String EXTRA_IS_ANIMATED = "extra_is_animated";

    @BindView(R.id.photo_watch_image)
    PhotoView mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_image);
        ButterKnife.bind(this);

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        boolean isAnimated = getIntent().getBooleanExtra(EXTRA_IS_ANIMATED, false);
        if (isAnimated) GlideUtils.setGif(this, imageUrl, mPhoto);
        else GlideUtils.setImageWithThumb(this, imageUrl, mPhoto);

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

}
