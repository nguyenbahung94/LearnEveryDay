package com.ekakashi.greenhouse.features.recipe.edit_basic_recipe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.ImageHelper;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.custom_gallery.MainActivityGallery;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCategory;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectFilterRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectPrefecture;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.OnItemCallBack;
import com.ekakashi.greenhouse.features.revision_history.RevisionHistoryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.view.View.GONE;


public class EditBasicRecipeActivity extends BaseActivity implements View.OnClickListener, EditBasicRecipeInterface.View, ImageHelper.ImageActionListener, TextWatcher {
    private EditText edtRecipeName;
    private EditText edtOverview;
    private TextView tvCrop;
    private ImageView imgName;
    private ImageView imgRecipe;
    private ImageView imgChangeable;
    private ImageView imgUnChangeable;
    private ImageView imgDisclosure;
    private ImageView imgUnDisclosure;
    private ImageView imgOverview;
    private TextView tvVersion;
    private TextView tvScope;
    private TextView tvChange;
    private TextView tvCategory;
    //    private TextView tvOverview;
    private TextView tvAuthor;
    private TextView tvPrefecture;
    private ScrollView scrollView;
    private View lineAuthor;

    private ObjectRecipe mRecipe;
    private CategoryAdapter mCategoryAdapter;
    private PrefectureAdapter mPrefectureAdapter;
    private CropAdapter mCropAdapter;
    private List<ObjectCategory> mCategoryList = new ArrayList<>();
    private MaterialDialog categoryDialog, changeDialog, scopeDialog, prefectureDialog, cropDialog;
    private EditBasicRecipePresenter mEditBasicRecipePresenter;
    private ImageHelper imageHelper;
    private String imageFile;
    private List<ObjectPrefecture> mPrefectureList = new ArrayList<>();
    private List<ObjectCrop> mCropList = new ArrayList<>();
    private int scrollX, scrollY;
    private int mCategoryId;
    private int mCropPos, mPrefecturePos, mCategoryPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_basic_recipe);
        initView();
        prepareData();
        addToolbar();
        addEvents();
    }

    private void initView() {
        edtRecipeName = findViewById(R.id.edtRecipeName);
        edtOverview = findViewById(R.id.edtOverview);
        imgName = findViewById(R.id.imgName);
        imgOverview = findViewById(R.id.imgOverview);
        tvCrop = findViewById(R.id.tvCrop);
        tvPrefecture = findViewById(R.id.tvPrefecture);
        tvVersion = findViewById(R.id.tvVersion);
        tvScope = findViewById(R.id.tvScope);
        tvChange = findViewById(R.id.tvChange);
        tvCategory = findViewById(R.id.tvCategory);
        lineAuthor = findViewById(R.id.lineAuthor);
//        tvOverview = findViewById(R.id.tvOverview);
        tvAuthor = findViewById(R.id.tvAuthor);
        scrollView = findViewById(R.id.scrollView);
        ImageView imgHistory = findViewById(R.id.imgHistory);
        RelativeLayout layoutCategory = findViewById(R.id.layoutCategory);
        RelativeLayout layoutPrefecture = findViewById(R.id.layoutPrefecture);
        RelativeLayout layoutChange = findViewById(R.id.layoutChange);
        RelativeLayout layoutScope = findViewById(R.id.layoutScope);
        RelativeLayout layoutCrop = findViewById(R.id.layoutCrop);
        ImageView imgCamera = findViewById(R.id.imgCamera);
        ImageView imgGallery = findViewById(R.id.imgGallery);
        imgRecipe = findViewById(R.id.imgRecipe);
        TextView tvRevisionHistory = findViewById(R.id.tvRevisionHistory);

        imgCamera.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        layoutScope.setOnClickListener(this);
        layoutChange.setOnClickListener(this);
        layoutPrefecture.setOnClickListener(this);
        layoutCategory.setOnClickListener(this);
        layoutCrop.setOnClickListener(this);
        imgHistory.setOnClickListener(this);
        imgName.setOnClickListener(this);
        imgOverview.setOnClickListener(this);
        tvRevisionHistory.setOnClickListener(this);

        //Changeable Avaibility Dialog
        changeDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_change, true).build();
        RelativeLayout layoutChangeable = (RelativeLayout) changeDialog.findViewById(R.id.layoutChangeable);
        RelativeLayout layoutUnChangeable = (RelativeLayout) changeDialog.findViewById(R.id.layoutUnChangeable);
        imgChangeable = (ImageView) changeDialog.findViewById(R.id.imgChangeable);
        imgUnChangeable = (ImageView) changeDialog.findViewById(R.id.imgUnChangeable);
        layoutChangeable.setOnClickListener(this);
        layoutUnChangeable.setOnClickListener(this);

        //Changeable Avaibility Dialog
        scopeDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_scope, true).build();
        RelativeLayout layoutDisclosure = (RelativeLayout) scopeDialog.findViewById(R.id.layoutDisclosure);
        RelativeLayout layoutUnDisclosure = (RelativeLayout) scopeDialog.findViewById(R.id.layoutUnDisclosure);
        imgDisclosure = (ImageView) scopeDialog.findViewById(R.id.imgDisclosure);
        imgUnDisclosure = (ImageView) scopeDialog.findViewById(R.id.imgUnDisclosure);
        layoutDisclosure.setOnClickListener(this);
        layoutUnDisclosure.setOnClickListener(this);

        //Category Dialog
        categoryDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_category, true).build();
        RecyclerView rvCategory = (RecyclerView) categoryDialog.findViewById(R.id.rvCustom);

        //Prefecture Dialog
        prefectureDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_prefecture, true).build();
        RecyclerView rvPrefecture = (RecyclerView) prefectureDialog.findViewById(R.id.rvPrefecture);

        //Crop Dialog
        cropDialog = new MaterialDialog.Builder(this).customView(R.layout.dialog_crop, true).build();
        RecyclerView rvCrop = (RecyclerView) cropDialog.findViewById(R.id.rvCrop);

        mEditBasicRecipePresenter = new EditBasicRecipePresenter(this);

        //Init ImageHelper
        imageHelper = new ImageHelper(this);
        imageHelper.setImageActionListener(this);

        //Category Adapter
        mCategoryAdapter = new CategoryAdapter();
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvCategory.setAdapter(mCategoryAdapter);

        //Prefecture Adapter
        mPrefectureAdapter = new PrefectureAdapter();
        rvPrefecture.setLayoutManager(new LinearLayoutManager(this));
        rvPrefecture.setAdapter(mPrefectureAdapter);

        //Crop Adapter
        mCropAdapter = new CropAdapter();
        rvCrop.setLayoutManager(new LinearLayoutManager(this));
        rvCrop.setAdapter(mCropAdapter);
    }

    private void prepareData() {
        //Get Intent recipe
        mRecipe = App.appRecipe;

        //Create local Prefecture
        tvPrefecture.setText("");
        mPrefectureList = Utils.getPrefectureList(this);
        if (!mPrefectureList.isEmpty()) {
            int index, size = mPrefectureList.size();
            for (index = 0; index < size; index++) {
                if (mRecipe.getPrefecture().equalsIgnoreCase(mPrefectureList.get(index).getName()) ||
                        mRecipe.getPrefecture().equalsIgnoreCase(mPrefectureList.get(index).getJapanName())) {
                    mPrefectureList.get(index).setSelected(true);
                    if (Prefs.getInstance(this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                        tvPrefecture.setText(mPrefectureList.get(index).getJapanName());
                    } else {
                        tvPrefecture.setText(mPrefectureList.get(index).getName());
                    }
                    mPrefecturePos = index;
                    break;
                }
            }
        }
        mPrefectureAdapter.setData(mPrefectureList);

        //Get Category from Server
        callApiToGetCategory();

        //Setup UI
        if (mRecipe.getRecipeName() == null || mRecipe.getRecipeName().isEmpty()) {
            mRecipe.setRecipeName("");
            imgName.setVisibility(GONE);
        } else {
            imgName.setVisibility(View.VISIBLE);
        }

        if (mRecipe.getDescription() == null) {
            mRecipe.setDescription("");
            imgOverview.setVisibility(GONE);
        } else {
            imgOverview.setVisibility(View.VISIBLE);
        }

        setUpUI();
    }

    private void setUpUI() {
        edtRecipeName.setText(mRecipe.getRecipeName());
        edtRecipeName.setSelection(edtRecipeName.getText().length());
        tvVersion.setText(String.valueOf(mRecipe.getVersion()));

        //Set scope
        if (!mRecipe.isScope()) {
            tvScope.setText(getString(R.string.disclosure));
            imgDisclosure.setVisibility(View.VISIBLE);
            imgUnDisclosure.setVisibility(View.INVISIBLE);
        } else {
            tvScope.setText(getString(R.string.undisclosure));
            imgDisclosure.setVisibility(View.INVISIBLE);
            imgUnDisclosure.setVisibility(View.VISIBLE);
        }

        if (mRecipe.getDescription() == null) {
            mRecipe.setDescription("");
        }
        edtOverview.setText(mRecipe.getDescription());

        //Set Default Author
        if (mRecipe.getVersion() == 1) {
            if (mRecipe.getOrganizationName() != null && !mRecipe.getOrganizationName().isEmpty()) {
                tvAuthor.setText(mRecipe.getOrganizationName());
            } else {
                tvAuthor.setVisibility(GONE);
                lineAuthor.setVisibility(GONE);
            }

        } else if (mRecipe.getVersion() > 1) {
            if (mRecipe.getRevisionHistoryList() != null && !mRecipe.getRevisionHistoryList().isEmpty()) {
                if (mRecipe.getRevisionHistoryList().get(0) != null && mRecipe.getRevisionHistoryList().get(0).getOrganizationName() != null && !mRecipe.getRevisionHistoryList().get(0).getOrganizationName().isEmpty()) {
                    tvAuthor.setText(mRecipe.getRevisionHistoryList().get(0).getOrganizationName());
                } else {
                    tvAuthor.setVisibility(GONE);
                    lineAuthor.setVisibility(GONE);
                }
            } else {
                tvAuthor.setVisibility(GONE);
                lineAuthor.setVisibility(GONE);
            }
        } else {
            tvAuthor.setVisibility(GONE);
            lineAuthor.setVisibility(GONE);
        }

        //Set Changeable
        if (mRecipe.isAvailability()) {
            tvChange.setText(getString(R.string.changeable));
            imgChangeable.setVisibility(View.VISIBLE);
            imgUnChangeable.setVisibility(View.INVISIBLE);
        } else {
            tvChange.setText(getString(R.string.unchangeable));
            imgChangeable.setVisibility(View.INVISIBLE);
            imgUnChangeable.setVisibility(View.VISIBLE);
        }

        //Set Image
        if (mRecipe.getImage() == null || mRecipe.getImage().isEmpty()) {
            mRecipe.setImage("");
        }
        Glide.with(this).load(mRecipe.getImage()).into(imgRecipe);
    }

    private void addToolbar() {
        String recipeName = "";
        if (mRecipe.getRecipeName() != null && !mRecipe.getRecipeName().isEmpty()) {
            recipeName = mRecipe.getRecipeName();
        }
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.edit_recipe), recipeName);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                saveClicked();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addEvents() {
        edtRecipeName.addTextChangedListener(this);
        edtOverview.addTextChangedListener(this);
        tvPrefecture.addTextChangedListener(this);

        mCategoryAdapter.setOnItemClickCallBack(new OnItemCallBack() {
            @Override
            public void OnItemClick(int position) {
                if (Prefs.getInstance(EditBasicRecipeActivity.this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                    tvCategory.setText(mCategoryList.get(position).getJapanName());
                } else {
                    tvCategory.setText(mCategoryList.get(position).getName());
                }
                mCategoryId = mCategoryList.get(position).getId();
                mCategoryPos = position;
                categoryDialog.dismiss();
                if (!isNetworkOffline()) {
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mEditBasicRecipePresenter.getCropsByCateId(mCategoryId);
                }
            }
        });

        mPrefectureAdapter.setOnItemClickCallBack(new OnItemCallBack() {
            @Override
            public void OnItemClick(int position) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(scrollX, scrollY);
                    }
                });

                mPrefecturePos = position;
                if (Prefs.getInstance(EditBasicRecipeActivity.this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                    tvPrefecture.setText(mPrefectureList.get(position).getJapanName());
                } else {
                    tvPrefecture.setText(mPrefectureList.get(position).getName());
                }
                prefectureDialog.dismiss();
            }
        });

        mCropAdapter.setOnItemClickCallBack(new OnItemCallBack() {
            @Override
            public void OnItemClick(int position) {
                mCropPos = position;
                if (Prefs.getInstance(EditBasicRecipeActivity.this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                    tvCrop.setText(mCropList.get(position).getNameJapan());
                } else {
                    tvCrop.setText(mCropList.get(position).getName());
                }
                cropDialog.dismiss();
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideKeyBroad(EditBasicRecipeActivity.this);
                setForCusDefault();
                return false;
            }
        });

    }

    private void saveClicked() {
        if (edtRecipeName.getText().toString().trim().isEmpty()) {
            edtRecipeName.setError(getString(R.string.error_field_required));
        }
        if (tvPrefecture.getText().toString().trim().isEmpty()) {
            tvPrefecture.setError(getString(R.string.error_field_required));
        }
        if (!edtRecipeName.getText().toString().trim().isEmpty() && !tvPrefecture.getText().toString().trim().isEmpty()) {
            if (imageFile != null && !imageFile.isEmpty()) {
                File file = new File(imageFile);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imageValue = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                if (isNetworkOffline()) {
                    return;
                }
                showLoadingDialog(getString(R.string.message_please_wait));
                mEditBasicRecipePresenter.updateRecipePicture(imageValue);
            } else {
                final EditRecipeRequest recipe = new EditRecipeRequest();
                recipe.parseToRequestObject(mRecipe);
                collectDataFromUI(recipe);
                if (isNetworkOffline()) {
                    return;
                }
                showLoadingDialog(getString(R.string.message_please_wait));
                mEditBasicRecipePresenter.editRecipeById(mRecipe.getId(), recipe);
            }
        }
    }

    private void collectDataFromUI(EditRecipeRequest recipeRequest) {
        recipeRequest.recipeName = edtRecipeName.getText().toString().trim();
        recipeRequest.crop = mCropList.get(mCropPos).getName();
        recipeRequest.prefectures = mPrefectureList.get(mPrefecturePos).getName();
        recipeRequest.recipeVersion = Integer.parseInt(tvVersion.getText().toString().trim());
        recipeRequest.category = mCategoryList.get(mCategoryPos).getName();
        recipeRequest.description = edtOverview.getText().toString().trim();

        recipeRequest.changeAvailability = tvChange.getText().toString().equals(getString(R.string.changeable));

        recipeRequest.scope = !tvScope.getText().toString().equals(getString(R.string.disclosure));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgName:
                edtRecipeName.requestFocus();
                edtRecipeName.setText("");
                imgName.setVisibility(GONE);
                break;
            case R.id.imgOverview:
                edtOverview.requestFocus();
                edtOverview.setText("");
                imgOverview.setVisibility(GONE);
                break;
            case R.id.layoutScope:
                scopeDialog.show();
                break;
            case R.id.layoutCrop:
                cropDialog.show();
                break;
            case R.id.layoutChange:
                changeDialog.show();
                break;
            case R.id.layoutCategory:
                categoryDialog.show();
                break;
            case R.id.tvRevisionHistory:
                Intent intent = new Intent(this, RevisionHistoryActivity.class);
                intent.putExtra(Utils.Name.REVISION_HISTORY, mRecipe);
                startActivity(intent);
                break;
            case R.id.imgCamera:
                imageHelper.takePhotoWithCamera();
                break;
            case R.id.imgGallery:
//                Intent intentImage = new Intent(this, AlbumSelectActivity.class);
//                intentImage.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
//                startActivityForResult(intentImage, Constants.REQUEST_CODE);
                Intent intentImage = new Intent(EditBasicRecipeActivity.this, MainActivityGallery.class);
                intentImage.putExtra(Utils.Constant.IMAGE_PICK_TYPE, Utils.Constant.PICK_ONE_IMAGE);
                startActivityForResult(intentImage, Utils.Constant.PICK_IMAGE_REQUEST_CODE);
                break;
            case R.id.layoutChangeable:
                changeDialog.dismiss();
                tvChange.setText(getString(R.string.changeable));
                imgChangeable.setVisibility(View.VISIBLE);
                imgUnChangeable.setVisibility(View.INVISIBLE);
                break;
            case R.id.layoutUnChangeable:
                changeDialog.dismiss();
                tvChange.setText(getString(R.string.unchangeable));
                imgChangeable.setVisibility(View.INVISIBLE);
                imgUnChangeable.setVisibility(View.VISIBLE);
                break;
            case R.id.layoutDisclosure:
                scopeDialog.dismiss();
                tvScope.setText(getString(R.string.disclosure));
                imgDisclosure.setVisibility(View.VISIBLE);
                imgUnDisclosure.setVisibility(View.INVISIBLE);
                break;
            case R.id.layoutUnDisclosure:
                scopeDialog.dismiss();
                tvScope.setText(getString(R.string.undisclosure));
                imgDisclosure.setVisibility(View.INVISIBLE);
                imgUnDisclosure.setVisibility(View.VISIBLE);
                break;
            case R.id.layoutPrefecture:
                setForCusDefault();
                prefectureDialog.show();
                scrollX = scrollView.getScrollX();
                scrollY = scrollView.getScrollY();
                break;
            default:
                break;
        }
    }

    private void callApiToGetCategory() {
        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mEditBasicRecipePresenter.getCategories();
    }

    @Override
    public void editSuccess(ObjectRecipe recipe) {
        hideLoadingDialog();
        if (recipe != null) {
            Toast.makeText(EditBasicRecipeActivity.this, R.string.edit_recipe_success, Toast.LENGTH_LONG).show();
//            Intent data = new Intent();
//            data.putExtra(Utils.Name.RECIPE, recipe);
            App.appRecipe = recipe;
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void failed(String string) {
        hideLoadingDialog();
        tvCrop.setText("");
        mCropAdapter.clearDataCrop();
        Toast.makeText(EditBasicRecipeActivity.this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getFilterSuccess(ObjectFilterRecipe filterRecipe) {
    }

    @Override
    public void updatePictureSuccess(BaseResponse baseResponse) {
        final EditRecipeRequest recipe = new EditRecipeRequest();
        recipe.parseToRequestObject(mRecipe);
        collectDataFromUI(recipe);
        recipe.imagePath = baseResponse.getUrl();
        if (isNetworkOffline()) {
            return;
        }
        mEditBasicRecipePresenter.editRecipeById(mRecipe.getId(), recipe);
    }

    @Override
    public void getCategoriesSuccess(List<ObjectCategory> categories) {
        if (categories != null && !categories.isEmpty()) {
            mCategoryList = categories;
            int index, size = mCategoryList.size();
            for (index = 0; index < size; index++) {
                if (mCategoryList.get(index).getName().equalsIgnoreCase(mRecipe.getCategory())
                        || mCategoryList.get(index).getJapanName().equalsIgnoreCase(mRecipe.getCategory())) {
                    mCategoryId = mCategoryList.get(index).getId();
                    mCategoryList.get(index).setSelected(true);
                    if (Prefs.getInstance(EditBasicRecipeActivity.this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                        tvCategory.setText(mCategoryList.get(index).getJapanName());
                    } else {
                        tvCategory.setText(mCategoryList.get(index).getName());
                    }
                    mCategoryPos = index;
                    break;
                }
            }
        }
        mCategoryAdapter.addData(mCategoryList);
        mEditBasicRecipePresenter.getCropsByCateId(mCategoryId);
    }

    @Override
    public void getCropsSuccess(List<ObjectCrop> cropList) {
        //TODO getCropsSuccess
        hideLoadingDialog();
        if (cropList != null && !cropList.isEmpty()) {
            //SET DEFAULT ITEM 0 IS SELECTED
            if (Prefs.getInstance(EditBasicRecipeActivity.this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                tvCrop.setText(cropList.get(0).getNameJapan());
            } else {
                tvCrop.setText(cropList.get(0).getName());
            }
            cropList.get(0).setSelected(true);
            //Is it crop in Recipe existed?
            int index, size = cropList.size();
            for (index = 0; index < size; index++) {
                if (mRecipe.getCrop().trim().equalsIgnoreCase(cropList.get(index).getName().trim())
                        || mRecipe.getCrop().trim().equalsIgnoreCase(cropList.get(index).getNameJapan().trim())) {
                    cropList.get(0).setSelected(false);
                    cropList.get(index).setSelected(true);
                    //SET TEXT FOR TEXT VIEW
                    if (Prefs.getInstance(EditBasicRecipeActivity.this).getLocale().equalsIgnoreCase(Utils.Name.LOCALE_JA)) {
                        tvCrop.setText(cropList.get(index).getNameJapan());
                    } else {
                        tvCrop.setText(cropList.get(index).getName());
                    }
                    mCropPos = index;
                    break;
                }
            }
            mCropList = cropList;
            mCropAdapter.addData(mCropList);
        } else {
            //TODO ELSE CASE
            customConfirmDialog(getString(R.string.server_error), "Cannot get list Crop");
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
        if (mEditBasicRecipePresenter != null) {
            mEditBasicRecipePresenter.onDestroy();
        }
        if (mCategoryAdapter != null) {
            mCategoryAdapter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageHelper.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
           /* if (requestCode == Constants.REQUEST_CODE) {
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                ArrayList<String> imageUri = new ArrayList<>();
                for (Image image : images) {
                    imageUri.add(image.path);
                }
                imageFile = imageUri.get(0);
                Glide.with(this).load(imageFile).into(imgRecipe);
            }*/
            if (requestCode == Utils.Constant.PICK_IMAGE_REQUEST_CODE) {
                ArrayList<String> listPositionImageLocal = data.getStringArrayListExtra(Utils.Constant.IMAGE_LIST_SELECTED);
                if (!listPositionImageLocal.isEmpty()) {
                    Glide.with(this).load(listPositionImageLocal.get(0)).into(imgRecipe);
                    imageFile = listPositionImageLocal.get(0);
                }
            }
        }
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
//        this.imageFile = imageFile;
//        Glide.with(this).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true).into(imgRecipe);
    }

    @Override
    public void onImageTakenFromCamera(String uri, File imageFile) {
        this.imageFile = imageFile.getPath();
        Glide.with(this).load(uri).into(imgRecipe);
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (edtRecipeName.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                edtRecipeName.setError(null);
                imgName.setVisibility(View.VISIBLE);
            } else {
                imgName.setVisibility(GONE);
            }
        }

        if (edtOverview.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                imgOverview.setVisibility(View.VISIBLE);
            } else {
                imgOverview.setVisibility(GONE);
            }
        }

        if (tvPrefecture.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                tvPrefecture.setError(null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ImageHelper.REQUEST_PICTURE_FROM_CAMERA: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        // the user has just denied one or all of the permissions
                        // use this message to explain why he needs to grant these permissions in order to proceed
                        //This message is displayed after the user has checked never ask again checkbox.
                        String message = "Camera permission needed. Please allow this permission in App Settings.";
                        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //this will be executed if User clicks OK button. This is gonna take the user to the App Settings
                                startAppSettingsConfigActivity();
                            }
                        };
                        new AlertDialog.Builder(this)
                                .setMessage(message)
                                .setPositiveButton(getString(R.string.btn_dialog_ok), listener)
                                .setNegativeButton(getString(R.string.cancel), null)
                                .create()
                                .show();
                    }/* else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // the user has just denied one or all of the permissions
                        // use this message to explain why he needs to grant these permissions in order to proceed
                        String message = "";
                        DialogInterface.OnClickListener listener = null;
                        //This message is displayed after the user has checked never ask again checkbox.
                        message = "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.";
                        listener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //this will be executed if User clicks OK button. This is gonna take the user to the App Settings
                                startAppSettingsConfigActivity();
                            }
                        };
                        new AlertDialog.Builder(TimelinePostActivity.this)
                                .setMessage(message)
                                .setPositiveButton("OK", listener)
                                .setNegativeButton("Cancel", null)
                                .create()
                                .show();
                    }*/


                    // permission denied
                    Log.d("onRequestPermissions", "permission denied");
                } else {
                    // permission was granted
                    imageHelper.takePhotoWithCamera();
                    Log.d("onRequestPermissions", "permission granted editSuccess");
                }
                break;
            }
            default:
                break;
        }
    }

    private void startAppSettingsConfigActivity() {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(i);
    }

    private void setForCusDefault() {
        findViewById(R.id.llForcusDefault).requestFocus();
        findViewById(R.id.llForcusDefault).requestFocusFromTouch();
    }
}
