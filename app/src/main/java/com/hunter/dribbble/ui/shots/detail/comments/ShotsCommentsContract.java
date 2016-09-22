package com.hunter.dribbble.ui.shots.detail.comments;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.CommentEntity;

import java.util.List;

import rx.Observable;

public interface ShotsCommentsContract {

    interface Model extends BaseModel {

        Observable<List<CommentEntity>> getComments(String id);
    }

    interface View extends BaseView {

        void getCommentsOnSuccess(List<CommentEntity> data);
    }

    abstract class Presenter extends BasePresenter<Model,View> {

        abstract void getComments(String id);
    }
}
