package com.ekakashi.greenhouse.features.recipe.add_field_greenhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.ekakashi.greenhouse.database.model_server_request.SearchRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.SearchRecipeResponse;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.ekakashi.greenhouse.features.add_field_greenhouse.step2.EKAddFieldStepTwoActivity;
import com.ekakashi.greenhouse.features.recipe.recipe_info.RecipeBasicInfoActivity;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemClickListener;
import com.ekakashi.greenhouse.features.recipe.stage.select_current_stage.SelectCurrentStageActivity;

import java.util.LinkedHashMap;
import java.util.List;

import static android.view.View.GONE;

public class AddFieldGreenHouseActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AddFieldGreenHouseInterface.View, SwipeRefreshLayout.OnRefreshListener, EditText.OnEditorActionListener {

    private RecyclerView mRvRecipe;
    private RecyclerView mRvCategory;
    private AlphaTextView btnGoNext;
    private EditText edtSearch;
    private ImageView imgClose;
    private RecipeSelectionAdapter mRecipeAdapter;
    private SnappyRecipeAdapter mCategoryAdapter;
    private View lineBelowSnappy, lineAboveSnappy;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LinkedHashMap<Integer, ObjectRecipe> mRecipeList = new LinkedHashMap<>();
    private static final int REQUEST_CODE = 20;
    private ObjectCreateField createField;
    private String textSearch = ""; //text to save input string of user
    private String mSelectedCategory = "";//to save category name after user's choice
    private AddFieldGreenHousePresenter mAddFieldGreenHousePresenter;
    private EndlessRecyclerOnScrollListener mScrollListener = null;
    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout relativeLayout;
    private boolean addMoreRecipe;

    //Variable for Load More Recipe
    private int recordPerPageLoadMore = 20;
    private int totalPageOfLoadMore = 1000;
    private int currentPageOfLoadMore = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_add_field_green_house);
        relativeLayout = findViewById(R.id.layoutContainFourStep);
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.CREATE_FILED);
            addMoreRecipe = getIntent().getBooleanExtra(Utils.Constant.ADD_MORE_RECIPE, false);
        }
        initView();
        addToolbar();
        prepareData();
        addEvents();
    }

    private void initView() {
        mRvRecipe = findViewById(R.id.rvRecipe);
        mRvCategory = findViewById(R.id.rvSnappyRecipe);
        btnGoNext = findViewById(R.id.btnGoNext);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        imgClose = findViewById(R.id.imgClose);
        edtSearch = findViewById(R.id.edtSearch);
        lineBelowSnappy = findViewById(R.id.lin_below_snappy);
        lineAboveSnappy = findViewById(R.id.lin_above_snappy);

        btnGoNext.setOnClickListener(this);
        edtSearch.setOnClickListener(this);
        imgClose.setOnClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light);
        mAddFieldGreenHousePresenter = new AddFieldGreenHousePresenter(this);
        btnGoNext.onSetAlpha(128);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        if (addMoreRecipe) {
            relativeLayout.setVisibility(GONE);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.add_recipe), "");
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.recipe_selection), "");
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.cancel));
        }
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                App.onDestroy();
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (!addMoreRecipe) {
                    App.onDestroy();
                    EKMainFieldActivity.startActivity(AddFieldGreenHouseActivity.this);
                }
            }
        });
    }

    private void prepareData() {
        //Snappy Recycler View
        mCategoryAdapter = new SnappyRecipeAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRvCategory.setLayoutManager(gridLayoutManager);
        mRvCategory.addItemDecoration(new GridSpacingItemDecoration(2, 15, true));
        mRvCategory.setAdapter(mCategoryAdapter);
//        SnapHelper snapHelperStart = new StartSnapHelper();
//        snapHelperStart.attachToRecyclerView(mRvCategory);

        //Normal Recycler View
        mRecipeAdapter = new RecipeSelectionAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        mRvRecipe.setLayoutManager(linearLayoutManager);
        mRvRecipe.setAdapter(mRecipeAdapter);
        mRvRecipe.setItemAnimator(new DefaultItemAnimator());

        getData();
    }

    private void getData() {
        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        if (mCategoryAdapter == null || mCategoryAdapter.getSnappyRecipeList() == null
                || mCategoryAdapter.getSnappyRecipeList().isEmpty()) {
            lineAboveSnappy.setVisibility(GONE);
            lineBelowSnappy.setVisibility(GONE);
        }
        mAddFieldGreenHousePresenter.getCategories();
    }

    private void UIFinishApi() {
        hideLoadingDialog();
        if (mCategoryAdapter != null && mCategoryAdapter.getSnappyRecipeList() != null
                && !mCategoryAdapter.getSnappyRecipeList().isEmpty()) {
            lineAboveSnappy.setVisibility(View.VISIBLE);
            lineBelowSnappy.setVisibility(View.VISIBLE);
        }
    }

    private void setCurrentStageId(List<ObjectRecipe> recipes) {
        for (ObjectRecipe recipe : recipes) {
            if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
                if (recipe.getEkFields().get(0) != null) {
                    if (recipe.getEkFields().get(0).getCurrentStage() != null) {
                        recipe.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                    }
                }
            } else {
                if (recipe.getStages() != null && !recipe.getStages().isEmpty()) {
                    recipe.setCurrentStageId(recipe.getStages().get(0).getId());
                } else {
                    recipe.setCurrentStageId(0);
                }
            }

            for (int index = 0; index < recipe.getStages().size(); index++) {
                if (recipe.getStages().get(index).getId() == recipe.getCurrentStageId()) {
                    recipe.getStages().get(index).setSelect(true);
                    break;
                }
            }

        }
    }


    private void addEvents() {
        mRecipeAdapter.setOnCheckRecipeInterface(new RecipeSelectionAdapter.OnCheckRecipeInterface() {
            @Override
            public void onClickImageCheckCallback(int numberRecipeSelected, int position) {
                if (numberRecipeSelected == 0) {
                    btnGoNext.setEnabled(false);
                    btnGoNext.onSetAlpha(128);
                } else {
                    btnGoNext.setEnabled(true);
                    btnGoNext.onDefaultTextView();
                }
            }

        });

        mRecipeAdapter.setOnPressInfoInterface(new RecipeSelectionAdapter.OnPressInfoInterface() {
            @Override
            public void onClickInfoCallback(ObjectRecipe recipe, int position) {
                App.appRecipe = recipe;
                startActivity(new Intent(AddFieldGreenHouseActivity.this, RecipeBasicInfoActivity.class));
            }
        });

        mRecipeAdapter.setOnItemClick(new OnItemClickListener() {
            @Override
            public void OnRecipeClick(ObjectRecipe recipe, int position) {
                App.appRecipe = recipe;
                startActivityForResult(new Intent(AddFieldGreenHouseActivity.this, SelectCurrentStageActivity.class), REQUEST_CODE);
            }
        });

        edtSearch.addTextChangedListener(this);

        mCategoryAdapter.setSnappyClickListener(new OnSnappyClickListener() {
            @Override
            public void OnSnappyItemClick(String category) {
                mSelectedCategory = category;
                textSearch = "";
                btnGoNext.setEnabled(false);
                btnGoNext.onSetAlpha(128);
                edtSearch.setText(textSearch);
                //TODO Call Api SearchRecipe with textSearch = ""
                if (isNetworkOffline()) {
                    return;
                }
                showLoadingDialog(getString(R.string.message_please_wait));
                currentPageOfLoadMore = 1;
                mAddFieldGreenHousePresenter.searchRecipe(createSearchRequest(mSelectedCategory, textSearch), currentPageOfLoadMore, recordPerPageLoadMore);
            }
        });

        mRvRecipe.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideKeyBroad();
            }
        });


        mScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // TODO LOAD MORE
                if (currentPageOfLoadMore <= totalPageOfLoadMore) {
                    if (isNetworkOffline()) {
                        return;
                    }
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mAddFieldGreenHousePresenter.searchRecipe(createSearchRequest(mSelectedCategory, textSearch), currentPageOfLoadMore, recordPerPageLoadMore);
                }
            }
        };

        mRvRecipe.addOnScrollListener(mScrollListener);
        edtSearch.setOnEditorActionListener(this);
    }

    private SearchRecipeRequest createSearchRequest(String category, String searchText) {
        SearchRecipeRequest searchRecipeRequest = new SearchRecipeRequest();
        searchRecipeRequest.setCategory(category);
        searchRecipeRequest.setSearchText(searchText);
        return searchRecipeRequest;
    }

    //------------TextWatcher---------
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textSearch = s.toString().trim();
        if (s.toString().trim().isEmpty()) {
            imgClose.setVisibility(GONE);
//            if (isNetworkOffline()) {
//                return;
//            }
//            currentPageOfLoadMore = 1;
//            showLoadingDialog(getString(R.string.message_please_wait));
//            mAddFieldGreenHousePresenter.searchRecipe(createSearchRequest(mSelectedCategory, textSearch), currentPageOfLoadMore, recordPerPageLoadMore);
        } else {
            imgClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    //------------End TextWatcher---------

    private void hideKeyBroad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoNext:
                onNextPressed();
                break;
            case R.id.imgClose:
                onClosePressed();
                break;
            default:
                break;
        }
    }

    private LinkedHashMap<Integer, ObjectRecipe> getRecipesSelected() {
        LinkedHashMap<Integer, ObjectRecipe> recipes = new LinkedHashMap<>();
        if (mRecipeList != null && !mRecipeList.isEmpty()) {
            for (Integer recipeId : mRecipeList.keySet()) {
                if (mRecipeList.get(recipeId).isSelected()) {
                    recipes.put(recipeId, mRecipeList.get(recipeId));
                }
            }
        }
        return recipes;
    }

    private LinkedHashMap<Integer, ObjectRecipe> convertArrayToLinkedHashMap(List<ObjectRecipe> recipeList) {
        LinkedHashMap<Integer, ObjectRecipe> recipeLinkedHashMap = new LinkedHashMap<>();
        for (ObjectRecipe recipe : recipeList) {
            recipeLinkedHashMap.put(recipe.getId(), recipe);
        }
        return recipeLinkedHashMap;
    }

    private void onNextPressed() {
        LinkedHashMap<Integer, ObjectRecipe> recipesSelected = getRecipesSelected();
        if (recipesSelected != null && !recipesSelected.isEmpty()) {
            ObjectRecipe recipe = recipesSelected.entrySet().iterator().next().getValue();
            if (recipe != null) {
                if (recipe.getStages() != null && !recipe.getStages().isEmpty()) {
                    recipe.setStageName(mRecipeAdapter.getCurrentStage(recipe));
                    if (addMoreRecipe) {
                        App.appRecipe = recipe;
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // Clear reference for AppRecipe
                        App.onDestroy();
                        ///Setting Object CreateField
                        createField.setStageName(recipe.getStageName());
                        createField.setRecipeName(recipe.getRecipeName());
                        createField.setRecipeDescription(recipe.getDescription());
                        createField.setRecipeDescription(recipe.getDescription());
                        createField.setRecipeImage(recipe.getImage());
                        createField.setRecipeId(recipe.getId());
                        createField.setCurrentStageId(0);
                        int index, size = recipe.getStages().size();
                        for (index = 0; index < size; index++) {
                            if (recipe.getStages().get(index).getName().trim().equalsIgnoreCase(createField.getStageName().trim())) {
                                createField.setCurrentStageId(recipe.getStages().get(index).getId());
                                createField.setSortNo(recipe.getStages().get(index).getOrderPos());
                                break;
                            }
                        }
                        Intent intent = new Intent(this, EKAddFieldStepTwoActivity.class);
                        intent.putExtra(Utils.Name.CREATE_FILED, createField);
                        startActivity(intent);

                        //TODO Next phase will uncomment
//                        if (!isNetworkOffline()) {
//                            showLoadingDialog(getString(R.string.message_please_wait));
//                            mAddFieldGreenHousePresenter.cloneRecipe(recipe.getId());
//                        }
                    }
                } else {
                    Toast.makeText(this, R.string.message_recipe_has_no_stage, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, R.string.message_please_select_the_recipe, Toast.LENGTH_LONG).show();
        }
    }

    private void onClosePressed() {
        textSearch = "";
        edtSearch.setText(textSearch);
    }

    @Override
    public void failed(String string) {
        mScrollListener.setLoading(false);
        UIFinishApi();
        Toast.makeText(AddFieldGreenHouseActivity.this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getCategoriesSuccess(List<ObjectCategory> categories) {
        if (categories != null && !categories.isEmpty()) {
            categories.get(0).setSelected(true);//Set the first item in Category List is selected
            mSelectedCategory = categories.get(0).getName().trim();
            mCategoryAdapter.setData(categories);

            //Update UI for Category
            if (mCategoryAdapter != null && mCategoryAdapter.getSnappyRecipeList() != null
                    && !mCategoryAdapter.getSnappyRecipeList().isEmpty()) {
                lineAboveSnappy.setVisibility(View.VISIBLE);
                lineBelowSnappy.setVisibility(View.VISIBLE);
            }

            //After load Categories getRecipeSuccess, call api to load recipes
            if (isNetworkOffline()) {
                return;
            }
            mAddFieldGreenHousePresenter.searchRecipe(createSearchRequest(mSelectedCategory, textSearch), currentPageOfLoadMore, recordPerPageLoadMore);
        } else {
            hideLoadingDialog();
            Toast.makeText(this, R.string.message_cant_load_category_list, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void searchResult(SearchRecipeResponse response) {
        hideLoadingDialog();
        mScrollListener.setLoading(false);
        if (response != null) {
            if (response.getPaging() != null) {
                totalPageOfLoadMore = response.getPaging().getNumOfPage();
            }

            if (response.getRecipes() != null && !response.getRecipes().isEmpty()) {
                //PAGE = 1
                if (currentPageOfLoadMore == 1) {
                    setCurrentStageId(response.getRecipes());
                    mRecipeList = convertArrayToLinkedHashMap(response.getRecipes());
                    mRecipeAdapter.setRecipeList(mRecipeList);
                }
                //PAGE >= 2. LOAD MORE
                else {
                    setCurrentStageId(response.getRecipes());
                    LinkedHashMap<Integer, ObjectRecipe> map = convertArrayToLinkedHashMap(response.getRecipes());
                    for (Integer recipeId : map.keySet()) {
                        if (mRecipeList.containsKey(recipeId)) {
                            mRecipeList.remove(recipeId);
                        }
                        mRecipeList.put(recipeId, map.get(recipeId));
                    }
                    mRecipeAdapter.notifyDataSetChanged();
                }
            } else {
                mRecipeList = new LinkedHashMap<>();
                mRecipeAdapter.setRecipeList(mRecipeList);
            }
            currentPageOfLoadMore++;
        } else {
            Toast.makeText(this, R.string.message_cant_load_recipes, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cloneRecipeSuccess(ObjectRecipe recipe) {
        //TODO CLONE RECIPE SUCCESS
        if (recipe != null) {
            if (createField != null) {
                //TODO CALL API EDIT RECIPE
                if (createField.getObjectRecipe().getStages() != null && !createField.getObjectRecipe().getStages().isEmpty()) {
                    for (ObjectGrowth stage : createField.getObjectRecipe().getStages()) {
                        stage.setId(null);//for send to BE
                    }
                    recipe.setStages(createField.getObjectRecipe().getStages());
                }
                EditRecipeRequest recipeRequest = new EditRecipeRequest();
                recipeRequest.parseToRequestObject(recipe);
                mAddFieldGreenHousePresenter.editRecipeById(recipe.getId(), recipeRequest);
            }
        } else {
            hideLoadingDialog();
            customConfirmDialog(getString(R.string.server_error), getString(R.string.message_clone_recipe_failed));
        }
    }

    @Override
    public void editRecipeSuccess(ObjectRecipe recipe) {
        //TODO SET OBJECT RECIPE, RECIPE ID, STAGE NAME FOR OBJECT CREATE FIELD
        hideLoadingDialog();
        if (recipe != null) {
            createField.setObjectRecipe(recipe);
            createField.setRecipeId(recipe.getId());
            createField.setCurrentStageId(0);
            int index, size = recipe.getStages().size();
            for (index = 0; index < size; index++) {
                if (recipe.getStages().get(index).getName().trim().equalsIgnoreCase(createField.getStageName().trim())) {
                    createField.setCurrentStageId(recipe.getStages().get(index).getId());
                    createField.setSortNo(recipe.getStages().get(index).getOrderPos());
                    break;
                }
            }
            Intent intent = new Intent(this, EKAddFieldStepTwoActivity.class);
            intent.putExtra(Utils.Name.CREATE_FILED, createField);
            startActivity(intent);
        } else {
            customConfirmDialog(getString(R.string.server_error), getString(R.string.message_cannot_edit_recipe));
        }
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        if (mAddFieldGreenHousePresenter != null) {
            mAddFieldGreenHousePresenter.onDestroy();
        }
        if (mRecipeAdapter != null) {
            mRecipeAdapter.onDestroy();
        }
        if (mCategoryAdapter != null) {
            mCategoryAdapter.onDestroy();
        }
        if (mScrollListener != null) {
            mScrollListener = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
    }

    @Override
    public void onRefresh() {
        //TODO Pull to refresh
        if (isNetworkOffline()) {
            return;
        }
        btnGoNext.setEnabled(false);
        btnGoNext.onSetAlpha(128);
        currentPageOfLoadMore = 1;
        showLoadingDialog(getString(R.string.message_please_wait));
        mAddFieldGreenHousePresenter.searchRecipe(createSearchRequest(mSelectedCategory, textSearch), currentPageOfLoadMore, recordPerPageLoadMore);

        // after refresh is done, remember to call the following code
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);  // This hides the spinner
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)||(actionId == EditorInfo.IME_ACTION_DONE)) {
            //TODO Handle event for DONE or ENTER on keyboard
            btnGoNext.setEnabled(false);
            btnGoNext.onSetAlpha(128);
            currentPageOfLoadMore = 1;
            if (!isNetworkOffline()) {
                showLoadingDialog(getString(R.string.message_please_wait));
                mAddFieldGreenHousePresenter.searchRecipe(createSearchRequest(mSelectedCategory, textSearch), currentPageOfLoadMore, recordPerPageLoadMore);
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    //TODO Update stage for Recipe
                    ObjectRecipe newRecipe = App.appRecipe;
                    if (newRecipe != null) {
                        mRecipeList.put(newRecipe.getId(), newRecipe);
                        mRecipeAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}