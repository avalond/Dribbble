package com.hunter.dribbble.ui.shots.detail;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.ShotsEntity;

import rx.Observable;

public interface ShotsDetailContract {

    interface Model extends BaseModel {

        Observable<ShotsEntity> getShotsDetail(int id);
    }

    interface View extends BaseView{

        void getShotsDetailOnSuccess(ShotsEntity data);
    }

    abstract class Presenter extends BasePresenter<Model,View>{

        abstract void getShotsDetail(int id);
    }
}
