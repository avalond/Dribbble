package com.hunter.dribbble.ui.shots.detail.comments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPFragment;
import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class ShotsCommentsFragment extends BaseMVPFragment<ShotsCommentsPresenter, ShotsCommentsModel> implements
        ShotsCommentsContract.View {

    @BindView(R.id.rv_shots_detail_comments)
    RecyclerView mRvShotsComments;

    private CommentsAdapter mAdapter;

    public static ShotsCommentsFragment newInstance(ShotsEntity entity) {
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.EXTRA_SHOTS_ENTITY, entity);
        ShotsCommentsFragment fragment = new ShotsCommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shots_comments;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        initList();

        ShotsEntity shotsEntity = (ShotsEntity) getArguments().getSerializable(AppConstants.EXTRA_SHOTS_ENTITY);
        mPresenter.getComments(shotsEntity.getId() + "");
    }

    private void initList() {
        mAdapter = new CommentsAdapter(mActivity, new ArrayList<CommentEntity>());
        mRvShotsComments.setAdapter(mAdapter);
        mRvShotsComments.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvShotsComments.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.LIST_VERTICAL));
    }

    @Override
    public void getCommentsOnSuccess(List<CommentEntity> data) {
        Collections.reverse(data);
        mAdapter.reloadData(data);
    }
}
