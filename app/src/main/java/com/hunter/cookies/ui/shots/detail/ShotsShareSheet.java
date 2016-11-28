package com.hunter.cookies.ui.shots.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hunter.cookies.R;
import com.hunter.cookies.utils.FileUtils;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.hunter.cookies.utils.ImageDownloadUtils.FILE_DIR;

public class ShotsShareSheet extends BottomSheetDialogFragment {

    private static final String ARGS_SHOTS_URL = "args_shots_url";
    private static final String ARGS_IMAGE_URL = "args_image_url";

    public static ShotsShareSheet newInstance(String shotsUrl, String imageUrl) {
        Bundle args = new Bundle();
        args.putString(ARGS_SHOTS_URL, shotsUrl);
        args.putString(ARGS_IMAGE_URL, imageUrl);

        ShotsShareSheet fragment = new ShotsShareSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheets_share_shots, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.tv_sheets_share_image)
    void shareImage() {
        shareGif();
        //        Glide.with(getContext())
        //             .load(getArguments().getString(ARGS_IMAGE_URL))
        //             .asBitmap()
        //             .into(new SimpleTarget<Bitmap>() {
        //                 @Override
        //                 public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        //                     Uri uri = Uri.parse(insertImage(getContext().getContentResolver(), resource, "", ""));
        //                     Intent intent = new Intent(Intent.ACTION_SEND);
        //                     intent.setType("image//gif");
        //                     intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //                     intent.putExtra(Intent.EXTRA_STREAM, uri);
        //                     startActivity(Intent.createChooser(intent, "分享到"));
        //                 }
        //             });
    }

    private void shareGif() {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                try {
                    subscriber.onNext(Glide.with(getContext())
                                           .load(getArguments().getString(ARGS_IMAGE_URL))
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

            }

            @Override
            public void onNext(File file) {
                File saveFile = FileUtils.saveToExternalCustomDir(FileUtils.getBytesFromFile(file), FILE_DIR,
                                                                  "1111.gif");
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("*/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(saveFile));
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
    }

    @OnClick(R.id.tv_sheets_share_link)
    void shareLink() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, getArguments().getString(ARGS_SHOTS_URL));
        startActivity(Intent.createChooser(intent, "分享到"));
    }
}
