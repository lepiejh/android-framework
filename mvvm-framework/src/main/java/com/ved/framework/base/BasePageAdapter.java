package com.ved.framework.base;

import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BasePageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> mTitles = new ArrayList<>();

    public void addTitle(String title){
        mTitles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public BasePageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        try {
            Field mFragments = getClass().getSuperclass().getDeclaredField("mFragments");
            mFragments.setAccessible(true);
            ((ArrayList) mFragments.get(this)).clear();

            Field mSavedState = getClass().getSuperclass().getDeclaredField("mSavedState");
            mSavedState.setAccessible(true);
            ((ArrayList) mSavedState.get(this)).clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.instantiateItem(container, position);
    }
}
