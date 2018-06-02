package com.ekakashi.greenhouse.features.recipe.edit_recipe;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.RoleOfUser;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.EditRecipeForFieldResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.AddFieldGreenHouseActivity;
import com.ekakashi.greenhouse.features.recipe.edit_basic_recipe.EditBasicRecipeActivity;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.OnInfoCallBack;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.ReorderStageActivity;
import com.ekakashi.greenhouse.features.view_information_field.EKViewFieldInformationActivity;
import com.ekakashi.greenhouse.features.weather.widget_list.template_recipe.WidgetListActivity;

import java.util.ArrayList;
import java.util.List;

public class EditRecipeActivity extends BaseActivity implements EditRecipeInterface.View, OnItemCallBack, View.OnClickListener, OnInfoCallBack {

    private RecyclerView rvRecipe;
    private List<ObjectRecipe> mRecipeList = new ArrayList<>();
    private EditRecipeAdapter mEditRecipeAdapter;
    private int mRecipePos = -1;
    private ObjectCreateField createField;
    private EditRecipePresenter mEditRecipePresenter;
    private RelativeLayout layoutNoItem;
    private ScrollView layoutItem;
    private boolean addMoreRecipe = false;
    private boolean editPressed = false;
    private MyToolbar myToolbar;
    private MaterialDialog moreDialog;
    private boolean isOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        initView();
        prepareData();
        addToolbar();
    }

    private void initView() {
        mEditRecipePresenter = new EditRecipePresenter(this);
        rvRecipe = findViewById(R.id.rvRecipe);
        RelativeLayout btnAddRecipe = findViewById(R.id.btnAddRecipe);
        TextView tvAddRecipe = findViewById(R.id.tvAddRecipe);
        layoutNoItem = findViewById(R.id.layoutNoItem);
        layoutItem = findViewById(R.id.layoutItem);
        moreDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_more, true).build();
        TextView tvBasicInfo = (TextView) moreDialog.findViewById(R.id.tvBasicInfo);
        TextView tvStage = (TextView) moreDialog.findViewById(R.id.tvStage);
        TextView tvWidget = (TextView) moreDialog.findViewById(R.id.tvWidget);
        TextView tvCancel = (TextView) moreDialog.findViewById(R.id.tvCancel);

        btnAddRecipe.setOnClickListener(this);
        tvAddRecipe.setOnClickListener(this);
        tvBasicInfo.setOnClickListener(this);
        tvStage.setOnClickListener(this);
        tvWidget.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void prepareData() {
        callApiToGetDetailRecipe();
        mEditRecipeAdapter = new EditRecipeAdapter(this);
        mEditRecipeAdapter.setItemCallBack(this);
        mEditRecipeAdapter.setOnInfoCallBack(this);
        rvRecipe.setLayoutManager(new LinearLayoutManager(this));
        rvRecipe.setAdapter(mEditRecipeAdapter);
        isOwner = RoleOfUser.Recipe.EditRecipeList(EditRecipeActivity.this);
    }

    private void callApiToGetDetailRecipe() {
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.EDIT_FILED);
        }
        if (createField != null && createField.getCountRecipe() > 0) {
            layoutItem.setVisibility(View.VISIBLE);
            layoutNoItem.setVisibility(View.GONE);
            if (isNetworkOffline()) {
                return;
            }
            showLoadingDialog(getString(R.string.message_please_wait));
            mEditRecipePresenter.getRecipeById(createField.getRecipeId());
        } else {
            layoutItem.setVisibility(View.GONE);
            layoutNoItem.setVisibility(View.VISIBLE);
        }
    }


    private void addToolbar() {
        String fieldName = createField.getPlaceName();
        if (fieldName == null) {
            fieldName = "";
        }
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.recipe_list), fieldName);
        if (isOwner) {
            if (createField != null && createField.getCountRecipe() > 0) {
                if (!editPressed) {
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
                } else {
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
                }
            } else {
                myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
            }
        } else {
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
        }

        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                onEditPressed();
            }
        });
    }

    private void onEditPressed() {
        if (!editPressed) {
            editPressed = true;
            mEditRecipeAdapter.setEditMode(true);
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
        } else {
            editPressed = false;
            mEditRecipeAdapter.setEditMode(false);
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
        }
    }

    @Override
    public void onBackPressed() {
        createField.setObjectRecipe(null);
        Intent data = new Intent(this, EKViewFieldInformationActivity.class);
        data.putExtra(Utils.Name.EDIT_FILED, createField);
        setResult(RESULT_OK, data);
        App.onDestroy();
        super.onBackPressed();
    }

    private void setCurrentStageId() {
        for (ObjectRecipe object : mRecipeList) {
            if (object.getEkFields() != null && !object.getEkFields().isEmpty()) {
                if (object.getEkFields().get(0).getCurrentStage() != null) {
                    object.setCurrentStageId(object.getEkFields().get(0).getCurrentStage().getId());
                }
            } else {
                if (object.getStages() != null && !object.getStages().isEmpty()) {
                    object.setCurrentStageId(object.getStages().get(0).getId());
                } else {
                    object.setCurrentStageId(0);
                }
            }
        }
    }

    @Override
    public void getRecipeSuccess(ObjectRecipe recipe) {
        hideLoadingDialog();
        if (recipe != null) {
            layoutItem.setVisibility(View.VISIBLE);
            layoutNoItem.setVisibility(View.GONE);
            if (isOwner) {
                if (!editPressed) {
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
                } else {
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
                }
            } else {
                myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
            }
            mRecipeList.add(recipe);
            setCurrentStageId();
            mEditRecipeAdapter.addData(mRecipeList);
            createField.setObjectRecipe(recipe);
            createField.setCountRecipe(mRecipeList.size());
            App.appRecipe = recipe;
        } else {
            customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
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
    public void cloneSuccess(ObjectRecipe objectRecipe) {
        if (objectRecipe != null) {
//            String json = new Gson().toJson(objectRecipe);
            createField.setRecipeId(objectRecipe.getId());
//            objectRecipe.setStages(createField.getObjectRecipe().getStages());
            if (isNetworkOffline()) {
                return;
            }
            //TODO EDIT STAGES FOR RECIPE BEFORE ADD RECIPE TO FIELD
            EditRecipeRequest request = new EditRecipeRequest();
            request.parseToRequestObject(objectRecipe);
            mEditRecipePresenter.editRecipeById(objectRecipe.getId(), request);
        } else {
            Toast.makeText(this, R.string.message_clone_recipe_failed, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void editRecipeSuccess(ObjectRecipe recipe) {
        List<ObjectGrowth> stagesList = recipe.getStages();
        for (ObjectGrowth ss : stagesList) {
            if (ss.getName().equalsIgnoreCase(createField.getStageName())) {
                createField.setCurrentStageId(ss.getId());
                break;
            }
        }
        mEditRecipePresenter.addRecipeForField(createField.getIdField(), recipe.getId(), createField.getCurrentStageId());
    }

    @Override
    public void addOrRemoveRecipeForFieldSuccess(EditRecipeForFieldResponse response) {
        if (response != null) {
            if (response.result) {
                if (addMoreRecipe) {//ADD RECIPE
                    mEditRecipePresenter.getRecipeById(createField.getRecipeId());
                } else {//REMOVE RECIPE
                    hideLoadingDialog();
                    mRecipeList.remove(mRecipePos);
                    mEditRecipeAdapter.notifyDataSetChanged();
                    createField.setCountRecipe(mRecipeList.size());
                    if (mRecipeList.isEmpty() || mRecipeList == null) {
                        layoutItem.setVisibility(View.GONE);
                        layoutNoItem.setVisibility(View.VISIBLE);

                        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
                        editPressed = false;
                        mEditRecipeAdapter.setEditMode(false);
                    } else {
                        layoutItem.setVisibility(View.VISIBLE);
                        layoutNoItem.setVisibility(View.GONE);
                        if (isOwner) {
                            if (!editPressed) {
                                myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
                            } else {
                                myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
                            }
                        } else {
                            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
                        }
                    }
                    //destroy appRecipe variable
                    App.onDestroy();
                }
                Toast.makeText(this, getString(R.string.toast_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.message_cant_edit_recipe_in) + createField.getPlaceName(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.message_cant_edit_recipe_in) + createField.getPlaceName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        if (mEditRecipePresenter != null) {
            mEditRecipePresenter.onDestroy();
        }
        if (mEditRecipeAdapter != null) {
            mEditRecipeAdapter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void OnItemClick(int position) {
        addMoreRecipe = false;
        mRecipePos = position;
        if (!mRecipeList.isEmpty()) {
            final ObjectRecipe recipe = mRecipeList.get(position);
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.text_confirm_delete_recipe)
                    .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (isNetworkOffline()) {
                                return;
                            }
                            showLoadingDialog(getString(R.string.message_please_wait));
                            mEditRecipePresenter.removeRecipeForField(createField.getIdField(), recipe.getId(), recipe.getCurrentStageId());
                        }
                    })
                    .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
            dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            dialog.show();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddRecipe:
                addMoreRecipe = true;
                if (mRecipeList.isEmpty()) {
                    Intent intent = new Intent(EditRecipeActivity.this, AddFieldGreenHouseActivity.class);
                    intent.putExtra(Utils.Constant.ADD_MORE_RECIPE, addMoreRecipe);
                    startActivityForResult(intent, Utils.Constant.ADD_RECIPE);
                } else {
                    Toast.makeText(this, R.string.message_cant_add_more_recipe, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvAddRecipe:
                addMoreRecipe = true;
                if (mRecipeList.isEmpty()) {
                    Intent intent = new Intent(EditRecipeActivity.this, AddFieldGreenHouseActivity.class);
                    intent.putExtra(Utils.Constant.ADD_MORE_RECIPE, addMoreRecipe);
                    startActivityForResult(intent, Utils.Constant.ADD_RECIPE);
                } else {
                    Toast.makeText(this, R.string.message_cant_add_more_recipe, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvBasicInfo:
                Intent intent = new Intent(this, EditBasicRecipeActivity.class);
//                intent.putExtra(Utils.Name.RECIPE, mRecipeList.get(mRecipePos));
                startActivityForResult(intent, Utils.Constant.EDIT_BASIC_RECIPE_CODE);
                moreDialog.dismiss();
                break;
            case R.id.tvStage:
                Intent data = new Intent(this, ReorderStageActivity.class);
//                data.putExtra(Utils.Name.RECIPE, mRecipeList.get(mRecipePos));
                startActivityForResult(data, Utils.Constant.EDIT_RECIPE_STAGE_CODE);
                moreDialog.dismiss();
                break;
            case R.id.tvWidget:
                if (moreDialog != null) {
                    moreDialog.dismiss();
                    moreDialog.cancel();
                }
                Intent start = new Intent(this, WidgetListActivity.class);
                start.putExtra(Utils.Name.CREATE_FILED, createField);
                startActivity(start);
                break;
            case R.id.tvCancel:
                moreDialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Utils.Constant.EDIT_BASIC_RECIPE_CODE) {
                ObjectRecipe recipe = App.appRecipe;
                if (recipe != null) {
                    mRecipeList.set(mRecipePos, recipe);
                    setCurrentStageId();
                    mEditRecipeAdapter.notifyItemChanged(mRecipePos);
                    if (createField != null) {
                        createField.setObjectRecipe(recipe);
                    }
                }
            }
            if (requestCode == Utils.Constant.EDIT_RECIPE_STAGE_CODE) {
                ObjectRecipe recipe = App.appRecipe;
                if (recipe != null) {
                    mRecipeList.set(mRecipePos, recipe);
                    setCurrentStageId();
                    mEditRecipeAdapter.notifyItemChanged(mRecipePos);
                    if (createField != null) {
                        createField.setObjectRecipe(recipe);
                    }
                }
            }
            if (requestCode == Utils.Constant.ADD_RECIPE) {
                ObjectRecipe recipe = App.appRecipe;
                if (recipe != null) {
                    createField.setObjectRecipe(recipe);
                    createField.setStageName(recipe.getStageName());
                    createField.setRecipeId(recipe.getId());
                    if (isNetworkOffline()) {
                        return;
                    }
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mEditRecipePresenter.cloneRecipe(createField);
                }
            }
        }
    }

    @Override
    public void onInfoCallBack(int position) {
        mRecipePos = position;
        if (isOwner) {
            moreDialog.show();
        } else {
            showCustomDialogEvent();
        }
    }

    private void showCustomDialogEvent() {
        new MaterialDialog.Builder(this)
                .content(R.string.message_upgrade_recipe)
                .cancelable(false)
                .neutralText(R.string.toolbar_cancel).neutralColor(Utils.getColor(this, R.color.bg_green_btn))
                .positiveText(R.string.view_details).positiveColor(Utils.getColor(this, R.color.bg_green_btn))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(EditRecipeActivity.this, getString(R.string.message_next_phase), Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void setPositionSelect(int positionSelect) {

    }
}
