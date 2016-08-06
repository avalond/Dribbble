package com.hunter.dribbble.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.activity.ProfileActivity;
import com.hunter.dribbble.ui.base.BaseFragment;
import com.hunter.dribbble.utils.HtmlFormatUtils;
import com.hunter.dribbble.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    TextView         mTvShotsUserName;
    @BindView(R.id.drawee_shots_detail_avatar)
    SimpleDraweeView mDraweeShotsAvatar;
    @BindView(R.id.tv_shots_detail_post_time)
    TextView         mTvShotsPostTime;

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

    private ShotsEntity mShotsEntity;

    public static ShotsDesFragment newInstance(ShotsEntity entity) {
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.EXTRA_SHOTS_ENTITY, entity);
        ShotsDesFragment fragment = new ShotsDesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shots_des, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void init() {
        mShotsEntity = (ShotsEntity) getArguments().getSerializable(AppConstants.EXTRA_SHOTS_ENTITY);

        mTvShotsTitle.setText(mShotsEntity.getTitle());
        String des = mShotsEntity.getDescription();
        if (!TextUtils.isEmpty(des)) {
            mTvShotsDes.setText(Html.fromHtml(des));
        } else {
            mTvShotsDes.setVisibility(View.GONE);
        }

        mDraweeShotsAvatar.setImageURI(Uri.parse(mShotsEntity.getUser().getAvatarUrl()));
        mTvShotsUserName.setText(mShotsEntity.getUser().getUsername());
        mTvShotsPostTime.setText(TimeUtils.getTimeFromISO8601(mShotsEntity.getUpdatedAt()) + " 投递");

        mTvShotsLike.setText(HtmlFormatUtils.setupBold(mShotsEntity.getLikesCount(), "赞"));
        mTvShotsView.setText(HtmlFormatUtils.setupBold(mShotsEntity.getViewsCount(), "浏览"));
        mTvShotsComment.setText(HtmlFormatUtils.setupBold(mShotsEntity.getCommentsCount(), "评论"));
        mTvShotsBucket.setText(HtmlFormatUtils.setupBold(mShotsEntity.getBucketsCount(), "收藏"));
    }

    @OnClick(R.id.drawee_shots_detail_avatar)
    void toProfile() {
        Intent intent = new Intent(mActivity, ProfileActivity.class);
        intent.putExtra(AppConstants.EXTRA_USER_ENTITY, mShotsEntity.getUser());
        startActivity(intent);
    }

}
