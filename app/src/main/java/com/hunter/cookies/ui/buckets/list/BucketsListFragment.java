package com.hunter.cookies.ui.buckets.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.cookies.R;
import com.hunter.cookies.base.mvp.BaseMVPListFragment;
import com.hunter.cookies.entity.BucketsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BucketsListFragment extends BaseMVPListFragment<BucketsListPresenter, BucketsListModel> implements
        BucketsListContract.View {

    @BindView(R.id.rv_buckets_list)
    RecyclerView mRvBuckets;
    @BindView(R.id.refresh_buckets_list)
    SwipeRefreshLayout mRefresh;

    public static BucketsListFragment newInstance() {
        Bundle args = new Bundle();
        BucketsListFragment fragment = new BucketsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buckets_list;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        BucketsAdapter adapter = new BucketsAdapter(new ArrayList<BucketsEntity>());
        mRvBuckets.setLayoutManager(new LinearLayoutManager(mContext));
        setupList(mRefresh, mRvBuckets, adapter);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getBucketsList();
    }

    @Override
    public void getBucketsListOnSuccess(List<BucketsEntity> datas) {
        setData(datas);
    }

}
