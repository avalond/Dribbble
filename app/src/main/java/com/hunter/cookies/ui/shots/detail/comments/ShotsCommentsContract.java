package com.hunter.cookies.ui.shots.detail.comments;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.CommentEntity;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

import rx.Observable;

public interface ShotsCommentsContract {

    interface Model extends BaseModel {

        Observable<List<CommentEntity>> getComments(int id, int page);

        Observable<ShotsEntity> putComments(int shotsId, int id, String body);

        Observable<String> deleteComments(int shotsId, int id);
    }

    interface View extends BaseView {

        void getCommentsOnSuccess(List<CommentEntity> data);

        void putCommentsOnSuccess(ShotsEntity data);

        void deleteCommentsOnSuccess();
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getComments(int id, int page);

        abstract void putComments(int shotsId, int id, String body);

        abstract void deleteComments(int shotsId, int id);
    }
}
