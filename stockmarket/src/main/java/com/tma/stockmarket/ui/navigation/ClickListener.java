package com.tma.stockmarket.ui.navigation;

import android.view.View;

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int posotion);
}
