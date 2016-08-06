package com.hunter.dribbble.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.FollowerEntity;
import com.hunter.dribbble.mvp.followers.FollowersPresenter;
import com.hunter.dribbble.mvp.followers.FollowersView;
import com.hunter.dribbble.ui.adapter.FollowersAdapter;
import com.hunter.dribbble.ui.base.BaseMvpFragment;
import com.hunter.library.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFollowersFragment extends BaseMvpFragment<FollowersPresenter> implements FollowersView {

    @BindView(R.id.rv_profile_followers)
    RecyclerView mRvFollowers;

    private FollowersAdapter mAdapter;

    public static ProfileFollowersFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(AppConstants.EXTRA_USER_ID, id);
        ProfileFollowersFragment fragment = new ProfileFollowersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_followers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init() {
        mAdapter = new FollowersAdapter(mActivity, new ArrayList<FollowerEntity>());
        mRvFollowers.setAdapter(mAdapter);
        mRvFollowers.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvFollowers.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.LIST_VERTICAL));

        mPresenter.getShots(getArguments().getString(AppConstants.EXTRA_USER_ID));
    }

    @Override
    protected FollowersPresenter createPresenter() {
        return new FollowersPresenter(this);
    }

    @Override
    public void onRetryClick(View retryView) {
    }

    @Override
    public void onSuccess(List<FollowerEntity> entities) {
        mAdapter.reloadData(entities);
    }
}
