package com.hunter.dribbble.mvp.token;

import com.hunter.dribbble.entity.TokenEntity;
import com.hunter.dribbble.mvp.BaseView;

public interface TokenView extends BaseView {

    void onSuccess(TokenEntity entity);
}
