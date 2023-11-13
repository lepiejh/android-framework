package com.ved.framework.widget;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class FilterEditText extends AppCompatEditText {
    public FilterEditText(@NonNull Context context) {
        super(context);
        init();
    }

    public FilterEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new SpecialCharacterFilter();
        setFilters(filters);
    }
}
