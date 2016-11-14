package com.hunter.cookies.base.mvp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter.adapter.BaseQuickAdapter;
import com.hunter.adapter.animation.AlphaInAnimation;
import com.hunter.adapter.animation.BaseAnimation;
import com.hunter.cookies.R;
import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.base.BaseFragment;

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
        View emptyView = inflater.inflate(R.layout.layout_empty_view, container, false);
        TextView tvEmptyViewMsg = (TextView) emptyView.findViewById(R.id.tv_empty_view_msg);
        tvEmptyViewMsg.setText(getEmptyViewMsg());
        mAdapter.setEmptyView(emptyView);

        /* 没有更多数据 */
        View noMoreView = inflater.inflate(R.layout.layout_no_more_view, container, false);
        mAdapter.setLoadNoMoreView(noMoreView);


        /* 加载失败 */
        View errorView = inflater.inflate(R.layout.layout_error_view, container, false);
        TextView tvErrorViewMsg = (TextView) errorView.findViewById(R.id.tv_error_view_retry);
        tvErrorViewMsg.setText(getErrorViewMsg());
        tvErrorViewMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefreshLayout.setRefreshing(true);
                requestData(true);
            }
        });
        mAdapter.setErrorView(errorView);

        /* 加载更多失败 */
        View loadMoreFail = inflater.inflate(R.layout.layout_load_more_fail, container, false);
        loadMoreFail.findViewById(R.id.tv_load_more_fail_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showLoading();
            }
        });
        loadMoreFail.findViewById(R.id.tv_load_more_fail_ignore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.resetItemStatus();
            }
        });

        mAdapter.setLoadMoreFailedView(loadMoreFail);


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
        View loadingView = inflater.inflate(R.layout.layout_load_more, container, false);
        mAdapter.setLoadingView(loadingView);
        mAdapter.openItemAnimation();
        mAdapter.openLoadMore(ApiConstants.ParamValue.PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.LoadMoreListener() {
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

        mAdapter.openItemAnimation(getItemAnimator());

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

    protected String getEmptyViewMsg() {
        return "没有更多内容";
    }

    public String getErrorViewMsg() {
        return "重新加载";
    }

    protected <T> void setData(List<T> datas) {
        mAdapter.loadSuccess(mIsRefresh, datas);
    }

    public void showLoading() {
    }

    public void showError(CharSequence errorMsg) {
        if (mIsRefresh) {
            mAdapter.loadOnError();
        } else {
            mAdapter.showLoadMoreFailedView();
            mIsLoadMoreFail = true;
        }
    }

    public void onComplete() {
        mRefreshLayout.setRefreshing(false);
    }

    protected BaseAnimation getItemAnimator() {
        return new AlphaInAnimation();
    }
}