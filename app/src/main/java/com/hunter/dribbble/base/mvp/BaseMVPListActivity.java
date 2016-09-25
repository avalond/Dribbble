package com.hunter.dribbble.base.mvp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.base.BaseActivity;

import java.util.List;

public class BaseMVPListActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity {

    public P mPresenter;
    public M mModel;

    private SwipeRefreshLayout mRefreshLayout;

    protected boolean mIsRefresh;
    protected int mPage;

    private View mEmptyView;
    private View mNoMoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    protected void setupList(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter adapter) {
        LayoutInflater inflater = LayoutInflater.from(this);
        /* 空页面 */
        mEmptyView = inflater.inflate(R.layout.layout_empty_view, (ViewGroup) recyclerView.getParent(), false);
        TextView tvEmptyViewMsg = (TextView) mEmptyView.findViewById(R.id.tv_empty_view_msg);
        tvEmptyViewMsg.setText(getEmptyViewMsg());

        /* 没有更多数据 */
        mNoMoreView = inflater.inflate(R.layout.layout_no_more_view, (ViewGroup) recyclerView.getParent(), false);

        /* 刷新 */
        mRefreshLayout = refreshLayout;
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent));
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
        adapter.openLoadMore(getPageSize());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestData(false);
            }
        });

        /* 首次进入自动刷新 */
        mRefreshLayout.setRefreshing(true);
        requestData(true);
    }

    protected void requestData(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (mIsRefresh) mPage = 1;
        else mPage++;
    }

    protected String getEmptyViewMsg() {
        return "再怎么找也没有啦";
    }

    protected int getPageSize() {
        return ApiConstants.ParamValue.PAGE_SIZE;
    }

    protected <T> void setData(List<T> datas, BaseQuickAdapter adapter) {
        if (mIsRefresh) {
            adapter.setNewData(datas);
            if (datas.size() == 0 && adapter.getEmptyView() == null) {
                adapter.setEmptyView(mEmptyView);
            } else if (datas.size() < getPageSize()) {
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

    public void onCompleted() {
        mRefreshLayout.setRefreshing(false);
    }
}
