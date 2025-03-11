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
        if (type == com.ved.framework.entity.InputType.TYPE_NULL){   //"none" 无限制类型
            editView.setInputType(InputType.TYPE_NULL);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_NORMAL){//"text" 普通文本类型
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS){//"textCapCharacters" 全部字符大写
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_CAP_WORDS){//"textCapWords" 单词首字母大写
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES){//"textCapSentences" 句子首字母大写
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_AUTO_CORRECT){//"textAutoCorrect" 自动修正
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE){//"textAutoComplete" 自动补全
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_MULTI_LINE){//"textMultiLine" 多行输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE){//"textImeMultiLine" 输入法多行输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS){//"textNoSuggestions" 无提示候选信息
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_URI){//"textUri" uri格式输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS){//"textEmailAddress" 邮件地址格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT){//"textEmailSubject" 邮件主题
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE){  //"textShortMessage" 短消息信息模式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE){  //"textLongMessage" 长消息信息模式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_PERSON_NAME){  //"textPersonName" 人名输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS){  //"textPostalAddress" 邮寄地址
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_PASSWORD){  //"textPassword" 密码格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){  //"textVisiblePassword" 密码可见格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT){  //"textWebEditText" web表单格式
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_FILTER){  //"textFilter" 文本筛选
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_PHONETIC){  //"textPhonetic" 拼音输入
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PHONETIC);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS){  //"textWebEmailAddress" web表单中添加邮件地址
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        }else if (type == com.ved.framework.entity.InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD){  //"textWebPassword" web表单中添加密码
            editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        }else if (type == com.ved.framework.entity.InputType.TYPE_NUMBER_VARIATION_NORMAL){  //"number" 数字格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }else if (type == com.ved.framework.entity.InputType.TYPE_NUMBER_FLAG_SIGNED){  //"numberSigned" 有符号数字格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }else if (type == com.ved.framework.entity.InputType.TYPE_NUMBER_FLAG_DECIMAL){  //"numberDecimal" 浮点数字格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }else if (type == com.ved.framework.entity.InputType.TYPE_NUMBER_VARIATION_PASSWORD){  //"numberPassword" 纯数字密码格式
            editView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }else if (type == com.ved.framework.entity.InputType.TYPE_CLASS_PHONE){  //"phone" 电话号码模式
            editView.setInputType(InputType.TYPE_CLASS_PHONE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_DATETIME_VARIATION_NORMAL){  //"datetime" 时间日期格式
            editView.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);
        }else if (type == com.ved.framework.entity.InputType.TYPE_DATETIME_VARIATION_DATE){  //"date" 日期键盘
            editView.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
        }else if (type == com.ved.framework.entity.InputType.TYPE_DATETIME_VARIATION_TIME){  //"time" 时间键盘
            editView.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
        }
    }
}
