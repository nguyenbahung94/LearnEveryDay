package com.ekakashi.greenhouse.features.member_list;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.invite_member.InviteMemberActivity;
import com.ekakashi.greenhouse.features.member_detail.MemberDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MemberListActivity extends BaseActivity implements View.OnClickListener, MemberListInterface.View,
        MemberListInterface.MemberListAdapterCallback, MyToolbar.ToolbarListener {

    private RelativeLayout btnInviteMember;
    private Button btnInviteEmpty;
    private LinearLayout layoutMember;
    private RelativeLayout layoutEmpty;
    private RecyclerView rvMember;

    private List<MemberListModel> list = new ArrayList<>();
    private String fieldName;
    private int fieldId;
    private MyToolbar myToolbar;
    private boolean isEditMode = false;

    private MemberListInterface.Presenter mPresenter;
    private static final int INVITE_MEMBER_REQUEST_CODE = 99;
    private static final int MEMBER_DETAIL_REQUEST_CODE = 100;
    public static final String MEMBER_DETAIL = "member_detail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        addControls();
        addEvents();
        initPresenter();
    }

    private void addEvents() {
        btnInviteMember.setOnClickListener(this);
        btnInviteEmpty.setOnClickListener(this);
    }

    private void addControls() {
        btnInviteMember = findViewById(R.id.btnInviteMember);
        btnInviteEmpty = findViewById(R.id.btnInviteEmpty);
        layoutMember = findViewById(R.id.layoutMember);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        rvMember = findViewById(R.id.rvMember);

        Intent intent = getIntent();
        fieldId = intent.getIntExtra(Utils.Constant.EK_FIELDS_ID, -1);
        fieldName = intent.getStringExtra(Utils.Constant.EK_FIELDS_NAME);

        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.member_list), fieldName);
        myToolbar.setToolbarListener(this);
    }

    private void editMember() {
        if (myToolbar.tvToolbarRight.getText().toString().equals(getString(R.string.txt_edit))) {
            isEditMode = true;
            myToolbar.tvToolbarRight.setText(getString(R.string.toolbar_done));
        } else {
            isEditMode = false;
            myToolbar.tvToolbarRight.setText(getString(R.string.txt_edit));
        }
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        MemberListAdapter adapter = new MemberListAdapter(this, list, isEditMode, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvMember.setLayoutManager(layoutManager);
        rvMember.setItemAnimator(new DefaultItemAnimator());
        rvMember.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInviteMember:
                startInviteMemberActivity();
                break;
            case R.id.btnInviteEmpty:
                startInviteMemberActivity();
                break;

            default:
                break;
        }
    }

    @Override
    public void onToolbarLeftListener() {
        onBackPressed();
    }

    @Override
    public void onToolbarRightListener() {
        editMember();
    }

    private void startInviteMemberActivity() {
        Intent intent = new Intent(this, InviteMemberActivity.class);
        intent.putExtra(Utils.Constant.EK_FIELDS_NAME, fieldName);
        intent.putExtra(Utils.Constant.EK_FIELDS_ID, fieldId);
        startActivityForResult(intent, INVITE_MEMBER_REQUEST_CODE);
    }

    @Override
    public void onMemberClick(int position) {
        Intent intent = new Intent(this, MemberDetailActivity.class);
        intent.putExtra(MEMBER_DETAIL, list.get(position));
        intent.putExtra(Utils.Constant.EK_FIELDS_NAME, fieldName);
        startActivityForResult(intent, MEMBER_DETAIL_REQUEST_CODE);
    }

    @Override
    public void initPresenter() {
        mPresenter = new MemberListPresenter(this);
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.getMemberList(fieldId);
    }

    @Override
    public void onMemberListSuccess(List<MemberListModel> listModels) {
        if (listModels != null) {
            list = listModels;
            if (list.size() == 0) {
                updateUIWhenEmptyList();
                setUpRecyclerView();
            } else {
                layoutEmpty.setVisibility(View.GONE);
                layoutMember.setVisibility(View.VISIBLE);
                myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT,
                        getString(isEditMode ? R.string.toolbar_done : R.string.txt_edit));
                setUpRecyclerView();
            }
        }
        hideLoadingDialog();
    }

    private void updateUIWhenEmptyList() {
        layoutEmpty.setVisibility(View.VISIBLE);
        layoutMember.setVisibility(View.GONE);
        isEditMode = false;
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
    }

    @Override
    public void onMemberListFail(String message) {
        list = new ArrayList<>();
        updateUIWhenEmptyList();
        setUpRecyclerView();
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDeleteMember(final int position) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.member_remove)
                .content(R.string.member_list_remove)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (isNetworkOffline()) {
                            return;
                        }
                        showLoadingDialog(getString(R.string.message_please_wait));
                        mPresenter.deleteMemberList(list.get(position).getInvitationId());
                    }
                })
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    @Override
    public void onDeleteSuccess() {
        mPresenter.getMemberList(fieldId);
    }

    @Override
    public void onDeleteFail() {
        hideLoadingDialog();
        Toast.makeText(this, getString(R.string.delete_member_fail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == MEMBER_DETAIL_REQUEST_CODE) {
                if (data.getBooleanExtra("isRefresh", false)) {
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mPresenter.getMemberList(fieldId);
                }
            }
            if (requestCode == INVITE_MEMBER_REQUEST_CODE) {
                if (data.getBooleanExtra("isRefresh", false)) {
                    showLoadingDialog(getString(R.string.message_please_wait));
                    mPresenter.getMemberList(fieldId);
                }
            }
        }
    }
}
