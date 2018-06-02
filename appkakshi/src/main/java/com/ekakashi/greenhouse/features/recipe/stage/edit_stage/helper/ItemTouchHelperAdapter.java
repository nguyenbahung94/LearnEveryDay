package com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper;

/**
 * Created by ptloc on 12/10/2017.
 */

public interface ItemTouchHelperAdapter {


    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
