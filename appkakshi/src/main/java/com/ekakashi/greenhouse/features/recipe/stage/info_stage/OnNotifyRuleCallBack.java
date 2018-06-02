package com.ekakashi.greenhouse.features.recipe.stage.info_stage;

import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;

/**
 * Created by ptloc on 3/9/2018.
 */

public interface OnNotifyRuleCallBack {
    void notifyRule(ObjectRule rule, int position);
}
