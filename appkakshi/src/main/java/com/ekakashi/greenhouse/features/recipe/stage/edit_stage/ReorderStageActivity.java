package com.ekakashi.greenhouse.features.recipe.stage.edit_stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.OnStartDragListener;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.SimpleItemTouchHelperCallback;
import com.ekakashi.greenhouse.features.recipe.stage.info_stage.InfoStageActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;

public class ReorderStageActivity extends BaseActivity implements OnStartDragListener, View.OnClickListener, ReorderStageInterface.View, OnItemCallBack, OnInfoCallBack, OnRemoveStageCallBack {

    private ItemTouchHelper touchHelper;
    private RecyclerView rvStage;
    private ObjectRecipe mRecipe;
    private EditStageAdapter editStageAdapter;
    private List<ObjectGrowth> mStageList;
    private ReorderStagePresenter mReorderStagePresenter;
    private boolean editPressed = false;
    private MaterialDialog moreDialog;
    private int stagePos = -1;
    private MyToolbar myToolbar;
    private SimpleItemTouchHelperCallback callback;
    private boolean removeStage = false;
    private RelativeLayout btnAddStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder_stage);
        initView();
        prepareData();
        addToolbar();
    }

    private void initView() {
        mStageList = new ArrayList<>();
        mReorderStagePresenter = new ReorderStagePresenter(this);
        rvStage = findViewById(R.id.rvStage);
        mToolbar = findViewById(R.id.toolbar);
        btnAddStage = findViewById(R.id.btnAddStage);
        btnAddStage.setOnClickListener(this);

        moreDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_more_reorder, true).build();
        TextView tvStage = (TextView) moreDialog.findViewById(R.id.tvStage);
        TextView tvRule = (TextView) moreDialog.findViewById(R.id.tvRule);
        TextView tvCancel = (TextView) moreDialog.findViewById(R.id.tvCancel);

        tvStage.setOnClickListener(this);
        tvRule.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void prepareData() {
        int currentStageOfRecipe = 0;
//        mRecipe = getIntent().getParcelableExtra(Utils.Name.RECIPE);
        mRecipe = App.appRecipe;
        if (mRecipe != null) {
            mStageList = mRecipe.getStages();
            sortStageList();
            lastStage();
            currentStageOfRecipe = mRecipe.getCurrentStageId();
        }
        editStageAdapter = new EditStageAdapter(this, mStageList, this, currentStageOfRecipe);
        callback = new SimpleItemTouchHelperCallback(editStageAdapter);
        touchHelper = new ItemTouchHelper(callback);
        editStageAdapter.setOnItemCallBack(this);
        editStageAdapter.setOnInfoCallBack(this);
        editStageAdapter.setOnRemoveStageCallBack(this);

        rvStage.setLayoutManager(new LinearLayoutManager(this));
        rvStage.setAdapter(editStageAdapter);

        touchHelper.attachToRecyclerView(rvStage);
    }

    private void sortStageList() {
        Collections.sort(mStageList, new Comparator<ObjectGrowth>() {
            @Override
            public int compare(ObjectGrowth o1, ObjectGrowth o2) {
                return o1.getOrderPos() - o2.getOrderPos();
            }
        });
    }


    private void addToolbar() {
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.select_current_stage), mRecipe.getRecipeName());
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (!editPressed) {
                    editPressed = true;
                    editStageAdapter.setEdit(true);
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
                } else {
                    editPressed = false;
                    editStageAdapter.setEdit(false);
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
                    onSaveClicked(0);
                }
            }
        });
    }

    private void lastStage() {
        if (mRecipe != null && mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()
                && mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty()
                && mRecipe.getEkFields().get(0) != null && mRecipe.getEkFields().get(0).getCurrentStage() != null) {
            if (mRecipe.getEkFields().get(0).getCurrentStage().getOrderPos() == mRecipe.getStages().size()) {
                // LAST STAGE and STATUS = 2 => hide AddStage button
                if (mRecipe.getEkFields().get(0).getCurrentStage().getStatus() == Integer.parseInt(Utils.Name.TWO)) {
                    btnAddStage.setVisibility(GONE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
        App.appRecipe = mRecipe;
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    private void onSaveClicked(int stagePosition) {
        List<ObjectGrowth> stages = editStageAdapter.getStages();
        mRecipe.setStages(stages);
        if (mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty()) {
            mRecipe.getEkFields().get(0).getCurrentStage().setId(mRecipe.getCurrentStageId());
        }
        EditRecipeRequest recipe = new EditRecipeRequest();
        recipe.parseToRequestObject(mRecipe);
        if (removeStage) {
            recipe.recipeStages.remove(stagePosition);
            //Set SortNo for each Recipe Stage
            if (recipe.recipeStages != null && !recipe.recipeStages.isEmpty()) {
                int index, size = recipe.recipeStages.size();
                for (index = 0; index < size; index++) {
                    recipe.recipeStages.get(index).sortNo = (index + 1);
                }
            }
            removeStage = false;
        }
//        String json = new Gson().toJson(recipe);

        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mReorderStagePresenter.editRecipeById(mRecipe.getId(), recipe);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder, int select, int drag) {
        if (drag >= select) {
            touchHelper.startDrag(viewHolder);
        }
    }


    @Override
    public void editRecipeSuccess(ObjectRecipe recipe) {
        hideLoadingDialog();
        Prefs.getInstance(App.getsInstance()).saveStatusCallApi(true);
        if (recipe != null) {
            if (!editPressed) {
                myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
                editStageAdapter.setEdit(editPressed);
            }

            mRecipe = recipe;
            if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
                if (recipe.getEkFields().get(0) != null) {
                    if (recipe.getEkFields().get(0).getCurrentStage() != null) {
                        editStageAdapter.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                        mRecipe.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                    } else {
                        mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                    }
                } else {
                    mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                }
            } else {
                mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
            }

            if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                mStageList = mRecipe.getStages();
                sortStageList();
            } else {
                mStageList = new ArrayList<>();
            }
            editStageAdapter.setData(mStageList);
            App.appRecipe = mRecipe;
            lastStage();
        } else {
            customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
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
        if (mReorderStagePresenter != null) {
            mReorderStagePresenter.onDestroy();
        }
        if (editStageAdapter != null) {
            editStageAdapter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void OnItemClick(int position) {
//        if (!editPressed) {
//            stagePos = position;
//            if (isNetworkOffline()) {
//                return;
//            }
//            showLoadingDialog(getString(R.string.message_please_wait));
//            if (mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty()) {
//                mReorderStagePresenter.changeRecipeStage(mRecipe.getEkFields().get(0).getEkField().getId(), mRecipe.getId(), mStageList.get(position).getId());
//            }
//        }
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
    }

    @Override
    public void onInfoCallBack(int position) {
        stagePos = position;
        moreDialog.show();
    }

    @Override
    public void changeRecipeStageSuccess(ChangeStageRecipeResponse response) {
        if (response != null) {
            editStageAdapter.setCurrentStageId(mStageList.get(stagePos).getId());
            mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
            editStageAdapter.notifyDataSetChanged();
            mReorderStagePresenter.getRecipeDetailById(mRecipe.getId());
        }
    }

    @Override
    public void getRecipeDetailByIdSuccess(ObjectRecipe recipe) {
        hideLoadingDialog();
        if (recipe != null) {
            mRecipe = recipe;
            if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
                if (recipe.getEkFields().get(0) != null) {
                    if (recipe.getEkFields().get(0).getCurrentStage() != null) {
                        editStageAdapter.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                        editStageAdapter.notifyDataSetChanged();
                        mRecipe.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                    } else {
                        mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                    }
                } else {
                    mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                }
            } else {
                mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
            }
        }
    }

    @Override
    public void setPositionSelect(int positionSelect) {
        callback.setSelect(positionSelect);
    }

    @Override
    public void onRemoveStage(ObjectGrowth stage, int position) {
//        mStageList.remove(stage);
        removeStage = true;
        onSaveClicked(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.Constant.EDIT_STAGE_CODE:
                    mRecipe = App.appRecipe;
                    mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                    if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                        mStageList = mRecipe.getStages();
                        sortStageList();
                        lastStage();
                        editStageAdapter.setData(mStageList);
                    }
                    break;
                case Utils.Constant.ADD_STAGE_CODE:
                    Prefs.getInstance(App.getsInstance()).saveStatusCallApi(true);
                    mRecipe = App.appRecipe;
                    mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                    if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                        mStageList = mRecipe.getStages();
                        sortStageList();
                        lastStage();
                        editStageAdapter.setData(mStageList);
                    }
                    break;
                case Utils.Constant.EDIT_RULE_CODE:
                    mRecipe = App.appRecipe;
                    mRecipe.setCurrentStageId(editStageAdapter.getCurrentStageId());
                    if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
                        mStageList = mRecipe.getStages();
                        sortStageList();
                        lastStage();
                        editStageAdapter.setData(mStageList);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddStage:
                //TODO Add Stage
                Intent data = new Intent(this, EditStageActivity.class);
                mRecipe.setStages(mStageList);
                App.appRecipe = mRecipe;
//                data.putExtra(Utils.Name.RECIPE, mRecipe);
                data.putExtra(Utils.Name.ADD_STAGE, EnumRule.FROM_REORDER);
                startActivityForResult(data, Utils.Constant.ADD_STAGE_CODE);
                break;
            case R.id.tvStage:
                //TODO Edit Stage
                Intent intent = new Intent(this, EditStageActivity.class);
                mRecipe.setStages(mStageList);
//                intent.putExtra(Utils.Name.RECIPE, mRecipe);
                App.appRecipe = mRecipe;
                App.appStage = mStageList.get(stagePos);
//                intent.putExtra(Utils.Name.STAGE, );
                intent.putExtra(Utils.Name.STAGE_POS, stagePos);
                intent.putExtra(Utils.Name.ADD_STAGE, EnumRule.FROM_EDIT_STAGE);
                startActivityForResult(intent, Utils.Constant.EDIT_STAGE_CODE);
                moreDialog.dismiss();
                break;
            case R.id.tvRule:
                //TODO Show list rule
                Intent rule = new Intent(this, InfoStageActivity.class);
                rule.putExtra(Utils.Name.RULE, true);//edit mode
//                rule.putExtra(Utils.Name.RECIPE, mRecipe);
                App.appRecipe = mRecipe;
                rule.putExtra(Utils.Name.STAGE, mStageList.get(stagePos).getId());
                rule.putExtra(Utils.Name.STAGE_POS, stagePos);
                startActivityForResult(rule, Utils.Constant.EDIT_RULE_CODE);
                moreDialog.dismiss();
                break;
            case R.id.tvCancel:
                moreDialog.dismiss();
                break;
            default:
                break;
        }
    }
}
