package com.ekakashi.greenhouse.features.weather.widget_list.helper;

import android.view.View;

import java.util.List;

/**
 * Created by nbhung on 11/17/2017.
 */

public interface OnItemCLickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);

    void onNoteListChanged(List<String> strings);

    void deleteItem(View view, int position);

    void clickButtonAdd();
}
