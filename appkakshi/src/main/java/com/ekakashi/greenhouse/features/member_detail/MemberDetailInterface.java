package com.ekakashi.greenhouse.features.member_detail;

import com.ekakashi.greenhouse.database.model_server_request.EditMemberListRequest;

/**
 * Created by nquochuy on 1/29/2018.
 */

public interface MemberDetailInterface {

    interface View {
        void initPresenter();

        void onUpdateSuccess();

        void onUpdateFail(String message);

        void tokenExpired();
    }

    interface Presenter {
        void editMemberList(EditMemberListRequest memberListRequest);
    }
}
