package com.hunter.cookies.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.hunter.cookies.R;

public class CustomLoadMore extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    @Override
    public boolean isLoadEndGone() {
        return false;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.lay_load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.lay_load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.tv_load_more_no_more;
    }
}