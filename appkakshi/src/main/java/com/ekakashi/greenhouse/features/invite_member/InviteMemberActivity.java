package com.ekakashi.greenhouse.features.invite_member;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.InviteMemberRequest;

import java.util.ArrayList;

public class InviteMemberActivity extends BaseActivity implements View.OnClickListener, InviteMemberInterface.SelectedAuthority, InviteMemberInterface.View {

    private RelativeLayout btnAddMember;
    private RecyclerView rvAuthority;
    private ArrayList<View> viewArrayList = new ArrayList<>();
    ArrayList<InviteMemberModel> inviteMemberList = new ArrayList<>();
    private int mAuthority = -1;
    private int fieldId;

    private InviteMemberInterface.Presenter mPresenter;
    private boolean hasFirstEmailValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_member);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        addToolbar();
        addControls();
        addEvents();
        initPresenter();
    }

    private void addToolbar() {
        Intent intent = getIntent();
        fieldId = intent.getIntExtra(Utils.Constant.EK_FIELDS_ID, -1);
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.member_list_invite),
                intent.getStringExtra(Utils.Constant.EK_FIELDS_NAME));
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_send));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                sendInvite();
            }
        });
    }

    private void addControls() {
        rvAuthority = findViewById(R.id.rvAuthority);
        btnAddMember = findViewById(R.id.btnAddMember);

        //Admin default selected
        inviteMemberList.add(new InviteMemberModel(getString(R.string.authority_administrator),
                Utils.Role.ADMIN, ""));
        inviteMemberList.add(new InviteMemberModel(getString(R.string.authority_user),
                Utils.Role.USER, getString(R.string.authority_user_description)));
        inviteMemberList.add(new InviteMemberModel(getString(R.string.authority_worker),
                Utils.Role.WORKER, getString(R.string.authority_worker_description)));

        InviteMemberAdapter adapter = new InviteMemberAdapter(inviteMemberList, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvAuthority.setLayoutManager(layoutManager);
        rvAuthority.setItemAnimator(new DefaultItemAnimator());
        rvAuthority.setAdapter(adapter);

        addMoreLayout();
    }

    private void addEvents() {
        btnAddMember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddMember:
                addMoreLayout();
                break;
            default:
                break;
        }
    }

    private void addMoreLayout() {
        if (viewArrayList.size() > 0) {
            EditText edEmail = viewArrayList.get(viewArrayList.size() - 1).findViewById(R.id.edEmail);
            if (edEmail.getText().toString().isEmpty()) {
                return;
            }
        }

        final ViewGroup main = findViewById(R.id.layoutListInviteMember);
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.layout_add_more_member, null);
        (view.findViewById(R.id.edEmail)).requestFocus();


        viewArrayList.add(view);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(45));
        params.setMargins(0, dp2px(15), 0, 0);
        main.addView(view, params);
    }

    private void sendInvite() {
        if (mAuthority == -1) {
            showSimpleMessage(getString(R.string.invite_member_validate_authority));
            return;
        }

        ArrayList<InviteMemberRequest.UserInviteList> memberList = new ArrayList<>();
        boolean isInvalidEmail = false;
        for (View view : viewArrayList) {
            EditText edEmail = view.findViewById(R.id.edEmail);
            //Check email is not empty
            if (!edEmail.getText().toString().trim().isEmpty()) {
                if (!Utils.isEmailValid(edEmail.getText().toString())) {
                    edEmail.setError(getString(R.string.error_invalid_email));
                    isInvalidEmail = true;
                } else if (!isInvalidEmail) {
                    InviteMemberRequest.UserInviteList member = new InviteMemberRequest.UserInviteList(mAuthority, edEmail.getText().toString());
                    memberList.add(member);
                    hasFirstEmailValid = true;
                }
            } else if (!hasFirstEmailValid) {
                isInvalidEmail = true;
            }
        }
        if (isInvalidEmail) {
            showSimpleMessage(getString(R.string.error_invalid_email));
            return;
        }
        if (isNetworkOffline()) {
            return;
        }
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.sendInvite(new InviteMemberRequest(fieldId, memberList));
    }

    private int dp2px(int dp) {
        float scaleValue = getResources().getDisplayMetrics().density;
        return (int) (dp * scaleValue + 0.5f);
    }

    @Override
    public void onSelectedAuthority(int authority) {
        mAuthority = authority;
    }

    @Override
    public void initPresenter() {
        mPresenter = new InviteMemberPresenter(this);
    }

    @Override
    public void onInviteSuccess() {
        hideLoadingDialog();
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.sent)
                .content(getString(R.string.invite_member_popup))
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.putExtra("isRefresh", true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    @Override
    public void onInviteFail(String message) {
        hideLoadingDialog();
        showSimpleMessage(message);
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }
}
