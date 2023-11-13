package com.ved.framework.widget;

import android.text.InputFilter;
import android.text.Spanned;

public class SpecialCharacterFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        StringBuilder filtered = new StringBuilder();

        for (int i = start; i < end; i++) {
            char character = source.charAt(i);

            if (Character.isLetterOrDigit(character)) {
                filtered.append(character);
            }
        }

        return filtered.toString();
    }
}
