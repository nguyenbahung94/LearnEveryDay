package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ekakashi.greenhouse.database.model_server_response.ObjectAction;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCondition;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.stage.info_stage.OnRemoveItemClick;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AddRuleActivity extends BaseActivity implements View.OnClickListener, OnItemCallBack, AddRuleInterface.View, OnRemoveItemClick {

    private TextView tvCondition;
    private TextView tvEditCondition;
    private TextView tvEditAction;
    private TextView tvDoneCondition;
    private TextView tvDoneAction;
    private TextView tvConditionName1;
    private TextView tvConditionDetail1;
    private TextView tvConditionName2;
    private TextView tvConditionDetail2;
    private ImageView imgDeleteCondition1, imgDeleteCondition2;
    private RelativeLayout btnAddCondition;
    private LinearLayout layoutButton;
    private RelativeLayout btnAddAction;
    private RelativeLayout layoutCondition1;
    private RelativeLayout layoutCondition2;
    private RecyclerView rvAction;
    private Button btnAnd;
    private Button btnOr;
    private boolean isEditConditionClick;
    private boolean isEditActionClick = false;
    private int ruleType;
    private ObjectRecipe mRecipe;
    private AddRulePresenter mAddRulePresenter;
    private ObjectGrowth mStage;
    private boolean fromAddStage;

    private ActionAdapter mActionAdapter;
    private List<ObjectAction> mActionList;
    private List<ObjectCondition> mConditionList;
    private List<ObjectRule> mRuleList = new ArrayList<>();
    private ObjectRule mRuleDetail;// Object Rule to show detail
    private boolean viewDetail;// VIEW DETAIL MODE: NO EDIT OR ADD NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rule);
        initView();
        addToolbar();
        prepareData();
        setUpUIForViewDetail();
    }

    private void initView() {
        tvCondition = findViewById(R.id.tvCondition);
        btnAddCondition = findViewById(R.id.btnAddCondition);
        btnAddAction = findViewById(R.id.btnAddAction);
        btnAnd = findViewById(R.id.btnAnd);
        btnOr = findViewById(R.id.btnOr);
        rvAction = findViewById(R.id.rvAction);
        tvEditCondition = findViewById(R.id.tvEditCondition);
        tvEditAction = findViewById(R.id.tvEditAction);
        tvDoneCondition = findViewById(R.id.tvDoneCondition);
        tvDoneAction = findViewById(R.id.tvDoneAction);
        imgDeleteCondition1 = findViewById(R.id.imgDeleteCondition1);
        imgDeleteCondition2 = findViewById(R.id.imgDeleteCondition2);
        layoutButton = findViewById(R.id.layoutButton);
        layoutCondition1 = findViewById(R.id.layoutCondition1);
        layoutCondition2 = findViewById(R.id.layoutCondition2);
        tvConditionName1 = findViewById(R.id.tvConditionName1);
        tvConditionDetail1 = findViewById(R.id.tvConditionDetail1);
        tvConditionName2 = findViewById(R.id.tvConditionName2);
        tvConditionDetail2 = findViewById(R.id.tvConditionDetail2);

        tvEditCondition.setOnClickListener(this);
        tvEditAction.setOnClickListener(this);
        tvDoneCondition.setOnClickListener(this);
        tvDoneAction.setOnClickListener(this);
        btnAddCondition.setOnClickListener(this);
        btnAddAction.setOnClickListener(this);
        btnAnd.setOnClickListener(this);
        btnOr.setOnClickListener(this);
        imgDeleteCondition1.setOnClickListener(this);
        imgDeleteCondition2.setOnClickListener(this);
        layoutCondition1.setOnClickListener(this);
        layoutCondition2.setOnClickListener(this);

        if (getIntent() != null) {
            ruleType = getIntent().getIntExtra(Utils.Name.TYPE, 0);
            mRecipe = App.appRecipe;
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
            if (mStage != null) {
                if (mStage.getRules() != null && !mStage.getRules().isEmpty()) {
                    mRuleList = mStage.getRules();
                }
            }
            //for View Detail
            fromAddStage = getIntent().getBooleanExtra(Utils.Name.ADD_STAGE, false);
            if (mRuleList != null && !mRuleList.isEmpty()) {
                int ruleId = getIntent().getIntExtra(Utils.Name.RULE, -1);
                if (ruleId != -1) {
                    for (ObjectRule rule : mRuleList) {
                        if (rule.getId() == ruleId) {
                            mRuleDetail = rule;
                            break;
                        }
                    }
                }
            }
            viewDetail = getIntent().getBooleanExtra(Utils.Name.VIEW_DETAIL, false);
        }
        mAddRulePresenter = new AddRulePresenter(this);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        if (viewDetail) {
            ruleType = mRuleDetail.getRuleType();
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.rule_information), Utils.getRuleType(this, ruleType));
        } else {
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.add_rule), Utils.getRuleType(this, ruleType));
        }

        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if ((mConditionList != null && !mConditionList.isEmpty()) && (mActionList != null && !mActionList.isEmpty())) {
                    //TODO: Call API save account setting
                    ObjectRule rule = new ObjectRule();
                    rule.setName(Utils.createRuleName(AddRuleActivity.this, mRecipe,ruleType, mConditionList, mActionList));
                    rule.setNotify(true);
                    rule.setActions(mActionList);
                    rule.setRuleType(ruleType);
                    rule.setConditions(mConditionList);

                    if (fromAddStage) {
//                        Intent intent = new Intent();
                        App.appRule = rule;
//                        intent.putExtra(Utils.Name.RULE, rule);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        mRuleList.add(rule);
                        mStage.setRules(mRuleList);
                        List<ObjectGrowth> stageList = mRecipe.getStages();
                        Integer currentStageId = mStage.getId();
                        if (currentStageId != null && stageList != null && !stageList.isEmpty()) {
                            for (int i = 0; i < stageList.size(); i++) {
                                if (stageList.get(i).getId() != null) {
                                    if (stageList.get(i).getId().intValue() == currentStageId.intValue()) {
                                        mRecipe.getStages().set(i, mStage);
                                        break;
                                    }
                                }
                            }
                        }

                        if (isNetworkOffline()) {
                            return;
                        }
                        EditRecipeRequest recipeRequest = new EditRecipeRequest();
                        recipeRequest.parseToRequestObject(mRecipe);
//                        String json = new Gson().toJson(recipeRequest);
                        showLoadingDialog(getString(R.string.message_please_wait));
                        mAddRulePresenter.editRecipeById(mRecipe.getId(), recipeRequest);
                    }
                } else {
                    Toast.makeText(AddRuleActivity.this, "User must select at least one Condition and one Action", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void prepareData() {
        mActionList = new ArrayList<>();
        mConditionList = new ArrayList<>();

        mActionAdapter = new ActionAdapter(this, mActionList);
        mActionAdapter.setOnItemCallBack(this);
        mActionAdapter.setOnRemoveCallBack(this);
        rvAction.setLayoutManager(new LinearLayoutManager(this));
        rvAction.setAdapter(mActionAdapter);
    }

    private void setUpUIForViewDetail() {
        if (viewDetail) {
            //UI FOR CONDITION LIST
            tvEditCondition.setVisibility(GONE);
            tvDoneCondition.setVisibility(GONE);
            btnAddCondition.setVisibility(GONE);
            btnAnd.setEnabled(false);
            btnOr.setEnabled(false);

            if (mRuleDetail.getConditions() != null && !mRuleDetail.getConditions().isEmpty()) {
                mConditionList = mRuleDetail.getConditions();

                // Case Condition list has one item
                if (mConditionList.size() == 1) {
                    imgDeleteCondition1.setVisibility(GONE);
                    layoutButton.setVisibility(GONE);
                    tvCondition.setText(getString(R.string.condition_1));
                    layoutCondition1.setVisibility(VISIBLE);
                    tvConditionDetail1.setText(Utils.createContentFromViewDetailCondition(this, ruleType, mConditionList.get(0), getStageInCondition(mConditionList.get(0))));
                    tvConditionName1.setText(getString(R.string.condition));
                }
                // Case Condition list has two items
                if (mConditionList.size() == 2) {
                    layoutButton.setVisibility(VISIBLE);
                    tvCondition.setText(getString(R.string.condition_2));

                    //CONDITION 1
                    layoutCondition1.setVisibility(VISIBLE);
                    imgDeleteCondition1.setVisibility(GONE);
                    tvConditionDetail1.setText(Utils.createContentFromViewDetailCondition(this, ruleType, mConditionList.get(0), getStageInCondition(mConditionList.get(0))));
                    tvConditionName1.setText(getString(R.string.condition) + " 1 ");

                    //CONDITION 2
                    layoutCondition2.setVisibility(VISIBLE);
                    imgDeleteCondition2.setVisibility(GONE);
                    tvConditionDetail2.setText(Utils.createContentFromViewDetailCondition(this, ruleType, mConditionList.get(1), getStageInCondition(mConditionList.get(1))));
                    tvConditionName2.setText(getString(R.string.condition) + " 2 ");

                    //SET BUTTON AND OR. WHICH BUTTON WILL TURN ON
                    if (mConditionList.get(1).isLogicalOperator()) {
                        //TRUE: AND BUTTON TURN ON
                        btnAnd.setBackground(getResources().getDrawable(R.drawable.button_border_corner_green));
                        btnAnd.setTextColor(getResources().getColor(R.color.white));

                        btnOr.setBackground(getResources().getDrawable(R.drawable.button_border_corner_white));
                        btnOr.setTextColor(getResources().getColor(R.color.bg_green_btn));

                    } else {
                        //FALSE: AND BUTTON TURN ON
                        btnOr.setBackground(getResources().getDrawable(R.drawable.button_border_corner_green));
                        btnOr.setTextColor(getResources().getColor(R.color.white));

                        btnAnd.setBackground(getResources().getDrawable(R.drawable.button_border_corner_white));
                        btnAnd.setTextColor(getResources().getColor(R.color.bg_green_btn));
                    }
                }
            }


            //UI FOR ACTION LIST
            tvEditAction.setVisibility(GONE);
            btnAddAction.setVisibility(GONE);
            tvDoneAction.setVisibility(GONE);
            if (mRuleDetail.getActions() != null && !mRuleDetail.getActions().isEmpty()) {
                mActionAdapter.setEditMode(false);//EDIT MODE IS OFF
                mActionList = mRuleDetail.getActions();
                mActionAdapter.setActions(mActionList);
            }
        }
    }

    private String getStageInCondition(ObjectCondition condition) {
        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
            for (ObjectGrowth stage : mRecipe.getStages()) {
                if (stage.getId() != null && condition.getAdditionalInformation() != null) {
                    if (stage.getId().intValue() == condition.getAdditionalInformation().intValue()) {
                        return stage.getName();
                    }
                }
            }
        }
        return "";
    }

//    private String createRuleName() {
//        String ruleName = "";
//        if (mConditionList != null && !mConditionList.isEmpty()) {
//            if (mConditionList.size() == 1) {
//                ruleName = mConditionList.get(0).getContent();
//            }
//            if (mConditionList.size() == 2) {
//                if (mConditionList.get(1).isLogicalOperator()) {//TRUE: AND // FALSE: OR
//                    ruleName = mConditionList.get(0).getContent() + " and " + mConditionList.get(1).getContent();
//                } else {
//                    ruleName = mConditionList.get(0).getContent() + " or " + mConditionList.get(1).getContent();
//                }
//            }
//            if (mActionList != null && !mActionList.isEmpty()) {
//                for (ObjectAction action : mActionList) {
//                    ruleName = ruleName.concat("\n" + Utils.getNameOfAction(this, action.getActionType()));
//                }
//            }
//        }
//        return ruleName;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvEditCondition:
                editCondition();
                break;
            case R.id.tvEditAction:
                editAction();
                break;
            case R.id.btnAddCondition:
                addCondition();
                break;
            case R.id.btnAddAction:
                addAction();
                break;
            case R.id.tvDoneCondition:
                doneCondition();
                break;
            case R.id.tvDoneAction:
                doneAction();
                break;
            case R.id.btnAnd:
                onAndClick();
                break;
            case R.id.btnOr:
                onOrClick();
                break;
            case R.id.imgDeleteCondition1:
                MaterialDialog dialogCondition1 = new MaterialDialog.Builder(this)
                        .title(R.string.text_confirm_delete_condition)
                        .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (mConditionList.size() == 1) {
                                    tvCondition.setText(getString(R.string.condition_0));
                                    layoutCondition1.setVisibility(GONE);
                                    layoutButton.setVisibility(GONE);
                                    layoutCondition2.setVisibility(GONE);
                                    btnAddCondition.setVisibility(VISIBLE);
                                    tvEditCondition.setVisibility(VISIBLE);
                                    tvDoneCondition.setVisibility(GONE);
                                    imgDeleteCondition1.setVisibility(GONE);
                                }
                                if (mConditionList.size() == 2) {
                                    btnAddCondition.setVisibility(VISIBLE);
                                    tvCondition.setText(getString(R.string.condition_1));
                                    layoutCondition1.setVisibility(VISIBLE);
                                    layoutCondition2.setVisibility(GONE);
                                    layoutButton.setVisibility(GONE);

                                    tvConditionName1.setText(mConditionList.get(0).getName());
                                    tvConditionDetail1.setText(mConditionList.get(0).getContent());
                                }
                                mConditionList.remove(0);
                            }
                        })
                        .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
                dialogCondition1.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                dialogCondition1.show();

                break;
            case R.id.imgDeleteCondition2:
                MaterialDialog dialogCondition2 = new MaterialDialog.Builder(this)
                        .title(getString(R.string.text_confirm_delete_condition))
                        .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                btnAddCondition.setVisibility(VISIBLE);
                                tvCondition.setText(getString(R.string.condition_1));
                                layoutCondition2.setVisibility(GONE);
                                layoutButton.setVisibility(GONE);

                                tvConditionName1.setText(mConditionList.get(0).getName());
                                tvConditionDetail1.setText(mConditionList.get(0).getContent());
                                mConditionList.remove(1);
                            }
                        })
                        .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
                dialogCondition2.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                dialogCondition2.show();
                break;
            case R.id.layoutCondition1:
                //Only use for see Detail Condition1
                if (viewDetail) {
                    Intent condition1 = new Intent(this, AddConditionActivity.class);
                    condition1.putExtra(Utils.Name.VIEW_DETAIL, true);
                    condition1.putExtra(Utils.Name.RULE, ruleType);
                    App.appCondition = mConditionList.get(0);
                    startActivity(condition1);
                }//else do nothing
                break;
            case R.id.layoutCondition2:
                //Only use for see Detail Condition2
                if (viewDetail) {
                    Intent condition2 = new Intent(this, AddConditionActivity.class);
                    condition2.putExtra(Utils.Name.VIEW_DETAIL, true);
                    condition2.putExtra(Utils.Name.RULE, ruleType);
                    App.appCondition = mConditionList.get(1);
                    startActivity(condition2);
                }//else do nothing
                break;
            default:
                break;
        }

    }

    private void editCondition() {
        isEditConditionClick = true;
        handleCondition();
    }

    private void editAction() {
        isEditActionClick = true;
        tvEditAction.setVisibility(GONE);
        tvDoneAction.setVisibility(VISIBLE);
        mActionAdapter.setEditMode(true);
    }

    private void addCondition() {
        Intent intent = new Intent(this, AddConditionActivity.class);
        App.appRecipe = mRecipe;
//        intent.putExtra(Utils.Name.RECIPE, mRecipe);
        intent.putExtra(Utils.Name.TYPE, ruleType);
        startActivityForResult(intent, Utils.Name.ADD_CONDITION_CODE);
    }

    private void addAction() {
        Intent intent = new Intent(this, AddActionActivity.class);
        intent.putExtra(Utils.Name.TYPE, ruleType);
        startActivityForResult(intent, Utils.Name.ADD_ACTION_CODE);
    }

    private void doneCondition() {
        isEditConditionClick = false;
        handleCondition();
    }

    private void doneAction() {
        isEditActionClick = false;
        tvEditAction.setVisibility(VISIBLE);
        tvDoneAction.setVisibility(GONE);
        mActionAdapter.setEditMode(false);
    }


    private void onAndClick() {
        btnAnd.setBackground(getResources().getDrawable(R.drawable.button_border_corner_green));
        btnAnd.setTextColor(getResources().getColor(R.color.white));

        btnOr.setBackground(getResources().getDrawable(R.drawable.button_border_corner_white));
        btnOr.setTextColor(getResources().getColor(R.color.bg_green_btn));

        if (mConditionList.size() == 2) {
            mConditionList.get(1).setLogicalOperator(true);
        }
    }

    private void onOrClick() {
        btnOr.setBackground(getResources().getDrawable(R.drawable.button_border_corner_green));
        btnOr.setTextColor(getResources().getColor(R.color.white));

        btnAnd.setBackground(getResources().getDrawable(R.drawable.button_border_corner_white));
        btnAnd.setTextColor(getResources().getColor(R.color.bg_green_btn));

        if (mConditionList.size() == 2) {
            mConditionList.get(1).setLogicalOperator(false);
        }
    }

    private void handleCondition() {
        if (isEditConditionClick) {
            tvEditCondition.setVisibility(GONE);
            tvDoneCondition.setVisibility(VISIBLE);

            imgDeleteCondition1.setVisibility(VISIBLE);
            imgDeleteCondition2.setVisibility(VISIBLE);
        } else {
            tvEditCondition.setVisibility(VISIBLE);
            tvDoneCondition.setVisibility(View.GONE);

            imgDeleteCondition1.setVisibility(GONE);
            imgDeleteCondition2.setVisibility(GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.Name.ADD_ACTION_CODE:
                    if (data.getParcelableExtra(Utils.Name.ACTION) != null) {
//                        mActionList.add((ObjectAction) data.getParcelableExtra(Utils.Name.ACTION));
                        mActionList.add(App.appAction);
                        int i, size = mActionList.size();
                        for (i = 0; i < size; i++) {
                            mActionList.get(i).setSortNo(i + 1);
                            //TODO set id
//                            mActionList.get(i).setId(i);
                        }
                        mActionAdapter.setEditMode(isEditActionClick);
                        if (mActionList.size() > 0) {
                            if (isEditActionClick) {
                                tvEditAction.setVisibility(GONE);
                                tvDoneAction.setVisibility(VISIBLE);
                            } else {
                                tvEditAction.setVisibility(VISIBLE);
                                tvDoneAction.setVisibility(GONE);
                            }

                        }
                    }
                    break;
                case Utils.Name.ADD_CONDITION_CODE:
                    if (data.getParcelableExtra(Utils.Name.CONDITION) != null) {
                        ObjectCondition condition = App.appCondition;
                        mConditionList.add(condition);
                        //set Id for Condition Item to send BE
                        int i, size = mConditionList.size();
                        for (i = 0; i < size; i++) {
                            //TODO set id
//                            mConditionList.get(i).setId(i);
                            mConditionList.get(i).setSortNo(i + 1);
                        }

                        //Set Up UI
                        if (mConditionList.size() == 0) {// Case Has no Condition
                            tvEditCondition.setVisibility(GONE);
                            layoutButton.setVisibility(GONE);
                            btnAddCondition.setVisibility(VISIBLE);
                            tvCondition.setText(R.string.condition_0);
                        } else {
                            if (mConditionList.size() == 1) {// Case Condition list has one item
                                handleCondition();
                                btnAddCondition.setVisibility(VISIBLE);
                                layoutButton.setVisibility(GONE);
                                tvCondition.setText(getString(R.string.condition_1));
                                tvConditionDetail1.setText(mConditionList.get(0).getContent());
                                layoutCondition1.setVisibility(VISIBLE);
                                tvConditionName1.setText(getString(R.string.condition));
                            }
                            if (mConditionList.size() == 2) {// Case Condition list has two items
                                handleCondition();
                                layoutButton.setVisibility(VISIBLE);
                                btnAddCondition.setVisibility(GONE);
                                tvCondition.setText(getString(R.string.condition_2));
                                onAndClick();

                                tvConditionDetail2.setText(mConditionList.get(1).getContent());
                                layoutCondition2.setVisibility(VISIBLE);
                                tvConditionName2.setText(getString(R.string.condition));
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        if (mActionAdapter != null) {
            mActionAdapter.onDestroy();
        }
        if (mAddRulePresenter != null) {
            mAddRulePresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void OnItemClick(int position) {
        //Click On Action item
        if (viewDetail) {
            Intent action = new Intent(this, AddActionActivity.class);
            action.putExtra(Utils.Name.VIEW_DETAIL, true);
            action.putExtra(Utils.Name.RULE, mRuleDetail.getRuleType());
            App.appAction = mActionList.get(position);
            startActivity(action);
        }
    }

    @Override
    public void onRemoveItem(int position) {
        if (mActionList.size() == 0) {
            tvEditAction.setVisibility(GONE);
            tvDoneAction.setVisibility(GONE);
            mActionAdapter.setEditMode(false);
            mActionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void editSuccess(ObjectRecipe recipe) {
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


            Intent intent = new Intent();
            intent.putExtra(Utils.Name.RECIPE, recipe);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void failed(String s) {
        hideLoadingDialog();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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


}
