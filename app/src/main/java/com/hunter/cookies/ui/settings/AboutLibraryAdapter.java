package com.hunter.cookies.ui.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hunter.adapter.BaseQuickAdapter;
import com.hunter.adapter.BaseViewHolder;
import com.hunter.cookies.R;
import com.hunter.cookies.entity.LibraryEntity;
import com.hunter.cookies.utils.IntentUtils;

import java.util.List;

public class AboutLibraryAdapter extends BaseQuickAdapter<LibraryEntity, BaseViewHolder> {

    public AboutLibraryAdapter(List<LibraryEntity> datas) {
        super(R.layout.item_about_library, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, final LibraryEntity entity) {
        holder.setText(R.id.tv_item_library_name, entity.getName());
        holder.setText(R.id.tv_item_library_author, entity.getAuthor());

        TextView tvLicense = holder.getView(R.id.tv_item_library_license);
        String license = entity.getLicense();
        if (TextUtils.isEmpty(license)) {
            tvLicense.setVisibility(View.GONE);
        } else {
            tvLicense.setVisibility(View.VISIBLE);
            tvLicense.setText(license);
        }
        holder.setOnClickListener(R.id.item_about_library, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivityToBrowser(mContext, entity.getLink());
            }
        });
        holder.setOnLongClickListener(R.id.item_about_library, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyLink(entity.getLink());
                return false;
            }
        });
    }

    private void copyLink(final String link) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("复制该链接？");
        dialog.setMessage(link);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cookies library", link);
                clipboard.setPrimaryClip(clip);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
