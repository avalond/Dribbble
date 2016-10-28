package com.hunter.dribbble.ui.profile.shots;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPListFragment;
import com.hunter.dribbble.entity.ShotsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProfileShotsFragment extends BaseMVPListFragment<ProfileShotsPresenter, ProfileShotsModel> implements
        ProfileShotsContract.View {

    public static final String ARGS_USER_ID = "args_user_id";
    @BindView(R.id.rv_single_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_single_list)
    SwipeRefreshLayout mRefresh;

    private ProfileShotsAdapter mAdapter;

    private String mUid;

    public static ProfileShotsFragment newInstance(String uid) {
        Bundle args = new Bundle();
        args.putString(ARGS_USER_ID, uid);
        ProfileShotsFragment fragment = new ProfileShotsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_single_list_with_refresh;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mUid = getArguments().getString(ARGS_USER_ID);
        initList();
    }

    private void initList() {
        mAdapter = new ProfileShotsAdapter(new ArrayList<ShotsEntity>());
        mRvList.setLayoutManager(new GridLayoutManager(mContext, 2));
        setupList(mRefresh, mRvList, mAdapter);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getUserShots(mUid, mPage);
    }

    @Override
    public void getUserShotsOnSuccess(List<ShotsEntity> datas) {
        setData(datas, mAdapter);
    }
}
