package com.example.nbhung94.takeapictureandroi8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 11/17/2017.
 */

public class TestTakeAPhoto extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 111;
    private List<String> mImageListNeedToBeUploaded = new ArrayList<>();

    private ImageView mImageView;
    private String mImagePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_aphoto);
        mImageView = findViewById(R.id.imgview);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // User may have declined earlier, ask Android if we should show him a reason

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // show an explanation to the user
                // Good practise: don't block thread after the user sees the explanation, try again to request the permission.
            } else {
                // request the permission.
                // CALLBACK_NUMBER is a integer constants
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                // The callback method gets the result of the request.
            }
        }
        findViewById(R.id.btnTakeAphoto).setOnClickListener(this);
        findViewById(R.id.btnChooseMutilImage).setOnClickListener(this);
        findViewById(R.id.btnSenSever).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTakeAphoto:
                dispatchTakePictureIntent();
                break;
            case R.id.btnChooseMutilImage:
                Intent intent = new Intent(TestTakeAPhoto.this, AlbumSelectActivity.class);
                //set limit on number of images that can be selected, default is 10
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1000);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;
            case R.id.btnSenSever:
                uploadPictures();
                break;
            default:
                break;
        }
    }

    private void uploadPictures() {
        if (!mImageListNeedToBeUploaded.isEmpty()) {
            File file = new File(mImageListNeedToBeUploaded.get(0));
            if (!file.exists()) {
                mImageListNeedToBeUploaded.remove(0);
                uploadPictures();
            } else {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                APIManager.updateImage(body, new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        mImageListNeedToBeUploaded.remove(0);
                        uploadPictures();
                        Log.e("code==", String.valueOf(response.code()) + "__" + response.message());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("failed update", "position=" + mImageListNeedToBeUploaded.get(0));
                        mImageListNeedToBeUploaded.remove(0);
                        uploadPictures();
                    }
                });
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    Log.d("", "permission granted success");
                } else {
                    // permission denied
                    Log.d("", "permission denied");
                }
                break;
            }
            default:
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imageFile = createImageFile();
            if (imageFile != null) {
                mImagePath = imageFile.getAbsolutePath();
                Uri imageUri = FileProvider.getUriForFile(TestTakeAPhoto.this,
                        BuildConfig.APPLICATION_ID + ".provider", imageFile);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File imageFile = createImageFile();
                if (imageFile != null) {
                    mImagePath = imageFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mImageListNeedToBeUploaded.add(mImagePath);
            Glide.with(this).load(mImagePath).fitCenter()./*placeholder(R.drawable.ic_pin_selected).*/into(mImageView);
        }
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            for (Image img : images) {
                mImageListNeedToBeUploaded.add(img.path);
                Log.e("img.path", img.path);
            }
            showList();

            Glide.with(this).load(mImageListNeedToBeUploaded.get(mImageListNeedToBeUploaded.size() - 1)).fitCenter()./*placeholder(R.drawable.ic_pin_selected).*/into(mImageView);
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Glide.with(this).load(mImagePath).fitCenter()./*placeholder(R.drawable.ic_pin_selected).*/into(mImageView);
        }
    }

    private void showList() {
        for (String img : mImageListNeedToBeUploaded) {
            Log.e("mImageView =", img);
        }
    }

    private File createImageFile() {
        // getExternalFilesDir returns /storage/emulated/0/Android/data/com.ekakashi.greenhouse/files/
        File mediaStorageDir = new File(getExternalFilesDir(null), "Pictures");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create an image file name
        String imageFileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        return new File(mediaStorageDir, imageFileName);
    }
}
