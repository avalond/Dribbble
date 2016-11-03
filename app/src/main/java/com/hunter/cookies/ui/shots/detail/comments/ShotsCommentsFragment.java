package com.hunter.cookies.ui.shots.detail.comments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.cookies.R;
import com.hunter.cookies.base.mvp.BaseMVPListFragment;
import com.hunter.cookies.entity.CommentEntity;
import com.hunter.cookies.entity.ShotsEntity;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShotsCommentsFragment extends BaseMVPListFragment<ShotsCommentsPresenter, ShotsCommentsModel> implements
        ShotsCommentsContract.View {

    public static final String ARGS_SHOTS_ID = "args_shots_id";

    @BindView(R.id.rv_single_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_single_list)
    SwipeRefreshLayout mRefresh;

    private int mShotsId;

    public static ShotsCommentsFragment newInstance(int shotsId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_SHOTS_ID, shotsId);
        ShotsCommentsFragment fragment = new ShotsCommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_single_list_with_refresh;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mShotsId = getArguments().getInt(ARGS_SHOTS_ID);
        initList();
    }

    @Override
    protected String getEmptyViewMsg() {
        return "还没有人评论哦";
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getComments(mShotsId, mPage);
    }

    private void initList() {
        CommentsAdapter adapter = new CommentsAdapter(new ArrayList<CommentEntity>());
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.LIST_VERTICAL));
        setupList(mRefresh, mRvList, adapter);
    }

    @Override
    public void getCommentsOnSuccess(List<CommentEntity> data) {
        setData(data);
    }

    @Override
    public void putCommentsOnSuccess(ShotsEntity data) {

    }

    @Override
    public void deleteCommentsOnSuccess() {

    }

    @Override
    public void onComplete() {
        mRefresh.setRefreshing(false);
        mRefresh.setEnabled(false);
    }

}
