package com.ved.framework.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


import androidx.annotation.NonNull;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;

public class LimitUtil {


    /**
     * 身份证允许输入字符
     */
    private static char[] idCardNumberLimitChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', 'X','x'};

    private static char[] numberLimitChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private static char[] doubleLimitChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            '.'};

    private static char[] fixPhoneLimitChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            '-'};


    /**
     * 限制字符输入
     */
    private static void limitCharsInput(EditText editText, char[] limitChars) {
        if (editText == null) {
            return;
        }
        editText.setKeyListener(new NumberKeyListener() {

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER;
            }

            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                return limitChars;
            }
        });
    }

    /**
     * 限制身份证输入字符
     */
    public static void limitIdCardNumberInput(EditText editText) {
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER | TYPE_CLASS_TEXT);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        limitCharsInput(editText, idCardNumberLimitChars);
    }

    /**
     * 清除限制
     */
    public static void clearLimit(EditText editText) {
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER | TYPE_CLASS_TEXT);
        editText.setFilters(new InputFilter[]{null});
        editText.setKeyListener(null);
    }


    /**
     * 限制百分号输入
     */
    public static void limitPercentInput(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().contains(".")) { //如果包含.
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) { //小数点长度超过4位
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) { // 第一位是.添加前置0
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") //第一位是0只能输入
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 校验邮箱输入
     */
    public static void checkEmailInput(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !TextUtils.isEmpty(editText.getText().toString())) {
                if (!RegexUtils.checkEmail(editText.getText().toString())) {
                    ToastUtils.showShort("邮箱格式不正确");
                }
            }
        });
    }

    /**
     * 校验整数输入
     */
    public static void checkIntegerInput(EditText editText) {
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER);
        limitCharsInput(editText, numberLimitChars);
    }

    /**
     * 校验浮点数输入
     */
    public static void checkDoubleInput(EditText editText) {
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER);
        limitPercentInput(editText);
        limitCharsInput(editText, doubleLimitChars);
    }

    /**
     * 校验固定电话输入
     */
    public static void checkTelephoneInput(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER);
        limitCharsInput(editText, fixPhoneLimitChars);
    }

    /**
     * 校验手机号码输入
     */
    public static void checkMobilePhoneInput(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER);
        limitCharsInput(editText, numberLimitChars);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 11 && !RegexUtils.isMobileNumber(s.toString())) {
                    ToastUtils.showShort("手机号码格式不正确");
                }
            }
        });
    }

    /**
     * 校验邮编输入
     */
    public static void checkPostCodeInput(EditText editText) {
        editText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE | TYPE_CLASS_NUMBER);
        limitCharsInput(editText, numberLimitChars);
    }
}
