package com.hunter.dribbble.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.mvp.comments.CommentsPresenter;
import com.hunter.dribbble.mvp.comments.CommentsView;
import com.hunter.dribbble.ui.adapter.CommentsAdapter;
import com.hunter.dribbble.ui.base.BaseMvpFragment;
import com.hunter.lib.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotsCommentsFragment extends BaseMvpFragment<CommentsPresenter> implements CommentsView {

    @BindView(R.id.rv_shots_detail_comments)
    RecyclerView mRvShotsComments;

    private CommentsAdapter mAdapter;

    @Override
    protected CommentsPresenter createPresenter() {
        return new CommentsPresenter(this);
    }

    public static ShotsCommentsFragment newInstance(ShotsEntity entity) {
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.EXTRA_SHOTS_ENTITY, entity);
        ShotsCommentsFragment fragment = new ShotsCommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shots_comments, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void init() {
        super.init();
        ShotsEntity shotsEntity = (ShotsEntity) getArguments().getSerializable(AppConstants.EXTRA_SHOTS_ENTITY);
        mPresenter.getShots(shotsEntity.getId() + "");

        mAdapter = new CommentsAdapter(mActivity, new ArrayList<CommentEntity>());
        mRvShotsComments.setAdapter(mAdapter);
        mRvShotsComments.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvShotsComments.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.LIST_VERTICAL));
    }

    @Override
    public void onRetryClick(View retryView) {

    }

    @Override
    public void onSuccess(List<CommentEntity> data) {
        Collections.reverse(data);
        mAdapter.reloadData(data);
    }
}
