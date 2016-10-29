package com.hunter.dribbble.ui.shots.detail.des;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.CheckLikeEntity;
import com.hunter.dribbble.entity.ShotsEntity;

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
        changeShotsLike(id, isLike);
    }

    private void changeShotsLike(int id, boolean isLike) {
        subscribe(mModel.changeShotsStatus(id, isLike), new BaseSubscriber<CheckLikeEntity>(mView) {
            @Override
            protected void onSuccess(CheckLikeEntity checkLikeEntity) {
            }
        });
    }
}
