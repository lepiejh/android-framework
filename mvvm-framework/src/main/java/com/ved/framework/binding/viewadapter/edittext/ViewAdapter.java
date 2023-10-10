package com.ved.framework.binding.viewadapter.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

    /**
     * EditText imeOptions的事件绑定
     * 设置：android:inputType="text|textVisiblePassword"
     *     android:imeOptions="actionSearch"
     */
    @BindingAdapter(value = {"onEditorActionListener"}, requireAll = false)
    public static void setOnEditorActionListener(EditText editText, final BindingCommand<String> onEditorActionListener) {
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 执行搜索操作
                if (onEditorActionListener != null) {
                    onEditorActionListener.execute(editText.getText().toString());
                }
                return true;
            }
            return false;
        });
    }

    @BindingAdapter(value = {"inputType"}, requireAll = false)
    public static void inputTypeCommand(EditText editView, final int type) {
        if (type == 0){   //"none" 无限制类型
            editView.setInputType(InputType.TYPE_NULL);
        }else if (type == 1){//"text" 普通文本类型
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }else if (type == 2){//"textCapCharacters" 全部字符大写
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }else if (type == 3){//"textCapWords" 单词首字母大写
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        }else if (type == 4){//"textCapSentences" 句子首字母大写
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        }else if (type == 5){//"textAutoCorrect" 自动修正
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        }else if (type == 6){//"textAutoComplete" 自动补全
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        }else if (type == 7){//"textMultiLine" 多行输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }else if (type == 8){//"textImeMultiLine" 输入法多行输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        }else if (type == 9){//"textNoSuggestions" 无提示候选信息
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }else if (type == 10){//"textUri" uri格式输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        }else if (type == 11){//"textEmailAddress" 邮件地址格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }else if (type == 12){//"textEmailSubject" 邮件主题
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        }else if (type == 13){  //"textShortMessage" 短消息信息模式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        }else if (type == 14){  //"textLongMessage" 长消息信息模式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        }else if (type == 15){  //"textPersonName" 人名输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }else if (type == 16){  //"textPostalAddress" 邮寄地址
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        }else if (type == 17){  //"textPassword" 密码格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else if (type == 18){  //"textVisiblePassword" 密码可见格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else if (type == 19){  //"textWebEditText" web表单格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        }else if (type == 20){  //"textFilter" 文本筛选
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
        }else if (type == 21){  //"textPhonetic" 拼音输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PHONETIC);
        }else if (type == 22){  //"textWebEmailAddress" web表单中添加邮件地址
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        }else if (type == 23){  //"textWebPassword" web表单中添加密码
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        }else if (type == 24){  //"number" 数字格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }else if (type == 25){  //"numberSigned" 有符号数字格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }else if (type == 26){  //"numberDecimal" 浮点数字格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }else if (type == 27){  //"numberPassword" 纯数字密码格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }else if (type == 28){  //"phone" 电话号码模式
            editView.setInputType(InputType.TYPE_CLASS_PHONE);
        }else if (type == 29){  //"datetime" 时间日期格式
            editView.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);
        }else if (type == 30){  //"date" 日期键盘
            editView.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
        }else if (type == 31){  //"time" 时间键盘
            editView.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
        }
    }
}
