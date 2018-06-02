package com.ekakashi.greenhouse.features.weather.widget_list.helper;

/**
 * Created by nbhung on 11/17/2017.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

