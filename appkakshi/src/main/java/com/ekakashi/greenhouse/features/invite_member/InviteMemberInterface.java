package com.ekakashi.greenhouse.features.invite_member;

import com.ekakashi.greenhouse.database.model_server_request.InviteMemberRequest;

/**
 * Created by nquochuy on 1/25/2018.
 */

public interface InviteMemberInterface {

    interface View {
        void initPresenter();

        void onInviteSuccess();

        void onInviteFail(String message);

        void tokenExpired();
    }

    interface Presenter {
        void sendInvite(InviteMemberRequest request);
    }

    interface SelectedAuthority {
        void onSelectedAuthority(int authority);
    }
}
