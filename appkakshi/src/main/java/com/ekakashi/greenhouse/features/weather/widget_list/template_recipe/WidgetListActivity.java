package com.ekakashi.greenhouse.features.weather.widget_list.template_recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.features.recipe.add_field_greenhouse.GridSpacingItemDecoration;
import com.ekakashi.greenhouse.features.weather.widget_list.template_default.DefaultTemplateActivity;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.OnItemCLickListener;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.OnStartDragListener;
import com.ekakashi.greenhouse.features.weather.widget_list.helper.SimpleItemTouchHelperCallback;


import java.util.List;

/**
 * Created by nbhung on 3/13/2018.
 */

public class WidgetListActivity extends BaseActivity implements View.OnClickListener, MyToolbar.ToolbarListener, OnStartDragListener, OnItemCLickListener, WidgetListInterface.View {
    private MyToolbar myToolbar;
    private boolean isEditState;
    private List<Data> defaultTemplateList;
    private WidgetListAdapter gridViewAdapter;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private WidgetListPresenter presenter;
    private ObjectCreateField createField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_list);
        initView();
    }

    private void initView() {
        if (getIntent() != null) {
            createField = getIntent().getParcelableExtra(Utils.Name.CREATE_FILED);
        }
        presenter = new WidgetListPresenter(this);
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.txt_template), getString(R.string.txt_Default_Template));
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
        myToolbar.setToolbarListener(this);
        mRecyclerView = findViewById(R.id.mRecycleView);
        findViewById(R.id.llButton).setOnClickListener(this);
        callApiGetListTemplate();
    }

    private void setUpRecycleView() {
        gridViewAdapter = new WidgetListAdapter(this, defaultTemplateList, this);
        gridViewAdapter.setOnItemCLickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(gridViewAdapter);
        final int spanCount = 2;
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(gridViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        callback.setApi(false);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llButton:
                startActivity(new Intent(this, DefaultTemplateActivity.class));
                break;
        }
    }

    @Override
    public void onToolbarLeftListener() {
        onBackPressed();
    }

    @Override
    public void onToolbarRightListener() {
        if (!isEditState) {
            isEditState = true;
            gridViewAdapter.isEdit = true;
            gridViewAdapter.notifyDataSetChanged();
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        } else {
            gridViewAdapter.isEdit = false;
            gridViewAdapter.notifyDataSetChanged();
            isEditState = false;
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.txt_edit));
            callApiUpdateSortNo();
        }

    }

    private void callApiUpdateSortNo() {
        if (!isNetworkOffline()) {
            showLoadingDialog(getString(R.string.loading));
            presenter.updateSortNo(defaultTemplateList);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (gridViewAdapter.isEdit) {
            mItemTouchHelper.startDrag(viewHolder);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (!gridViewAdapter.isEdit) {
            Toast.makeText(this, "item = " + position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onNoteListChanged(List<String> strings) {

    }

    @Override
    public void deleteItem(View view, int position) {
        Toast.makeText(this, "position=" + position, Toast.LENGTH_SHORT).show();
        defaultTemplateList.remove(position);
        gridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickButtonAdd() {
        Toast.makeText(this, "button add clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListSuccess(List<Data> listData) {
        if (listData != null) {
            hideLoadingDialog();
            defaultTemplateList = listData;
            setUpRecycleView();
        }
    }

    @Override
    public void getListFailed(String message) {
        hideLoadingDialog();
        Toast.makeText(WidgetListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSortNoSuccess() {
        hideLoadingDialog();
        Toast.makeText(this, "update getRecipeSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        Utils.tokenExpired(this);
    }

    public void callApiGetListTemplate() {
        if (!isNetworkOffline()) {
            showLoadingDialog(getString(R.string.loading));
            presenter.getListWidget("");
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDetroy();
        }
        presenter = null;
        super.onDestroy();
    }
}
