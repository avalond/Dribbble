package com.hunter.dribbble.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hunter.dribbble.App;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.base.mvp.BaseMVPActivity;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.event.EventViewMode;
import com.hunter.dribbble.ui.buckets.BucketsFragment;
import com.hunter.dribbble.ui.profile.ProfileActivity;
import com.hunter.dribbble.ui.settings.SettingsActivity;
import com.hunter.dribbble.ui.shots.list.ShotsListFragment;
import com.hunter.dribbble.ui.user.login.LoginActivity;
import com.hunter.dribbble.ui.user.search.dialog.SearchFragment;
import com.hunter.dribbble.utils.UserInfoUtils;
import com.hunter.dribbble.utils.ViewModelUtils;
import com.hunter.dribbble.widget.spinner.MaterialSpinner;
import com.hunter.lib.util.SPUtils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMVPActivity<UserInfoPresenter, UserInfoModel> implements UserInfoContract.View,
        AccountHeader.OnAccountHeaderListener, Toolbar.OnMenuItemClickListener, Drawer.OnDrawerItemClickListener {

    /**
     * 侧滑菜单 Item 标识
     */
    private static final int NAV_IDENTITY_PROFILE = 100;
    private static final int NAV_IDENTITY_HOME = 101;
    private static final int NAV_IDENTITY_FOLLOWING = 102;
    private static final int NAV_IDENTITY_BUCKETS = 103;
    private static final int NAV_IDENTITY_LIKES = 104;
    private static final int NAV_IDENTITY_SETTINGS = 105;

    /**
     * Spinner 菜单
     */
    private static final String[] SELECTOR_TYPE = {"全部", "团队", "首秀", "精品", "再创作", "动画"};
    private static final String[] SELECTOR_SORT = {"最热", "最新", "浏览最多", "评论最多"};
    private static final String[] SELECTOR_TIME = {"当前", "周", "月", "年", "所有"};

    /**
     * Toolbar 菜单栏图标
     */
    private static final int VIEW_MODE_ICON_RES[] = {
            R.mipmap.ic_action_small_info,
            R.mipmap.ic_action_small,
            R.mipmap.ic_action_large_info,
            R.mipmap.ic_action_large
    };

    public static final int REQUEST_CODE_LOGIN = 100;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.spinner_selector_type)
    MaterialSpinner mSpinnerSelectorType;
    @BindView(R.id.spinner_selector_sort)
    MaterialSpinner mSpinnerSelectorSort;
    @BindView(R.id.spinner_selector_time)
    MaterialSpinner mSpinnerSelectorTime;

    private List<Fragment> mFragmentList;
    private ShotsListFragment mShotsListFragment;

    private FragmentManager mFragmentManager;

    private SearchFragment mSearchFragment;

    private long mExitTime;

    private AccountHeader mAccountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        shouldAuthorize();
        initNavDrawer();
        initFragment();
        initSpinner();
        initUserInfo();
    }

    private void shouldAuthorize() {
        if (!(boolean) SPUtils.get(this, AppConstants.SP_IS_FIRST_RUN, false)) {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN);
            SPUtils.put(this, AppConstants.SP_IS_FIRST_RUN, true);
        }
    }

    private void initNavDrawer() {
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);

        ProfileDrawerItem navHeader = new ProfileDrawerItem();
        if (App.getAppConfig().isLogin()) {
            navHeader.withName(UserInfoUtils.getCurrentUser(this).getName())
                     .withIcon(UserInfoUtils.getCurrentUser(this).getAvatarUrl())
                     .withIdentifier(NAV_IDENTITY_PROFILE);
        } else {
            navHeader.withName("点击头像登录")
                     .withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                     .withIdentifier(NAV_IDENTITY_PROFILE);
        }
        mAccountHeader = new AccountHeaderBuilder().withActivity(this)
                                                   .addProfiles(navHeader)
                                                   .withSelectionListEnabled(false)
                                                   .withOnAccountHeaderListener(this)
                                                   .build();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem();
        homeDrawerItem.withName("首页")
                      .withIcon(R.drawable.iv_home_grey_24dp)
                      .withIdentifier(NAV_IDENTITY_HOME)
                      .withOnDrawerItemClickListener(this)
                      .withSelectedIcon(R.drawable.iv_home_pink_24dp);

        PrimaryDrawerItem followingDrawerItem = new PrimaryDrawerItem();
        followingDrawerItem.withName("关注的人")
                           .withIdentifier(NAV_IDENTITY_FOLLOWING)
                           .withIcon(R.drawable.iv_follower_grey_24dp)
                           .withOnDrawerItemClickListener(this)
                           .withSelectedIcon(R.drawable.iv_follower_pink_24dp);

        PrimaryDrawerItem bucketsDrawerItem = new PrimaryDrawerItem();
        bucketsDrawerItem.withName("收藏夹")
                         .withIdentifier(NAV_IDENTITY_BUCKETS)
                         .withIcon(R.drawable.iv_bucket_grey_24dp)
                         .withOnDrawerItemClickListener(this)
                         .withSelectedIcon(R.drawable.iv_bucket_pink_24dp);

        PrimaryDrawerItem likesDrawerItem = new PrimaryDrawerItem();
        likesDrawerItem.withName("喜欢")
                       .withIdentifier(NAV_IDENTITY_LIKES)
                       .withIcon(R.drawable.iv_like_grey_24dp)
                       .withOnDrawerItemClickListener(this)
                       .withSelectedIcon(R.drawable.iv_like_pink_24dp);

        SecondaryDrawerItem settingsDrawerItem = new SecondaryDrawerItem();
        settingsDrawerItem.withName("设置")
                          .withIdentifier(NAV_IDENTITY_SETTINGS)
                          .withIcon(R.drawable.iv_settings_grey_24dp)
                          .withOnDrawerItemClickListener(this)
                          .withSelectable(false);

        DrawerBuilder builder = new DrawerBuilder();
        builder.withActivity(this)
               .withToolbar(mToolbar)
               .withActionBarDrawerToggleAnimated(true)
               .withAccountHeader(mAccountHeader)
               .addDrawerItems(homeDrawerItem, followingDrawerItem, bucketsDrawerItem, likesDrawerItem,
                               new DividerDrawerItem(), settingsDrawerItem);

        builder.build();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case NAV_IDENTITY_HOME:
                mToolbar.setTitle(getString(R.string.app_name));
                showFragmentByIndex(0);
                break;

            case NAV_IDENTITY_FOLLOWING:

                break;
            case NAV_IDENTITY_BUCKETS:
                mToolbar.setTitle("收藏夹");
                showFragmentByIndex(1);
                break;
            case NAV_IDENTITY_LIKES:

                break;
            case NAV_IDENTITY_SETTINGS:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return false;
    }

    private void initSpinner() {
        mSpinnerSelectorType.setItems(mShotsListFragment, SELECTOR_TYPE);
        mSpinnerSelectorSort.setItems(mShotsListFragment, SELECTOR_SORT);
        mSpinnerSelectorTime.setItems(mShotsListFragment, SELECTOR_TIME);
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();

        mFragmentList = new ArrayList<>();
        mShotsListFragment = ShotsListFragment.newInstance();
        mFragmentList.add(mShotsListFragment);
        mFragmentList.add(BucketsFragment.newInstance());

        showFragmentByIndex(0);

        mSearchFragment = SearchFragment.newInstance();
    }

    private void showFragmentByIndex(int showIndex) {
        mFragmentManager.beginTransaction().replace(R.id.container_main, mFragmentList.get(showIndex)).commit();
    }

    private void initUserInfo() {
        if (App.getAppConfig().isLogin()) mPresenter.getUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_LOGIN) {
            showToast("授权成功");
            mPresenter.getUserInfo();
        }
    }

    /**
     * 点击侧边栏头像事件
     */
    @Override
    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
        if (App.getAppConfig().isLogin()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, UserInfoUtils.getCurrentUser(this));
            startActivity(intent);
        } else startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN);

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mode, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_small_info:
            case R.id.menu_small:
            case R.id.menu_large_info:
            case R.id.menu_large:
                int viewModeIndex = item.getOrder();
                invalidateOptionsMenu();
                EventBus.getDefault().post(new EventViewMode(viewModeIndex));

                showToastForStrong("浏览模式切换为", getString(ViewModelUtils.VIEW_MODE_TITLE_RES[viewModeIndex]));
                break;
            case R.id.menu_search:
                mSearchFragment.show(getSupportFragmentManager(), SearchFragment.class.getSimpleName());
                break;
        }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_view_mode).setIcon(VIEW_MODE_ICON_RES[App.getAppConfig().getViewMode()]);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast("再次点击退出应用");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getUserInfoOnSuccess(UserEntity entity) {
        UserInfoUtils.setUserInfo(this, entity);
        ProfileDrawerItem navHeader = new ProfileDrawerItem();
        navHeader.withName(entity.getName()).withIcon(entity.getAvatarUrl());
        navHeader.withIdentifier(NAV_IDENTITY_PROFILE);
        mAccountHeader.updateProfile(navHeader);
    }

}
