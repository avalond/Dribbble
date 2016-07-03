package com.hunter.dribbble;

public interface AppConstants {

    String SP_KEE_ACCESS_TOKEN = "access_token";
    String SP_VIEW_MODE        = "view_mode";
    String SP_IS_FIRST         = "is_first";

    String EXTRA_SHOTS_ENTITY   = "extra_shots_entity";
    String EXTRA_AUTHORIZE_CODE = "extra_authorize_code";

    int REQUEST_CODE_AUTHORIZE = 1;
    int REQUEST_CODE_LOGIN     = 2;

    int VIEW_MODE_SMALL_WITH_INFO = 0;
    int VIEW_MODE_SMALL           = 1;
    int VIEW_MODE_LARGE_WITH_INFO = 2;
    int VIEW_MODE_LARGE           = 3;

    String[] SELECTOR_TYPE = {"全部", "团队", "首秀", "精品", "再创作", "动画"};

    String[] SELECTOR_SORT = {"最热", "最新", "浏览最多", "评论最多"};

    String[] SELECTOR_TIME = {"当前", "周", "月", "年", "所有"};
}
