package com.hunter.dribbble.mvp.followers;

import com.hunter.dribbble.entity.FollowerEntity;
import com.hunter.dribbble.mvp.BaseView;

import java.util.List;

public interface FollowersView extends BaseView {

    void onSuccess(List<FollowerEntity> entities);
}
