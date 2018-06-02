package com.tma.stockmarket.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerFragmentComponent;
import com.tma.stockmarket.di.module.FragmentModule;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.model.database.DBmanager;
import com.tma.stockmarket.ui.view.activity.MainViewRegister;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class RegisterProfileFragment extends Fragment {
    private final static int SUCCESSFUL = 200;
    private final int REQUEST_CODE = 191;
    private final int REQUEST_TAKE_PHOTE = 1199;
    private final int REQUEST_CHOOSE_PHOTE = 9911;
    @Inject
    SharedPreferences mSharedPreferences;
    private MainViewRegister mainViewRegister;
    private ImageView imgUser;
    private EditText edtIdUser, edtPassword;
    private Button btnOk, btnChoose, btnTakePhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().netComponent(((App) getActivity().getApplicationContext()).getmNetcomponent())
                .fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_profile, container, false);
        imgUser = (ImageView) view.findViewById(R.id.img_profile);
        edtIdUser = (EditText) view.findViewById(R.id.edt_IdUser);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnChoose = (Button) view.findViewById(R.id.btnchooseIamge);
        btnTakePhoto = (Button) view.findViewById(R.id.btntakePhoto);
        Log.e("run", "run");
        Log.e("get shared", mSharedPreferences.getString(getString(R.string.iduser), ""));
        event();
        //   mainViewRegister.saveFragment(SUCCESSFUL);
        return view;
    }

    private void event() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtIdUser.getText() == null || edtIdUser.getText().toString().equals("") || edtPassword.getText() == null || edtPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Image ,id , password cant be null", Toast.LENGTH_SHORT).show();

                } else {
                    DBmanager database = new DBmanager(getContext());
                    if (database.CheckUser(edtIdUser.getText().toString())) {
                        Toast.makeText(getActivity(), "ID exist", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "success!", Toast.LENGTH_SHORT).show();
                        saveShared();
                    }

                }
            }


        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
    }

    private void saveShared() {
        byte[] image;
        if (imgUser.getDrawable() == null) {
            ImageView tam = new ImageView(getContext());
            tam.setImageResource(R.drawable.imgdefault);
            image = getByteArrayFromImageView(tam);
        } else {
            image = getByteArrayFromImageView(imgUser);
        }
        String saveThis = Base64.encodeToString(image, Base64.DEFAULT);
        mSharedPreferences.edit().putString(getString(R.string.iduser), edtIdUser.getText().toString()).apply();
        mSharedPreferences.edit().putString(getString(R.string.imageuser), saveThis).apply();
        mSharedPreferences.edit().putString(getString(R.string.passuser), edtPassword.getText().toString()).apply();
        mainViewRegister.saveFragment(SUCCESSFUL);

    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTE);
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//
//                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
//                imgUser.setImageBitmap(bitmap);
//            }
//        }
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTE) {
                Uri imageUri = data.getData();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgUser.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_TAKE_PHOTE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get(getString(R.string.data));
                imgUser.setImageBitmap(bitmap);
            }
        }
    }

    private byte[] getByteArrayFromImageView(ImageView imgv) {

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainViewRegister = (MainViewRegister) context;
    }
}
