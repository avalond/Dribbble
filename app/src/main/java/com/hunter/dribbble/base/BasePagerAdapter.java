package com.hunter.dribbble.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BasePagerAdapter extends PagerAdapter {

    private FragmentManager mManager;
    private List<Fragment>  mFragments;
    private List<String>    mTitle;

    public BasePagerAdapter(FragmentManager manager, List<Fragment> data) {
        this(manager, data, null);
    }

    public BasePagerAdapter(FragmentManager manager, List<Fragment> data, List<String> title) {
        mManager = manager;
        mFragments = data;
        mTitle = title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = mFragments.get(position);
        if (!fragment.isAdded()) {
            mManager.beginTransaction().add(fragment, fragment.getClass().getName()).commitAllowingStateLoss();
            mManager.executePendingTransactions();
        }
        View view = fragment.getView();
        if (view.getParent() == null) {
            container.addView(view);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mFragments.get(position).getView());
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle != null ? mTitle.get(position) : super.getPageTitle(position);
    }

}
