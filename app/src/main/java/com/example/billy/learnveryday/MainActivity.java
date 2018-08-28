package com.example.billy.learnveryday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
 private EditText ccEditText;

    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_number);
        ccEditText = findViewById(R.id.edtphone);
        ccEditText.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Remove all spacing char
                int pos = 0;
                while (true) {
                    if (pos >= s.length()) break;
                    if (space == s.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == s.length())) {
                        s.delete(pos, pos + 1);
                    } else {
                        pos++;
                    }
                }

                // Insert char where needed.
                pos = 4;
                while (true) {
                    if (pos >= s.length()) break;
                    final char c = s.charAt(pos);
                    // Only if its a digit where there should be a space we insert a space
                    if ("0123456789".indexOf(c) >= 0) {
                        s.insert(pos, "" + space);
                    }
                    pos += 5;
                }
            }
        });
    }
}
