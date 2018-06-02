package com.ekakashi.greenhouse.features.recipe.recipe_selected;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemClickListener;
import com.ekakashi.greenhouse.features.recipe.stage.select_current_stage.SelectCurrentStageActivity;

import java.util.List;

public class RecipeSelectedActivity extends BaseActivity implements View.OnClickListener {

    private static final int SELECT_STAGE_CODE = 52;
    private RecyclerView mRvRecipe;
    private List<ObjectRecipe> mRecipeList;
    private RecipeSelectedAdapter mRecipeSelectedAdapter;
    private ObjectCreateField createField;

    public static final String RECIPE_SELECTED_LIST = "RECIPE_SELECTED_LIST";
    public static final String RECIPE = "RECIPE";
    public static final String CREATE_FIELD = "CREATE_FIELD";
    private int recipePosInAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selected);
        initView();
        addToolbar();
        prepareData();
        addEvents();
    }

    private void initView() {
        mRvRecipe = findViewById(R.id.rvRecipe);
        Button btnGoNext = findViewById(R.id.btnGoNext);
        btnGoNext.setOnClickListener(this);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.recipe_selected), "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
            }
        });
    }

    private void prepareData() {
        mRecipeList = getIntent().getParcelableArrayListExtra(RECIPE_SELECTED_LIST);
        createField = getIntent().getParcelableExtra(CREATE_FIELD);

        mRecipeSelectedAdapter = new RecipeSelectedAdapter(this, mRecipeList);
        mRvRecipe.setLayoutManager(new LinearLayoutManager(this));
        mRvRecipe.setAdapter(mRecipeSelectedAdapter);
    }

    private void addEvents() {
        mRecipeSelectedAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnRecipeClick(ObjectRecipe recipe, int position) {
                mRecipeList.remove(position);
                mRecipeSelectedAdapter.notifyDataSetChanged();
                if (mRecipeList == null || mRecipeList.isEmpty()) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        mRecipeSelectedAdapter.setOnRecipeStageInterface(new RecipeSelectedAdapter.OnRecipeStageInterface() {
            @Override
            public void onClickRecipeStageCallback(ObjectRecipe recipe, int position) {
                recipePosInAdapter = position;
                Intent intent = new Intent(RecipeSelectedActivity.this, SelectCurrentStageActivity.class);
                intent.putExtra(Utils.Name.RECIPE, recipe);
                startActivityForResult(intent, SELECT_STAGE_CODE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoNext:
                onGoNextPressed();
                break;

            default:
                break;
        }

    }

    private void onGoNextPressed() {
        if (mRecipeList != null && !mRecipeList.isEmpty()) {
            createField.setObjectRecipe(mRecipeList.get(0));
            if (mRecipeList.get(0).getEkFields() != null && !mRecipeList.get(0).getEkFields().isEmpty()) {
                if (mRecipeList.get(0).getEkFields().get(0).getCurrentStage().getId() != null) {
                    createField.setCurrentStageId(mRecipeList.get(0).getEkFields().get(0).getCurrentStage().getId());
                    createField.setRecipeId(mRecipeList.get(0).getId());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_STAGE_CODE) {
                ObjectRecipe oldRecipe = mRecipeList.get(recipePosInAdapter);
                ObjectRecipe newRecipe = data.getParcelableExtra(Utils.Name.RECIPE);
                if (oldRecipe == null || newRecipe == null
                        || oldRecipe.getId() != newRecipe.getId()) {
                    throw new IllegalStateException("SELECT CURRENT STAGE ERROR");
                } else {
                    int positionOldRecipe = mRecipeList.indexOf(oldRecipe);
                    if (positionOldRecipe == -1) {
                        throw new IllegalStateException("SELECT CURRENT STAGE ERROR 1");
                    } else {
                        mRecipeList.set(positionOldRecipe, newRecipe);
                    }
                    mRecipeList.set(recipePosInAdapter, newRecipe);
                    mRecipeSelectedAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
