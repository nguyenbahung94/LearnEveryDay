package com.ekakashi.greenhouse.common.text_watcher_callback;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


public class TextWatcherCallback implements TextWatcher, View.OnClickListener {
    private EditText editText;
    private View btnClear;
    private TextWatcherInterface mCallback;

    public TextWatcherCallback(EditText editText, View btnClear, TextWatcherInterface mCallback) {
        this.editText = editText;
        this.btnClear = btnClear;
        this.mCallback = mCallback;
        this.btnClear.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        btnClear.setVisibility(editable.length() == 0 ? View.INVISIBLE : View.VISIBLE);
        if (editable.length() == 0) {
            mCallback.onTextWatcherCallback(true);
            btnClear.setVisibility(View.INVISIBLE);
        } else {
            btnClear.setVisibility(View.VISIBLE);
            mCallback.onTextWatcherCallback(false);
        }
    }

    @Override
    public void onClick(View v) {
        editText.getText().clear();
        v.setVisibility(View.INVISIBLE);
    }
}
