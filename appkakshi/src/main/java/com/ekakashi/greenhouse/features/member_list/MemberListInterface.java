package com.ekakashi.greenhouse.features.member_list;

import java.util.List;

public interface MemberListInterface {

    interface View {
        void initPresenter();

        void onMemberListSuccess(List<MemberListModel> listModels);

        void onMemberListFail(String message);

        void onDeleteSuccess();

        void onDeleteFail();

        void tokenExpired();
    }

    interface Presenter {
        void getMemberList(int fieldId);

        void deleteMemberList(int invitationId);
    }

    interface MemberListAdapterCallback {
        void onDeleteMember(int position);

        void onMemberClick(int position);
    }
}
