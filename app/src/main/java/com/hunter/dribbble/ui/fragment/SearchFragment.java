package com.hunter.dribbble.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.ui.adapter.SearchHistoryAdapter;
import com.hunter.lib.util.CircularRevealAnim;
import com.hunter.lib.util.KeyBoardUtils;
import com.hunter.lib.util.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends DialogFragment {

    @BindView(R.id.ibtn_search)
    ImageButton  mIbtnSearch;
    @BindView(R.id.et_search_input)
    EditText     mEtSearchInput;
    @BindView(R.id.rv_search_history)
    RecyclerView mRvSearchHistory;
    @BindView(R.id.tv_search_clean)
    TextView     mTvSearchClean;

    private View mRootView;

    private CircularRevealAnim mCircularRevealAnim;

    private SearchHistoryAdapter mAdapter;
    private List<String>         mData;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, mRootView);
        setCancelable(false);

        initAnim();
        initSearchEvent();
        initHistory();

        return mRootView;
    }

    private void initAnim() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(new CircularRevealAnim.AnimListener() {
            @Override
            public void onHideAnimationEnd() {
                mEtSearchInput.setText("");
                dismiss();
            }

            @Override
            public void onShowAnimationEnd() {
                if (isVisible()) KeyBoardUtils.openKeyboard(getContext(), mEtSearchInput);
            }
        });

        /**
         * 显示
         */
        mIbtnSearch = (ImageButton) mRootView.findViewById(R.id.ibtn_search);
        mIbtnSearch.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mIbtnSearch.getViewTreeObserver().removeOnPreDrawListener(this);
                mCircularRevealAnim.show(mIbtnSearch, mRootView);
                return true;
            }
        });

        /**
         * 隐藏
         */
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    hideAnim();
                }
                return false;
            }
        });

    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.96);
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    private void initSearchEvent() {
        mEtSearchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) search();
                return false;
            }
        });
    }

    private void initHistory() {
        mData = SPUtils.getStrList(getContext(), AppConstants.SP_SEARCH_HISTORY);
        mAdapter = new SearchHistoryAdapter(getContext(), mData, mTvSearchClean);

        mRvSearchHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvSearchHistory.setAdapter(mAdapter);

        /**
         * 清除历史记录
         */
        if (mData.size() != 0) {
            mTvSearchClean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.clean();
                }
            });
        } else {
            mTvSearchClean.setVisibility(View.GONE);
        }

        /**
         * Item 事件
         */
        mAdapter.setHistoryClickListener(new SearchHistoryAdapter.OnItemHistoryClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                mAdapter.orderChange(position, data);
                mEtSearchInput.setText(data);
                search();
            }
        });
        mAdapter.setHistoryDeleteListener(new SearchHistoryAdapter.OnItemHistoryDeleteListener() {
            @Override
            public void onItemDelete(String data, int position) {
                mAdapter.remove(position);
            }
        });

    }

    @OnClick({R.id.ibtn_search_back, R.id.view_search_outside})
    void hideAnim() {
        KeyBoardUtils.closeKeyboard(getContext(), mEtSearchInput);
        mCircularRevealAnim.hide(mIbtnSearch, mRootView);
    }

    @OnClick(R.id.ibtn_search)
    void search() {
        String searchKey = mEtSearchInput.getText().toString();
        if (TextUtils.isEmpty(searchKey)) return;

        hideAnim();

        if (mData.contains(searchKey)) mData.remove(searchKey);
        mData.add(0, searchKey);
    }

    @Override
    public void onPause() {
        super.onPause();
        SPUtils.putStrList(getContext(), AppConstants.SP_SEARCH_HISTORY, mAdapter.getData());
    }
}
