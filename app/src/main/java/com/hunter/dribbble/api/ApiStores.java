package com.hunter.dribbble.api;

import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.entity.TokenEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiStores {

    @POST(ApiConstants.Path.TOKEN)
    Observable<TokenEntity> getToken(@QueryMap Map<String, String> map);

    @GET(ApiConstants.Path.SHOTS)
    Observable<List<ShotsEntity>> getShotsList(@QueryMap Map<String, String> map);

}
