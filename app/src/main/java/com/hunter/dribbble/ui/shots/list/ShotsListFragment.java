package com.hunter.dribbble.ui.shots.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hunter.dribbble.App;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPListFragment;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.event.EventViewMode;
import com.hunter.dribbble.ui.profile.ProfileActivity;
import com.hunter.dribbble.ui.shots.detail.ShotsDetailActivity;
import com.hunter.dribbble.utils.ViewModelUtils;
import com.hunter.dribbble.utils.listener.SimpleAdapterClickListener;
import com.hunter.dribbble.widget.spinner.MaterialSpinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShotsListFragment extends BaseMVPListFragment<ShotsListPresenter, ShotsListModel> implements
        MaterialSpinner.OnItemSelectedListener, ShotsListContract.View {

    @BindView(R.id.rv_single_list)
    RecyclerView mRvShotsList;
    @BindView(R.id.refresh_single_list)
    SwipeRefreshLayout mRefresh;

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
        return R.layout.layout_single_list_with_refresh;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        initList();
        setupList(mRefresh, mRvShotsList, mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initList() {
        mAdapter = new ShotsListAdapter(new ArrayList<ShotsEntity>());
        mRvShotsList.setAdapter(mAdapter);

        ViewModelUtils.changeLayoutManager(mRvShotsList, App.getInstance().getViewMode());
        mRvShotsList.setItemAnimator(new DefaultItemAnimator());
        mRvShotsList.addOnItemTouchListener(new SimpleAdapterClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                /* 点击进入 shots 详情 */
                Intent intent = new Intent(mActivity, ShotsDetailActivity.class);
                intent.putExtra(ShotsDetailActivity.EXTRA_SHOTS_ENTITY, mAdapter.getItem(i));
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                /* 点击进入用户详情 */
                Intent intent = new Intent(mActivity, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, mAdapter.getItem(i).getUser());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.getShots(mType, mSort, mTime, mPage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeViewMode(EventViewMode event) {
        ViewModelUtils.changeLayoutManager(mRvShotsList, event.viewMode);
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

        mRefresh.setRefreshing(true);
        requestData(true);
    }

    @Override
    public void getShotsOnSuccess(List<ShotsEntity> data) {
        setData(data, mAdapter);
    }
}
