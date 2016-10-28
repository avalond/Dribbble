package com.hunter.dribbble.ui.profile.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.hunter.dribbble.R;
import com.hunter.dribbble.base.BaseFragment;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.utils.HtmlFormatUtils;

import butterknife.BindView;

public class ProfileDetailFragment extends BaseFragment {

    public static final String ARGS_USER_ENTITY = "args_user_entity";

    @BindView(R.id.tv_profile_detail_shots)
    TextView mTvShots;
    @BindView(R.id.tv_profile_detail_like)
    TextView mTvLike;
    @BindView(R.id.tv_profile_detail_buckets)
    TextView mTvBuckets;
    @BindView(R.id.tv_profile_detail_follower)
    TextView mTvFollower;
    @BindView(R.id.tv_profile_detail_following)
    TextView mTvFollowing;
    @BindView(R.id.tv_profile_detail_project)
    TextView mTvProject;
    @BindView(R.id.tv_profile_detail_location)
    TextView mTvLocation;
    @BindView(R.id.tv_profile_detail_twitter)
    TextView mTvTwitter;
    @BindView(R.id.tv_profile_detail_web)
    TextView mTvWeb;

    public static ProfileDetailFragment newInstance(UserEntity userEntity) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER_ENTITY, userEntity);
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_detail;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        UserEntity userEntity = (UserEntity) getArguments().getSerializable(ARGS_USER_ENTITY);

        mTvShots.setText(HtmlFormatUtils.setupBold(userEntity.getShotsCount(), "作品"));
        mTvLike.setText(HtmlFormatUtils.setupBold(userEntity.getLikesCount(), "赞"));
        mTvBuckets.setText(HtmlFormatUtils.setupBold(userEntity.getBucketsCount(), "收藏"));
        mTvFollower.setText(HtmlFormatUtils.setupBold(userEntity.getFollowersCount(), "关注"));
        mTvFollowing.setText(HtmlFormatUtils.setupBold(userEntity.getFollowingsCount(), "粉丝"));
        mTvProject.setText(HtmlFormatUtils.setupBold(userEntity.getProjectsCount(), "项目"));

        String location = userEntity.getLocation();
        if (!TextUtils.isEmpty(location)) {
            mTvLocation.setText(location);
        } else {
            mTvLocation.setVisibility(View.GONE);
        }

        String twitter = userEntity.getLinks().getTwitter();
        if (!TextUtils.isEmpty(twitter)) {
            mTvTwitter.setText(twitter);
            mTvTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            mTvTwitter.setVisibility(View.GONE);
        }

        String web = userEntity.getLinks().getWeb();
        if (!TextUtils.isEmpty(web)) {
            mTvWeb.setText(web);
            mTvTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            mTvWeb.setVisibility(View.GONE);
        }
    }
}
