package com.hunter.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.hunter.adapter.animation.AlphaInAnimation;
import com.hunter.adapter.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class BaseQuickAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int ITEM_VIEW = 100;
    public static final int HEADER_VIEW = 101;
    public static final int LOADING_VIEW = 102;
    public static final int FOOTER_VIEW = 103;
    public static final int EMPTY_VIEW = 104;
    public static final int ERROR_VIEW = 105;

    public static final int DEF_ANIM_DURATION = 225;

    private boolean mNextLoadEnable;

    private boolean mLoadingMoreEnable;

    private boolean mFirstOnlyEnable = true;
    private boolean mOpenAnimationEnable;

    private boolean mIsUseEmpty = true;

    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;

    private int mDuration = DEF_ANIM_DURATION;

    private int mLastAddedAnimPos = -1;

    private RequestLoadMoreListener mRequestLoadMoreListener;

    private BaseAnimation mItemAnimation;

    private LinearLayout mHeaderView;
    private LinearLayout mFooterView;
    private View mEmptyView;
    private View mLoadingView;
    private View mNoMoreView;

    private int mPageSize = -1;

    private View mLoadMoreFailedView;

    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;

    public interface RequestLoadMoreListener {

        void onLoadMoreRequested();
    }

    public BaseQuickAdapter(List<T> data) {
        this(0, data);
    }

    public BaseQuickAdapter(int layoutResId, List<T> data) {
        mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) mLayoutResId = layoutResId;
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        mRequestLoadMoreListener = requestLoadMoreListener;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void openLoadMore(int pageSize) {
        mPageSize = pageSize;
        mNextLoadEnable = true;
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position + getHeaderViewCount());
    }

    public void add(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void setNewData(List<T> data) {
        mData = data == null ? new ArrayList<T>() : data;
        if (mRequestLoadMoreListener != null) {
            mNextLoadEnable = true;
            // mFooterView = null;
        }
        if (mLoadMoreFailedView != null) {
            removeFooterView(mLoadMoreFailedView);
        }
        mLastAddedAnimPos = -1;
        notifyDataSetChanged();
    }

    public void addData(int position, T data) {
        if (0 <= position && position < mData.size()) {
            mData.add(position, data);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, mData.size() - position);
        }
    }

    public void addData(T data) {
        mData.add(data);
        notifyItemInserted(mData.size() - 1);
    }

    public void addData(int position, List<T> data) {
        if (0 <= position && position < mData.size()) {
            mData.addAll(position, data);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, mData.size() - position - data.size());
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }

    public void addData(List<T> newData) {
        mData.addAll(newData);
        hideLoadingMore();
        //        notifyItemRangeInserted(mData.size() - newData.size() + getHeaderViewCount(), newData.size());
        notifyDataSetChanged();
    }

    public boolean isLoading() {
        return mLoadingMoreEnable;
    }

    public void hideLoadingMore() {
        if (mNextLoadEnable) mLoadingMoreEnable = false;
    }

    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
    }

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public int getHeaderViewCount() {
        return mHeaderView == null ? 0 : 1;
    }

    public int getFooterLayoutCount() {
        return mFooterView == null ? 0 : 1;
    }

    public int getEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        int i = isLoadMore() ? 1 : 0;
        int count = mData.size() + i + getHeaderViewCount() + getFooterLayoutCount();
        if (mData.size() == 0 && mEmptyView != null && mIsUseEmpty) {
            if (count == 0 && (!mHeadAndEmptyEnable || !mFootAndEmptyEnable)) {
                count += getEmptyViewCount();
            } else if (mHeadAndEmptyEnable || mFootAndEmptyEnable) {
                count += getEmptyViewCount();
            }

            if ((mHeadAndEmptyEnable && getHeaderViewCount() == 1 && count == 1) || count == 0) {
                count += getEmptyViewCount();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) return HEADER_VIEW;

        /* 没有数据时，已设置 EmptyView */
        if (mData.size() == 0 && position <= 2) {
            if (position == 0)
            if ((mHeadAndEmptyEnable || mFootAndEmptyEnable) && position == 1) {
                /* 显示 footer */
                if (mHeaderView == null && mFooterView != null) return FOOTER_VIEW;
                /* 显示 EmptyView*/
                else if (mHeaderView != null) return EMPTY_VIEW;
            } else if (position == 0) {
                if (mHeaderView == null) return EMPTY_VIEW;
                else if (mFooterView != null) return EMPTY_VIEW;
            } else if (position == 2 && (mFootAndEmptyEnable || mHeadAndEmptyEnable) && mHeaderView != null &&
                    mEmptyView != null) {
                return FOOTER_VIEW;
            } else if ((!mFootAndEmptyEnable || !mHeadAndEmptyEnable) && position == 1 && mFooterView != null) {
                return FOOTER_VIEW;
            }
        } else if (mData.size() == 0 && mEmptyView != null && getItemCount() == (mHeadAndEmptyEnable ? 2 : 1)) {
            return EMPTY_VIEW;
        } else if (position == mData.size() + getHeaderViewCount()) {
            if (mNextLoadEnable) return LOADING_VIEW;
            else return FOOTER_VIEW;
        } else if (position > mData.size() + getHeaderViewCount()) {
            return FOOTER_VIEW;
        }
        return getDefItemViewType(position - getHeaderViewCount());
    }

    protected int getDefItemViewType(int position) {
        return ITEM_VIEW;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH viewHolder;
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case HEADER_VIEW:
                viewHolder = createViewHolder(mHeaderView);
                break;
            case FOOTER_VIEW:
                viewHolder = createViewHolder(mFooterView);
                break;
            case LOADING_VIEW:
                if (mLoadingView == null) viewHolder = createViewHolder(parent, R.layout.def_loading);
                else viewHolder = createViewHolder(mLoadingView);
                break;
            case EMPTY_VIEW:
                viewHolder = createViewHolder(mEmptyView);
                break;
            default:
                viewHolder = onCreateDefViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    protected VH createViewHolder(View view) {
        return (VH) new BaseViewHolder(view);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    /**
     * 当布局为 StaggeredGridLayoutManager 时需要占满整个宽度
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder
                    .itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastAddedAnimPos) {
                BaseAnimation animation;
                if (mItemAnimation != null) {
                    animation = mItemAnimation;
                    for (Animator anim : animation.getAnimators(holder.itemView)) {
                        anim.setDuration(mDuration).start();
                        anim.setInterpolator(new AccelerateDecelerateInterpolator());
                    }
                }
                mLastAddedAnimPos = holder.getLayoutPosition();
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        /* 返回当前所在行数的最大列数，为了占满一行 */
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW)
                        return gridManager.getSpanCount();
                    return 1;
                }
            });
        }

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mRequestLoadMoreListener != null && mPageSize == -1)
                    openLoadMore(recyclerView.getLayoutManager().getChildCount());
            }
        });
    }

    @Override
    public void onBindViewHolder(VH holder, int positions) {
        switch (holder.getItemViewType()) {
            case LOADING_VIEW:
                addLoadMore();
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                convert(holder, mData.get(holder.getLayoutPosition() - getHeaderViewCount()));
                break;
        }
    }

    private void addLoadMore() {
        if (isLoadMore() && !mLoadingMoreEnable) {
            mLoadingMoreEnable = true;
            mRequestLoadMoreListener.onLoadMoreRequested();
        }
    }

    private boolean isLoadMore() {
        return mNextLoadEnable && mPageSize != -1 && mRequestLoadMoreListener != null && mData.size() >= mPageSize;
    }

    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }

    protected VH createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createViewHolder(getItemView(layoutResId, parent));
    }

    public LinearLayout getHeaderView() {
        return mHeaderView;
    }

    public void addHeaderView(View header) {
        addHeaderView(header, -1);
    }

    public void addHeaderView(View header, int index) {
        addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public void addHeaderView(View header, int index, int orientation) {
        if (mHeaderView == null) {
            mHeaderView = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderView.setOrientation(LinearLayout.VERTICAL);
                mHeaderView.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderView.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderView.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        index = index >= mHeaderView.getChildCount() ? -1 : index;
        mHeaderView.addView(header, index);
        notifyDataSetChanged();
    }

    public void removeHeaderView(View header) {
        if (mHeaderView == null) return;
        mHeaderView.removeView(header);
        if (mHeaderView.getChildCount() == 0) mHeaderView = null;
        notifyItemChanged(0);
    }

    public void removeAllHeaderView() {
        if (mHeaderView == null) return;
        mHeaderView.removeAllViews();
        mHeaderView = null;
        notifyItemChanged(0);
    }

    public LinearLayout getFooterView() {
        return mFooterView;
    }

    public void addFooterView(View footer) {
        addFooterView(footer, -1);
    }

    public void addFooterView(View footer, int index) {
        if (footer == null) return;
        mNextLoadEnable = false;
        if (mFooterView == null) {
            mFooterView = new LinearLayout(footer.getContext());
            mFooterView.setOrientation(LinearLayout.VERTICAL);
            mFooterView.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        index = index >= mFooterView.getChildCount() ? -1 : index;
        mFooterView.addView(footer, index);
        notifyItemChanged(getItemCount());
    }

    public void removeFooterView(View footer) {
        if (mFooterView == null) return;
        mFooterView.removeView(footer);
        if (mFooterView.getChildCount() == 0) mFooterView = null;
        notifyItemChanged(mData.size());
    }

    public void removeAllFooterView() {
        if (mFooterView == null) return;
        mFooterView.removeAllViews();
        mFooterView = null;
        notifyItemChanged(mData.size());
    }

    public void setLoadMoreFailedView(View view) {
        mLoadMoreFailedView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFooterView(mLoadMoreFailedView);
                openLoadMore(mPageSize);
            }
        });
    }

    public void showLoadMoreFailedView() {
        notifyComplete();
        if (mLoadMoreFailedView == null) {
            mLoadMoreFailedView = mLayoutInflater.inflate(R.layout.def_load_more_failed, null);
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFooterView(mLoadMoreFailedView);
                    openLoadMore(mPageSize);
                }
            });
        }
        addFooterView(mLoadMoreFailedView);
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View emptyView) {
        setEmptyView(false, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
        mEmptyView = emptyView;
    }

    public void isUseEmpty(boolean isUseEmpty) {
        mIsUseEmpty = isUseEmpty;
    }

    public void notifyComplete() {
        mNextLoadEnable = false;
        mLoadingMoreEnable = false;
        notifyDataSetChanged();
    }

    public void loadComplete(boolean isRefresh, List<T> datas) {
        if (datas == null) datas = new ArrayList<>();
        if (isRefresh) {
            setNewData(datas);
            if (datas.size() < mPageSize) {
                notifyComplete();
                addFooterView(mNoMoreView);
            }
        } else {
            if (datas.size() == 0) {
                notifyComplete();
                addFooterView(mNoMoreView);
            } else {
                addData(datas);
            }
        }
    }

    public void isFirstOnly(boolean firstOnly) {
        mFirstOnlyEnable = firstOnly;
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public void openItemAnimation() {
        openItemAnimation(new AlphaInAnimation());
    }

    public void openItemAnimation(BaseAnimation animation) {
        mOpenAnimationEnable = true;
        mItemAnimation = animation;
    }

    protected abstract void convert(VH helper, T item);

    @Override
    public long getItemId(int position) {
        return position;
    }

}
