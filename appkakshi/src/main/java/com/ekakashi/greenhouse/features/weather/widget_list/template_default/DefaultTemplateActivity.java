package com.ekakashi.greenhouse.features.weather.widget_list.template_default;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.GridSpacingItemDecoration;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.OnItemCLickListener;

import java.util.List;

/**
 * Created by nbhung on 3/12/2018.
 */

public class DefaultTemplateActivity extends BaseActivity implements View.OnClickListener, MyToolbar.ToolbarListener, OnItemCLickListener, TemplateDefaultInterface.View {
    private MyToolbar myToolbar;
    private List<Data> defaultTemplateList;
    private RecyclerView mRecycleView;
    private DefaultTemplateAdapter defaultTemplateAdapter;
    private TemplateDefaultPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_template);
        mRecycleView = findViewById(R.id.mRecycleView);
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.txt_search), getString(R.string.txt_Default_Template));
        myToolbar.setToolbarListener(this);
        presenter = new TemplateDefaultPresenter(this);
        callApi();

    }

    private void setUpRecycleView() {
        defaultTemplateAdapter = new DefaultTemplateAdapter(this, defaultTemplateList);
        defaultTemplateAdapter.setOnItemCLickListener(this);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(defaultTemplateAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(DefaultTemplateActivity.this, 2);
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        mRecycleView.setLayoutManager(layoutManager);
    }

    private void callApi() {
        showLoadingDialog(getString(R.string.loading));
        presenter.getListTemplateDefault();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onToolbarLeftListener() {
        onBackPressed();
    }

    @Override
    public void onToolbarRightListener() {
        Toast.makeText(DefaultTemplateActivity.this, "Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, defaultTemplateList.get(position).getId() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onNoteListChanged(List<String> strings) {

    }

    @Override
    public void deleteItem(View view, int position) {

    }

    @Override
    public void clickButtonAdd() {

    }

    @Override
    public void getListSuccess(List<Data> listData) {
        hideLoadingDialog();
        if (listData != null) {
            defaultTemplateList = listData;
            setUpRecycleView();
        }

    }

    @Override
    public void getListFailed(String error) {
        hideLoadingDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void TokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(DefaultTemplateActivity.this);
    }
}
