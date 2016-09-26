package com.hunter.dribbble.ui.user.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hunter.dribbble.App;
import com.hunter.dribbble.R;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.base.mvp.BaseMVPListActivity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.profile.ProfileActivity;
import com.hunter.dribbble.ui.shots.detail.ShotsDetailActivity;
import com.hunter.dribbble.ui.shots.list.ShotsListAdapter;
import com.hunter.dribbble.utils.ViewModelUtils;
import com.hunter.dribbble.utils.listener.SimpleAdapterClickListener;

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
        mShotsAdapter = new ShotsListAdapter(new ArrayList<ShotsEntity>());
        mRvResult.setAdapter(mShotsAdapter);

        ViewModelUtils.changeLayoutManager(mRvResult, App.getInstance().getViewMode());
        mRvResult.setItemAnimator(new DefaultItemAnimator());
        mRvResult.addOnItemTouchListener(new SimpleAdapterClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                /* 点击进入 shots 详情 */
                Intent intent = new Intent(SearchActivity.this, ShotsDetailActivity.class);
                intent.putExtra(ShotsDetailActivity.EXTRA_SHOTS_ENTITY, mShotsAdapter.getItem(i));
                intent.putExtra(ShotsDetailActivity.EXTRA_IS_FROM_SEARCH, true);
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                /* 点击进入用户详情 */
                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, mShotsAdapter.getItem(i).getUser());
                startActivity(intent);
            }
        });
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
