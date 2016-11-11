package com.hunter.adapter.listener;

import android.view.View;

public interface SimpleOnItemClickListener<T> {

    void onItemClickListner(View v, int pos, T data);
}
