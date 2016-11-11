package com.hunter.adapter.listener;

import android.view.View;

public interface OnItemChildClickListener<T> {

    void onItemChildClickListener(View v, int pos, T data);
}
