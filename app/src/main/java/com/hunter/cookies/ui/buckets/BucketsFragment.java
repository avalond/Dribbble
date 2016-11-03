package com.hunter.cookies.ui.buckets;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.cookies.R;
import com.hunter.cookies.base.mvp.BaseMVPListFragment;
import com.hunter.cookies.entity.BucketsEntity;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BucketsFragment extends BaseMVPListFragment<BucketsPresenter, BucketsModel> implements
        BucketsContract.View {

    @BindView(R.id.rv_buckets)
    RecyclerView mRvBuckets;
    @BindView(R.id.refresh_buckets)
    SwipeRefreshLayout mRefresh;

    private BucketsAdapter mAdapter;

    public static BucketsFragment newInstance() {
        Bundle args = new Bundle();
        BucketsFragment fragment = new BucketsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buckets;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mAdapter = new BucketsAdapter(new ArrayList<BucketsEntity>());
        mRvBuckets.setLayoutManager(new LinearLayoutManager(mContext));
        mRvBuckets.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.LIST_VERTICAL));
        setupList(mRefresh, mRvBuckets, mAdapter);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getBuckets();
    }

    @Override
    public void getBucketsOnSuccess(List<BucketsEntity> datas) {
        setData(datas);
    }

}
