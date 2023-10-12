package com.ved.framework.binding.viewadapter.seekbar;

import android.widget.SeekBar;

import com.ved.framework.binding.command.BindingCommand;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {

    @BindingAdapter(value = {"onProgressChanged","onStartTrackingTouch","onStopTrackingTouch"}, requireAll = false)
    public static void onSeekBarChangeListener(SeekBar seekBar, final BindingCommand<Integer> onProgressChanged,
                                              final BindingCommand<Void> onStartTrackingTouch,
                                              final BindingCommand<Void> onStopTrackingTouch) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (onProgressChanged != null){
                    onProgressChanged.execute(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (onStartTrackingTouch != null){
                    onStartTrackingTouch.execute();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (onStopTrackingTouch != null){
                    onStopTrackingTouch.execute();
                }
            }
        });
    }
}
