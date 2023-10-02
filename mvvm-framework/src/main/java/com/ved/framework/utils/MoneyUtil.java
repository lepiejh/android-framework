package com.ved.framework.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;

/**
 * 设置EditText只能输入金额<br>
 * 用法: Money.setMoneyFormat(editText)
 * Created by maxwell on 2016/9/13 0013.
 */
public final class MoneyUtil {

    private MoneyUtil() {
    }

    /**
     * 限制只能输入金额
     */
    public static void setMoneyFormat(final EditText editText) {
        if (editText == null) {
            return;
        }
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789.")); //限制输入数字和小数点

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) { //如果包含小数点
                    //如果已经包含小数点,则不能再输入小数点,只能输入数字
                    if (editText.getText().toString().indexOf(".", editText.getText().toString().indexOf(".") + 1) >
                            0) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString()
                                .length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                    }
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) { //小数点之后只能有2位数字
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length()); //光标设置到最后
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) { //如果第1位输入小数点,则补零
                    s = "0" + s;
                    editText.setText(s); //设置为"0."
                    editText.setSelection(2); //光标位于小数点之后
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) { //0开头且长度>1
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
