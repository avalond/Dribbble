package com.hunter.dribbble.base.mvp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.base.BaseFragment;
import com.hunter.dribbble.widget.LoadFrameLayout;
import com.hunter.dribbble.widget.ErrorView;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseMVPListFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements
        View.OnClickListener {

    public P mPresenter;
    public M mModel;

    private SwipeRefreshLayout mRefreshLayout;

    protected boolean mIsRefresh;
    protected int mPage;

    private View mEmptyView;
    private View mNoMoreView;
    private LoadFrameLayout mLoadFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mRootView);
        ErrorView errorView = new ErrorView(mContext);
        errorView.setOnClickListener(this);
        mLoadFrameLayout = new LoadFrameLayout(mContext);
        mLoadFrameLayout.setContentView(mRootView);
        mLoadFrameLayout.setErrorView(errorView);
        ButterKnife.bind(this, mLoadFrameLayout);
        return mLoadFrameLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

    protected void setupList(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter adapter) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        /* 空页面 */
        mEmptyView = inflater.inflate(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent(), false);
        TextView tvEmptyViewMsg = (TextView) mEmptyView.findViewById(R.id.tv_empty_view_msg);
        tvEmptyViewMsg.setText(getEmptyViewMsg());

        /* 没有更多数据 */
        mNoMoreView = inflater.inflate(R.layout.layout_no_more_view, (ViewGroup) recyclerView.getParent(), false);

        /* 刷新 */
        mRefreshLayout = refreshLayout;
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.accent));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(true);
            }
        });

        /* 加载更多 */
        View loadingView = inflater.inflate(R.layout.layout_load_more, (ViewGroup) recyclerView.getParent(), false);
        adapter.setLoadingView(loadingView);
        adapter.openLoadAnimation();
        adapter.openLoadMore(ApiConstants.ParamValue.PAGE_SIZE);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestData(false);
            }
        });

        /* 首次进入自动刷新 */
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                requestData(true);
            }
        }, 200);

        recyclerView.setAdapter(adapter);
    }

    protected void requestData(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (mIsRefresh) mPage = 1;
        else mPage++;
    }

    protected String getEmptyViewMsg() {
        return "没有更多内容";
    }

    protected <T> void setData(List<T> datas, BaseQuickAdapter adapter) {
        if (mIsRefresh) {
            adapter.setNewData(datas);
            if (datas.size() == 0 && adapter.getEmptyView() == null) {
                adapter.setEmptyView(mEmptyView);
            } else if (datas.size() < ApiConstants.ParamValue.PAGE_SIZE) {
                adapter.loadComplete();
                adapter.addFooterView(mNoMoreView);
            }
        } else {
            if (datas.size() == 0) {
                adapter.loadComplete();
                adapter.addFooterView(mNoMoreView);
            } else adapter.addData(datas);
        }
    }

    public void showDialog(CharSequence msg) {
    }

    public void onSuccess() {
        mRefreshLayout.setRefreshing(false);
        mLoadFrameLayout.showContentView();
    }

    public void onError() {
        mRefreshLayout.setRefreshing(false);
        mLoadFrameLayout.showErrorView();
    }

    @Override
    public void onClick(View v) {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
            mLoadFrameLayout.showContentView();
            requestData(true);
        }
    }
}