package com.hunter.dribbble.ui.user.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hunter.dribbble.App;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.base.mvp.BaseMVPListActivity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.shots.list.ShotsListAdapter;
import com.hunter.dribbble.utils.ViewModelUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseMVPListActivity<SearchPresenter, SearchModel> implements SearchContract.View {

    public static final String EXTRA_SEARCH_KEY = "extra_search_key";

    @BindView(R.id.et_search_input)
    EditText mEtInput;
    @BindView(R.id.ibtn_search)
    ImageButton mIbtnSearch;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvResult;
    @BindView(R.id.refresh_search)
    SwipeRefreshLayout mRefresh;

    private String mSearchKey;

    private ShotsListAdapter mShotsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mSearchKey = getIntent().getStringExtra(EXTRA_SEARCH_KEY);
        mEtInput.setText(mSearchKey);
        mEtInput.setSelection(mSearchKey.length());

        initShotsList();
        setupList(mRefresh, mRvResult, mShotsAdapter);
    }

    private void initShotsList() {
        mShotsAdapter = new ShotsListAdapter(this, new ArrayList<ShotsEntity>());
        mRvResult.setAdapter(mShotsAdapter);

        ViewModelUtils.changeLayoutManager(mRvResult, App.getAppConfig().getViewMode());
        mRvResult.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void requestData(boolean isRefresh) {
        super.requestData(isRefresh);
        mPresenter.searchShots(mSearchKey, mPage);
    }

    @Override
    protected int getPageSize() {
        return ApiConstants.ParamValue.SEARCH_PAGE_SIZE;
    }

    @Override
    public void searchShotsOnSuccess(List<ShotsEntity> data) {
        setData(data, mShotsAdapter);
    }

    @Override
    public void onSuccess() {
        mRefresh.setRefreshing(false);
        mRefresh.setEnabled(false);
    }

    @OnClick(R.id.ibtn_search_back)
    void finishActivity() {
        finish();
    }
}
