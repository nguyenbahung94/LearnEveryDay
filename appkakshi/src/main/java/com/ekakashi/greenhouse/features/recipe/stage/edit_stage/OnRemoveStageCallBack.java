package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;

/**
 * Created by ptloc on 3/1/2018.
 */

public interface OnRemoveStageCallBack {
    void onRemoveStage(ObjectGrowth stage, int position);
}
