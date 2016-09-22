package com.hunter.dribbble.ui.shots.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPFragment;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.event.EventViewMode;
import com.hunter.dribbble.ui.profile.ProfileActivity;
import com.hunter.dribbble.ui.shots.detail.ShotsDetailActivity;
import com.hunter.dribbble.utils.LayoutManagerUtils;
import com.hunter.dribbble.widget.spinner.MaterialSpinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShotsListFragment extends BaseMVPFragment<ShotsListPresenter, ShotsListModel> implements
        MaterialSpinner.OnItemSelectedListener, ShotsListContract.View {

    @BindView(R.id.rv_shots_list)
    RecyclerView mRvShotsList;

    private ShotsListAdapter mAdapter;

    private int mType;
    private int mSort;
    private int mTime;

    public static ShotsListFragment newInstance() {
        Bundle args = new Bundle();
        ShotsListFragment fragment = new ShotsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shots_list;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        initList();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initList() {
        mAdapter = new ShotsListAdapter(new ArrayList<ShotsEntity>());
        mRvShotsList.setAdapter(mAdapter);
        mRvShotsList.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRvShotsList.setItemAnimator(new DefaultItemAnimator());
        mRvShotsList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivityWithOptions(mRvShotsList.getChildAt(i).findViewById(R.id.drawee_item_shots_preview),
                                         mAdapter.getItem(i));
            }
        });
        mAdapter.setUserInfoListener(new ShotsListAdapter.OnItemClickUserInfoListener() {
            @Override
            public void onItemClickUserInfo(UserEntity entity) {
                Intent intent = new Intent(mActivity, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, entity);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        mPresenter.getShots(mType, mSort, mTime);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeViewMode(EventViewMode event) {
        LayoutManagerUtils.changeLayoutManager(mRvShotsList, mContext, event.viewMode);
    }

    @Override
    public void onItemSelected(View view, int position, Object item) {
        switch (view.getId()) {
            case R.id.spinner_selector_sort:
                mSort = position;
                break;

            case R.id.spinner_selector_type:
                mType = position;
                break;

            case R.id.spinner_selector_time:
                mTime = position;
                break;
        }
        loadData();
    }

    private void startActivityWithOptions(View view, ShotsEntity data) {
        Pair pair = new Pair<>(view, ViewCompat.getTransitionName(view));
        ActivityOptionsCompat aoc = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);
        Intent intent = new Intent(mActivity, ShotsDetailActivity.class);
        intent.putExtra(AppConstants.EXTRA_SHOTS_ENTITY, data);
        ActivityCompat.startActivity(getActivity(), intent, aoc.toBundle());
    }

    @Override
    public void getShotsOnSuccess(List<ShotsEntity> data) {
        mAdapter.setNewData(data);
    }

}
