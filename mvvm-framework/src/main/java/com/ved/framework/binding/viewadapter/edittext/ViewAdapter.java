package com.ved.framework.binding.viewadapter.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ved.framework.binding.command.BindingCommand;

import androidx.databinding.BindingAdapter;

/**
 * Created by ved on 2017/6/16.
 */

public class ViewAdapter {
    /**
     * EditText重新获取焦点的事件绑定
     */
    @BindingAdapter(value = {"requestFocus"}, requireAll = false)
    public static void requestFocusCommand(EditText editText, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            editText.setSelection(editText.getText().length());
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
        editText.setFocusableInTouchMode(needRequestFocus);
    }

    /**
     * EditText输入文字改变的监听
     */
    @BindingAdapter(value = {"beforeTextChanged","textChanged","afterTextChanged"}, requireAll = false)
    public static void addTextChangedListener(EditText editText, final BindingCommand<String> beforeTextChanged,
                                              final BindingCommand<String> textChanged,
                                              final BindingCommand<Editable> afterTextChanged) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (beforeTextChanged != null){
                    beforeTextChanged.execute(charSequence.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if (textChanged != null) {
                    textChanged.execute(text.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (afterTextChanged != null){
                    afterTextChanged.execute(editable);
                }
            }
        });
    }
}
