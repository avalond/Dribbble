package com.hunter.dribbble.ui.shots.detail.des;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseFragment;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.profile.ProfileActivity;
import com.hunter.dribbble.ui.user.search.SearchActivity;
import com.hunter.dribbble.utils.HtmlFormatUtils;
import com.hunter.dribbble.utils.TimeUtils;
import com.hunter.dribbble.utils.glide.GlideUtils;
import com.hunter.dribbble.widget.TagFlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class ShotsDesFragment extends BaseFragment {

    @BindView(R.id.tv_shots_detail_title)
    TextView mTvShotsTitle;
    @BindView(R.id.tv_shots_detail_des)
    TextView mTvShotsDes;

    /**
     * 用户相关
     */
    @BindView(R.id.tv_shots_detail_user_name)
    TextView  mTvShotsUserName;
    @BindView(R.id.iv_shots_detail_avatar)
    ImageView mIvShotsAvatar;
    @BindView(R.id.tv_shots_detail_post_time)
    TextView  mTvShotsPostTime;

    /**
     * 赞 浏览 收藏 评论
     */
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

    public static ShotsDesFragment newInstance(ShotsEntity entity) {
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.EXTRA_SHOTS_ENTITY, entity);
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
        mShotsEntity = (ShotsEntity) getArguments().getSerializable(AppConstants.EXTRA_SHOTS_ENTITY);

        mTvShotsTitle.setText(mShotsEntity.getTitle());
        String des = mShotsEntity.getDescription();
        if (!TextUtils.isEmpty(des)) {
            mTvShotsDes.setText(Html.fromHtml(des));
            mTvShotsDes.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            mTvShotsDes.setVisibility(View.GONE);
        }

        GlideUtils.setAvatar(mContext, mShotsEntity.getUser().getAvatarUrl(), mIvShotsAvatar);
        mTvShotsUserName.setText(mShotsEntity.getUser().getUsername());
        mTvShotsPostTime.setText(TimeUtils.getTimeFromISO8601(mShotsEntity.getUpdatedAt()) + " 投递");

        mTvShotsLike.setText(HtmlFormatUtils.setupBold(mShotsEntity.getLikesCount(), "赞"));
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

}
