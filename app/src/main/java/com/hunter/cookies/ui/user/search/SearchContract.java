package com.hunter.cookies.ui.user.search;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

import rx.Observable;

public interface SearchContract {

    interface Model extends BaseModel {

        Observable<List<ShotsEntity>> searchShots(String key, int page);
    }

    interface View extends BaseView {

        void searchShotsOnSuccess(List<ShotsEntity> data);
    }

    abstract class Presenter extends BasePresenter<Model,View>{

        abstract void searchShots(String key, int page);
    }
}
