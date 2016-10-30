package com.hunter.cookies.ui.shots.list;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

import rx.Observable;

public interface ShotsListContract {

    interface Model extends BaseModel {

        Observable<List<ShotsEntity>> getShots(int type, int sort, int timeFrame, int page);
    }

    interface View extends BaseView {

        void getShotsOnSuccess(List<ShotsEntity> data);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getShots(int type, int sort, int timeFrame, int page);
    }

}
