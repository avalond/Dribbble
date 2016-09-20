package com.hunter.dribbble.utils;

import com.hunter.dribbble.api.ApiConstants;

import java.util.HashMap;
import java.util.Map;

public class UrlUtils {

    /**
     * 获取登录链接
     */
    public static String getAuthorizeUrl() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.CLIENT_ID, ApiConstants.ParamValue.CLIENT_ID);
        params.put(ApiConstants.ParamKey.REDIRECT_URI, ApiConstants.ParamValue.REDIRECT_URI);
        params.put(ApiConstants.ParamKey.SCOPE, ApiConstants.ParamValue.SCOPE);
        params.put(ApiConstants.ParamKey.STATE, ApiConstants.ParamValue.STATE);

        return formatToUrl(ApiConstants.Url.OAUTH_URL + ApiConstants.Path.AUTHORIZE, params);
    }

    public static String formatToUrl(String url, Map<String, String> params) {
        StringBuilder sb = null;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb == null) {
                sb = new StringBuilder(url + "?");
            } else {
                sb.append("&");
            }

            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }

        return sb.toString();
    }
}
