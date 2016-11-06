package com.hunter.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.hunter.adapter.animation.AlphaInAnimation;
import com.hunter.adapter.animation.BaseAnimation;
import com.hunter.adapter.animation.ScaleInAnimation;
import com.hunter.adapter.animation.SlideInBottomAnimation;
import com.hunter.adapter.animation.SlideInLeftAnimation;
import com.hunter.adapter.animation.SlideInRightAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class BaseQuickAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int ALPHA_IN = 0x00000001;
    public static final int SCALE_IN = 0x00000002;
    public static final int SLIDE_IN_BOTTOM = 0x00000003;
    public static final int SLIDE_IN_LEFT = 0x00000004;
    public static final int SLIDE_IN_RIGHT = 0x00000005;

    @IntDef({ALPHA_IN, SCALE_IN, SLIDE_IN_BOTTOM, SLIDE_IN_LEFT, SLIDE_IN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {

    }

    public static final int HEADER_VIEW = 0x00000001;
    public static final int LOADING_VIEW = 0x00000002;
    public static final int FOOTER_VIEW = 0x00000003;
    public static final int EMPTY_VIEW = 0x00000004;
    public static final int ERROR_VIEW = 0x00000005;

    private boolean mNextLoadEnable = false;

    private boolean mLoadingMoreEnable = false;

    private boolean mFirstOnlyEnable = true;

    private boolean mOpenAnimationEnable = false;

    private boolean mEmptyEnable;

    private boolean mIsUseEmpty = true;

    private boolean mHeadAndEmptyEnable;

    private boolean mFootAndEmptyEnable;

    private Interpolator mInterpolator = new LinearInterpolator();

    private int mDuration = 300;

    private int mLastPosition = -1;

    private RequestLoadMoreListener mRequestLoadMoreListener;

    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();

    private LinearLayout mHeaderView;
    private LinearLayout mFooterView;
    private int mPageSize = -1;
    /**
     * View to show if there are no items to show.
     */
    private View mEmptyView;

    private View mLoadMoreFailedView;

    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;

    private View mLoadingView;

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
        this.mData = data == null ? new ArrayList<T>() : data;
        if (mRequestLoadMoreListener != null) {
            mNextLoadEnable = true;
            // mFooterView = null;
        }
        if (mLoadMoreFailedView != null) {
            removeFooterView(mLoadMoreFailedView);
        }
        mLastPosition = -1;
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
                mEmptyEnable = true;
                count += getEmptyViewCount();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) return HEADER_VIEW;

        /* 没有数据时，已设置 EmptyView */
        if (mData.size() == 0 && mEmptyEnable && mEmptyView != null && position <= 2) {
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
        } else if (mData.size() == 0 && mEmptyView != null && getItemCount() == (mHeadAndEmptyEnable ? 2 : 1) &&
                mEmptyEnable) {
            return EMPTY_VIEW;
        } else if (position == mData.size() + getHeaderViewCount()) {
            if (mNextLoadEnable) return LOADING_VIEW;
            else return FOOTER_VIEW;
        } else if (position > mData.size() + getHeaderViewCount()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position - getHeaderViewCount());
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
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation;
                if (mCustomAnimation != null) animation = mCustomAnimation;
                else animation = mSelectAnimation;

                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    anim.setDuration(mDuration).start();
                    anim.setInterpolator(mInterpolator);
                }
                mLastPosition = holder.getLayoutPosition();
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
            case 0:
                convert(holder, mData.get(holder.getLayoutPosition() - getHeaderViewCount()));
                break;
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

    public LinearLayout getFooterView() {
        return mFooterView;
    }

    public void addHeaderView(View header) {
        addHeaderView(header, -1);
    }

    /**
     * When index = -1 or index >= child count in mHeaderView
     * the effect of this method is the same as that of {@link #addHeaderView(View)}.
     */
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

    public void addFooterView(View footer) {
        addFooterView(footer, -1);
    }

    public void addFooterView(View footer, int index) {
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

    public void removeHeaderView(View header) {
        if (mHeaderView == null) return;

        mHeaderView.removeView(header);
        if (mHeaderView.getChildCount() == 0) mHeaderView = null;
        notifyDataSetChanged();
    }

    public void removeFooterView(View footer) {
        if (mFooterView == null) return;

        mFooterView.removeView(footer);
        if (mFooterView.getChildCount() == 0) mFooterView = null;
        notifyDataSetChanged();
    }

    public void removeAllHeaderView() {
        if (mHeaderView == null) return;
        mHeaderView.removeAllViews();

        mHeaderView = null;
    }

    public void removeAllFooterView() {
        if (mFooterView == null) return;

        mFooterView.removeAllViews();
        mFooterView = null;
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

    /**
     * Call this method when load more failed.
     */
    public void showLoadMoreFailedView() {
        loadComplete();
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

    /**
     * Sets the view to show if the adapter is empty
     */
    public void setEmptyView(View emptyView) {
        setEmptyView(false, false, emptyView);
    }

    /**
     * @param isHeadAndEmpty false will not show headView if the data is empty true will show emptyView and headView
     */
    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    /**
     * set emptyView show if adapter is empty and want to show headview and footview
     */
    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
        mEmptyView = emptyView;
        mEmptyEnable = true;
    }

    public void isUseEmpty(boolean isUseEmpty) {
        mIsUseEmpty = isUseEmpty;
    }

    /**
     * When the current adapter is empty, the BaseQuickAdapter can display a special view
     * called the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Finished pull to refresh on the load
     */
    public void loadComplete() {
        mNextLoadEnable = false;
        mLoadingMoreEnable = false;
        notifyDataSetChanged();
    }

    public void isFirstOnly(boolean firstOnly) {
        mFirstOnlyEnable = firstOnly;
    }

    /**
     * @param layoutResId ID for an XML layout resource to load
     * @param parent      Optional view to be the parent of the generated hierarchy or else simply an object that
     *                    provides a set of LayoutParams values for root of the returned
     *                    hierarchy
     * @return view will be return
     */
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public interface RequestLoadMoreListener {

        void onLoadMoreRequested();
    }

    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHA_IN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALE_IN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDE_IN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDE_IN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDE_IN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    public void openLoadAnimation(BaseAnimation animation) {
        mOpenAnimationEnable = true;
        mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        mOpenAnimationEnable = true;
    }

    protected abstract void convert(VH helper, T item);

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int getItemPosition(T item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

}
