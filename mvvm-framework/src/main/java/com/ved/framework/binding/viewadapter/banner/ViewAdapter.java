package com.ved.framework.binding.viewadapter.banner;

import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.ved.framework.binding.command.BindingCommand;
import com.ved.framework.entity.OnPageScrolled;
import com.ved.framework.entity.XBannerDataWrapper;
import com.ved.framework.entity.XBannerInfo;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by ved on 2017/6/16.
 */
public class ViewAdapter {
    @BindingAdapter(value = {"loadImageCommand"}, requireAll = false)
    public static void loadImage(final XBanner xBanner, final BindingCommand<XBannerDataWrapper> bindingCommand) {
        xBanner.loadImage((banner, model, view, position) -> bindingCommand.execute(new XBannerDataWrapper(banner, model, view, position)));
    }

    @BindingAdapter(value = {"onPageChangeCommand"}, requireAll = false)
    public static void setOnPageChangeListener(final XBanner xBanner, final BindingCommand<OnPageScrolled> bindingCommand) {
        xBanner.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                bindingCommand.execute(new OnPageScrolled(position, positionOffset, positionOffsetPixels));
            }
        });
    }

    @BindingAdapter("setBannerData")
    public static void setBannerData(final XBanner xBanner, List<LocalImageInfo> localImageInfoList) {
        xBanner.setBannerData(localImageInfoList);
    }

    @BindingAdapter("setBannerInfo")
    public static void setBannerInfo(final XBanner xBanner, List<XBannerInfo> xBannerInfoList) {
        xBanner.setBannerData(xBannerInfoList);
    }
}
