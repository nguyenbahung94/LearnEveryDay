package com.ekakashi.greenhouse.features.add_field_greenhouse.step2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.map.CustomLatLngObject;
import com.ekakashi.greenhouse.map.MapsFragmentActivity;

public class EKAddFieldStepTwoActivity extends BaseActivity implements View.OnClickListener {
    private ObjectCreateField createField;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EKAddFieldStepTwoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        initView();
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.CREATE_FILED);
        }

    }

    private void initView() {
        createField = new ObjectCreateField();
        //
        findViewById(R.id.btnAddField).setOnClickListener(this);
        handleEvent();
    }

    private void handleEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddField:
                Intent intent = new Intent(this, MapsFragmentActivity.class);
                intent.putExtra(Utils.Name.CREATE_FILED, createField);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
