package com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by ptloc on 12/10/2017.
 */

public interface CustomOnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
