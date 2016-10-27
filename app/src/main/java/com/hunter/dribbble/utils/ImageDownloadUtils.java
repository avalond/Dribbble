package com.hunter.dribbble.utils;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.entity.ShotsEntity;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.hunter.dribbble.utils.FileUtils.getExternalBaseDir;

public class ImageDownloadUtils {

    public static final String FILE_DIR = "dribbble";

    public static void downloadImage(final BaseActivity activity, final ShotsEntity shotsEntity) {
        downloadImage(activity, shotsEntity.getImages().getHidpi(), shotsEntity.getTitle(), shotsEntity.isAnimated());
    }

    public static void downloadImage(final BaseActivity activity, final String imageUrl, final String title,
                                     final boolean isAnim) {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                try {
                    subscriber.onNext(Glide.with(activity)
                                           .load(imageUrl)
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
                activity.showToast("下载失败，请重试");
            }

            @Override
            public void onNext(File file) {
                String suffix = isAnim ? ".gif" : ".png";
                File saveFile = FileUtils.saveToExternalCustomDir(FileUtils.getBytesFromFile(file), FILE_DIR,
                        title + suffix);
                if (saveFile != null) {
                    activity.showToastForStrongWithAction("保存至",
                            saveFile.getPath().replace(getExternalBaseDir(), "根目录"), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(saveFile)));
                } else {
                    activity.showToast("下载失败，请重试");
                }
            }
        });
    }
}
