package com.ekakashi.greenhouse.features.recipe.stage.info_stage;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.AddRuleActivity;
import com.ekakashi.greenhouse.features.recipe.rule.select_type.SelectTypeActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class InfoStageActivity extends BaseActivity implements OnItemCallBack, OnRemoveItemClick, OnNotifyRuleCallBack, InfoStageInterface.View, View.OnClickListener {

    private RecyclerView rvRule;
    private RuleListAdapter mRuleListAdapter;
    private ObjectRecipe mRecipe = new ObjectRecipe();
    private List<ObjectRule> mRuleList = new ArrayList<>();
    private ObjectGrowth mStage;
    private TextView tvRuleList;
    private TextView tvDescription;
    private TextView tvDescriptionContent;
    private boolean fromReorder = false;
    private boolean editPressed = false;
    private boolean fromSelectStage = false;
    private MyToolbar myToolbar;
    private RelativeLayout btnAddRule;
    private InfoStagePresenter mInfoStagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_stage);
        initView();
        addToolbar();
        prepareData();
    }

    private void initView() {
        rvRule = findViewById(R.id.rvRule);
        btnAddRule = findViewById(R.id.btnAddRule);
        tvRuleList = findViewById(R.id.tvRuleList);
        tvDescription = findViewById(R.id.tvDescription);
        tvDescriptionContent = findViewById(R.id.tvDescriptionContent);

        btnAddRule.setOnClickListener(this);

        if (getIntent() != null) {
            mRecipe = App.appRecipe;
            //
            //GET STAGE
            if (mRecipe != null) {
                if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                    int stageId = getIntent().getIntExtra(Utils.Name.STAGE, -1);
                    if (stageId != -1) {
                        for (ObjectGrowth stage : mRecipe.getStages()) {
                            if (stage.getId() == stageId) {
                                mStage = stage;
                                break;
                            }
                        }
                    }
                }
            }
            fromReorder = getIntent().getBooleanExtra(Utils.Name.RULE, false);
            fromSelectStage = getIntent().getBooleanExtra(Utils.Name.FROM_SELECT_STAGE, false);
        }
    }

    private void addToolbar() {
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, mStage.getName(), mRecipe.getRecipeName());
        if (fromReorder) {
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
            //TODO Next phase will be Visible
            btnAddRule.setVisibility(GONE);
        } else {
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
            btnAddRule.setVisibility(GONE);
        }
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (fromReorder) {
                    //TODO Next phase will open
//                    if (!editPressed) {
//                        editPressed = true;
//                        mRuleListAdapter.setEdit(true);
//                        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
//                    } else {
//                        editPressed = false;
//                        mRuleListAdapter.setEdit(false);
//                        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
//                    }
                    Toast.makeText(InfoStageActivity.this, getString(R.string.message_next_phase), Toast.LENGTH_LONG).show();
                } else {
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fromReorder) {
            App.appRecipe = mRecipe;
//            Intent rule = new Intent();
//            rule.putExtra(Utils.Name.RECIPE, mRecipe);
            setResult(RESULT_OK);// go to ReorderStageActivity.java
        }
        super.onBackPressed();
    }

    private void prepareData() {
        mRuleListAdapter = new RuleListAdapter(this, mRecipe, mRuleList);
        mRuleListAdapter.setFromSelectStage(fromSelectStage);
        mRuleListAdapter.setOnItemCallBack(this);
        mRuleListAdapter.setOnNotifyRuleCallBack(this);
        mRuleListAdapter.setOnRemoveItemClick(this);
        rvRule.setLayoutManager(new LinearLayoutManager(this));
        rvRule.setAdapter(mRuleListAdapter);
        mInfoStagePresenter = new InfoStagePresenter(this);

        if (mRecipe != null) {
            if (mStage != null) {
                if (mStage.getRules() != null && !mStage.getRules().isEmpty()) {
                    int i;
                    List<ObjectGrowth> stageList = mRecipe.getStages();
                    Integer currentStageId = mStage.getId();
                    if (stageList != null && !stageList.isEmpty() && currentStageId != null) {
                        for (i = 0; i < stageList.size(); i++) {
                            if (stageList.get(i).getId() != null) {
                                if (stageList.get(i).getId().intValue() == currentStageId.intValue()) {
                                    mStage = stageList.get(i);
                                    mRuleList = stageList.get(i).getRules();
                                    mRuleListAdapter.setRules(mRuleList);
                                    break;
                                }
                            }
                        }
                    }
                    tvRuleList.setVisibility(View.VISIBLE);
                } else {
                    tvRuleList.setVisibility(View.VISIBLE);
                }
            } else {
                tvRuleList.setVisibility(View.VISIBLE);
            }

            if (mStage.getDescription() != null && !mStage.getDescription().isEmpty()) {
                tvDescriptionContent.setText(mStage.getDescription());
            } else {
                tvDescriptionContent.setText("");
            }
            tvDescriptionContent.requestFocus();
        }
    }

    @Override
    public void OnItemClick(int position) {
        Intent rule = new Intent(this, AddRuleActivity.class);
        rule.putExtra(Utils.Name.VIEW_DETAIL, true);
        rule.putExtra(Utils.Name.STAGE, mStage.getId());
        rule.putExtra(Utils.Name.RULE, mRuleList.get(position).getId());
        startActivity(rule);
    }

    @Override
    public void editRecipeSuccess(ObjectRecipe recipe) {
        hideLoadingDialog();
        if (recipe != null) {

            int tempStageId = mRecipe.getCurrentStageId();
            mRecipe = recipe;
            if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
                if (recipe.getEkFields().get(0) != null) {
                    if (recipe.getEkFields().get(0).getCurrentStage() != null) {
                        mRecipe.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                    } else {
                        mRecipe.setCurrentStageId(tempStageId);
                    }
                } else {
                    mRecipe.setCurrentStageId(tempStageId);
                }
            } else {
                mRecipe.setCurrentStageId(tempStageId);
            }//ELSE USE DEFAULT CURRENT STAGE OF mRECIPE OBJECT

            List<ObjectGrowth> stageList = mRecipe.getStages();
            Integer currentStageId = mStage.getId();
            if (stageList != null && !stageList.isEmpty() && currentStageId != null) {
                int i;
                for (i = 0; i < stageList.size(); i++) {
                    if (stageList.get(i).getId() != null) {
                        if (stageList.get(i).getId().intValue() == currentStageId.intValue()) {
                            mStage = stageList.get(i);
                            mRuleList = stageList.get(i).getRules();
                            mRuleListAdapter.setRules(mRuleList);
                            break;
                        }
                    }
                }
            }

        } else {
            Toast.makeText(this, R.string.message_cannot_edit_recipe, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failed(String error) {
        hideLoadingDialog();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddRule:
                Intent intent = new Intent(this, SelectTypeActivity.class);
                intent.putExtra(Utils.Name.ADD_RULE, true);
                intent.putExtra(Utils.Name.RECIPE, mRecipe);
                intent.putExtra(Utils.Name.STAGE, mStage);
                startActivityForResult(intent, Utils.Name.ADD_RULE_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.Name.ADD_RULE_CODE:
                    if (data.getParcelableExtra(Utils.Name.RECIPE) != null) {
                        ObjectRecipe recipe = data.getParcelableExtra(Utils.Name.RECIPE);
                        recipe.setCurrentStageId(mRecipe.getCurrentStageId());
                        mRecipe = recipe;
                        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                            int i;
                            for (i = 0; i < mRecipe.getStages().size(); i++) {
                                if (mRecipe.getStages().get(i).getId().intValue() == mStage.getId().intValue()) {
                                    mStage = mRecipe.getStages().get(i);
                                    mRuleList = mRecipe.getStages().get(i).getRules();
                                    break;
                                }
                            }
                            if (mRuleList != null && !mRuleList.isEmpty()) {
                                tvRuleList.setVisibility(View.VISIBLE);
                            }
                            mRuleListAdapter.setRules(mRuleList);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void notifyRule(ObjectRule rule, int position) {
        //TODO Change Notify's Rule
        if (!isNetworkOffline()) {
            showLoadingDialog(getString(R.string.message_please_wait));
            EditRecipeRequest recipeRequest = new EditRecipeRequest();
            recipeRequest.parseToRequestObject(mRecipe);
            //String json = new Gson().toJson(recipeRequest);
            mInfoStagePresenter.editRecipeById(mRecipe.getId(), recipeRequest);
        }
    }

    @Override
    public void onRemoveItem(final int position) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.text_confirm_delete_rule)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO REMOVE RULE
                        if (isNetworkOffline()) {
                            return;
                        }
                        showLoadingDialog(getString(R.string.message_please_wait));
                        EditRecipeRequest recipeRequest = new EditRecipeRequest();
                        mRuleList.remove(position);
                        recipeRequest.parseToRequestObject(mRecipe);
//                        String json = new Gson().toJson(recipeRequest);
                        mInfoStagePresenter.editRecipeById(mRecipe.getId(), recipeRequest);
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }
}
