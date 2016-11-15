package com.hunter.cookies.ui.shots.detail.des;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.CheckLikeEntity;
import com.hunter.cookies.entity.ShotsEntity;

public class ShotsDetailPresenter extends ShotsDetailContract.Presenter {

    @Override
    void getShotsDetail(int id) {
        subscribe(mModel.getShotsDetail(id), new BaseSubscriber<ShotsEntity>(mView) {
            @Override
            protected void onSuccess(ShotsEntity shotsEntity) {
                mView.getShotsDetailOnSuccess(shotsEntity);
            }
        });
    }

    @Override
    void checkShotsLike(int id) {
        subscribe(mModel.checkShotsLike(id), new BaseSubscriber<CheckLikeEntity>(mView) {
            @Override
            protected void onSuccess(CheckLikeEntity checkLikeEntity) {
                mView.checkShotsLikeOnSuccess(true);
            }

            @Override
            protected void onFail(String msg) {
                mView.checkShotsLikeOnSuccess(false);
            }
        });
    }

    @Override
    void changeShotsStatus(int id, boolean isLike) {
        subscribe(mModel.changeShotsStatus(id, isLike), new BaseSubscriber<CheckLikeEntity>(mView) {
            @Override
            protected void onSuccess(CheckLikeEntity checkLikeEntity) {
            }
        });
    }

    @Override
    void addShotsToBuckets(int bucketsId, int shotsId) {
        subscribe(mModel.addShotsToBuckets(bucketsId, shotsId), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String s) {
                mView.addShotsToBucketsOnSuccess();
            }
        });
    }

}
