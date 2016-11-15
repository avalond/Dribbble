package com.hunter.cookies.ui.shots.detail.des;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.cookies.App;
import com.hunter.cookies.R;
import com.hunter.cookies.base.mvp.BaseMVPFragment;
import com.hunter.cookies.entity.ShotsEntity;
import com.hunter.cookies.ui.profile.ProfileActivity;
import com.hunter.cookies.ui.user.login.LoginActivity;
import com.hunter.cookies.ui.user.search.SearchActivity;
import com.hunter.cookies.utils.HtmlFormatUtils;
import com.hunter.cookies.utils.TimeUtils;
import com.hunter.cookies.utils.glide.GlideUtils;
import com.hunter.cookies.widget.TagFlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class ShotsDesFragment extends BaseMVPFragment<ShotsDetailPresenter, ShotsDetailModel> implements
        ShotsDetailContract.View {

    public static final String ARGS_SHOTS_ENTITY = "args_shots_entity";
    public static final String ARGS_SHOTS_ID = "args_shots_id";

    private static final int REQUEST_CODE_LOGIN = 100;

    @BindView(R.id.tv_shots_detail_title)
    TextView mTvShotsTitle;
    @BindView(R.id.tv_shots_detail_des)
    TextView mTvShotsDes;
    @BindView(R.id.tv_shots_detail_user_name)
    TextView mTvShotsUserName;
    @BindView(R.id.iv_shots_detail_avatar)
    ImageView mIvShotsAvatar;
    @BindView(R.id.tv_shots_detail_post_time)
    TextView mTvShotsPostTime;
    @BindView(R.id.iv_shots_detail_like)
    ImageView mIvShotsLike;
    @BindView(R.id.tv_shots_detail_like)
    TextView mTvShotsLike;
    @BindView(R.id.tv_shots_detail_view)
    TextView mTvShotsView;
    @BindView(R.id.tv_shots_detail_bucket)
    TextView mTvShotsBucket;
    @BindView(R.id.tv_shots_detail_comment)
    TextView mTvShotsComment;

    @BindView(R.id.tag_shots_detail_des)
    TagFlowLayout mTagFlowLayout;

    private ShotsEntity mShotsEntity;
    private int mShotsId;

    private boolean mIsLiked;
    private boolean mIsAddBuckets;

    public static ShotsDesFragment newInstance(ShotsEntity entity) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_SHOTS_ENTITY, entity);
        ShotsDesFragment fragment = new ShotsDesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShotsDesFragment newInstance(int shotsId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_SHOTS_ID, shotsId);
        ShotsDesFragment fragment = new ShotsDesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shots_des;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mShotsId = getArguments().getInt(ARGS_SHOTS_ID);
        if (mShotsId == 0) {
            initShotsDes((ShotsEntity) getArguments().getSerializable(ARGS_SHOTS_ENTITY));
            mShotsId = mShotsEntity.getId();
        } else mPresenter.getShotsDetail(mShotsId);

        if (App.getAppConfig().isLogin()) mPresenter.checkShotsLike(mShotsId);
    }

    private void initShotsDes(ShotsEntity shotsEntity) {
        mShotsEntity = shotsEntity;
        mTvShotsTitle.setText(mShotsEntity.getTitle());
        String des = mShotsEntity.getDescription();
        if (!TextUtils.isEmpty(des)) {
            HtmlFormatUtils.Html2String(mTvShotsDes, des);
        } else {
            mTvShotsDes.setVisibility(View.GONE);
        }

        GlideUtils.setAvatar(mContext, mShotsEntity.getUser().getAvatarUrl(), mIvShotsAvatar);
        mTvShotsUserName.setText(mShotsEntity.getUser().getUsername());
        mTvShotsPostTime.setText(TimeUtils.getTimeFromISO8601(mShotsEntity.getUpdatedAt()) + " 投递");

        mTvShotsLike.setText(HtmlFormatUtils.setupBold(mShotsEntity.getLikesCount(), "喜欢"));
        mTvShotsView.setText(HtmlFormatUtils.setupBold(mShotsEntity.getViewsCount(), "浏览"));
        mTvShotsComment.setText(HtmlFormatUtils.setupBold(mShotsEntity.getCommentsCount(), "评论"));
        mTvShotsBucket.setText(HtmlFormatUtils.setupBold(mShotsEntity.getBucketsCount(), "收藏"));

        mTagFlowLayout.setTags(mShotsEntity.getTags());
        mTagFlowLayout.setOnTagItemClickListener(new TagFlowLayout.OnTagItemClickListener() {
            @Override
            public void onClick(View v, String content) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(SearchActivity.EXTRA_SEARCH_KEY, content);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.iv_shots_detail_avatar)
    void toProfile() {
        Intent intent = new Intent(mActivity, ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, mShotsEntity.getUser());
        startActivity(intent);
    }

    @Override
    public void getShotsDetailOnSuccess(ShotsEntity data) {
        initShotsDes(data);
    }

    /**
     * 需要登录
     */
    @OnClick(R.id.ll_shots_detail_like)
    void clickShotsLike() {
        if (App.getAppConfig().isLogin()) changeShotsLike();
        else shouldLogin();
    }

    /**
     * 执行动画，发送送请求
     */
    private void changeShotsLike() {
        mIsLiked = !mIsLiked;
        mIvShotsLike.setImageResource(mIsLiked ? R.drawable.iv_like_pink_24dp : R.drawable.iv_unlike_grey_24dp);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(ObjectAnimator.ofFloat(mIvShotsLike, "scaleY", 0.8f, 1.2f, 1f))
               .with(ObjectAnimator.ofFloat(mIvShotsLike, "scaleX", 0.8f, 1.2f, 1f));
        animSet.setDuration(500);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIvShotsLike.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIvShotsLike.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mIvShotsLike.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animSet.start();

        int likeCount = mShotsEntity.getLikesCount();
        mShotsEntity.setLikesCount(mIsLiked ? ++likeCount : --likeCount);
        mTvShotsLike.setText(HtmlFormatUtils.setupBold(mShotsEntity.getLikesCount(), "喜欢"));

        mPresenter.changeShotsStatus(mShotsId, mIsLiked);
    }

    /**
     * 收藏
     */
    @OnClick(R.id.ll_shots_detail_bucket)
    void clickShotsBucket() {
        if (App.getAppConfig().isLogin()) changeShotsBucket();
        else shouldLogin();
    }

    private void changeShotsBucket() {

    }

    private void shouldLogin() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage("登录后才可以执行该操作");
        dialog.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(mContext, LoginActivity.class), REQUEST_CODE_LOGIN);
            }
        });
        dialog.setNegativeButton("算了", null);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_LOGIN) showToast("授权成功");
    }

    @Override
    public void checkShotsLikeOnSuccess(boolean isLiked) {
        mIsLiked = isLiked;
        mIvShotsLike.setImageResource(mIsLiked ? R.drawable.iv_like_pink_24dp : R.drawable.iv_unlike_grey_24dp);
    }

    @Override
    public void addShotsToBucketsOnSuccess() {
    }
}
