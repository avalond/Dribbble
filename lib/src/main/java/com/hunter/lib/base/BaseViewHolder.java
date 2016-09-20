package com.hunter.lib.base;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    public View mConvertView;

    public BaseViewHolder(View view) {
        super(view);
        mViews = new SparseArray<>();
        mConvertView = view;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setTvText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }

    public void setTvTextByHtml(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(Html.fromHtml(text));
    }

    public void setDraweeImg(int viewId, String url) {
        SimpleDraweeView draweeView = getView(viewId);
        draweeView.setImageURI(Uri.parse(url));
    }

    public void setDraweeGif(int viewId, String url) {
        SimpleDraweeView draweeView = getView(viewId);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                            .setUri(url)
                                            .setAutoPlayAnimations(true)
                                            .build();
        draweeView.setController(controller);
    }

    public void setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
    }

}