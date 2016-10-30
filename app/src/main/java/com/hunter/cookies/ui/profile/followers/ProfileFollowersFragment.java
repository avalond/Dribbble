package com.hunter.cookies.ui.profile.followers;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.cookies.R;
import com.hunter.cookies.base.mvp.BaseMVPListFragment;
import com.hunter.cookies.entity.FollowerEntity;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProfileFollowersFragment extends BaseMVPListFragment<FollowersPresenter, FollowersModel> implements
        FollowersContract.View {

    public static final String ARGS_USER_ID = "args_user_id";

    @BindView(R.id.rv_single_list)
    RecyclerView mRvFollowers;
    @BindView(R.id.refresh_single_list)
    SwipeRefreshLayout mRefresh;

    private FollowersAdapter mAdapter;

    public static ProfileFollowersFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(ARGS_USER_ID, id);
        ProfileFollowersFragment fragment = new ProfileFollowersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_single_list_with_refresh;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        initList();
        setupList(mRefresh, mRvFollowers, mAdapter);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getFollowers(getArguments().getString(ARGS_USER_ID), mPage);
    }

    private void initList() {
        mAdapter = new FollowersAdapter(new ArrayList<FollowerEntity>());
        mRvFollowers.setAdapter(mAdapter);
        mRvFollowers.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvFollowers.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.LIST_VERTICAL));
    }

    @Override
    public void getFollowersOnSuccess(List<FollowerEntity> data) {
        setData(data, mAdapter);
    }

}
