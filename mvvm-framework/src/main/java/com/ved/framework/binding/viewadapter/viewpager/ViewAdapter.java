package com.ved.framework.binding.viewadapter.viewpager;

import com.ved.framework.binding.command.BindingCommand;
import com.ved.framework.entity.ViewPagerDataWrapper;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by ved on 2017/6/18.
 */
public class ViewAdapter {
    @BindingAdapter(value = {"onPageScrolledCommand", "onPageSelectedCommand", "onPageScrollStateChangedCommand","currentItem","offscreenPageLimit"}, requireAll = false)
    public static void onScrollChangeCommand(final ViewPager viewPager,
                                             final BindingCommand<ViewPagerDataWrapper> onPageScrolledCommand,
                                             final BindingCommand<Integer> onPageSelectedCommand,
                                             final BindingCommand<Integer> onPageScrollStateChangedCommand,
                                             final int currentItem,
                                             final int offscreenPageLimit) {
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            private int state;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageScrolledCommand != null) {
                    onPageScrolledCommand.execute(new ViewPagerDataWrapper(position, positionOffset, positionOffsetPixels, state));
                }
            }

            @Override
            public void onPageSelected(int position) {
                PagerAdapter pagerAdapter = viewPager.getAdapter();
                if (pagerAdapter != null){
                    Fragment fragment = (Fragment) pagerAdapter.instantiateItem(viewPager, position);
                    fragment.setMenuVisibility(true); // 使当前 Fragment 的菜单可见

                    // 设置其他 Fragment 的菜单不可见
                    for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                        if (i != position) {
                            Fragment otherFragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, i);
                            otherFragment.setMenuVisibility(false); // 使其他 Fragment 的菜单不可见
                        }
                    }
                }
                if (onPageSelectedCommand != null) {
                    onPageSelectedCommand.execute(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
                if (onPageScrollStateChangedCommand != null) {
                    onPageScrollStateChangedCommand.execute(state);
                }
            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(currentItem);
        viewPager.setOffscreenPageLimit(offscreenPageLimit);
        viewPager.post(() -> onPageChangeListener.onPageSelected(currentItem));
    }
}
