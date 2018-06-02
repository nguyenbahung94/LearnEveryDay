package com.example.nbhung94.customrecyclerview.recyclerview.helper;

import android.view.View;

import java.util.List;

/**
 * Created by nbhung on 11/17/2017.
 */

public interface OnItemCLickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);

    void onNoteListChanged(List<String> strings);
}
