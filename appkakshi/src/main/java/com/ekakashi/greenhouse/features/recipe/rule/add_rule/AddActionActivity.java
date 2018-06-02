package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.ImageHelper;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectAction;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;
import com.ekakashi.greenhouse.features.recipe.rule.select_type.SelectTypeActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.view.View.GONE;

public class AddActionActivity extends BaseActivity implements View.OnClickListener, TextWatcher, ImageHelper.ImageActionListener, AddActionInterface.View {

    private RelativeLayout layoutType;
    private RelativeLayout layoutMoreThanFive;
    private RelativeLayout layoutUploadImage;
    private RelativeLayout layoutDetail;
    private RelativeLayout layoutTitle;
    private LinearLayout layout2Image;
    private LinearLayout layout3Image;
    private LinearLayout layoutDisplayImage;
    private EditText edtTitle;
    private EditText edtContent;
    private EditText edtUrl;
    private ImageView imgCamera;
    private ImageView imgContent;
    private ImageView imgTitle;
    private ImageView imgUrl;
    private ImageView imgGallery;
    private ImageView imgOnlyOne;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;
    private ImageView imgFour;
    private ImageView imgFive;
    private TextView tvNumberImage;
    private TextView url;
    private TextView tvType;
    private TextView title;
    private TextView tvDetail;
    private TextView tvUrl;
    private View viewTrans;
    private WebView wvContent;

    private ImageHelper imageHelper;
    private List<String> uriImageList = new ArrayList<>();
    private AddActionPresenter mAddActionPresenter;
    private int ruleType;
    private int actionType = 1;
    private String uriImage;
    private String urlImage;
    private boolean viewDetail;
    private ObjectAction mActionDetail;
    final String mimeType = "text/html";
    final String encoding = "UTF-8";
    private ObjectAction.ObjectActionContent mObjectActionContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        initView();
        addToolbar();
        prepareData();
        parseContentActionToObject();
        setUpUI();
    }

    private void initView() {
        layoutType = findViewById(R.id.layoutType);
        layoutMoreThanFive = findViewById(R.id.layoutMoreThanFive);
        layout2Image = findViewById(R.id.layout2Image);
        layout3Image = findViewById(R.id.layout3Image);
        layoutTitle = findViewById(R.id.layoutTitle);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtUrl = findViewById(R.id.edtUrl);
        tvNumberImage = findViewById(R.id.tvNumberImage);
        imgCamera = findViewById(R.id.imgCamera);
        imgGallery = findViewById(R.id.imgGallery);
        imgUrl = findViewById(R.id.imgUrl);
        imgTitle = findViewById(R.id.imgTitle);
        imgContent = findViewById(R.id.imgContent);
        imgOnlyOne = findViewById(R.id.imgOnlyOne);
        imgOne = findViewById(R.id.imgOne);
        imgTwo = findViewById(R.id.imgTwo);
        imgThree = findViewById(R.id.imgThree);
        imgFour = findViewById(R.id.imgFour);
        imgFive = findViewById(R.id.imgFive);
        viewTrans = findViewById(R.id.viewTrans);
        url = findViewById(R.id.url);
        layoutUploadImage = findViewById(R.id.layoutUploadImage);
        tvType = findViewById(R.id.tvType);
        title = findViewById(R.id.title);
        layoutDisplayImage = findViewById(R.id.layoutDisplayImage);
        layoutDetail = findViewById(R.id.layoutDetail);
        wvContent = findViewById(R.id.wvContent);
        tvDetail = findViewById(R.id.detail);
        tvUrl = findViewById(R.id.tvUrl);

        imgTitle.setOnClickListener(this);
        imgContent.setOnClickListener(this);
        imgUrl.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        layoutType.setOnClickListener(this);
        imgOnlyOne.setOnClickListener(this);
        imgOne.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        imgThree.setOnClickListener(this);
        imgFour.setOnClickListener(this);
        imgFive.setOnClickListener(this);
        layoutMoreThanFive.setOnClickListener(this);
        edtTitle.addTextChangedListener(this);
        edtContent.addTextChangedListener(this);
        edtUrl.addTextChangedListener(this);

        if (getIntent() != null) {
            ruleType = getIntent().getIntExtra(Utils.Name.TYPE, 0);
            viewDetail = getIntent().getBooleanExtra(Utils.Name.VIEW_DETAIL, false);
            mActionDetail = App.appAction;
        }

        final WebSettings webSettings = wvContent.getSettings();
        webSettings.setDefaultFontSize(15);//15sp

    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        if (viewDetail) {
            ruleType = getIntent().getIntExtra(Utils.Name.RULE, 1);
            actionType = mActionDetail.getActionType();
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.view_action_information), Utils.getRuleType(this, ruleType));
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
        } else {
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.add_action), Utils.getRuleType(this, ruleType));
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        }

        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (edtContent.getText().toString().isEmpty()) {
                    edtContent.setError(getString(R.string.error_field_required));
                    return;
                }
                Intent intent = new Intent();
                ObjectAction action = new ObjectAction();
                switch (actionType) {
                    case EnumRule.POST_TO_TIMELINE:
                        action.setName("Action");
                        action.setContent(edtContent.getText().toString().trim());
                        action.setActionType(EnumRule.POST_TO_TIMELINE);
                        App.appAction = action;
//                        intent.putExtra(Utils.Name.ACTION, action);
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case EnumRule.MOVE_TO_NEXT_STAGE:
                        action.setName("Action");
                        action.setContent(edtContent.getText().toString().trim());
                        action.setActionType(EnumRule.MOVE_TO_NEXT_STAGE);
//                        action.setTitle(edtTitle.getText().toString().trim());
                        App.appAction = action;
//                        intent.putExtra(Utils.Name.ACTION, action);
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE:
                        if (uriImage != null) {
                            File file = new File(uriImage);
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part imageValue = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                            if (isNetworkOffline()) {
                                return;
                            }
                            showLoadingDialog(getString(R.string.message_please_wait));
                            mAddActionPresenter.uploadImage(imageValue);
                        } else {
                            action.setName("Action");
                            action.setContent(edtContent.getText().toString().trim());
                            action.setActionType(EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE);
                            action.setUrl(edtUrl.getText().toString().trim());
                            action.setTitle(edtTitle.getText().toString().trim());
                            action.setImage(urlImage);
                            App.appAction = action;
//                            intent.putExtra(Utils.Name.ACTION, action);
                            setResult(RESULT_OK);
                            finish();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void prepareData() {
        mAddActionPresenter = new AddActionPresenter(this);
        imageHelper = new ImageHelper(this);
        imageHelper.setImageActionListener(this);

        if (edtTitle.getText().toString().trim().isEmpty()) {
            imgTitle.setVisibility(GONE);
        } else {
            imgTitle.setVisibility(View.VISIBLE);
            edtTitle.setError(null);
        }
    }

    private void parseContentActionToObject() {
        if (mActionDetail.getContent() != null && !mActionDetail.getContent().isEmpty()) {
            mObjectActionContent = new Gson().fromJson(mActionDetail.getContent(), ObjectAction.ObjectActionContent.class);
            if (mObjectActionContent != null) {
                if (mObjectActionContent.getImage().contains("base64")) {
                    String[] imageList = mObjectActionContent.getImage().split("base64,");
                    if (imageList.length != 0) {
                        if (imageList[1] != null && !imageList[1].isEmpty()) {
                            mObjectActionContent.setImage(imageList[1]);
                        }
                    }
                }
            }
        }
    }

    private void setUpUI() {
        switch (actionType) {
            case EnumRule.POST_TO_TIMELINE:
                if (viewDetail) {
                    layoutType.setEnabled(false);
                    tvDetail.setVisibility(GONE);
                    wvContent.setVisibility(View.GONE);
                    edtContent.setVisibility(View.VISIBLE);
                    edtContent.setEnabled(false);
                    if (mObjectActionContent != null && mObjectActionContent.getContent() != null && !mObjectActionContent.getContent().isEmpty()) {
                        edtContent.setText(mObjectActionContent.getContent());
                    } else {
                        edtContent.setText("");
                    }
//                    }
                }
                tvType.setText(Utils.getActionType(this, EnumRule.POST_TO_TIMELINE));
                url.setVisibility(GONE);
                edtUrl.setVisibility(GONE);
                title.setVisibility(GONE);
                layoutTitle.setVisibility(GONE);
                layoutUploadImage.setVisibility(GONE);
                break;
            case EnumRule.MOVE_TO_NEXT_STAGE:
                if (viewDetail) {
                    layoutType.setEnabled(false);
                    edtTitle.setEnabled(false);
                    edtContent.setEnabled(false);
                    wvContent.setVisibility(View.GONE);
                    edtContent.setVisibility(View.VISIBLE);
                    tvDetail.setVisibility(GONE);

                    //CONTENT
                    if (mObjectActionContent != null && mObjectActionContent.getContent() != null && !mObjectActionContent.getContent().isEmpty()) {
                        edtContent.setText(mObjectActionContent.getContent());
                    } else {
                        edtContent.setText("");
                    }

                }
                tvType.setText(Utils.getActionType(this, EnumRule.MOVE_TO_NEXT_STAGE));
                url.setVisibility(GONE);
                edtUrl.setVisibility(GONE);
                title.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                layoutUploadImage.setVisibility(GONE);
                break;
            case EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE:
                if (viewDetail) {
                    layoutType.setEnabled(false);
                    edtTitle.setEnabled(false);
                    edtUrl.setEnabled(false);
                    edtContent.setEnabled(false);

                    edtContent.setVisibility(View.VISIBLE);
                    tvUrl.setVisibility(View.VISIBLE);
                    edtUrl.setVisibility(View.GONE);
                    wvContent.setVisibility(GONE);
                    layoutUploadImage.setVisibility(View.GONE);
                    //TITLE
                    if (mObjectActionContent != null && mObjectActionContent.getTitle() != null && !mObjectActionContent.getTitle().isEmpty()) {
                        edtTitle.setText(mObjectActionContent.getTitle());
                    } else {
                        edtTitle.setText("");
                    }
                    //CONTENT
                    if (mObjectActionContent != null && mObjectActionContent.getContent() != null && !mObjectActionContent.getContent().isEmpty()) {
                        edtContent.setText(mObjectActionContent.getContent());
                    } else {
                        edtContent.setText("");
                    }
                    //URL
                    if (mObjectActionContent != null && mObjectActionContent.getUrl() != null && !mObjectActionContent.getUrl().isEmpty()) {
                        tvUrl.setText(mObjectActionContent.getUrl());
                    } else {
                        tvUrl.setText("");
                    }
                    //IMAGE
                    if (mObjectActionContent != null && mObjectActionContent.getImage() != null && !mObjectActionContent.getImage().isEmpty()) {
                        displayOnlyOneImage();
                        byte[] decodedString = Base64.decode(mObjectActionContent.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imgOnlyOne.setImageBitmap(decodedByte);
                    } else {
                        imgOnlyOne.setVisibility(View.GONE);
                    }

                } else {
                    layoutUploadImage.setVisibility(View.VISIBLE);
                }
                url.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                layoutTitle.setVisibility(View.VISIBLE);
                tvType.setText(Utils.getActionType(this, EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE));
                break;
            default:
                tvType.setText(Utils.getActionType(this, EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE));
                layoutType.setEnabled(false);
                edtTitle.setEnabled(false);
                edtContent.setEnabled(false);
                edtUrl.setEnabled(false);
                layoutUploadImage.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgTitle:
                edtTitle.requestFocus();
                edtTitle.setText("");
                imgTitle.setVisibility(GONE);
                break;
            case R.id.imgUrl:
                edtUrl.requestFocus();
                edtUrl.setText("");
                imgUrl.setVisibility(GONE);
                break;
            case R.id.imgContent:
                edtContent.requestFocus();
                edtContent.setText("");
                imgContent.setVisibility(GONE);
                break;
            case R.id.layoutType:
                Intent intentType = new Intent(this, SelectTypeActivity.class);
                intentType.putExtra(Utils.Name.TYPE, ruleType);
                intentType.putExtra(Utils.Name.TYPE_OF_ACTION, actionType);
                startActivityForResult(intentType, Utils.Name.TYPE_OF_ACTION_CODE);
                break;
            case R.id.imgCamera:
                imageHelper.takePhotoWithCamera();
                break;
            case R.id.imgGallery:
                Intent intentImage = new Intent(this, AlbumSelectActivity.class);
                intentImage.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intentImage, Constants.REQUEST_CODE);
//                imageHelper.selectImageFromGallery();
                break;
            case R.id.layoutMoreThanFive:
                Toast.makeText(this, "More Than Five Image", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgOnlyOne:
                break;
            case R.id.imgOne:
                break;
            case R.id.imgTwo:
                break;
            case R.id.imgThree:
                break;
            case R.id.imgFour:
                break;
            case R.id.imgFive:
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Title
        if (edtTitle.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                edtTitle.setError(null);
                imgTitle.setVisibility(View.VISIBLE);
            } else {
                imgTitle.setVisibility(GONE);
            }
        }
        //URL
        if (edtUrl.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                imgUrl.setVisibility(View.VISIBLE);
            } else {
                imgUrl.setVisibility(GONE);
            }
        }
        // Content
        if (edtContent.hasFocus()) {
            if (!s.toString().trim().isEmpty()) {
                imgContent.setVisibility(View.VISIBLE);
            } else {
                imgContent.setVisibility(GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
//        if (!uri.isEmpty()) {
//            if (uriImageList == null) {
//                uriImageList = new ArrayList<>();
//            }
//            uriImageList.add(uri);
//        }
//        showImages();
//        displayOnlyOneImage();
//        uriImage = imageFile;
//        Glide.with(this).load(uri).into(imgOnlyOne);
    }

    @Override
    public void onImageTakenFromCamera(String uri, File imageFile) {
//        if (!uri.isEmpty()) {
//            if (uriImageList == null) {
//                uriImageList = new ArrayList<>();
//            }
//            uriImageList.add(uri);
//        }
//        showImages();
        displayOnlyOneImage();
        uriImage = imageFile.getPath();
        Glide.with(this).load(uri).into(imgOnlyOne);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageHelper.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == Utils.Name.TYPE_OF_ACTION_CODE) {
                ruleType = data.getIntExtra(Utils.Name.TYPE, 0);
                actionType = data.getIntExtra(Utils.Name.TYPE_OF_ACTION, 0);

                tvType.setText(Utils.getActionType(this, actionType));
                setUpUI();
            }
            if (requestCode == Constants.REQUEST_CODE) {
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                ArrayList<String> imageUri = new ArrayList<>();
                for (Image image : images) {
                    imageUri.add(image.path);
                }
                uriImage = imageUri.get(0);
                displayOnlyOneImage();
                Glide.with(this).load(uriImage).into(imgOnlyOne);
            }
        }
    }

    private void showImages() {
        if (uriImageList != null && !uriImageList.isEmpty()) {
            switch (uriImageList.size()) {
                case 1:
                    displayOnlyOneImage();
                    break;
//                case 2:
//                    displayTwoImage();
//                    break;
//                case 3:
//                    displayThreeImage();
//                    break;
//                case 4:
//                    displayFourImage();
//                    break;
//                case 5:
//                    displayFiveImage();
//                    break;
                default:
                    displayMoreThanFiveImage();
                    break; // more than five image
            }
        }
    }

    private void displayOnlyOneImage() {
        layout2Image.setVisibility(GONE);
        layout3Image.setVisibility(GONE);

        imgOnlyOne.setVisibility(View.VISIBLE);
//        Glide.with(this).load(uriImageList.get(0)).into(imgOnlyOne);
    }

//    private void displayTwoImage() {
//        imgOnlyOne.setVisibility(GONE);
//        layout3Image.setVisibility(GONE);
//
//        layout2Image.setVisibility(View.VISIBLE);
//        Glide.with(this).load(uriImageList.get(0)).into(imgOne);
//        Glide.with(this).load(uriImageList.get(1)).into(imgTwo);
//    }
//
//    private void displayThreeImage() {
//        imgOnlyOne.setVisibility(GONE);
//        layout2Image.setVisibility(GONE);
//        viewTrans.setVisibility(GONE);
//        tvNumberImage.setVisibility(GONE);
//
//        layout3Image.setVisibility(View.VISIBLE);
//        Glide.with(this).load(uriImageList.get(0)).into(imgThree);
//        Glide.with(this).load(uriImageList.get(1)).into(imgFour);
//        Glide.with(this).load(uriImageList.get(2)).into(imgFive);
//    }
//
//    private void displayFourImage() {
//        layout2Image.setVisibility(GONE);
//        viewTrans.setVisibility(GONE);
//        tvNumberImage.setVisibility(View.GONE);
//
//        imgOnlyOne.setVisibility(View.VISIBLE);
//        layout3Image.setVisibility(View.VISIBLE);
//
//        Glide.with(this).load(uriImageList.get(0)).into(imgOnlyOne);
//        Glide.with(this).load(uriImageList.get(1)).into(imgThree);
//        Glide.with(this).load(uriImageList.get(2)).into(imgFour);
//        Glide.with(this).load(uriImageList.get(3)).into(imgFive);
//    }
//
//    private void displayFiveImage() {
//        imgOnlyOne.setVisibility(GONE);
//        viewTrans.setVisibility(View.GONE);
//        tvNumberImage.setVisibility(View.GONE);
//
//        layout2Image.setVisibility(View.VISIBLE);
//        layout3Image.setVisibility(View.VISIBLE);
//
//        Glide.with(this).load(uriImageList.get(0)).into(imgOne);
//        Glide.with(this).load(uriImageList.get(1)).into(imgTwo);
//        Glide.with(this).load(uriImageList.get(2)).into(imgThree);
//        Glide.with(this).load(uriImageList.get(3)).into(imgFour);
//        Glide.with(this).load(uriImageList.get(4)).into(imgFive);
//    }

    @SuppressLint("SetTextI18n")
    private void displayMoreThanFiveImage() {
        imgOnlyOne.setVisibility(GONE);

        layout2Image.setVisibility(View.VISIBLE);
        layout3Image.setVisibility(View.VISIBLE);
        viewTrans.setVisibility(View.VISIBLE);
        tvNumberImage.setVisibility(View.VISIBLE);
        tvNumberImage.setText(getString(R.string.plus) + String.valueOf(uriImageList.size()));

        Glide.with(this).load(uriImageList.get(0)).into(imgOne);
        Glide.with(this).load(uriImageList.get(1)).into(imgTwo);
        Glide.with(this).load(uriImageList.get(2)).into(imgThree);
        Glide.with(this).load(uriImageList.get(3)).into(imgFour);
        Glide.with(this).load(uriImageList.get(4)).into(imgFive);
    }


    @Override
    public void success(BaseResponse baseResponse) {
        hideLoadingDialog();
        if (baseResponse != null) {
            urlImage = baseResponse.getUrl();
            Intent intent = new Intent();
            ObjectAction action = new ObjectAction();
            if (actionType == EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE) {
                action.setName("Action");
                action.setContent(edtContent.getText().toString().trim());
                action.setActionType(actionType);
                action.setUrl(edtUrl.getText().toString().trim());
                action.setTitle(edtTitle.getText().toString().trim());
                action.setImage(urlImage);
                App.appAction = action;
//                intent.putExtra(Utils.Name.ACTION, action);
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, R.string.message_cannot_upload_image, Toast.LENGTH_LONG).show();
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
        if (mAddActionPresenter != null) {
            mAddActionPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
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
                                .setPositiveButton("OK", listener)
                                .setNegativeButton("Cancel", null)
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
}
