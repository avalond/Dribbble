package com.hunter.dribbble.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hunter.dribbble.R;

public class GlideUtils {

    public static void setAvatar(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context))
                .placeholder(R.drawable.shape_corner_grey)
                .into(imageView);
    }

    public static void setPreviewImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.shape_grey).into(imageView);
    }

    public static void setImageWithThumb(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.shape_grey).thumbnail(0.2f).into(imageView);
    }

    public static void setGif(Context context, String url, ImageView imageView) {
        Glide.clear(imageView);
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.shape_grey)
                .into(imageView);
    }
}
