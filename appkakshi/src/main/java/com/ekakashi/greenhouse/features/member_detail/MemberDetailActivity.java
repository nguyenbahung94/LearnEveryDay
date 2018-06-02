package com.ekakashi.greenhouse.features.member_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.EditMemberListRequest;
import com.ekakashi.greenhouse.features.invite_member.InviteMemberAdapter;
import com.ekakashi.greenhouse.features.invite_member.InviteMemberInterface;
import com.ekakashi.greenhouse.features.invite_member.InviteMemberModel;
import com.ekakashi.greenhouse.features.member_list.MemberListActivity;
import com.ekakashi.greenhouse.features.member_list.MemberListModel;

import java.util.ArrayList;

public class MemberDetailActivity extends BaseActivity implements MemberDetailInterface.View, InviteMemberInterface.SelectedAuthority {

    private RecyclerView rvAuthority;
    private TextView tvEmail;
    private ImageView imgAvatar;
    private ArrayList<InviteMemberModel> inviteMemberList = new ArrayList<>();
    private MyToolbar myToolbar;

    private MemberListModel member;
    private int mAuthority;
    private MemberDetailInterface.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        initPresenter();
        addControls();
        getIntentData();
        setUpRecyclerView();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        member = intent.getParcelableExtra(MemberListActivity.MEMBER_DETAIL);
        tvEmail.setText(member.getEmail());
        mAuthority = member.getAuthority();
        Glide.with(this).load(member.getUrlImage()).error(R.drawable.ic_user_default).into(imgAvatar);

        //priority is: Nickname > Username > Email
        String name = !TextUtils.isEmpty(member.getNickName()) ? member.getNickName() :
                (!TextUtils.isEmpty(member.getUserName()) ? member.getUserName() : member.getEmail());
        addToolbar(name, intent.getStringExtra(Utils.Constant.EK_FIELDS_NAME));
    }

    private void addControls() {
        imgAvatar = findViewById(R.id.imgAvatar);
        tvEmail = findViewById(R.id.tvEmail);
        rvAuthority = findViewById(R.id.rvAuthority);
    }


    private void setUpRecyclerView() {
        inviteMemberList.add(new InviteMemberModel(getString(R.string.authority_administrator),
                Utils.Role.ADMIN, ""));
        inviteMemberList.add(new InviteMemberModel(getString(R.string.authority_user), Utils.Role.USER,
                getString(R.string.authority_user_description)));
        inviteMemberList.add(new InviteMemberModel(getString(R.string.authority_worker), Utils.Role.WORKER,
                getString(R.string.authority_worker_description)));
        inviteMemberList.get(member.authority).setSelected(true);

        InviteMemberAdapter adapter = new InviteMemberAdapter(inviteMemberList, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvAuthority.setLayoutManager(layoutManager);
        rvAuthority.setItemAnimator(new DefaultItemAnimator());
        rvAuthority.setAdapter(adapter);
    }

    private void addToolbar(String member, String field) {
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, member, field);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                editMemberAuthority();
            }
        });
    }

    @Override
    public void onSelectedAuthority(int authority) {
        mAuthority = authority;
    }

    private void editMemberAuthority() {
        if (mPresenter != null) {
            mPresenter.editMemberList(new EditMemberListRequest(mAuthority, member.getInvitationId(),
                    member.getNickName(), member.getUrlImage()));
        }
    }

    @Override
    public void initPresenter() {
        mPresenter = new MemberDetailPresenter(this);
    }

    @Override
    public void onUpdateSuccess() {
        hideLoadingDialog();
        Intent intent = new Intent();
        intent.putExtra("isRefresh", true);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    public void onUpdateFail(String message) {
        hideLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }
}
