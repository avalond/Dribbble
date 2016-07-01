package com.hunter.library.util;

import java.util.Map;

/**
 * 拼接 Url
 */
public class UrlUtils {

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
