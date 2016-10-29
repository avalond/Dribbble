package com.hunter.dribbble.api;

import com.hunter.dribbble.entity.CheckLikeEntity;
import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.entity.FollowerEntity;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.entity.TokenEntity;
import com.hunter.dribbble.entity.UserEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiStores {

    @POST(ApiConstants.Path.TOKEN)
    Observable<TokenEntity> getToken(@QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.SHOTS)
    Observable<List<ShotsEntity>> getShotsList(@QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.SHOTS_DETAIL)
    Observable<ShotsEntity> getShotsDetail(@Path("id") String id);

    @GET(ApiConstants.Path.SHOTS_COMMENTS)
    Observable<List<CommentEntity>> getComments(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.SHOTS_LIKE)
    Observable<CheckLikeEntity> checkShotsLike(@Path("id") String id);

    @POST(ApiConstants.Path.SHOTS_LIKE)
    Observable<CheckLikeEntity> likeShots(@Path("id") String id);

    @DELETE(ApiConstants.Path.SHOTS_LIKE)
    Observable<CheckLikeEntity> unlikeShots(@Path("id") String id);

    @GET(ApiConstants.Path.USER_FOLLOWERS)
    Observable<List<FollowerEntity>> getFollowers(@Path("id") String id, @QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.SEARCH)
    Observable<List<ShotsEntity>> search(@QueryMap Map<String, String> params);

    @GET(ApiConstants.Path.USER)
    Observable<UserEntity> getUserInfo();

    @GET(ApiConstants.Path.USER_SHOTS)
    Observable<List<ShotsEntity>> getUserShots(@Path("id") String id, @QueryMap Map<String, String> params);
}
