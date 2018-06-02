package com.ekakashi.greenhouse.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ekakashi.greenhouse.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nquochuy on 12/7/2017.
 */

public class ImageHelper {

    private Activity mContext;
    private Fragment fragment;
    private String mImagePath;

    private static final String TAG = "ImageInputHelper";
    public static final int REQUEST_PICTURE_FROM_GALLERY = 23;
    public static final int REQUEST_PICTURE_FROM_CAMERA = 24;
    private File tempFileFromSource = null;
    private Uri tempUriFromSource = null;
    private ImageActionListener imageActionListener;

    public ImageHelper(Activity context) {
        this.mContext = context;
    }

    public ImageHelper(Fragment fragment) {
        this.fragment = fragment;
        this.mContext = fragment.getActivity();
    }

    public void setImageActionListener(ImageActionListener imageActionListener) {
        this.imageActionListener = imageActionListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_PICTURE_FROM_GALLERY) && (resultCode == Activity.RESULT_OK)) {
            if (data != null) {
                Log.e(TAG, "Image selected from gallery");
                if (imageActionListener != null) {
                    imageActionListener.onImageSelectedFromGallery(data.getData(), tempFileFromSource);
                }
            }
        } else if ((requestCode == REQUEST_PICTURE_FROM_CAMERA) && (resultCode == Activity.RESULT_OK)) {

            Log.e(TAG, "Image selected from camera");
            if (imageActionListener != null) {
                imageActionListener.onImageTakenFromCamera(mImagePath, tempFileFromSource);
            }
        }
    }

    public void selectImageFromGallery(Context context) {
      /*  if (isRequestingPermission()) {
            return;
        }
        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("choose", "png", mContext.getExternalCacheDir());
                tempUriFromSource = Uri.fromFile(tempFileFromSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource);
        if (fragment == null) {
            mContext.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY);
        } else {
            fragment.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY);
        }*/
    }

    public void takePhotoWithCamera() {
        if (isRequestingPermission()) {
            return;
        }

        tempFileFromSource = createImageFile();
        tempUriFromSource = Uri.fromFile(tempFileFromSource);
        mImagePath = tempFileFromSource.getAbsolutePath();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = createImageFile();
        if (imageFile != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri imageUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", imageFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource);
            }

            if (fragment == null) {
                mContext.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA);
            } else {
                fragment.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA);
            }
        }

    }

    private boolean isRequestingPermission() {
        if (imageActionListener == null) {
            throw new RuntimeException("ImageSelectionListener must be set before calling openGalleryIntent(), openCameraIntent() or requestCropImage().");
        }
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, REQUEST_PICTURE_FROM_CAMERA);
            return true;
        }
        return false;
    }

    private File createImageFile() {
        // getExternalFilesDir returns /storage/emulated/0/Android/data/com.ekakashi.greenhouse/files/
        File mediaStorageDir = new File(mContext.getExternalFilesDir(null), "Pictures");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create an image file name
        String imageFileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        return new File(mediaStorageDir, imageFileName);

    }

    /**
     * Listener interface for receiving callbacks from the ImageHelper.
     */
    public interface ImageActionListener {
        void onImageSelectedFromGallery(Uri uri, File imageFile);

        void onImageTakenFromCamera(String uri, File imageFile);

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                        @NonNull int[] grantResults);
    }
}
