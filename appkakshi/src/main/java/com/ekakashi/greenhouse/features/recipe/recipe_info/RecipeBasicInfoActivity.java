package com.ekakashi.greenhouse.features.recipe.recipe_info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectPrefecture;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.edit_basic_recipe.EditBasicRecipeActivity;
import com.ekakashi.greenhouse.features.revision_history.RevisionHistoryActivity;

import java.util.List;

public class RecipeBasicInfoActivity extends BaseActivity implements View.OnClickListener, RecipeBasicInfoInterface.View {

    private TextView tvNameRecipe;
    private TextView tvCategoryRecipe;
    private TextView tvPrefectureRecipe;
    private TextView tvScopeRecipe;
    private TextView tvCropRecipe;
    private TextView tvPlaceRecipe;
    private TextView tvPlaceTitle;
    private TextView tvVerRecipe;
    private TextView tvAuthorRecipe;
    private ImageView imgRecipe;
    private boolean editRecipe = false;
    private ObjectRecipe mRecipe;
    private RecipeBasicInfoPresenter mBasicInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_basic_info);
        initPresenter();
        initView();
        getData();

    }

    private void initView() {
        tvNameRecipe = findViewById(R.id.tvNameRecipe);
        tvCategoryRecipe = findViewById(R.id.tvCategoryRecipe);
        tvPrefectureRecipe = findViewById(R.id.tvRefectureRecipe);
        tvScopeRecipe = findViewById(R.id.tvScopeRecipe);
        tvCropRecipe = findViewById(R.id.tvCropRecipe);
        tvPlaceRecipe = findViewById(R.id.tvPlaceRecipe);
        tvPlaceTitle = findViewById(R.id.place);
        tvVerRecipe = findViewById(R.id.tvVerRecipe);
        tvAuthorRecipe = findViewById(R.id.tvAuthorRecipe);
        imgRecipe = findViewById(R.id.imgRecipe);

        RelativeLayout layoutAuthor = findViewById(R.id.layoutAuthor);
        RelativeLayout layoutOverview = findViewById(R.id.layoutOverview);

        layoutAuthor.setOnClickListener(this);
        layoutOverview.setOnClickListener(this);
    }

    private void getData() {
        mRecipe = App.appRecipe;
        editRecipe = getIntent().getBooleanExtra(Utils.Name.IS_EDIT_RECIPE, false);
        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mBasicInfoPresenter.getRecipe(mRecipe.getId());

    }

    private void addToolbar() {
        String fieldName = "";
        if (mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty()
                && mRecipe.getEkFields().get(0) != null && mRecipe.getEkFields().get(0).getEkField() != null
                && mRecipe.getEkFields().get(0).getEkField().getName() != null
                && !mRecipe.getEkFields().get(0).getEkField().getName().isEmpty()) {
            fieldName = mRecipe.getEkFields().get(0).getEkField().getName();
        }
        MyToolbar myToolbar = new MyToolbar(mToolbar);

        if (editRecipe) {
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.edit_recipe), fieldName);
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
        } else {
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, mRecipe.getRecipeName(), fieldName);
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        }
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                if (editRecipe) {
                    //TODO Update Recipe
                    Intent intent = new Intent();
                    intent.putExtra(Utils.Name.RECIPE, mRecipe);
                    setResult(RESULT_OK, intent);
                }
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                //TODO Press EDIT
                if (editRecipe) {
                    Intent intent = new Intent(RecipeBasicInfoActivity.this, EditBasicRecipeActivity.class);
                    intent.putExtra(Utils.Name.RECIPE, mRecipe);
                    startActivityForResult(intent, Utils.Constant.EDIT_BASIC_RECIPE_CODE);
                }
                onBackPressed();
            }
        });
    }

    private void loadData() {
        tvNameRecipe.setText(mRecipe.getRecipeName());

        //SET TEXT FOR PREFECTURE
        tvPrefectureRecipe.setText("");// SET DEFAULT
        List<ObjectPrefecture> prefectures = Utils.getPrefectureList(this);
        if (!prefectures.isEmpty() && mRecipe.getPrefecture() != null && !mRecipe.getPrefecture().isEmpty()) {
            for (ObjectPrefecture prefecture : prefectures) {
                if (prefecture.getName().equalsIgnoreCase(mRecipe.getPrefecture())
                        || prefecture.getJapanName().equalsIgnoreCase(mRecipe.getPrefecture())) {
                    if (Prefs.getInstance(this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                        tvPrefectureRecipe.setText(prefecture.getJapanName());
                    } else {
                        tvPrefectureRecipe.setText(prefecture.getName());
                    }
                    break;
                }
            }
        }

        //Set place
        if (mRecipe.getVersion() != Integer.parseInt(Utils.Name.ONE)) {
            if (mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty() && mRecipe.getEkFields().get(0) != null && mRecipe.getEkFields().get(0).getEkField() != null) {
                if (mRecipe.getEkFields().get(0).getEkField().getFieldType() == Integer.parseInt(Utils.Name.ONE)) {
                    tvPlaceRecipe.setText(getString(R.string.txt_greenhouse));
                } else {
                    tvPlaceRecipe.setText(getString(R.string.txt_field));
                }
            } else {
                tvPlaceRecipe.setText("");
            }
        } else {
            tvPlaceTitle.setVisibility(View.GONE);
            tvPlaceRecipe.setVisibility(View.GONE);
        }

        tvVerRecipe.setText(String.valueOf(mRecipe.getVersion()));

        Glide.with(this).load(mRecipe.getImage()).into(imgRecipe);
        //Set scope of disclosure
        if (!mRecipe.isScope()) {
            tvScopeRecipe.setText(getString(R.string.disclosure));
        } else {
            tvScopeRecipe.setText(getString(R.string.undisclosure));
        }

        //Set Image
        if (mRecipe.getImage() == null) {
            mRecipe.setImage("");
        }


        //Set Default Author
        if (mRecipe.getVersion() == Integer.parseInt(Utils.Name.ONE)) {
            if (mRecipe.getOrganizationName() != null && !mRecipe.getOrganizationName().isEmpty()) {
                tvAuthorRecipe.setText(mRecipe.getOrganizationName());
            } else {
                tvAuthorRecipe.setText("");
            }

        } else if (mRecipe.getVersion() > Integer.parseInt(Utils.Name.ONE)) {
            if (mRecipe.getRevisionHistoryList() != null && !mRecipe.getRevisionHistoryList().isEmpty()) {
                if (mRecipe.getRevisionHistoryList().get(0) != null && mRecipe.getRevisionHistoryList().get(0).getOrganizationName() != null && !mRecipe.getRevisionHistoryList().get(0).getOrganizationName().isEmpty()) {
                    tvAuthorRecipe.setText(mRecipe.getRevisionHistoryList().get(0).getOrganizationName());
                } else {
                    tvAuthorRecipe.setText("");
                }
            } else {
                tvAuthorRecipe.setText("");
            }
        } else {
            tvAuthorRecipe.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutAuthor:
                startActivity(new Intent(this, RevisionHistoryActivity.class));
                break;
            case R.id.layoutOverview:
                new MaterialDialog.Builder(this).title(getString(R.string.overview)).content(mRecipe.getDescription()).build().show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.Constant.EDIT_BASIC_RECIPE_CODE:
                    ObjectRecipe recipe = data.getParcelableExtra(Utils.Name.RECIPE);
                    if (recipe != null) {
                        mRecipe = recipe;
                        loadData();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void initPresenter() {
        mBasicInfoPresenter = new RecipeBasicInfoPresenter(this);
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public void getRecipeSuccess(ObjectRecipe recipe) {
        if (recipe != null) {
            mRecipe = recipe;
            App.appRecipe = mRecipe;
            loadData();
            addToolbar();
            mBasicInfoPresenter.getCategories();
        } else {
            customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
        }
    }

    @Override
    public void getRecipeFailed(String error) {
        hideLoadingDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCategoriesSuccess(List<ObjectCategory> categories) {
        tvCategoryRecipe.setText("");
        if (categories != null) {
            if (mRecipe.getCategory() != null && !mRecipe.getCategory().isEmpty()) {
                for (ObjectCategory category : categories) {
                    if (category.getName().equalsIgnoreCase(mRecipe.getCategory())
                            || category.getJapanName().equalsIgnoreCase(mRecipe.getCategory())) {
                        if (Prefs.getInstance(this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                            tvCategoryRecipe.setText(category.getJapanName());
                        } else {
                            tvCategoryRecipe.setText(category.getName());
                        }
                        mBasicInfoPresenter.getCropsByCateId(category.getId());
                        break;
                    }
                }
            }

        } else {
            customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
        }

    }

    @Override
    public void getCategoriesFailed(String error) {
        hideLoadingDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCropsSuccess(List<ObjectCrop> cropList) {
        hideLoadingDialog();
        tvCropRecipe.setText("");
        if (cropList != null) {
            if (mRecipe.getCrop() != null && !mRecipe.getCrop().isEmpty()) {
                for (ObjectCrop crop : cropList) {
                    if (crop.getName().equalsIgnoreCase(mRecipe.getCrop())
                            || crop.getNameJapan().equalsIgnoreCase(mRecipe.getCrop())) {
                        if (Prefs.getInstance(this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                            tvCropRecipe.setText(crop.getNameJapan());
                        } else {
                            tvCropRecipe.setText(crop.getName());
                        }
                        break;
                    }
                }
            }
        } else {
            customConfirmDialog(getString(R.string.server_error), getString(R.string.please_try_again_later));
        }
    }

    @Override
    public void getCropsFailed(String error) {
        hideLoadingDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBasicInfoPresenter != null) {
            mBasicInfoPresenter.onDestroy();
        }
    }
}
