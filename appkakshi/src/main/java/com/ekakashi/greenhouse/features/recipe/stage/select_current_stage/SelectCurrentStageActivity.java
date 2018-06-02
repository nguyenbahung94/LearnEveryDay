package com.ekakashi.greenhouse.features.recipe.stage.select_current_stage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.EditStageActivity;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.CustomOnStartDragListener;
import com.ekakashi.greenhouse.features.recipe.stage.edit_stage.helper.CustomSimpleItemTouchHelperCallback;
import com.ekakashi.greenhouse.features.recipe.stage.info_stage.InfoStageActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class SelectCurrentStageActivity extends BaseActivity implements OnItemCallBack, View.OnClickListener, CustomOnStartDragListener {

    private SelectionStageAdapter mStageAdapter;
    private List<ObjectGrowth> mStageList;
    private ObjectRecipe mRecipe;
    private MyToolbar myToolbar;
    private boolean edit = false;//initialize Edit variable = false
    private ItemTouchHelper touchHelper;
    private RelativeLayout btnAddStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_current_stage);
        initView();
        addToolbar();
    }

    private void initView() {
        RecyclerView mRvStage = findViewById(R.id.rvStage);
        btnAddStage = findViewById(R.id.btnAddStage);
        btnAddStage.setOnClickListener(this);

        mRecipe = App.appRecipe;
        if (mRecipe != null) {
            mStageList = mRecipe.getStages();
        } else {
            mStageList = new ArrayList<>();
        }

//        setCurrentStageId();
        mStageAdapter = new SelectionStageAdapter(this, mStageList);
        CustomSimpleItemTouchHelperCallback callback = new CustomSimpleItemTouchHelperCallback(mStageAdapter);
        touchHelper = new ItemTouchHelper(callback);
        mStageAdapter.setOnItemCallBack(this);
        mStageAdapter.setDragStartListener(this);
        mRvStage.setLayoutManager(new LinearLayoutManager(this));
        mRvStage.setAdapter(mStageAdapter);
        touchHelper.attachToRecyclerView(mRvStage);
        //TODO Next phase will close
        btnAddStage.setVisibility(GONE);
    }

    private void addToolbar() {
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.select_current_stage), mRecipe.getRecipeName());
        //TODO Next phase will open
        //myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackButtonPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (!edit) {
                    edit = true;
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_done));
                    mStageAdapter.setEditMode(true);
                } else {
                    edit = false;
                    myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
                    mStageAdapter.setEditMode(false);
                }
            }
        });
    }

//    private void setCurrentStageId() {
//        if (mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty()) {
//            mRecipe.setCurrentStageId(mRecipe.getEkFields().get(0).getCurrentStage().getId());
//        } else {
//            if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
//                mRecipe.setCurrentStageId(mRecipe.getStages().get(0).getId());
//            } else {
//                mRecipe.setCurrentStageId(0);
//            }
//        }
//
//        for (int index = 0; index < mRecipe.getStages().size(); index++) {
//            if (mRecipe.getStages().get(index).getId() == mRecipe.getCurrentStageId()) {
//                mRecipe.getStages().get(index).setSelect(true);
//                break;
//            }
//        }
//    }

    private void onBackButtonPressed() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
            if (isSelectStage()) {
                App.appRecipe.setStages(mStageAdapter.getStages());
                App.appRecipe.setStageName(getStageName());
//                mRecipe.setStages(mStageAdapter.getStages());
//                mRecipe.setStageName(getStageName());
                setResult(RESULT_OK);
                super.onBackPressed();
            } else {
                Toast.makeText(this, R.string.recipe_current_stage_is_required, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.recipe_stage_is_required, Toast.LENGTH_LONG).show();
        }
    }

    private String getStageName() {
        for (ObjectGrowth stage : mRecipe.getStages()) {
            if (stage.isSelect()) {
                return stage.getName();
            }
        }
        return null;
    }

    private boolean isSelectStage() {
        for (ObjectGrowth stage : mRecipe.getStages()) {
            if (stage.isSelect()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, InfoStageActivity.class);
        App.appRecipe = mRecipe;
        intent.putExtra(Utils.Name.STAGE, mStageList.get(position).getId());
        intent.putExtra(Utils.Name.FROM_SELECT_STAGE, true);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (mStageAdapter != null) {
            mStageAdapter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddStage:
                Intent addStage = new Intent(this, EditStageActivity.class);
                addStage.putExtra(Utils.Name.RECIPE, mRecipe);//to check stage list
                addStage.putExtra(Utils.Name.ADD_STAGE, EnumRule.FROM_SELECT_CURRENT_STAGE);
                startActivityForResult(addStage, Utils.Constant.ADD_STAGE_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.Constant.ADD_STAGE_CODE:
                    ObjectGrowth stage = data.getParcelableExtra(Utils.Name.STAGE);
                    mRecipe.getStages().add(stage);
                    mStageAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}
