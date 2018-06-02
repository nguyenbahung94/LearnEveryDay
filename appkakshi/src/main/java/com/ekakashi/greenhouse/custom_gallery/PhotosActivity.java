package com.ekakashi.greenhouse.custom_gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 3/26/2018.
 */

public class PhotosActivity extends BaseActivity implements SelectPhotosCallback {
    private List<String> listSelected = new ArrayList<>();
    private List<String> listImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_recyclerview);
        addToolbar();
        addControls();
    }

    private void addControls() {
        int imageFolderPosition;
        int imagePickType;
        if (getIntent() != null) {
            imageFolderPosition = getIntent().getIntExtra(Utils.Constant.IMAGE_FOLDER_POSITION, 0);
            imagePickType = getIntent().getIntExtra(Utils.Constant.IMAGE_PICK_TYPE, Utils.Constant.PICK_MULTI_IMAGE);

        } else {
            imageFolderPosition = 0;
            imagePickType = Utils.Constant.PICK_MULTI_IMAGE;
        }

        RecyclerView mRecyclerView = findViewById(R.id.recycleview);
        //todo change list here
        Bundle bundle = getIntent().getExtras();
        ArrayList<Model_images> listModel_Img=null;
        if (bundle != null) {
            listModel_Img = bundle.getParcelableArrayList(Utils.Constant.LIST_MODEL_IMAGE);

        }
        if (listModel_Img != null) {
            listImage = listModel_Img.get(imageFolderPosition).getAl_imagepath();
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 2, true));

        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(this, listImage, listSelected, imagePickType, this);
        listSelected.clear();
        mRecyclerView.setAdapter(photoViewAdapter);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.camera_action), "");
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_BUTTON_ADD, getString(R.string.add));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

                if (listSelected != null && !listSelected.isEmpty()) {
                    setDataResult();
                } else {
                    Toast.makeText(PhotosActivity.this, R.string.image_pick_at_least_1_image, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSelectOneImage() {
        setDataResult();
    }

    private void setDataResult() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(Utils.Constant.IMAGE_LIST_SELECTED, (ArrayList<String>) listSelected);
        setResult(RESULT_OK, intent);
        finish();
    }
}
