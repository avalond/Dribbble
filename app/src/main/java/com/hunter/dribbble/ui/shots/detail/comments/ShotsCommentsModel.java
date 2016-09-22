package com.hunter.dribbble.ui.shots.detail.comments;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.entity.CommentEntity;

import java.util.List;

import rx.Observable;

public class ShotsCommentsModel implements ShotsCommentsContract.Model {

    @Override
    public Observable<List<CommentEntity>> getComments(String id) {
        return ApiClient.getForRest().getComments(id);
    }
}
