package com.hunter.cookies.ui.shots.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hunter.cookies.App;
import com.hunter.cookies.R;
import com.hunter.cookies.base.mvp.BaseMVPListFragment;
import com.hunter.cookies.entity.ShotsEntity;
import com.hunter.cookies.event.EventViewMode;
import com.hunter.cookies.utils.ViewModelUtils;
import com.hunter.cookies.widget.spinner.MaterialSpinner;

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initList() {
        ShotsAdapter adapter = new ShotsAdapter(getActivity(), new ArrayList<ShotsEntity>());
        ViewModelUtils.changeLayoutManager(mRvShotsList, App.getAppConfig().getViewMode());
        setupList(mRefresh, mRvShotsList, adapter);
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
        setData(data);
    }
}
