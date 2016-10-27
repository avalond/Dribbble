package com.hunter.dribbble.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.lib.util.SPUtils;

import java.util.HashMap;
import java.util.Map;

public class UserInfoUtils {

    public static final String KEY_CURRENT_USER = "user";

    private static Map<String, UserEntity> sUserEntity = new HashMap<>();

    public static UserEntity getCurrentUser(Context context) {
        UserEntity currentUser = sUserEntity.get(KEY_CURRENT_USER);
        if (currentUser != null) return currentUser;

        String userStr = (String) SPUtils.get(context, AppConstants.SP_CURRENT_USER, "");
        if (TextUtils.isEmpty(userStr)) return null;

        UserEntity user = new Gson().fromJson(userStr, UserEntity.class);
        sUserEntity.put(AppConstants.SP_CURRENT_USER, user);
        return user;
    }

    public static void clearUserInfo(Context context) {
        sUserEntity.clear();
        SPUtils.put(context, AppConstants.SP_CURRENT_USER, "");
    }

    public static void setUserInfo(Context context, UserEntity entity) {
        sUserEntity.put(KEY_CURRENT_USER, entity);
        SPUtils.put(context, AppConstants.SP_CURRENT_USER, new Gson().toJson(entity));
    }
}
