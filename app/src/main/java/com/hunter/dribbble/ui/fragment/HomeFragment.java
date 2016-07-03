package com.hunter.dribbble.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.dribbble.App;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.event.EventViewMode;
import com.hunter.dribbble.mvp.shots.ShotsPresenter;
import com.hunter.dribbble.mvp.shots.ShotsView;
import com.hunter.dribbble.ui.adapter.ShotsHomeAdapter;
import com.hunter.dribbble.ui.base.BaseMvpFragment;
import com.hunter.dribbble.utils.ViewModeChangeUtils;
import com.hunter.dribbble.widget.spinner.MaterialSpinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseMvpFragment<ShotsPresenter> implements MaterialSpinner.OnItemSelectedListener,
                                                                             ShotsView {

    @BindView(R.id.rv_shots_home)
    RecyclerView mRvShotsHome;

    ShotsHomeAdapter mAdapter;

    private int mType;
    private int mSort;
    private int mTime;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        initList();

        loadData();
    }

    private void initList() {
        int viewMode = App.getInstance().getViewMode();
        mAdapter = new ShotsHomeAdapter(mActivity, new ArrayList<ShotsEntity>(), viewMode);
        ViewModeChangeUtils.setLayoutManager(mRvShotsHome, mActivity, mAdapter, viewMode);
        mRvShotsHome.setAdapter(mAdapter);
        mRvShotsHome.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadData() {
        mPresenter.getShots(ApiConstants.ParamValue.LIST_VALUES[mType],
                            ApiConstants.ParamValue.SORT_VALUES[mSort],
                            ApiConstants.ParamValue.TIME_VALUES[mTime]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeViewMode(EventViewMode event) {
        ViewModeChangeUtils.changeViewMode(mRvShotsHome, mActivity, mAdapter, event.viewMode);
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

    @Override
    protected ShotsPresenter createPresenter() {
        return new ShotsPresenter(this);
    }

    @Override
    public void onSuccess(List<ShotsEntity> entities) {
        mAdapter.reloadData(entities);
    }
}
