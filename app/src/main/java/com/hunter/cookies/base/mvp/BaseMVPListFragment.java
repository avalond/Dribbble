package com.hunter.cookies.base.mvp;

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
import com.hunter.cookies.R;
import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.base.BaseFragment;
import com.hunter.cookies.widget.CustomLoadMore;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseMVPListFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment {

    public P mPresenter;
    public M mModel;

    private SwipeRefreshLayout mRefreshLayout;

    private BaseQuickAdapter mAdapter;

    protected boolean mIsRefresh;
    protected boolean mIsLoadMoreFail;
    protected int mPage;

    private View mErrorView;
    private View mEmptyView;

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
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

    protected void setupList(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter adapter) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mAdapter = adapter;

        ViewGroup container = (ViewGroup) recyclerView.getParent();
        /* 没有数据 */
        mEmptyView = inflater.inflate(R.layout.layout_empty_view, container, false);
        TextView tvEmptyViewMsg = (TextView) mEmptyView.findViewById(R.id.tv_empty_view_msg);
        tvEmptyViewMsg.setText(getEmptyViewMsg());

        /* 加载失败 */
        mErrorView = inflater.inflate(R.layout.layout_error_view, (ViewGroup) recyclerView.getParent(), false);
        TextView tvErrorViewMsg = (TextView) mErrorView.findViewById(R.id.tv_error_view_retry);
        tvErrorViewMsg.setText(getErrorViewMsg());
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefreshLayout.setRefreshing(true);
                requestData(true);
            }
        });

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
        mAdapter.setLoadMoreView(new CustomLoadMore());
        mAdapter.setAutoLoadMoreSize(18);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    protected void requestData(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (mIsRefresh) {
            mPage = 1;
        } else {
            if (mIsLoadMoreFail) mIsLoadMoreFail = false;
            else mPage++;
        }
    }

    protected int getPageSize() {
        return ApiConstants.ParamValue.PAGE_SIZE;
    }

    protected String getEmptyViewMsg() {
        return "没有更多内容";
    }

    public String getErrorViewMsg() {
        return "重新加载";
    }

    protected <T> void setData(List<T> datas) {
        mAdapter.isUseEmpty(true);
        if (mIsRefresh) {
            mAdapter.setNewData(datas);
            if (datas == null || datas.size() == 0) {
                mAdapter.setEmptyView(mEmptyView);
                mAdapter.notifyDataSetChanged();
            } else if (datas.size() < getPageSize()) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.loadMoreComplete();
            }
        } else {
            if (datas.size() == 0) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.addData(datas);
                mAdapter.loadMoreComplete();
            }
        }
    }

    public void showLoading() {
    }

    public void showError() {
        if (mIsRefresh) {
            mAdapter.setEmptyView(mErrorView);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.loadMoreFail();
        }
    }

    public void onComplete() {
        mRefreshLayout.setRefreshing(false);
    }

}