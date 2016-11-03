package com.hunter.cookies.api;

import java.util.HashMap;

public class PagerMap extends HashMap<String, String> {

    public PagerMap(int page) {
        put(ApiConstants.ParamKey.PAGE, page + "");
        put(ApiConstants.ParamKey.PER_PAGE, ApiConstants.ParamValue.PAGE_SIZE + "");
    }
}
