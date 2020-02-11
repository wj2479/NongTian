package com.qdhc.ny.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 申健 on 2018/7/17.
 * 光标在文字后边
 */

@SuppressLint("AppCompatCustomView")
public class LastInputEditText extends EditText {
    public LastInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTextChangeListener();
    }

    private void setTextChangeListener() {

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                    setTextDirection(View.TEXT_DIRECTION_RTL);
                } else {
                    setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                    setTextDirection(View.TEXT_DIRECTION_LTR);
                }
            }
        });
    }

    public LastInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextChangeListener();
    }

    public LastInputEditText(Context context) {
        super(context);
        setTextChangeListener();
    }
}
