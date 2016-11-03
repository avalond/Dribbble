package com.hunter.cookies.ui.user.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hunter.cookies.App;
import com.hunter.cookies.R;
import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.base.mvp.BaseMVPListActivity;
import com.hunter.cookies.entity.ShotsEntity;
import com.hunter.cookies.ui.shots.list.ShotsAdapter;
import com.hunter.cookies.utils.ViewModelUtils;

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

    private ShotsAdapter mShotsAdapter;

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
    }

    private void initShotsList() {
        mShotsAdapter = new ShotsAdapter(this, new ArrayList<ShotsEntity>());
        ViewModelUtils.changeLayoutManager(mRvResult, App.getAppConfig().getViewMode());
        setupList(mRefresh, mRvResult, mShotsAdapter);
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
        setData(data);
    }

    @Override
    public void onComplete() {
        mRefresh.setRefreshing(false);
        mRefresh.setEnabled(false);
    }

    @OnClick(R.id.ibtn_search_back)
    void finishActivity() {
        finish();
    }
}
