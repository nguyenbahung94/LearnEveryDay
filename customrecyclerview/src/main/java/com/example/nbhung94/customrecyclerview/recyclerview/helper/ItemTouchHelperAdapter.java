package com.example.nbhung94.customrecyclerview.recyclerview.helper;

/**
 * Created by nbhung on 11/17/2017.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

