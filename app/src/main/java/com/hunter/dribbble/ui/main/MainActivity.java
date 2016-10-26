package com.hunter.dribbble.ui.main;

import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMVPActivity<UserInfoPresenter, UserInfoModel> implements UserInfoContract.View,
        AccountHeader.OnAccountHeaderListener, Toolbar.OnMenuItemClickListener, Drawer.OnDrawerItemClickListener {

    private static final String TAG_SEARCH = "tag_search";

    private static final int NAV_IDENTITY_PROFILE = 100;
    private static final int NAV_IDENTITY_HOME = 101;
    private static final int NAV_IDENTITY_FOLLOWING = 102;
    private static final int NAV_IDENTITY_BUCKETS = 103;
    private static final int NAV_IDENTITY_LIKES = 104;
    private static final int NAV_IDENTITY_SETTINGS = 105;

    private static final int VIEW_MODE_ICON_RES[] = {
            R.mipmap.ic_action_small_info,
            R.mipmap.ic_action_small,
            R.mipmap.ic_action_large_info,
            R.mipmap.ic_action_large
    };

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.spinner_selector_type)
    MaterialSpinner mSpinnerSelectorType;
    @BindView(R.id.spinner_selector_sort)
    MaterialSpinner mSpinnerSelectorSort;
    @BindView(R.id.spinner_selector_time)
    MaterialSpinner mSpinnerSelectorTime;

    private ShotsListFragment mShotsListFragment;
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

    private void initNavDrawer() {
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);

        ProfileDrawerItem navHeader = new ProfileDrawerItem();
        navHeader.withName("点击头像登录")
                 .withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                 .withIdentifier(NAV_IDENTITY_PROFILE);
        mAccountHeader = new AccountHeaderBuilder().withActivity(this)
                                                   .addProfiles(navHeader)
                                                   .withSelectionListEnabled(false)
                                                   .withOnAccountHeaderListener(this)
                                                   .build();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem();
        homeDrawerItem.withName("Home")
                      .withIcon(R.drawable.iv_home_grey_24dp)
                      .withIdentifier(NAV_IDENTITY_HOME)
                      .withOnDrawerItemClickListener(this)
                      .withSelectedIcon(R.drawable.iv_home_pink_24dp);

        PrimaryDrawerItem followingDrawerItem = new PrimaryDrawerItem();
        followingDrawerItem.withName("Following")
                           .withIdentifier(NAV_IDENTITY_FOLLOWING)
                           .withIcon(R.drawable.iv_follower_grey_24dp)
                           .withOnDrawerItemClickListener(this)
                           .withSelectedIcon(R.drawable.iv_follower_pink_24dp);

        PrimaryDrawerItem bucketsDrawerItem = new PrimaryDrawerItem();
        bucketsDrawerItem.withName("Buckets")
                         .withIdentifier(NAV_IDENTITY_BUCKETS)
                         .withIcon(R.drawable.iv_bucket_grey_24dp)
                         .withOnDrawerItemClickListener(this)
                         .withSelectedIcon(R.drawable.iv_bucket_pink_24dp);

        PrimaryDrawerItem likesDrawerItem = new PrimaryDrawerItem();
        likesDrawerItem.withName("Likes")
                       .withIdentifier(NAV_IDENTITY_LIKES)
                       .withIcon(R.drawable.iv_like_grey_24dp)
                       .withOnDrawerItemClickListener(this)
                       .withSelectedIcon(R.drawable.iv_like_pink_24dp);

        SecondaryDrawerItem settingsDrawerItem = new SecondaryDrawerItem();
        settingsDrawerItem.withName("Settings")
                          .withIdentifier(NAV_IDENTITY_SETTINGS)
                          .withIcon(R.drawable.iv_settings_grey_24dp)
                          .withOnDrawerItemClickListener(this)
                          .withSelectedIcon(R.drawable.iv_settings_pink_24dp);

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
                break;

            case NAV_IDENTITY_FOLLOWING:

                break;
            case NAV_IDENTITY_BUCKETS:

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
        mSpinnerSelectorType.setItems(AppConstants.SELECTOR_TYPE);
        mSpinnerSelectorType.setOnItemClickListener(mShotsListFragment);

        mSpinnerSelectorSort.setItems(AppConstants.SELECTOR_SORT);
        mSpinnerSelectorSort.setOnItemClickListener(mShotsListFragment);

        mSpinnerSelectorTime.setItems(AppConstants.SELECTOR_TIME);
        mSpinnerSelectorTime.setOnItemClickListener(mShotsListFragment);
    }

    private void shouldAuthorize() {
        if (!(boolean) SPUtils.get(this, AppConstants.SP_IS_FIRST, false)) {
            startActivityForResult(new Intent(this, LoginActivity.class), AppConstants.REQUEST_CODE_LOGIN);
            SPUtils.put(this, AppConstants.SP_IS_FIRST, true);
        }
    }

    private void initFragment() {
        mShotsListFragment = ShotsListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container_main, mShotsListFragment).commit();

        mSearchFragment = SearchFragment.newInstance();
    }

    private void initUserInfo() {
        if (App.getInstance().isLogin()) mPresenter.getUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AppConstants.REQUEST_CODE_LOGIN) {
            showToast("授权成功");
        }
    }

    /**
     * 点击侧边栏头像事件
     */
    @Override
    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
        if (App.getInstance().isLogin()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, UserInfoUtils.getCurrentUser(this));
            startActivity(intent);
        } else startActivityForResult(new Intent(this, LoginActivity.class), AppConstants.REQUEST_CODE_LOGIN);

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
                mSearchFragment.show(getSupportFragmentManager(), TAG_SEARCH);
                break;
        }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_view_mode).setIcon(VIEW_MODE_ICON_RES[App.getInstance().getViewMode()]);
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
