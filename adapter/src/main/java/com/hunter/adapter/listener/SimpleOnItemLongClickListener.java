package com.hunter.adapter.listener;

import android.view.View;

public interface SimpleOnItemLongClickListener<T> {

    void onItemLongClickListner(View v, int pos, T data);
}
