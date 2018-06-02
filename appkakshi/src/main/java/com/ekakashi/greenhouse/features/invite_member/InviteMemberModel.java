package com.ekakashi.greenhouse.features.invite_member;

/**
 * Created by nquochuy on 1/24/2018.
 */

public class InviteMemberModel {
    private String roleName;
    private String description;
    private boolean isSelected;
    private int authority;


    public InviteMemberModel(String roleName, int authority, String description) {
        this.roleName = roleName;
        this.authority = authority;
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }
}
