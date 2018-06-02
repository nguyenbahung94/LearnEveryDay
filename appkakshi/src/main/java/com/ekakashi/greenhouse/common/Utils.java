package com.ekakashi.greenhouse.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.database.model_server_response.ObjectAction;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCondition;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectPrefecture;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectType;
import com.ekakashi.greenhouse.features.login_screen.LoginActivity;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;
import com.ekakashi.greenhouse.map.CustomLatLngObject;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static final String WEATHER_KEY = "67f7bd0c0704eb5b97bfcfaa836e2139";

    public static class Name {
        public static final String LOCALE_EN = "en";
        public static final String LOCALE_JA = "ja";

        public static final int TOOLBAR_LEFT_ACCOUNT = 1;
        public static final int TOOLBAR_LEFT_BACK_BUTTON = 2;
        public static final int TOOLBAR_LEFT_TEXT_CANCEL = 3;
        public static final int TOOLBAR_LEFT_TEXT_CLOSE = 4;

        public static final int TOOLBAR_CENTER_TEXT_ONLY = 1;
        public static final int TOOLBAR_CENTER_TEXT_TOP = 2;
        public static final int TOOLBAR_CENTER_TEXT_BOTTOM = 3;

        public static final int TOOLBAR_RIGHT_BUTTON_ADD = 1;
        public static final int TOOLBAR_RIGHT_BUTTON_INFO = 2;
        public static final int TOOLBAR_RIGHT_TEXT = 3;

        public static final int ADD_RULE_CODE = 100;
        public static final int TYPE_OF_RULE_CODE = 101;
        public static final int TYPE_OF_ACTION_CODE = 102;
        public static final int ADD_ACTION_CODE = 103;
        public static final int ADD_CONDITION_CODE = 104;

        public static final int TIMELINE_LOAD_MORE_RECORD = 10;

        public static final String CREATE_FILED = "createField";
        public static final String EDIT_FILED = "edtfield";
        public static final String EDIT_NAME = "edtname";
        public static final String OBJECT_RESULT = "objectResult";
        public static final String ID_SELECTED = "idselected";
        public static final String STATE = "state";
        public static final String ONE = "1";
        public static final String TWO = "2";
        public static final String REVISION_HISTORY = "revision_history";
        public static final String RECIPE = "recipe";
        public static final String ADD_RULE = "add_rule";
        public static final String TYPE = "type";
        public static final String STAGE = "stage";
        public static final String ADD_STAGE = "add_stage";
        public static final String ACTION = "action";
        public static final String CONDITION = "condition";
        public static final String IS_EDIT_RECIPE = "is_edit_recipe";
        public static final String STAGE_POS = "stage_pos";
        public static final String TYPE_OF_ACTION = "type_of_action";
        public static final String RULE = "rule";
        public static final String FROM_SELECT_STAGE = "from_select_type";

        public static final String DIARY_TYPE = "TYPE";
        public static final String DIARY_POSITION = "ImagePosition";
        public static final String DIARY_LIST_IMAGE = "images_of_diary";
        public static final String DIARY_DETAIL = "diary_detail";
        public static final String DIARY_ITEM = "diary_item";

        public static final String ADVICE_ADVICE = "advice_advice";
        public static final String ADVICE_TITLE = "advice_title";

        public static final String NOTIFICATION_TYPE = "notification_setting";
        public static final String VIEW_DETAIL = "view_detail";

        public static final String NUM_SELECT = "numSelect";
        public static final String VALUE = "value";
        public static final String LIST_OF_POSITION = "listofposition";
        public static final String ZERO = "0";
    }

    public static class Device {
        public static final String TABLE_NAME_LIST_DEVICE = "ListDevice";
        public static final String NAME_DEVICE = "name_device";
        public static final String ID_DEVICE = "id_device";
        public static final String AUTOMATIC = "automatic";
        public static final String DATE_REGISTER = "date_register";
    }

    public static class ErrorCode {
        public static final int SUCCESS_200 = 200;
        public static final int ERROR_401 = 401;
        public static final int ERROR_404 = 404;
        public static final int ERROR_500 = 500;
    }

    public static class Role {
        public static final int ADMIN = 1;
        public static final int WORKER = 2;
        public static final int USER = 3;

    }

    public static class MoveToNextStage {
        public static final int NOT_PROCESS__LAST_STAGE = 1;
        public static final int NOT_PROCESS__NOT_LAST_STAGE = 2;
        public static final int PROCESS__LAST_STAGE = 3;
        public static final int PROCESS__NOT_LAST_STAGE = 4;
        public static final int UNQUALIFIED_COMPLETED_STAGE = 5;
    }

    public static final String INVITATION_STATUS_PENDING = "Pending";

    public static class Constant {
        public static final String EK_FIELDS_ID = "ek_fields_id";
        public static final String EK_FIELDS_NAME = "ek_fields_name";
        public static final String TIMELINE_FILTER_TYPE = "timeline_filter_type";
        public static final String TIMELINE_FILTER_IDS = "timeline_filter_ids";
        public static final String TIMELINE_FILTER_MODEL = "timeline_filter_model";
        public static final String TIMELINE_DETAIL_MODEL = "timeline_detail_model";
        public static final String TIMELINE_POST_EDIT = "timeline_post_edit";
        public static final String TIMELINE_POST_TYPE = "timeline_post_type";
        public static final String TIMELINE_POST_DATA = "timeline_post_data";
        public static final String TIMELINE_EDIT_DATA = "timeline_edit_data";
        public static final String ADD_MORE_RECIPE = "add_more_recipe";

        public static final String SETTING_INTENT = "setting_intent";
        public static final int SETTING_INTENT_FACEBOOK = 1;
        public static final int SETTING_INTENT_POLICY = 2;
        public static final int SETTING_INTENT_TERM = 3;
        public static final int SETTING_INTENT_LICENSE = 4;
        public static final int INTENT_HELP_FORGOT_PASSWORD = 5;
        public static final int SETTING_INTENT_INTRUCTIONS = 6;

        public static final int TIMELINE_TYPE_NOTIFICATION = 1;
        public static final int TIMELINE_TYPE_ADVICE = 2;
        public static final int TIMELINE_TYPE_DIARY = 3;
        public static final int TIMELINE_TYPE_REMOTE_CONTROL = 4;

        public static final String EK_FORMAT_DATE_1 = "%s/%s/%s";
        public static final String EK_FORMAT_DATE_2 = "%s-%s-%s";
        public static final String EK_FORMAT_DATE_3 = "%s.%s.%s";
        public static final int ERROR_401 = 401;

        public static final String EK_SCAN_BAR_CODE = "scan_bar_code";
        public static final String EK_DEVICE_OBJECT = "device_object";
        public static final int EK_DEVICE_REGISTER_ACTIVITY = 100;
        public static final int EK_SCAN_BAR_CODE_ACTIVITY = 101;
        public static final int EDIT_STAGE_CODE = 20;
        public static final int EDIT_RULE_CODE = 29;
        public static final int ADD_STAGE_CODE = 10;
        public static final int ADD_RECIPE = 998;
        public static final int EDIT_BASIC_RECIPE_CODE = 999;
        public static final int EDIT_RECIPE_STAGE_CODE = 98;

        public static final int NOTIFICATION_TYPE_NOTIFICATION = 1;
        public static final int NOTIFICATION_TYPE_ADVICE = 2;
        public static final int NOTIFICATION_TYPE_DIARY = 3;
        public static final int NOTIFICATION_TYPE_REMOTE_CONTROL = 4;
        public static final int NOTIFICATION_TYPE_NEWS = 5;

        public static final int PICK_ONE_IMAGE = 1;
        public static final int PICK_MULTI_IMAGE = 2;
        public static final int PICK_IMAGE_REQUEST_CODE = 13;
        public static final int PICK_IMAGE_DETAIL_REQUEST_CODE = 14;
        public static final String IMAGE_LIST_SELECTED = "image_list_selected";
        public static final String IMAGE_PICK_TYPE = "image_pick_type";
        public static final String IMAGE_FOLDER_POSITION = "image_folder_position";
        public static final String LIST_MODEL_IMAGE = "model_image_list";
    }

    public static class Weather {
        public static final int WEATHER_TEMPERATURE = 1010;
        public static final int HUMIDITY = 1011;
        public static final int WIND_DIRECTION = 1012;
        public static final int WIND_VELOCITY = 1013;
        public static final int PRECIPITATION = 1014;
        public static final int AMOUNT_OF_INSOLATION = 1015;
        public static final int SUNRISE_SUNSET = 1016;
        public static final int THREE_WIDGET = 3;
        public static final int FOUR_WIDGET = 4;
        public static final int TWO_WIDGET = 2;
        public static final int ONE_COLUMN = 1;
        public static final int TWO_COLUMN = 2;
        public static final int ONE_WIDGET = 1;
        public static final int ONE_WIDGET_TWO_COLUMN = 3;
        public static final int ONE_WIDGET_TWO_COLUMN_AND_TWO_WIDGET_ONE_COLUMN = 4;
        public static final int TWO_GRAPH = 5;
        public static final int GRAPH_ABOVE_WEATHER = 6;
        public static final int GRAPH_ABOVE_TWO_WEATHER = 7;


    }

    public static class Register {
        public static final String SETTING_INTENT = "setting_intent";
        public static final String LANG_REGISTER = "LangRegister";
    }

    /**
     * get color
     *
     * @param context the application context
     * @param id      resource id
     * @return int color
     */
    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * This code is got from lib on internet.
     * get upper most of list LatLong
     *
     * @param lsOperation list LatLong
     * @return the upper LatLong
     */
    public static CustomLatLngObject getUpperLatLng(List<CustomLatLngObject> lsOperation) {
        if (lsOperation.isEmpty()) return null;
        CustomLatLngObject topLatLng = lsOperation.get(0);
        for (CustomLatLngObject item : lsOperation) {
            if (item.getY() > topLatLng.getY() || (item.getY() == topLatLng.getY() && item.getX() < topLatLng.getX())) {
                topLatLng = item;
            }
        }
        return topLatLng;
    }

    /**
     * This code is got from a lib on internet.
     * A custom sort function that sorts obj1 and obj2 based on their slope
     * that is formed from the upper most point from the array of points.
     *
     * @param upper : object
     * @param obj1  : object1
     * @param obj2  : object2
     * @return -1,0,1.
     */
    public static int comparePointSort(CustomLatLngObject upper, CustomLatLngObject obj1, CustomLatLngObject obj2) {
        // Exclude the 'upper' point from the sort (which should come first).
        if (obj1 == upper) return -1;
        if (obj2 == upper) return 1;

        // Find the slopes of 'obj1' and 'obj2' when a line is
        // drawn from those points through the 'upper' point.
        double slope1 = upper.getSlope(obj1);
        double slope2 = upper.getSlope(obj2);

        // 'obj1' and 'obj2' are on the same line towards 'upper'.
        if (slope1 == slope2) {
            // The point closest to 'upper' will come first.
            return obj1.getDistance(upper) < obj2.getDistance(upper) ? -1 : 1;
        }

        // If 'obj1' is to the right of 'upper' and 'obj2' is the the left.
        if (slope1 <= 0 && slope2 > 0) return -1;

        // If 'obj1' is to the left of 'upper' and 'obj2' is the the right.
        if (slope1 > 0 && slope2 <= 0) return 1;

        // It seems that both slopes are either positive, or negative.
        return slope1 > slope2 ? -1 : 1;
    }

    public static String getAddressFromLatLong(Context context, Double lattitu, Double longtitu) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lattitu, longtitu, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }
                strAdd = strReturnedAddress.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;

    }

    public static BitmapDescriptor getFromDrawable(Context context, int res) {
        Drawable drawable = context.getResources().getDrawable(res);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    public static Bitmap getBigBitMapSelected() {
        BitmapDrawable bitmapClick = (BitmapDrawable) ContextCompat.getDrawable(App.getsInstance(), R.drawable.iconclicked);
        if (bitmapClick != null) {
            Bitmap bClick = bitmapClick.getBitmap();
            return Bitmap.createScaledBitmap(bClick, (int) App.getsInstance().getResources().getDimension(R.dimen.marker_big), (int) App.getsInstance().getResources().getDimension(R.dimen.marker_big), false);
        }
        return null;
    }

    public static Bitmap getBigBitMap() {
        BitmapDrawable bitmapdraw = (BitmapDrawable) ContextCompat.getDrawable(App.getsInstance(), R.drawable.ic_pin_selected);
        if (bitmapdraw != null) {
            Bitmap b = bitmapdraw.getBitmap();
            return Bitmap.createScaledBitmap(b, (int) App.getsInstance().getResources().getDimension(R.dimen.marker_big), (int) App.getsInstance().getResources().getDimension(R.dimen.marker_big), false);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void hideKeyBroad(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static List<LatLng> parseJson(String polygon) {
        List<LatLng> listLatLong = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(polygon);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Double mlat = object.getDouble("lat");
                Double mlong = object.getDouble("lng");
                listLatLong.add(new LatLng(mlat, mlong));
            }
            return listLatLong;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MaterialDialog createProgressBar(Context context, String message) {
        MaterialDialog mLoadingDialog = new MaterialDialog.Builder(context)
                .content(message)
                .progress(true, 0)
                .cancelable(false)
                .build();
        mLoadingDialog.getProgressBar().getProgressDrawable().setColorFilter(ContextCompat.getColor(context, R.color.bg_green_btn), PorterDuff.Mode.SRC_IN);

        return mLoadingDialog;
    }

    /*public static void showDiaLog(Context context, String title, String message, MaterialDialog.SingleButtonCallback liCallback) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(liCallback).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }*/

    public static void showDiaLogConfirm(Context context, int title, int message, MaterialDialog.SingleButtonCallback liCallback) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(liCallback)
                .negativeText(R.string.cancel).negativeColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    public static void tokenExpired(final Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.token_expired)
                .content(R.string.message_log_in_again)
                .cancelable(false)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        logout(context);
                    }
                }).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    public static void logout(Context context) {
        Prefs.getInstance(context).saveToken("");
        Prefs.getInstance(context).saveUserId(-1);
        Prefs.getInstance(context).saveUUID("");
        App.getDatabaseHelper(context).getAccountDao().deleteAll();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static DatePickerDialog createDatePicker(Context context, int year, int month, int day, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog mDatePickerDialog = null;
        if (Build.VERSION.SDK_INT == 24) {    // Android 7.0 Nougat, API 24
            final Context contextThemeWrapper =
                    new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog);
            try {
                mDatePickerDialog =
                        new FixedHoloDatePickerDialog(contextThemeWrapper, listener, year,
                                month, day);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException |
                    InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            mDatePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, listener,
                    year, month, day);
        }
        return mDatePickerDialog;
    }

    public static TimePickerDialog createTimePicker(Context context, int hour, int minutes, boolean is24Hour, TimePickerDialog.OnTimeSetListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    // Android 7.0 Nougat, API 24
            final Context contextThemeWrapper =
                    new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog);
            return new FixedHoloTimePickerDialog(contextThemeWrapper, listener, hour, minutes, is24Hour);
        } else {
            return new TimePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, listener,
                    hour, minutes, is24Hour);
        }
    }

    public static String getRuleType(Context context, int type) {
        String rule = "";
        switch (type) {
            case EnumRule.MOMENTARY:
                rule = context.getString(R.string.text_rule_1);
                break;
            case EnumRule.AGGREGATION:
                rule = context.getString(R.string.text_rule_2);
                break;
            case EnumRule.CONTROL_DEVICE:
                rule = context.getString(R.string.text_rule_3);
                break;
            default:
                Log.e("RULE TYPE", "There is no rule type supported");
                break;
        }
        return rule;
    }

    public static String getActionType(Context context, int type) {
        String action = "";
        switch (type) {
            case EnumRule.POST_TO_TIMELINE:
                action = context.getString(R.string.text_action_1);
                break;
//            case EnumRule.MOVE_TO_NEXT_STAGE:
//                action = context.getString(R.string.text_action_2);
//                break;
            case EnumRule.MOVE_TO_NEXT_STAGE:
                action = context.getString(R.string.proceed_to_next_stage);
                break;
            case EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE:
                action = context.getString(R.string.text_action_4);
                break;
            default:
                Log.e("RULE TYPE", "There is no action type supported");
                break;
        }
        return action;
    }

    public static List<ObjectType> actionListTypeBaseOnRule(Context context, int ruleType) {
        List<ObjectType> actionList = new ArrayList<>();
        switch (ruleType) {
            case EnumRule.MOMENTARY:
                actionList.add(new ObjectType(EnumRule.POST_TO_TIMELINE, context.getString(R.string.text_action_1), true));
                actionList.add(new ObjectType(EnumRule.MOVE_TO_NEXT_STAGE, context.getString(R.string.text_action_3), false));
                actionList.add(new ObjectType(EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE, context.getString(R.string.text_action_4), false));
                break;
            case EnumRule.AGGREGATION:
                actionList.add(new ObjectType(EnumRule.POST_TO_TIMELINE, context.getString(R.string.text_action_1), true));
                actionList.add(new ObjectType(EnumRule.MOVE_TO_NEXT_STAGE, context.getString(R.string.text_action_3), false));
                actionList.add(new ObjectType(EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE, context.getString(R.string.text_action_4), false));
                break;
            case EnumRule.CONTROL_DEVICE:
                break;
            default:
                break;
        }
        return actionList;
    }

    public static String getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * true if activity is finished. false if user rotate screen.
     *
     * @param activity : current activity.
     * @return false if user rotate screen.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static boolean isFinishActivity(Activity activity) {
        return (activity != null && !activity.isChangingConfigurations());
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Enable or disable 1 view
     * If function setEnable not working, this's save your day :D
     *
     * @param view: the view what enable or disable
     */
    public static void enableView(View view, boolean isEnable) {
        view.setClickable(isEnable);
        view.setAlpha(isEnable ? 1.0f : 0.5f);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.background_toolbar);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    @NonNull
    public static String[] getMeasurementItem(Context context, int ruleType, int measurementDevice) {
        if (measurementDevice == EnumRule.ONE_KM_MESH) {
            if (ruleType == EnumRule.MOMENTARY) {
                return context.getResources().getStringArray(R.array.measurementItemListForMomentary);
            } else {
                return context.getResources().getStringArray(R.array.measurementItemListForAggregation);
            }
        }
        return context.getResources().getStringArray(R.array.measurementItemDeviceList);
    }

    public static String getDeterStandardStringForAggregation(Context context, int countingMethod) {
        String determinationStandard;
        switch (countingMethod) {
            case EnumRule.TOTAL_OR_ACCUMULATION:
                determinationStandard = context.getString(R.string.reached);
                break;
            case EnumRule.DAILY_AVERAGE:
                determinationStandard = context.getString(R.string.above_or_below);
                break;
            case EnumRule.DAYTIME_AVERAGE:
                determinationStandard = context.getString(R.string.above_or_below);
                break;
            case EnumRule.NIGHTLY_AVERAGE:
                determinationStandard = context.getString(R.string.above_or_below);
                break;
            case EnumRule.HIGHEST:
                determinationStandard = context.getString(R.string.above);
                break;
            case EnumRule.LOWEST:
                determinationStandard = context.getString(R.string.below);
                break;
            case EnumRule.DAY_DIFFERENCE:
                determinationStandard = "In the case of";
                break;
            case EnumRule.NUMERICAL_COMPARISON:
                determinationStandard = "Between";
                break;
            default:
                determinationStandard = "";
                break;
        }

        return determinationStandard;
    }

    public static String getDeterminationStandardForMomentary(Context context, int determinationMethod) {
        String determinationStandard;
        switch (determinationMethod) {
            case EnumRule.GREATER_THAN:
                determinationStandard = context.getString(R.string.greater_than_equal_to);
                break;
            case EnumRule.LESS_THAN:
                determinationStandard = context.getString(R.string.less_than_equal_to);
                break;
            default:
                determinationStandard = "";
                break;
        }
        return determinationStandard;
    }

    public static String[] getDeterminationStandardList(Context context) {
        return context.getResources().getStringArray(R.array.determinationStandardList);
    }

    public static String[] getCountingMethod(Context context) {
        return new String[]{context.getString(R.string.total_accumulation)};
//        return new String[]{"Total", "Daily Average", "Daytime Average", "Nightly Average", "Highest", "Lowest",
//                "Day Difference", "Numerical comparison"};
    }


    @NonNull
    public static String createContentFromAddCondition(Context context, int ruleType, ObjectCondition condition) {
        if (ruleType == EnumRule.MOMENTARY) {
            return "When the 1km Mesh having "
                    + condition.getMeasurementItemString() + " "
                    + condition.getDeterminationStandardString().toLowerCase() + " "
                    + condition.getDeterminationValue() + " "
                    + getUnitOfMeasurementItem(context, condition.getMeasurementItemString());
        }
        return "The "
                + condition.getAggregationMethod() + " of 1km Mesh's "
                + condition.getMeasurementItemString() + " "
                + condition.getDeterminationStandardString().toLowerCase() + " "
                + condition.getDeterminationValue() + " "
                + getUnitOfMeasurementItem(context, condition.getMeasurementItemString())
                + ". Begin calculate from "
                + condition.getStageName();
    }

    @NonNull
    public static String createContentFromViewDetailCondition(Context context, int ruleType, ObjectCondition condition, String stageName) {
        String language = Prefs.getInstance(context).getLocale();
        String string = "";
        if (language.equalsIgnoreCase(Name.LOCALE_JA)) {
            if (ruleType == EnumRule.MOMENTARY) {
                string = context.getString(R.string.one_km_mesh) + "の"
                        + getSpecificMeasurementItem(context, ruleType, condition.getMeasurementItem() - 1) + "が、"
                        + condition.getDeterminationValue()
                        + getSpecificUnitOfMeasurementItem(condition.getMeasurementItem() - 1)
                        + getDeterminationStandardForMomentary(context, condition.getDeterminationStandard()) + "の場合";
            } else if (ruleType == EnumRule.AGGREGATION) {
                string = context.getString(R.string.one_km_mesh) + "の"
                        + getSpecificMeasurementItem(context, ruleType, condition.getMeasurementItem() - 1) + "の"
                        + getCountingMethod(context)[0] + "が、"
                        + condition.getDeterminationValue()
                        + getSpecificUnitOfMeasurementItem(condition.getMeasurementItem() - 1)
                        + getDeterStandardStringForAggregation(context, condition.getCountingMethod() - 1).toLowerCase()
                        + stageName + context.getResources().getString(R.string.begin_calculate_from);
            }
        } else {
            if (ruleType == EnumRule.MOMENTARY) {
                string = "When the " + context.getString(R.string.one_km_mesh) + " having "
                        + getSpecificMeasurementItem(context, ruleType, condition.getMeasurementItem() - 1) + " "
                        + getDeterminationStandardForMomentary(context, condition.getDeterminationStandard()) + " "
                        + condition.getDeterminationValue() + " "
                        + getUnitOfMeasurementItem(context, getSpecificMeasurementItem(context, ruleType, condition.getMeasurementItem() - 1));
            } else if (ruleType == EnumRule.AGGREGATION) {
                string = "The " + getCountingMethod(context)[0] + " of " + context.getString(R.string.one_km_mesh) + " 's "
                        + getSpecificMeasurementItem(context, ruleType, condition.getMeasurementItem() - 1) + " "
                        + getDeterStandardStringForAggregation(context, condition.getCountingMethod() - 1).toLowerCase() + " "
                        + condition.getDeterminationValue() + " "
                        + getUnitOfMeasurementItem(context, getSpecificMeasurementItem(context, ruleType, condition.getMeasurementItem() - 1)) + ". "
                        + context.getResources().getString(R.string.begin_calculate_from) + " " + stageName;
            }
        }
        return string;
    }

    public static String getSpecificMeasurementItem(Context context, int ruleType, int deviceValue) {
        String measurementItem = "";
        List<String> list;
        if (ruleType == EnumRule.MOMENTARY) {
            list = Arrays.asList(context.getResources().getStringArray(R.array.measurementItemListForMomentary));
            if (!list.isEmpty()) {
                if (deviceValue < list.size()) {
                    measurementItem = list.get(deviceValue);
                }
            }
        } else if (ruleType == EnumRule.AGGREGATION) {
            list = Arrays.asList(context.getResources().getStringArray(R.array.measurementItemListForAggregation));
            if (!list.isEmpty()) {
                if (deviceValue < list.size()) {
                    measurementItem = list.get(deviceValue);
                }
            }
        } else {
            measurementItem = "";
        }
        return measurementItem;
    }

    public static String getSpecificUnitOfMeasurementItem(int deviceValue) {
        String measurementItem;
        switch (deviceValue) {
            case EnumRule.PRECIPITATION:
                measurementItem = "mm/h";
                break;
            case EnumRule.TEMPERATURE:
                measurementItem = "℃";
                break;
            case EnumRule.WINDSPEED:
                measurementItem = "m/s";
                break;
            case EnumRule.MOISTURE:
                measurementItem = "%";
                break;
            case EnumRule.AMOUNT_OF_SOLAR_RADIATION:
                measurementItem = "W/m2";
                break;
            default:
                measurementItem = "";
                break;
        }
        return measurementItem;
    }

    @NonNull
    public static String getUnitOfMeasurementItem(Context context, String measurementItem) {
        if (measurementItem.equalsIgnoreCase(context.getString(R.string.precipitation))) {
            return "mm/h";
        } else if (measurementItem.equalsIgnoreCase(context.getString(R.string.temperature_action))) {
            return "℃";
        } else if (measurementItem.equalsIgnoreCase(context.getString(R.string.humidity_moisture))) {
            return "%";
        } else if (measurementItem.equalsIgnoreCase(context.getString(R.string.solar_radiation_amount))) {
            return "W/m2";
        } else if (measurementItem.equalsIgnoreCase(context.getString(R.string.wind_speed))) {
            return "m/s";
        } else {
            return "";
        }
    }

    @NonNull
    public static List<ObjectPrefecture> getPrefectureList(Context context) {
        List<String> prefectureENs = Arrays.asList(context.getResources().getStringArray(R.array.prefecturesEN));
        List<String> prefectureJPs = Arrays.asList(context.getResources().getStringArray(R.array.prefecturesJP));
        List<ObjectPrefecture> prefectures = new ArrayList<>();
        int index, size = prefectureENs.size();
        for (index = 0; index < size; index++) {
            prefectures.add(new ObjectPrefecture(prefectureENs.get(index), prefectureJPs.get(index)));
        }
        return prefectures;
    }

    public static CharSequence displayAsHtml(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        }
        return Html.fromHtml(text);
    }

    private static String getNameOfAction(Context context, int actionType) {
        String actionName;
        switch (actionType) {
            case EnumRule.POST_TO_TIMELINE:
                actionName = context.getResources().getString(R.string.post_to_time_line);
                break;
            case EnumRule.MOVE_TO_NEXT_STAGE:
                actionName = context.getString(R.string.proceed_to_next_stage);
                break;
            case EnumRule.GIVE_CULTIVATION_MANAGEMENT_ADVICE:
                actionName = context.getResources().getString(R.string.give_cultivation_management_advice);
                break;
            default:
                actionName = "";
                break;
        }
        return actionName;
    }

    public static String createRuleName(Context context, ObjectRecipe recipe, int ruleType, List<ObjectCondition> conditions, List<ObjectAction> actions) {
        String ruleName = "";
        if (conditions != null && !conditions.isEmpty()) {
            if (ruleType == EnumRule.MOMENTARY) {
                if (conditions.size() == 1) {
                    ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), "");
                } else if (conditions.size() == 2) {
                    if (conditions.get(1).isLogicalOperator()) {//TRUE: AND // FALSE: OR
                        ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), "")
                                + "\n" + context.getString(R.string.and)
                                + "\n" + createContentFromViewDetailCondition(context, ruleType, conditions.get(1), "");
                    } else {
                        ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), "")
                                + "\n" + context.getString(R.string.or)
                                + "\n" + createContentFromViewDetailCondition(context, ruleType, conditions.get(1), "");
                    }
                }
            } else if (ruleType == EnumRule.AGGREGATION) {
                if (conditions.size() == 1) {
                    if (conditions.get(0).getStage() != null && conditions.get(0).getStage().getName() != null && !conditions.get(0).getStage().getName().isEmpty()) {
                        ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), conditions.get(0).getStage().getName());
                    } else {
                        int additionInformation = conditions.get(0).getAdditionalInformation();
                        if (recipe.getStages() != null && !recipe.getStages().isEmpty()) {
                            for (ObjectGrowth stage : recipe.getStages()) {
                                if (stage.getId() == additionInformation) {
                                    if (stage.getName() != null && !stage.getName().isEmpty()) {
                                        ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), stage.getName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else if (conditions.size() == 2) {
                    String stageName1 = "", stageName2 = "";
                    if (conditions.get(0).getStage() != null
                            && conditions.get(0).getStage().getName() != null
                            && !conditions.get(0).getStage().getName().isEmpty()) {
                        stageName1 = conditions.get(0).getStage().getName();
                    } else {
                        int additionInformation = conditions.get(0).getAdditionalInformation();
                        if (recipe != null && recipe.getStages() != null && !recipe.getStages().isEmpty()) {
                            for (ObjectGrowth stage : recipe.getStages()) {
                                if (stage.getId() == additionInformation) {
                                    if (stage.getName() != null && !stage.getName().isEmpty()) {
                                        stageName1 = stage.getName();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (conditions.get(1).getStage() != null
                            && conditions.get(1).getStage().getName() != null
                            && !conditions.get(1).getStage().getName().isEmpty()) {
                        stageName2 = conditions.get(1).getStage().getName();
                    } else {
                        int additionInformation = conditions.get(1).getAdditionalInformation();
                        if (recipe != null && recipe.getStages() != null && !recipe.getStages().isEmpty()) {
                            for (ObjectGrowth stage : recipe.getStages()) {
                                if (stage.getId() == additionInformation) {
                                    if (stage.getName() != null && !stage.getName().isEmpty()) {
                                        stageName2 = stage.getName();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (conditions.get(1).isLogicalOperator()) {//TRUE: AND // FALSE: OR
                        ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), stageName1)
                                + "\n" + context.getString(R.string.and)
                                + "\n" + createContentFromViewDetailCondition(context, ruleType, conditions.get(1), stageName2);
                    } else {
                        ruleName = createContentFromViewDetailCondition(context, ruleType, conditions.get(0), stageName1)
                                + "\n" + context.getString(R.string.or)
                                + "\n" + createContentFromViewDetailCondition(context, ruleType, conditions.get(1), stageName2);
                    }
                }
            }

            if (actions != null && !actions.isEmpty()) {
                for (ObjectAction action : actions) {
                    ruleName = ruleName.concat("\n" + Utils.getNameOfAction(context, action.getActionType()));
                }
            }
        }
        return ruleName;
    }
}
