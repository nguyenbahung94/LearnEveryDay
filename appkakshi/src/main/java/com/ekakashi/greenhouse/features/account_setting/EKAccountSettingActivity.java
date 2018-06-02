package com.ekakashi.greenhouse.features.account_setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.ImageHelper;
import com.ekakashi.greenhouse.common.MyTextWatcher;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.common.text_watcher_callback.TextWatcherCallback;
import com.ekakashi.greenhouse.common.text_watcher_callback.TextWatcherInterface;
import com.ekakashi.greenhouse.custom_gallery.MainActivityGallery;
import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.dao.AccountDao;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.AlphaTextView;

import java.io.File;
import java.util.List;


public class EKAccountSettingActivity extends BaseActivity implements View.OnClickListener, ImageHelper.ImageActionListener, EKAccountSettingInterface.View, TextWatcherInterface {

    private ImageHelper imageHelper;
    private ImageView imgAvatar;
    private ImageButton btnChangeAvatar;

    private EditText edNickName;
    private EditText edFullName;
    private EditText edMyWeb;
    private AlphaTextView tvEmail;

    private ImageButton btnClearNickName;
    private ImageButton btnClearFirstName;

    private TextView btnSave;

    private Account mUser;

    private File imagePath;
    private EKAccountSettingInterface.Presenter mPresenter;
    private List<String> listPositionImageLocal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        initPresenter();
        addToolbar();
        addControls();
        addEvents();

    }

    private void initPresenter() {
        mPresenter = new EKAccountSettingPresenter(this);
        mPresenter.getAccountUser();
    }


    private void addToolbar() {
        ImageView btnBack = mToolbar.findViewById(R.id.imgToolbarBack);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this);
        TextView title = mToolbar.findViewById(R.id.tvToolbarCenter);
        title.setText(R.string.account_setting);
        btnSave = mToolbar.findViewById(R.id.tvToolbarRight);
        btnSave.setText(R.string.toolbar_save);
        btnSave.setVisibility(View.VISIBLE);
        btnSave.setOnClickListener(this);
    }

    private void addControls() {
        imgAvatar = findViewById(R.id.imgAvatar);
        btnChangeAvatar = findViewById(R.id.btnChangePhoto);
        imageHelper = new ImageHelper(this);
        imageHelper.setImageActionListener(this);

        edNickName = findViewById(R.id.ed_nick_name);
        edFullName = findViewById(R.id.ed_full_name);

        tvEmail = findViewById(R.id.tvEmail);
        edMyWeb = findViewById(R.id.ed_my_web);

        btnClearNickName = findViewById(R.id.clear_nick_name);
        btnClearFirstName = findViewById(R.id.clear_first_name);
    }

    private void initUI() {
        if (mUser == null) {
            return;
        }

        tvEmail.setText(mUser.email);
        edNickName.setText(mUser.getNickName());
        edFullName.setText(mUser.getName());
        edMyWeb.setText(mUser.userWeb);

        Glide.with(this).load(mUser.image).placeholder(R.drawable.ic_user_default)
                .error(R.drawable.ic_user_default).into(imgAvatar);

        tvEmail.onSetAlpha(128);
    }

    private void addEvents() {
        imageHelper.setImageActionListener(this);
        btnChangeAvatar.setOnClickListener(this);

        btnClearNickName.setOnClickListener(this);
        btnClearFirstName.setOnClickListener(this);

        edFullName.addTextChangedListener(new MyTextWatcher(edFullName, btnClearFirstName));
        edNickName.addTextChangedListener(new TextWatcherCallback(edNickName, btnClearNickName, this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePhoto:
                changePhoto();
                break;
            case R.id.tvToolbarRight:
                validationField();
                break;
            case R.id.imgToolbarBack:
                finish();
                break;
            default:
                break;
        }

    }

    private void validationField() {
        boolean fail = false;

        if (edNickName.getText().toString().isEmpty()) {
            edNickName.setError(getString(R.string.request_enter_nick_name));
            fail = true;
        }

        if (fail) {
            return;
        }

        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.doSendRequest(edFullName.getText().toString(), tvEmail.getText().toString().trim(),
                edNickName.getText().toString(), imagePath);
    }


    private void changePhoto() {
        new MaterialDialog.Builder(this)
                .title(R.string.camera_action)
                .items(R.array.arrCameraAction)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            imageHelper.takePhotoWithCamera();
                        } else {
                            //imageHelper.selectImageFromGallery(EKAccountSettingActivity.this);
                            Intent intent = new Intent(EKAccountSettingActivity.this, MainActivityGallery.class);
                            intent.putExtra(Utils.Constant.IMAGE_PICK_TYPE, Utils.Constant.PICK_ONE_IMAGE);
                            startActivityForResult(intent, Utils.Constant.PICK_IMAGE_REQUEST_CODE);
                        }
                    }

                }).show();
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        imagePath = new File(Utils.getPath(this, uri));
        Glide.with(this).load(uri).into(imgAvatar);
    }

    @Override
    public void onImageTakenFromCamera(String uri, File imageFile) {
        imagePath = imageFile;
        Glide.with(this).load(uri).into(imgAvatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageHelper.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Utils.Constant.PICK_IMAGE_REQUEST_CODE) {
            listPositionImageLocal = data.getStringArrayListExtra(Utils.Constant.IMAGE_LIST_SELECTED);
            if (!listPositionImageLocal.isEmpty() && listPositionImageLocal != null) {
                Glide.with(this).load(listPositionImageLocal.get(0)).into(imgAvatar);
                imagePath = new File(listPositionImageLocal.get(0));
            }
        }
    }

    @Override
    public Account getUser() {
        return mUser;
    }

    @Override
    public void getAccountSuccess(Account account) {
        mUser = account;
        initUI();
    }

    @Override
    public void getAccountFail() {
        mUser = mDatabaseHelper.getAccountDao().getAccount();
        initUI();
    }

    @Override
    public void onUpdateSuccess() {
        hideLoadingDialog();
        Toast.makeText(this, R.string.toast_success, Toast.LENGTH_LONG).show();
        Prefs.getInstance(this).saveReloadTimeline(true);
        finish();
    }

    @Override
    public void onUpdateFail(String errorMessage) {
        hideLoadingDialog();
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public AccountDao getAccountDao() {
        return mDatabaseHelper.getAccountDao();
    }

    @Override
    public void onTextWatcherCallback(boolean isTextEmpty) {
        Utils.enableView(btnSave, !isTextEmpty);
    }
}
