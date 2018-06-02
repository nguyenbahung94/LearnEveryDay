package com.ekakashi.greenhouse.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


public class MyTextWatcher implements TextWatcher, View.OnClickListener {
    private EditText editText;
    private View btnClear;

    public MyTextWatcher(EditText editText, View btnClear) {
        this.editText = editText;
        this.btnClear = btnClear;
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
    }

    @Override
    public void onClick(View v) {
        editText.getText().clear();
        v.setVisibility(View.INVISIBLE);
    }
}
