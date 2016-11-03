package com.hunter.cookies.api;

import com.hunter.cookies.entity.CheckLikeEntity;
import com.hunter.cookies.entity.CommentEntity;
import com.hunter.cookies.entity.FollowerEntity;
import com.hunter.cookies.entity.ShotsEntity;
import com.hunter.cookies.entity.TokenEntity;
import com.hunter.cookies.entity.UserEntity;

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

    @POST(ApiConstants.Path.SHOTS_PUT_COMMENTS)
    Observable<ShotsEntity> putComments(@Path("shots") String shots, @Path("id") String id,
                                                @QueryMap Map<String, String> params);

    @DELETE(ApiConstants.Path.SHOTS_PUT_COMMENTS)
    Observable<String> deleteComments(@Path("shots") String shots, @Path("id") String id);

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

    @GET(ApiConstants.Path.USER_BUCKETS)
    Observable<List<BucketsEntity>> getUserBuckets();


}
