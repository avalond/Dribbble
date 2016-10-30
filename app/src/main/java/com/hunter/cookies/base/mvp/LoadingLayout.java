package com.hunter.cookies.base.mvp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.hunter.cookies.R;

public abstract class LoadingLayout extends FrameLayout {

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public int state = STATE_UNKNOWN;
    private Context mContext;
    private View loadingView;// 加载中的界面
    private View errorView;// 错误界面
    private View emptyView;// 空界面
    private View successView;// 加载成功的界面

    public LoadingLayout(Context context) {
        this(context, null, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        loadingView = createLoadingView(); // 创建了加载中的界面
        if (loadingView != null) {
            this.addView(loadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        errorView = createErrorView(); // 加载错误界面
        if (errorView != null) {
            this.addView(errorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        emptyView = createEmptyView(); // 加载空的界面
        if (emptyView != null) {
            this.addView(emptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        /**
         * 注意这里依赖是就初始化成功的界面，也可以在状态成功的时候才初始化成功的界面
         */
        successView = createSuccessView();
        if (successView != null) {
            this.addView(successView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        showPage();// 根据不同的状态显示不同的界面
    }

    // 根据不同的状态显示不同的界面
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(state == STATE_UNKNOWN || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (state == STATE_SUCCESS) {
            if (successView == null) {
                successView = createSuccessView();
                this.addView(successView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            successView.setVisibility(View.VISIBLE);
        } else {
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /* 创建了空的界面 */
    private View createEmptyView() {
        return View.inflate(mContext, R.layout.loadpage_empty, null);
    }

    /* 创建了错误界面 */
    private View createErrorView() {
        View view = View.inflate(mContext, R.layout.loadpage_error, null);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    /* 创建加载中的界面 */
    private View createLoadingView() {
        return View.inflate(mContext, R.layout.loadpage_loading, null);
    }

    /**
     * 枚举类，对应相应的状态码，用来表示各种状态
     */
    public enum LoadResult {
        loading(1), error(2), empty(3), success(4);
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 注意请求成功后必须调用show方法，会根据这个压面显示相应的数据
     *
     * @param loadResult
     */
    public void show(LoadResult loadResult) {
        state = loadResult.getValue();
        showPage();
    }

    /***
     * 创建成功的界面
     *
     * @return
     */
    public abstract View createSuccessView();
}