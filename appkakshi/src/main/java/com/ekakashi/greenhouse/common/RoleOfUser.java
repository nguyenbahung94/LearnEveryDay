package com.ekakashi.greenhouse.common;

import android.content.Context;

import com.ekakashi.greenhouse.database.model_server_response.EnumUserRoleOnField;

/**
 * Created by vvhoan on 4/12/2018.
 */

public class RoleOfUser {
    public static class TimeLine{
        public static boolean ReadTimeLine(Context context)
        {
            return true;
        }
        public static boolean PostonTimeLine(Context context)
        {
            boolean flag;
            flag = Prefs.getInstance(context).getUserRoleOnDetailField() != EnumUserRoleOnField.Viewer;
            return flag;
        }
        public static boolean EditPostonTimeline(Context context)
        {
            boolean flag;
            flag = Prefs.getInstance(context).getUserRoleOnDetailField() != EnumUserRoleOnField.Viewer;
            return flag;
        }
        public static boolean DeletePostonTimeline(Context context)
        {
            boolean flag;
            flag = Prefs.getInstance(context).getUserRoleOnDetailField() != EnumUserRoleOnField.Viewer;
            return flag;
        }
    }
    public static class Field{
        public static boolean ViewField(Context context)
        {
            return true;
        }
        public static boolean EditField(Context context)
        {
            boolean flag;
            flag = !(Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Viewer || Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Worker);
            return flag;
        }
        public static boolean DeleteField(Context context)
        {
            boolean flag;
            flag = !(Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Viewer || Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Worker);
            return flag;
        }
    }
    public static class Notifications{
        public static boolean ReceiveNotifications(Context context){
            boolean flag;
            flag = Prefs.getInstance(context).getUserRoleOnDetailField() != EnumUserRoleOnField.Viewer;
            return flag;
        }
    }
    public static class MembersList{
        public static boolean InviteMembers(Context context)
        {
            boolean flag;
            flag = !(Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Viewer || Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Worker);
            return flag;
        }
        public static boolean ChangeAuthorities(Context context)
        {
            boolean flag;
            flag = !(Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Viewer || Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Worker);
            return flag;
        }
        public static boolean DeleteMembers(Context context)
        {
            boolean flag;
            flag = !(Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Viewer || Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Worker);
            return flag;
        }
    }
    public static class Recipe{
        public static boolean EditRecipeList(Context context)
        {
            boolean flag;
            flag = Prefs.getInstance(context).getUserRoleOnDetailField() == EnumUserRoleOnField.Owner;
            return flag;
        }
    }

}
