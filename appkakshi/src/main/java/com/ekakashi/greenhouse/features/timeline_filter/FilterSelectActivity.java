package com.ekakashi.greenhouse.features.timeline_filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline_filter.adapters.FilterFieldAdapter;
import com.ekakashi.greenhouse.features.timeline_filter.adapters.FilterMemberAdapter;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;

import java.util.ArrayList;

public class FilterSelectActivity extends BaseActivity implements FilterInterface.View, FilterInterface.UnselectedAll {

    private RecyclerView rvFilter;
    private FilterFieldAdapter adapterField;
    private FilterMemberAdapter adapterMember;
    private LinearLayout layoutAll;
    private ImageView imgFilter;

    private FilterInterface.Presenter mPresenter;

    private FilterModel filterModel = new FilterModel();
    private String filterType;
    private ArrayList<Integer> selectedIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_select);
        addControls();
        initPresenter();
    }

    private void addControls() {
        rvFilter = findViewById(R.id.rvFilter);
        imgFilter = findViewById(R.id.imgFilter);
        layoutAll = findViewById(R.id.layoutAll);
        //
        Intent intent = getIntent();
        filterType = intent.getStringExtra(Utils.Constant.TIMELINE_FILTER_TYPE);
        addToolbar(filterType.equalsIgnoreCase(FilterActivity.FILTER_PLACE) ? getString(R.string.txt_place) : getString(R.string.filter_member));
        selectedIds = intent.getIntegerArrayListExtra(Utils.Constant.TIMELINE_FILTER_IDS);
        if (selectedIds != null && !selectedIds.isEmpty()) {
            layoutAll.setSelected(false);
            imgFilter.setImageResource(R.drawable.ic_uncheck);
        } else {
            layoutAll.setSelected(true);
            imgFilter.setImageResource(R.drawable.ic_checked);
        }
        //
        layoutAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutAll.isSelected()) {
                    return;
                }
                //checked
                layoutAll.setSelected(true);
                imgFilter.setImageResource(R.drawable.ic_checked);

                if (filterType.equalsIgnoreCase(FilterActivity.FILTER_PLACE)) {
                    if (adapterField != null) {
                        ArrayList<FilterField> fieldList = adapterField.getFilterFields();
                        if (fieldList != null && !fieldList.isEmpty()) {
                            for (FilterField field : fieldList) {
                                field.setSelected(false);
                            }
                            adapterField.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (adapterMember != null) {
                        ArrayList<FilterMember> memberList = adapterMember.getFilterMembers();
                        if (memberList != null && !memberList.isEmpty()) {
                            for (FilterMember member : memberList) {
                                member.setSelected(false);
                            }
                            adapterMember.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void addToolbar(String title) {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, title, "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter = new FilterPresenter(this);
        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        if (filterType.equals(FilterActivity.FILTER_PLACE)) {
            mPresenter.callApiGetPlaceList();
        } else {
            mPresenter.callApiGetMemberList();
        }
    }

    @Override
    public void onFailGetPlaceApi(String message) {
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailGetMemberApi(String message) {
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailGetCropApi(String message) {
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseGetPlaceListApi(ArrayList<FilterField> list) {
        hideLoadingDialog();
        if (list == null) {
            list = new ArrayList<>();
        }

        //updated status selected for new list
        if (selectedIds != null && !selectedIds.isEmpty()) {
            for (FilterField item : list) {
                if (selectedIds.contains(item.getId())) {
                    item.setSelected(true);
                } else {
                    item.setSelected(false);
                }
            }
        }
        //
        if (adapterField == null) {
            adapterField = new FilterFieldAdapter(list, this, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvFilter.setLayoutManager(layoutManager);
            rvFilter.setItemAnimator(new DefaultItemAnimator());
            rvFilter.setAdapter(adapterField);
        } else {
            adapterField.setFilterFields(list);
            adapterField.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseGetMemberListApi(ArrayList<FilterMember> list) {
        hideLoadingDialog();
        if (list == null) {
            list = new ArrayList<>();
        }

        //updated status selected for new list
        if (selectedIds != null && !selectedIds.isEmpty()) {
            for (FilterMember item : list) {
                if (selectedIds.contains(item.getEkUserId())) {
                    item.setSelected(true);
                } else {
                    item.setSelected(false);
                }
            }
        }
        //
        if (adapterMember == null) {
            adapterMember = new FilterMemberAdapter(list, this, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvFilter.setLayoutManager(layoutManager);
            rvFilter.setItemAnimator(new DefaultItemAnimator());
            rvFilter.setAdapter(adapterMember);
        } else {
            adapterMember.setFilterMembers(list);
            adapterMember.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseGetCropListApi(ArrayList<String> list) {
        hideLoadingDialog();
        if (list == null) {
            list = new ArrayList<>();
        }


    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (filterType.equalsIgnoreCase(FilterActivity.FILTER_PLACE)) {
            getSelectedPlaces();
        } else {
            getSelectedMembers();
        }
        intent.putExtra(Utils.Constant.TIMELINE_FILTER_MODEL, filterModel);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void getSelectedPlaces() {
        if (adapterField != null) {
            ArrayList<FilterField> fieldList = adapterField.getFilterFields();
            if (fieldList != null && !fieldList.isEmpty()) {
                ArrayList<FilterField> selectedPlaces = new ArrayList<>();
                for (FilterField place : fieldList) {
                    if (place.isSelected()) {
                        selectedPlaces.add(place);
                    }
                }
                filterModel.setPlaceList(selectedPlaces);
            }
        }
    }

    private void getSelectedMembers() {
        if (adapterMember != null) {
            ArrayList<FilterMember> memberList = adapterMember.getFilterMembers();
            if (memberList != null && !memberList.isEmpty()) {
                ArrayList<FilterMember> selectedMembers = new ArrayList<>();
                for (FilterMember member : memberList) {
                    if (member.isSelected()) {
                        selectedMembers.add(member);
                    }
                }
                filterModel.setMemberList(selectedMembers);
            }
        }
    }


    @Override
    public void onUnselectedAllCallback() {
        layoutAll.setSelected(false);
        imgFilter.setImageResource(R.drawable.ic_uncheck);
    }

    @Override
    protected void onDestroy() {
        if (adapterMember != null) {
            adapterMember.onDestroyData();
            adapterMember = null;
        }
        if (adapterField != null) {
            adapterField.onDestroyData();
            adapterField = null;
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
