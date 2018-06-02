package com.tma.stockmarket.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import com.tma.stockmarket.ui.interactor.ExchangeRate.EdtProfileAccountInteractor;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.model.database.DBmanager;
import com.tma.stockmarket.ui.view.activity.MainActivityView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by nbhung on 8/3/2017.
 */

public class EdtProfileAccountFragment extends Fragment {
    private final static int REQUEST_CHOOSE_PHOTE = 999;
    @Inject
    SharedPreferences mSharedPreferences;
    @Inject
    DBmanager mDBmanager;
    @Inject
    EdtProfileAccountInteractor edtProfileAccountInteractor;
    private EditText edtFullName, edtPhone, edtPassWord;
    private Button btnOk;
    private ImageView imgUser;
    private MainActivityView mainActivityView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().netComponent(((App) getActivity().getApplicationContext()).getmNetcomponent())
                .fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);
        edtFullName = (EditText) view.findViewById(R.id.edtFullname);
        edtPassWord = (EditText) view.findViewById(R.id.edteditPassword);
        edtPhone = (EditText) view.findViewById(R.id.edteditphone);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        imgUser = (ImageView) view.findViewById(R.id.img_editimage);
        event();
        return view;
    }

    private void event() {
        edtFullName.setText(mSharedPreferences.getString(getString(R.string.fullname), ""));
        edtPassWord.setText(mSharedPreferences.getString(getString(R.string.passuser), ""));
        edtPhone.setText(mSharedPreferences.getString(getString(R.string.phone), ""));
        byte[] byteImage = Base64.decode(mSharedPreferences.getString(getString(R.string.imageuser), ""), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        imgUser.setImageBitmap(myBitmap);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPhone.getText().toString().equals("") || edtFullName.getText().toString().equals("") || edtPassWord.getText().toString().equals("") || edtPhone.getText() == null || edtFullName.getText() == null || edtPassWord.getText() == null) {
                    Toast.makeText(getActivity(), "phone or name or pass cant be null", Toast.LENGTH_SHORT).show();
                } else
                    edtProfileAccountInteractor.saveDataAccount(edtFullName, edtPassWord, edtPhone, getByteArrayFromImageView(imgUser), mDBmanager, mSharedPreferences, getContext(),mainActivityView);
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityView = (MainActivityView) context;
    }

    private byte[] getByteArrayFromImageView(ImageView imgv) {

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
