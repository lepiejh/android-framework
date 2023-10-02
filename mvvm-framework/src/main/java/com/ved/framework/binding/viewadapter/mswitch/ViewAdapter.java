package com.ved.framework.binding.viewadapter.mswitch;

import com.ved.framework.binding.command.BindingCommand;
import com.suke.widget.SwitchButton;

import androidx.databinding.BindingAdapter;

/**
 * Created by ved on 2017/6/18.
 */

public class ViewAdapter {
    /**
     * 设置开关状态
     *
     * @param mSwitch Switch控件
     */
    @BindingAdapter("switchState")
    public static void setSwitchState(SwitchButton mSwitch, boolean isChecked) {
        mSwitch.setChecked(isChecked);
    }

    /**
     * 设置是否可用
     *
     * @param mSwitch Switch控件
     */
    @BindingAdapter("switchEnable")
    public static void setEnable(SwitchButton mSwitch, boolean isEnable) {
        mSwitch.setEnabled(isEnable);
    }

    /**
     * Switch的状态改变监听
     *
     * @param mSwitch        Switch控件
     * @param changeListener 事件绑定命令
     */
    @BindingAdapter("onCheckedChangeCommand")
    public static void onCheckedChangeCommand(final SwitchButton mSwitch, final BindingCommand<Boolean> changeListener) {
        if (changeListener != null) {
            mSwitch.setOnCheckedChangeListener((view, isChecked) -> changeListener.execute(isChecked));
        }
    }
}
