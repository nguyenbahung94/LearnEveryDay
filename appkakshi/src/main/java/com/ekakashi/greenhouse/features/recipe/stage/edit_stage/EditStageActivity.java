package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRule;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;
import com.ekakashi.greenhouse.features.recipe.rule.select_type.SelectTypeActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class EditStageActivity extends BaseActivity implements View.OnClickListener, TextWatcher, EditStageInterface.View {

    private ObjectGrowth mStage;
    private ObjectGrowth mAddStage = new ObjectGrowth();
    private EditRuleAdapter mEditRuleAdapter;
    private ObjectRecipe mRecipe;
    private EditStagePresenter mEditStagePresenter;
    private int mStagePosition;
    private List<ObjectRule> mRuleList = new ArrayList<>();
    private int mStageId;
    private int addStage = 1;

    private EditText edtStageName;
    private EditText edtDescription;
    private ImageView imgName;
    private ImageView imgDescription;
    private RelativeLayout layoutAddRule;
    private RecyclerView mRvRule;
    private Toolbar mToolbar;
    private TextView rule;
    private String mStageNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stage);
        initView();
        addToolbar();
        prepareData();
    }

    private void initView() {
        edtStageName = findViewById(R.id.edtStageName);
        imgName = findViewById(R.id.imgName);
        layoutAddRule = findViewById(R.id.layoutAddRule);
        edtDescription = findViewById(R.id.edtDescription);
        imgDescription = findViewById(R.id.imgDescription);
        mRvRule = findViewById(R.id.rvRule);
        mToolbar = findViewById(R.id.toolbar);
        rule = findViewById(R.id.rule);

        imgDescription.setOnClickListener(this);
        layoutAddRule.setOnClickListener(this);
        imgName.setOnClickListener(this);
        edtStageName.addTextChangedListener(this);
        edtDescription.addTextChangedListener(this);

        if (getIntent() != null) {
            addStage = getIntent().getIntExtra(Utils.Name.ADD_STAGE, 1);
            mRecipe = App.appRecipe;
            mStage = App.appStage;
            if (mStage != null) {
                mStageId = mStage.getId();
            }
            mStagePosition = getIntent().getIntExtra(Utils.Name.STAGE_POS, -1);
        }
        mEditStagePresenter = new EditStagePresenter(this);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);

        if (addStage == EnumRule.FROM_REORDER) {
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.growing_stage_info), mRecipe.getRecipeName());
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);

            //TODO Next phase will be Visible
            layoutAddRule.setVisibility(View.GONE);
            rule.setVisibility(View.GONE);
            mRvRule.setVisibility(View.GONE);
        } else if (addStage == EnumRule.FROM_EDIT_STAGE) {
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.growing_stage_info), mRecipe.getRecipeName());
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);

            layoutAddRule.setVisibility(View.GONE);
            rule.setVisibility(View.GONE);
            mRvRule.setVisibility(GONE);
        } else {
            //TODO addStage == EnumRule.FROM_SELECT_CURRENT_STAGE
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.add_growing_stage), mRecipe.getRecipeName());
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);

            layoutAddRule.setVisibility(View.GONE);
            rule.setVisibility(View.GONE);
            mRvRule.setVisibility(GONE);
        }

        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (addStage != EnumRule.FROM_REORDER) {
                    if (addStage == EnumRule.FROM_SELECT_CURRENT_STAGE) {
                        addStageFromSelectStage();
                    } else {
                        onEditStage();
                    }
                } else {
                    //TODO Next phase will uncomment
//                    if (mRuleList != null && !mRuleList.isEmpty()) {
                    addStageFromReorder();
//                    } else {
//                        Toast.makeText(EditStageActivity.this, getString(R.string.stage_need_one_rule), Toast.LENGTH_LONG).show();
//                    }
                }
            }
        });
    }

    private void prepareData() {
        if (addStage == EnumRule.FROM_EDIT_STAGE) {
            mStageNameEdit = mStage.getName();
            edtStageName.setText(mStage.getName());
            edtStageName.setSelection(edtStageName.getText().toString().length());
            edtDescription.setText(mStage.getDescription());
        } else {
            //TODO addStage == EnumRule.FROM_SELECT_CURRENT_STAGE
            mEditRuleAdapter = new EditRuleAdapter(new ArrayList<ObjectRule>(), this);
        }

        if (edtStageName.getText().toString().trim().isEmpty()) {
            imgName.setVisibility(GONE);
        } else {
            imgName.setVisibility(View.VISIBLE);
        }

        if (edtDescription.getText().toString().trim().isEmpty()) {
            imgDescription.setVisibility(GONE);
        } else {
            imgDescription.setVisibility(View.VISIBLE);
        }

        mRvRule.setLayoutManager(new LinearLayoutManager(this));
        mRvRule.setAdapter(mEditRuleAdapter);
    }

    private void addStageFromReorder() {
        if (!edtStageName.getText().toString().trim().isEmpty()) {
            String stageName = edtStageName.getText().toString().trim();
            if (!isExisted(stageName)) {
                mAddStage.setName(edtStageName.getText().toString().trim());
                mAddStage.setDescription(edtDescription.getText().toString().trim());
                mAddStage.setOrderPos(mRecipe.getStages().size() + 1);

                EditRecipeRequest recipe = new EditRecipeRequest();
                recipe.parseToRequestObject(mRecipe);
//                String jsonBefore = new Gson().toJson(recipe);
                recipe.recipeStages.add(new EditRecipeRequest.RecipeStages(mAddStage));
//                String jsonAfter = new Gson().toJson(recipe);
                if (isNetworkOffline()) {
                    return;
                }
                showLoadingDialog(getString(R.string.message_please_wait));
                mEditStagePresenter.editRecipeById(mRecipe.getId(), recipe);
            } else {
                Toast.makeText(this, R.string.stage_name_is_already_existed, Toast.LENGTH_LONG).show();
            }
        } else {
            edtStageName.setError(getString(R.string.error_field_required));
        }
    }

    private void onEditStage() {
        if (!edtStageName.getText().toString().trim().isEmpty()) {
            String stageName = edtStageName.getText().toString().trim();
            if (!isExistedForEditStageName(stageName)) {
                mStage.setName(stageName);
                mStage.setDescription(edtDescription.getText().toString().trim());
//                mRecipe.getStages().set(mStagePosition, mStage);
                EditRecipeRequest recipe = new EditRecipeRequest();
                recipe.parseToRequestObject(mRecipe);
                recipe.recipeStages.set(mStagePosition, new EditRecipeRequest.RecipeStages(mStage));
//                String json = new Gson().toJson(recipe);
                if (isNetworkOffline()) {
                    return;
                }
                showLoadingDialog(getString(R.string.message_please_wait));
                mEditStagePresenter.editRecipeById(mRecipe.getId(), recipe);
            } else {
                Toast.makeText(this, R.string.stage_name_is_already_existed, Toast.LENGTH_LONG).show();
            }
        } else {
            edtStageName.setError(getString(R.string.error_field_required));
        }
    }

    private boolean isExistedForEditStageName(String stageName) {
        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
            List<ObjectGrowth> stages = new ArrayList<>();
            stages.addAll(mRecipe.getStages());
            for (ObjectGrowth stage : stages) {
                if (mStageNameEdit.equalsIgnoreCase(stage.getName())) {
                    stages.remove(stage);
                    break;
                }
            }

            for (ObjectGrowth stage : stages) {
                if (stageName.equalsIgnoreCase(stage.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addStageFromSelectStage() {
        if (!edtStageName.getText().toString().trim().isEmpty()) {
            if (mRecipe != null) {
                String stageName = edtStageName.getText().toString().trim();
                if (!isExisted(stageName)) {
                    ObjectGrowth stage = new ObjectGrowth();
                    stage.setName(stageName);
                    stage.setDescription(edtDescription.getText().toString().trim());
                    stage.setOrderPos(mRecipe.getStages().size() + 1);

                    Intent intent = new Intent();
                    intent.putExtra(Utils.Name.STAGE, stage);
                    setResult(RESULT_OK, intent);
                    finish();// go to SelectCurrentStageActivity.java
                } else {
                    Toast.makeText(this, R.string.stage_name_is_already_existed, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            edtStageName.setError(getString(R.string.error_field_required));
        }
    }

    @Override
    public void editSuccess(ObjectRecipe recipe) {
        hideLoadingDialog();
        if (recipe != null) {
            int temptStageId = mRecipe.getCurrentStageId();
            mRecipe = recipe;
            if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
                if (recipe.getEkFields().get(0) != null) {
                    if (recipe.getEkFields().get(0).getCurrentStage() != null) {
                        mRecipe.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                    } else {
                        mRecipe.setCurrentStageId(temptStageId);
                    }
                } else {
                    mRecipe.setCurrentStageId(temptStageId);
                }
            } else {
                mRecipe.setCurrentStageId(temptStageId);
            }
            App.appRecipe = mRecipe;
            if (addStage == EnumRule.FROM_REORDER) {
//                Intent data = new Intent();
//                data.putExtra(Utils.Name.RECIPE, mRecipe);
                setResult(RESULT_OK);
                finish();
            }
            if (addStage == EnumRule.FROM_EDIT_STAGE) {
//                Intent data = new Intent();
//                data.putExtra(Utils.Name.RECIPE, mRecipe);
                setResult(RESULT_OK);
                finish();
            }
        }

    }

    private boolean isExisted(String stageName) {
        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
            for (ObjectGrowth stage : mRecipe.getStages()) {
                if (stageName.equalsIgnoreCase(stage.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (edtStageName.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                edtStageName.setError(null);
                imgName.setVisibility(View.VISIBLE);
            } else {
                imgName.setVisibility(View.GONE);
            }
        }
        if (edtDescription.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                imgDescription.setVisibility(View.VISIBLE);
            } else {
                imgDescription.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgName:
                edtStageName.requestFocus();
                edtStageName.setText("");
                imgName.setVisibility(GONE);
                break;
            case R.id.imgDescription:
                edtDescription.requestFocus();
                edtDescription.setText("");
                imgDescription.setVisibility(GONE);
                break;
            case R.id.layoutAddRule:
                //TODO ADD RULE ON ADD STAGE, WAITING UNTIL NEXT PHASE
                Intent intent = new Intent(this, SelectTypeActivity.class);
                intent.putExtra(Utils.Name.ADD_RULE, true);
                App.appRecipe = mRecipe;
                App.appStage = mStage;
//                intent.putExtra(Utils.Name.RECIPE, mRecipe);
//                intent.putExtra(Utils.Name.STAGE, mStage);
                intent.putExtra(Utils.Name.ADD_STAGE, true);
                startActivityForResult(intent, Utils.Name.ADD_RULE_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Utils.Name.ADD_RULE_CODE) {
                if (addStage == EnumRule.FROM_REORDER) {
//                    if (data.getParcelableExtra(Utils.Name.RULE) != null) {
//                        ObjectRule rule = data.getParcelableExtra(Utils.Name.RULE);
                    ObjectRule rule = App.appRule;
                    List<ObjectRule> rules = mAddStage.getRules();
                    if (rules == null || rules.isEmpty()) {
                        rules = new ArrayList<>();
                    }
                    rules.add(rule);
                    int i, size = rules.size();
                    for (i = 0; i < size; i++) {
                        rules.get(i).setId(i);
                    }
                    mAddStage.setRules(rules);
                    mRuleList = rules;
                    mEditRuleAdapter.setData(mRuleList);
//                    }
                }
                if (addStage == EnumRule.FROM_EDIT_STAGE) {
//                    if (data.getParcelableExtra(Utils.Name.STAGE) != null) {
//                        ObjectGrowth newStage = data.getParcelableExtra(Utils.Name.STAGE);
                    ObjectGrowth newStage = App.appStage;
                    if (newStage.getId() == mStageId) {
                        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                            int i;
                            for (i = 0; i < mRecipe.getStages().size(); i++) {
                                if (mRecipe.getStages().get(i).getId() == mStageId) {
                                    mRecipe.getStages().set(i, newStage);
                                    mRuleList = newStage.getRules();
                                    break;
                                }
                            }
                        }
                    }
                    mEditRuleAdapter.setData(mRuleList);
//                    }
                }
            }
        }
    }


    @Override
    public void failed(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        hideLoadingDialog();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        if (mEditStagePresenter != null) {
            mEditStagePresenter.onDestroy();
        }
        if (mEditRuleAdapter != null) {
            mEditRuleAdapter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
    }
}
