package com.hunter.dribbble.ui.profile.followers;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPFragment;
import com.hunter.dribbble.entity.FollowerEntity;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProfileFollowersFragment extends BaseMVPFragment<FollowersPresenter, FollowersModel> implements
        FollowersContract.View {

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
    protected int getLayoutId() {
        return R.layout.fragment_profile_followers;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        initList();
        mPresenter.getFollowers(getArguments().getString(AppConstants.EXTRA_USER_ID));
    }

    private void initList() {
        mAdapter = new FollowersAdapter(mActivity, new ArrayList<FollowerEntity>());
        mRvFollowers.setAdapter(mAdapter);
        mRvFollowers.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvFollowers.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.LIST_VERTICAL));
    }

    @Override
    public void getFollowersOnSuccess(List<FollowerEntity> data) {
        mAdapter.reloadData(data);
    }
}
