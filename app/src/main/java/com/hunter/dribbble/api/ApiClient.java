package com.hunter.dribbble.api;

import android.text.TextUtils;

import com.hunter.dribbble.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final boolean IS_DEBUG = true;

    private static final int TIME_OUT_SECONDS = 15;

    private static Retrofit sOAuthRetrofit;
    private static Retrofit sRestRetrofit;

    public synchronized static ApiStores createForRest() {
        if (sRestRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (IS_DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            String token = App.getInstance().getToken();
            final String access_token = "Bearer " + (TextUtils.isEmpty(token) ? ApiConstants.ParamValue.TOKEN : token);
            Interceptor tokenInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().header("Authorization", access_token).build();
                    return chain.proceed(request);
                }
            };
            builder.addNetworkInterceptor(tokenInterceptor);

            builder.retryOnConnectionFailure(true).connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS);

            sRestRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.Url.SERVER_URL)
                                                  .client(builder.build())
                                                  .addConverterFactory(GsonConverterFactory.create())
                                                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                  .build();
        }

        return sRestRetrofit.create(ApiStores.class);
    }

    public synchronized static void cleanApiClient() {
        if (sRestRetrofit != null) {
            sRestRetrofit = null;
        }
    }

    public synchronized static ApiStores createForOAtuh() {
        if (sOAuthRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (IS_DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            builder.retryOnConnectionFailure(true).connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS);

            sOAuthRetrofit = new Retrofit.Builder().baseUrl(ApiConstants.Url.OAUTH_URL)
                                                   .client(builder.build())
                                                   .addConverterFactory(GsonConverterFactory.create())
                                                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                   .build();
        }

        return sOAuthRetrofit.create(ApiStores.class);
    }

}
