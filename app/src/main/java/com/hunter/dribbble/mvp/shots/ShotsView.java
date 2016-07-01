package com.hunter.dribbble.mvp.shots;

import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.mvp.BaseView;

import java.util.List;

public interface ShotsView extends BaseView {

    void onSuccess(List<ShotsEntity> entities);
}
