package com.hunter.dribbble.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.mvp.shots.ShotsPresenter;
import com.hunter.dribbble.mvp.shots.ShotsView;
import com.hunter.dribbble.ui.base.BaseMvpActivity;
import com.hunter.library.util.SPUtils;

import java.util.List;

public class MainActivity extends BaseMvpActivity<ShotsPresenter> implements ShotsView {

    @Override
    protected ShotsPresenter createPresenter() {
        return new ShotsPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFirst = (boolean) SPUtils.get(this, AppConstants.SP_IS_FIRST, false);
        if (!isFirst) {
            startActivityForResult(new Intent(this, LoginActivity.class), AppConstants.REQUEST_CODE_LOGIN);
            SPUtils.put(this, AppConstants.SP_IS_FIRST, false);
        } else {
            loadDate();
        }
    }

    private void loadDate() {
        mPresenter.getShots("", "", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AppConstants.REQUEST_CODE_LOGIN) {
            toast("授权成功");
        }
    }

    @Override
    public void onSuccess(List<ShotsEntity> entities) {

    }
}
