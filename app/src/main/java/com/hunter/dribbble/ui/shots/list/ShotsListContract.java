package com.hunter.dribbble.ui.shots.list;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.ShotsEntity;

import java.util.List;

import rx.Observable;

public interface ShotsListContract {

    interface Model extends BaseModel {

        Observable<List<ShotsEntity>> getShots(int type, int sort, int timeFrame);
    }

    interface View extends BaseView {

        void getShotsOnSuccess(List<ShotsEntity> data);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getShots(int type, int sort, int timeFrame);
    }

}
