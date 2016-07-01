package com.hunter.dribbble.api;

public interface ApiConstants {

    interface Url {

        String SERVER_URL = "https://api.dribbble.com/v1/";

        String OAUTH_URL = "https://dribbble.com/oauth/";

        String REDIRECT_URL = "http://lhunter.org/";
    }

    interface Path {

        String AUTHORIZE = "authorize";

        String TOKEN = "token";

        String SHOTS = "shots";
    }

    interface ParamKey {

        String CLIENT_ID = "client_id";

        String CLIENT_SECRET = "client_secret";

        String REDIRECT_URI = "redirect_uri";

        String SCOPE = "scope";

        String STATE = "state";

        String CODE = "code";

        String LIST = "list";

        String TIME_FRAME = "timeframe";

        String DATE = "date";

        String SORT = "sort";
    }

    interface ParamValue {

        String CLIENT_ID = "e9976e732df8ed56f928882d23be3b47543a4e0b8c3a6a05fa0af7d7a7d3b34a";

        String CLIENT_SECRET = "ab73df88cc0dba31bf5776859ca63b9a4fba7ba6ee6e9361bbaa06aa9109fe37";

        String TOKEN = "8f0fa00867e1e9883fdd82c0e98f701bc4cea519dc2c42a96e5d3699a237f62c";

        String REDIRECT_URI = "http://lhunter.org/";

        String SCOPE = "public write comment upload";

        String STATE = "hunter";

        /**
         * 类型，默认返回所有类型
         */
        String[] LIST_VALUES = {"", "teams", "debuts", "playoffs", "rebounds", "animated"};

        /**
         * 排序，默认返回综合排序
         */
        String[] SORT_VALUES = {"", "recent", "views", "comments"};

        /**
         * 时间段，默认返回最新
         */
        String[] TIME_VALUES = {"", "week", "month", "year", "ever"};
    }

}
