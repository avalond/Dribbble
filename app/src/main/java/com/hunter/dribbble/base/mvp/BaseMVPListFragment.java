package com.hunter.dribbble.base.mvp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.base.BaseFragment;

import java.util.List;

public abstract class BaseMVPListFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment {

    public P mPresenter;
    public M mModel;

    private SwipeRefreshLayout mRefreshLayout;

    protected boolean mIsRefresh;
    protected int     mPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

    protected void refreshAndLoadMore(SwipeRefreshLayout refreshLayout,
                                      RecyclerView recyclerView,
                                      BaseQuickAdapter adapter) {
        /**
         * 刷新事件
         */
        mRefreshLayout = refreshLayout;
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.accent));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(true);
            }
        });

        /**
         * 加载更多事件
         */
        View loadingView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.layout_load_more, (ViewGroup) recyclerView.getParent(), false);
        adapter.setLoadingView(loadingView);
        adapter.openLoadAnimation();
        adapter.openLoadMore(ApiConstants.ParamValue.PAGE_SIZE);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestData(false);
            }
        });

        /**
         * 首次进入自动刷新
         */
        mRefreshLayout.setRefreshing(true);
        requestData(true);
    }

    protected void requestData(boolean isRefresh) {
        mIsRefresh = isRefresh;
    }

    protected <T> void setData(List<T> datas, BaseQuickAdapter adapter) {
        if (mIsRefresh) adapter.setNewData(datas);
        else adapter.addData(datas);
    }

    public void showDialog(CharSequence msg) {
    }

    public void onCompleted() {
        mRefreshLayout.setRefreshing(false);
    }

}