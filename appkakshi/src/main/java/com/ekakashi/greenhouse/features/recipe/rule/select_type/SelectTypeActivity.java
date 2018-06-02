package com.ekakashi.greenhouse.features.recipe.rule.select_type;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.database.model_server_response.ObjectType;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.AddRuleActivity;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;

import java.util.ArrayList;
import java.util.List;

public class SelectTypeActivity extends BaseActivity implements OnItemCallBack {

    private RecyclerView rvRuleType;
    private boolean addRule = false;// false is Rule, true is Action
    private int ruleType = 1;
    private int actionType = 1;
    private List<ObjectType> mRuleTypes = new ArrayList<>();
    private List<ObjectType> mActionTypes = new ArrayList<>();
    private ObjectRecipe mRecipe;
    private ObjectGrowth mStage;
    private boolean fromAddStage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        initView();
        addToolbar();
        prepareData();
    }

    private void initView() {
        rvRuleType = findViewById(R.id.rvRuleType);

        if (getIntent() != null) {
            addRule = getIntent().getBooleanExtra(Utils.Name.ADD_RULE, false);
            mRecipe = App.appRecipe;
            mStage = App.appStage;
            fromAddStage = getIntent().getBooleanExtra(Utils.Name.ADD_STAGE, false);
        }
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.text_select_type), "");

        if (addRule) {
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.text_next));
        } else {
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        }

        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (addRule) {
                    //NEXT BUTTON
                    if (ruleType != EnumRule.CONTROL_DEVICE) {
                        Intent intent = new Intent(SelectTypeActivity.this, AddRuleActivity.class);
                        intent.putExtra(Utils.Name.TYPE, ruleType);
                        intent.putExtra(Utils.Name.TYPE_OF_ACTION, actionType);
                        App.appRecipe = mRecipe;
                        App.appStage = mStage;
//                        intent.putExtra(Utils.Name.RECIPE, mRecipe);
//                        intent.putExtra(Utils.Name.STAGE, mStage);
                        intent.putExtra(Utils.Name.ADD_STAGE, fromAddStage);
                        startActivityForResult(intent, Utils.Name.TYPE_OF_RULE_CODE);
                    } else {
                        Toast.makeText(SelectTypeActivity.this, getString(R.string.message_next_phase), Toast.LENGTH_LONG).show();
                    }
                } else {
                    //SAVE BUTTON
                    Intent intent = new Intent();
                    intent.putExtra(Utils.Name.TYPE, ruleType);
                    intent.putExtra(Utils.Name.TYPE_OF_ACTION, actionType);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


    private void prepareData() {
        TypeAdapter mTypeAdapter;
        if (addRule) {
            //TYPE OF RULE
            if (mRuleTypes == null) {
                mRuleTypes = new ArrayList<>();
            } else {
                mRuleTypes.add(new ObjectType(EnumRule.MOMENTARY, getString(R.string.text_rule_1), true));
                mRuleTypes.add(new ObjectType(EnumRule.AGGREGATION, getString(R.string.text_rule_2), false));
                mRuleTypes.add(new ObjectType(EnumRule.CONTROL_DEVICE, getString(R.string.text_rule_3), false));
            }
            mTypeAdapter = new TypeAdapter(mRuleTypes);
        } else {
            // TYPE OF ACTION
            if (getIntent() != null) {
                actionType = getIntent().getIntExtra(Utils.Name.TYPE_OF_ACTION, 1);
                ruleType = getIntent().getIntExtra(Utils.Name.TYPE, 1);
            }
            if (mActionTypes == null) {
                mActionTypes = new ArrayList<>();
            } else {
                mActionTypes.addAll(Utils.actionListTypeBaseOnRule(this, ruleType));
            }

            //Reset Selected In Action List
            for (ObjectType type : mActionTypes) {
                if (type.isSelect()) {
                    type.setSelect(!type.isSelect());
                }
            }

            mActionTypes.get(actionType - 1).setSelect(true);
            mTypeAdapter = new TypeAdapter(mActionTypes);
        }

        mTypeAdapter.setCallBack(this);
        rvRuleType.setLayoutManager(new LinearLayoutManager(this));
        rvRuleType.setAdapter(mTypeAdapter);
    }

    @Override
    public void OnItemClick(int position) {
        if (addRule) {
            //TYPE OF RULE
            ruleType = position + 1;
        } else {
            //TYPE OF ACTION
            actionType = position + 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.Name.TYPE_OF_RULE_CODE:
                    if (fromAddStage) {
//                        ObjectRule rule = data.getParcelableExtra(Utils.Name.RULE);
//                        ObjectRule rule = App.appRule;
//                        Intent intent = new Intent();
//                        intent.putExtra(Utils.Name.RULE, rule);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        if (data.getParcelableExtra(Utils.Name.RECIPE) != null) {
                            mRecipe = data.getParcelableExtra(Utils.Name.RECIPE);
                            Intent intent = new Intent();
                            intent.putExtra(Utils.Name.RECIPE, mRecipe);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
