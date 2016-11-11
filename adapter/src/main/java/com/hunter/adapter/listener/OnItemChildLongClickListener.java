package com.hunter.adapter.listener;

import android.view.View;

public interface OnItemChildLongClickListener<T> {

    void onItemChildLongClickListener(View v, int pos, T data);
}
