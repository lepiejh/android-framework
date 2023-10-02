package com.ved.framework.binding.viewadapter.checkbox;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ved.framework.binding.command.BindingCommand;

import androidx.databinding.BindingAdapter;

/**
 * Created by ved on 2017/6/16.
 */

public class ViewAdapter {
    /**
     * @param bindingCommand //绑定监听
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onCheckedChangedCommand"}, requireAll = false)
    public static void setCheckedChanged(final CheckBox checkBox, final BindingCommand<Boolean> bindingCommand) {
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> bindingCommand.execute(b));
    }
}
