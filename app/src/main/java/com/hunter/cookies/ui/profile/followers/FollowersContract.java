package com.hunter.cookies.ui.profile.followers;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.FollowerEntity;

import java.util.List;

import rx.Observable;

public interface FollowersContract {

    interface Model extends BaseModel {

        Observable<List<FollowerEntity>> getFollowers(String id, int page);
    }

    interface View extends BaseView {

        void getFollowersOnSuccess(List<FollowerEntity> data);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getFollowers(String id, int page);
    }
}
