package com.hunter.cookies.ui.shots.detail.comments;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.entity.CommentEntity;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class ShotsCommentsModel implements ShotsCommentsContract.Model {

    @Override
    public Observable<List<CommentEntity>> getComments(int id, int page) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE, page + "");
        params.put(ApiConstants.ParamKey.PER_PAGE, ApiConstants.ParamValue.PAGE_SIZE + "");
        return ApiClient.getForRest().getComments(id + "", params);
    }

    @Override
    public Observable<ShotsEntity> putComments(int shotsId, int id, String body) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.BODY, body);
        return ApiClient.getForRest().putComments(shotsId + "", id + "", params);
    }

    @Override
    public Observable<String> deleteComments(int shotsId, int id) {
        return ApiClient.getForRest().deleteComments(shotsId + "", id + "");
    }
}
