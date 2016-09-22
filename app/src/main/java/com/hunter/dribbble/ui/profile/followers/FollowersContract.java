package com.hunter.dribbble.ui.profile.followers;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.FollowerEntity;

import java.util.List;

import rx.Observable;

public interface FollowersContract {

    interface Model extends BaseModel {

        Observable<List<FollowerEntity>> getFollowers(String id);
    }

    interface View extends BaseView {

        void getFollowersOnSuccess(List<FollowerEntity> data);
    }

    abstract class Presenter extends BasePresenter<Model,View>{

        abstract void getFollowers(String id);
    }
}
