package com.ekakashi.greenhouse.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.BuildConfig;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.dao.DeviceObject;
import com.ekakashi.greenhouse.database.model_server_request.AddFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.AddRecipeForFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.DeviceDetailRequest;
import com.ekakashi.greenhouse.database.model_server_request.DeviceTokenRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditMemberListRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_request.GetListFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.InviteMemberRequest;
import com.ekakashi.greenhouse.database.model_server_request.MemberListRequest;
import com.ekakashi.greenhouse.database.model_server_request.NotificationSettingRequest;
import com.ekakashi.greenhouse.database.model_server_request.RemoveRecipeForFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.ResetPassRequest;
import com.ekakashi.greenhouse.database.model_server_request.SearchRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_request.SystemNewsCountRequest;
import com.ekakashi.greenhouse.database.model_server_request.TimelineRequest;
import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;
import com.ekakashi.greenhouse.database.model_server_response.CategoryResponse;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.DeviceDetaildResponse;
import com.ekakashi.greenhouse.database.model_server_response.EditRecipeForFieldResponse;
import com.ekakashi.greenhouse.database.model_server_response.FertilizerResponse;
import com.ekakashi.greenhouse.database.model_server_response.LoginResponse;
import com.ekakashi.greenhouse.database.model_server_response.NotificationSettingReponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCrop;
import com.ekakashi.greenhouse.database.model_server_response.ObjectFilterRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRequestUpdateSortNoTemplate;
import com.ekakashi.greenhouse.database.model_server_response.ObjectResponseUpdateSorNoTemplate;
import com.ekakashi.greenhouse.database.model_server_response.ObjectWeatherResponse;
import com.ekakashi.greenhouse.database.model_server_response.ResponseAddField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseEditField;
import com.ekakashi.greenhouse.database.model_server_response.ResponseGetFields;
import com.ekakashi.greenhouse.database.model_server_response.ResponseRemoveField;
import com.ekakashi.greenhouse.database.model_server_response.SearchRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.SystemNewsCountResponse;
import com.ekakashi.greenhouse.database.model_server_response.WorkTypeResponse;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;
import com.ekakashi.greenhouse.features.member_list.MemberListModel;
import com.ekakashi.greenhouse.features.system_news.SystemNews;
import com.ekakashi.greenhouse.features.timeline.models.DiaryID;
import com.ekakashi.greenhouse.features.timeline.models.TimelineHelper;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIManager {

    private static final String API_WEATHER_URL = "http://api.openweathermap.org/";

    private static final String API_BASE_URL = BuildConfig.API_BASE_URL;
    private static final String API_BASE_URL_SERVER_FIELD = BuildConfig.API_BASE_URL_SERVER_FIELD;
    private static final String API_BASE_URL_SERVER_RECIPE = BuildConfig.API_BASE_URL_SERVER_RECIPE;
    private static final String API_BASE_URL_GET_TEMPLATE_RECIPE = BuildConfig.API_BASE_URL_GET_TEMPLATE_RECIPE;

    private static OkHttpClient client;
    private static OkHttpClient clientHeader;
    private static OkHttpClient clientTemplate;
    private static Retrofit mRetrofit;
    private static Retrofit mRetrofitGetWeather;
    private static Retrofit mRetrofitFields;
    private static Retrofit mRetrofitWeather;
    private static Retrofit mRetrofitRecipe;
    private static Retrofit mRetrofitTemplateRecipe;
    private static Gson mGson = new GsonBuilder().setLenient().create();
    private static Gson mGsonRecipe = new GsonBuilder().setLenient().create();

    private static OkHttpClient getHttpClientBuilder() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .header("Accept", "*/*")
                                    .header("Content-Type", "application/json")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
        }
        return client;
    }

    private static OkHttpClient getHttpClientBuilderHeader() {
        if (clientHeader == null) {
            clientHeader = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new HttpInterceptor())
                    .build();
        }
        return clientHeader;
    }

    private static OkHttpClient getHttpClientBuilGetTemPlate() {
        if (clientTemplate == null) {
            clientTemplate = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                            String token = Prefs.getInstance(App.getsInstance()).getToken();
                            Request request = chain.request().newBuilder()
                                    //  .header("Accept", "*/*")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
        }
        return clientTemplate;
    }

    public static API getApiWeather() {
        if (mRetrofitGetWeather == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create(mGson));
            mRetrofitGetWeather = builder.client(getHttpClientBuilder()).build();
        }
        return mRetrofitGetWeather.create(API.class);
    }

    public static API getApiWeatherTemplate() {
        if (mRetrofitWeather == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_GET_TEMPLATE_RECIPE)
                    .addConverterFactory(GsonConverterFactory.create(mGson));
            mRetrofitWeather = builder.client(getHttpClientBuilder()).build();
        }
        return mRetrofitWeather.create(API.class);
    }


    public static API getAPI() {
        if (mRetrofit == null) {
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(mGson));
            mRetrofit = builder.client(getHttpClientBuilderHeader()).build();
        }
        return mRetrofit.create(API.class);
    }

    public static API getAPIField() {
        if (mRetrofitFields == null) {
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL_SERVER_FIELD)
                            .addConverterFactory(GsonConverterFactory.create(mGson));
            mRetrofitFields = builder.client(getHttpClientBuilderHeader()).build();
        }
        return mRetrofitFields.create(API.class);
    }

    public static API getAPIRecipe() {
        if (mRetrofitRecipe == null) {
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL_SERVER_RECIPE)
                            .addConverterFactory(GsonConverterFactory.create(mGsonRecipe));
            mRetrofitRecipe = builder.client(getHttpClientBuilderHeader()).build();
        }
        return mRetrofitRecipe.create(API.class);
    }


    public static API getAPITemplateRecipe() {
        if (mRetrofitTemplateRecipe == null) {
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(mGsonRecipe));
            mRetrofitTemplateRecipe = builder.client(getHttpClientBuilderHeader()).build();
        }
        return mRetrofitTemplateRecipe.create(API.class);
    }

    public static Call<ObjectWeatherResponse> getWeather(String lat, String longt, String units, Callback<ObjectWeatherResponse> listener) {
        Call<ObjectWeatherResponse> call = getApiWeather().getWeather(lat, longt, units, Utils.WEATHER_KEY);
        call.enqueue(listener);
        return call;
    }

    public static Call<LoginResponse> login(String email, String password, Callback<LoginResponse> listener) {
        Call<LoginResponse> call = getAPI().login(email, password, "password", "ekakashi");
        call.enqueue(listener);
        return call;
    }

    public static Call<Void> logout(Callback<Void> listener){
        Call<Void> call = getAPI().logout();
        call.enqueue(listener);
        return call;
    }

    public static Call<Void> resetPassword(String email, Callback<Void> listener) {
        Call<Void> call = getAPI().resetPassword(new ResetPassRequest(email));
        call.enqueue(listener);
        return call;
    }

    public static void updateUserAccount(String fullName, String email, String nickName, File fileURL, Callback<Void> listener) {
        RequestBody requestFile = null;
        if (fileURL != null) {
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileURL);
        }
        RequestBody full = RequestBody.create(MediaType.parse("text/plain"), fullName);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody nick = RequestBody.create(MediaType.parse("text/plain"), nickName);
        Call<Void> call = getAPI().updateUserAccount(full, emailBody, nick, requestFile);
        call.enqueue(listener);
    }

    public static void getUserAccount( Callback<Account> listener) {
        Call<Account> call = getAPI().getUserAccount();
        call.enqueue(listener);
    }

    public static Call<BaseResponse> updateImage(MultipartBody.Part mFile, Callback<BaseResponse> listener) {
        Call<BaseResponse> call = getAPI().updateAvatar(mFile);
        call.enqueue(listener);
        return call;
    }

    public static void getTimeline(Context context, Integer returnMaxNum, FilterModel filterModel, Callback<TimelineResponse> listener) {
        TimelineRequest timelineRequest = new TimelineRequest();
        timelineRequest.setFromDay(DateTimeNow.getMinimumOrMaximumTimeFromDateString(filterModel.getFromDay(),
                context.getString(R.string.format_date_time), false));
        timelineRequest.setToDay(DateTimeNow.getMinimumOrMaximumTimeFromDateString(filterModel.getToDay(),
                context.getString(R.string.format_date_time), true));
        timelineRequest.setMemberList(TimelineHelper.getMemberListId(filterModel.getMemberList()));
        timelineRequest.setPlaceList(TimelineHelper.getPlaceListId(filterModel.getPlaceList()));
        timelineRequest.setTimelineType(filterModel.getTypeList());
        timelineRequest.setSearchValue(filterModel.getSearchText());
        timelineRequest.setStartPosition(filterModel.getStartPosition());
        timelineRequest.setReturnMaxNum(returnMaxNum);

        Call<TimelineResponse> call = getAPI().getTimeline(timelineRequest);
        call.enqueue(listener);
    }

    public static Call<ObjectRecipe> getRecipeById(int id, Callback<ObjectRecipe> listener) {
        Call<ObjectRecipe> call = getAPIRecipe().getRecipe(id);
        call.enqueue(listener);
        return call;
    }

    public static Call<ResponseGetFields> getListFields(Callback<ResponseGetFields> listener, GetListFieldRequest getListFieldRequest) {
        Call<ResponseGetFields> call = getAPIField().getListFields();
        //  Call<List<ResponseGetFields>> call = getAPIField().getListFields(authorization, getListFieldRequest);
        call.enqueue(listener);
        return call;
    }

    public static Call<SearchRecipeResponse> getRecipeList(Callback<SearchRecipeResponse> listener) {
        Call<SearchRecipeResponse> call = getAPIRecipe().getRecipeList();
        call.enqueue(listener);
        return call;
    }


    public static Call<ResponseDetailField> getDetailField(Callback<ResponseDetailField> listener, int id) {
        Call<ResponseDetailField> call = getAPIField().getDetailField(id);
        call.enqueue(listener);
        return call;
    }

    public static Call<ResponseAddField> addField(Callback<ResponseAddField> listener, AddFieldRequest addFieldRequest) {
        Call<ResponseAddField> call = getAPIField().addFiled(addFieldRequest);
        call.enqueue(listener);
        return call;
    }

    public static Call<ResponseEditField> editField(Callback<ResponseEditField> listener, EditFieldRequest editFieldRequest) {
        Call<ResponseEditField> call = getAPIField().editField(String.valueOf(editFieldRequest.getUserId()), editFieldRequest);
        call.enqueue(listener);
        return call;
    }

    public static Call<ResponseRemoveField> removeField(Callback<ResponseRemoveField> listener, int id) {
        Call<ResponseRemoveField> call = getAPIField().removeField(id);
        call.enqueue(listener);
        return call;
    }

    public static void getFilterPlace(Callback<ArrayList<FilterField>> listener) {
        Call<ArrayList<FilterField>> call = getAPI().getFilterPlace();
        call.enqueue(listener);
    }

    public static void getFilterMember(Callback<ArrayList<FilterMember>> listener) {
        Call<ArrayList<FilterMember>> call = getAPI().getFilterMember(null);
        call.enqueue(listener);
    }

    public static Call<BaseResponse> deleteRecipe(int id, Callback<BaseResponse> listener) {
        Call<BaseResponse> call = getAPIRecipe().deleteRecipe(id);
        call.enqueue(listener);
        return call;
    }

    public static Call<ObjectRecipe> cloneRecipe(int recipeId, Callback<ObjectRecipe> listener) {
        Call<ObjectRecipe> call = getAPIRecipe().cloneRecipe(recipeId);
        call.enqueue(listener);
        return call;
    }

    public static Call<ObjectRecipe> cloneRecipeForField(String id, Callback<ObjectRecipe> listener, AddFieldRequest.Recipe recipe) {
        Call<ObjectRecipe> call = getAPIRecipe().cloneRecipeForFiled(id, recipe);
        call.enqueue(listener);
        return call;
    }

    public static void getWorkType(int workPlaceId, Callback<WorkTypeResponse> listener) {
        Call<WorkTypeResponse> call = getAPI().getWorkType(workPlaceId);
        call.enqueue(listener);
    }

    public static void getTargetCrop(int workPlaceId, Callback<ArrayList<String>> listener) {
        Call<ArrayList<String>> call = getAPI().getTargetCrop(workPlaceId);
        call.enqueue(listener);
    }

    public static void getPesticide(Callback<ArrayList<String>> listener) {
        Call<ArrayList<String>> call = getAPI().getPesticide();
        call.enqueue(listener);
    }

    public static void getFertilizer(Callback<FertilizerResponse> listener) {
        Call<FertilizerResponse> call = getAPI().getFertilizer();
        call.enqueue(listener);
    }

    public static void getDisease(Callback<ArrayList<String>> listener) {
        Call<ArrayList<String>> call = getAPI().getDisease();
        call.enqueue(listener);
    }

    public static void createDiary(String workDescription, int workPlace,
                                   Integer worktypeId, String worktypeName, String crop, String diseaseType,
                                   String pesticideType, Integer fertilizerId, String fertilizerType, String N, String P, String K,
                                   String issuedDate, String imageUrl1, String imageUrl2, String imageUrl3, Callback<Void> listener) {
        RequestBody work = RequestBody.create(MediaType.parse("text/plain"), workDescription);
        RequestBody place = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(workPlace));
        RequestBody disease = RequestBody.create(MediaType.parse("text/plain"), diseaseType);
        RequestBody pesticide = RequestBody.create(MediaType.parse("text/plain"), pesticideType);
        RequestBody workTypeName = RequestBody.create(MediaType.parse("text/plain"), worktypeName);
        RequestBody targetCrop = RequestBody.create(MediaType.parse("text/plain"), crop);
        RequestBody fertilizer = RequestBody.create(MediaType.parse("text/plain"), fertilizerType);
        RequestBody nitro = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(N));
        RequestBody phospho = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(P));
        RequestBody kali = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(K));
        RequestBody date = null;
        if (issuedDate != null) {
            date = RequestBody.create(MediaType.parse("text/plain"), issuedDate);
        }

        MultipartBody.Part image1 = null;
        if (imageUrl1 != null && !imageUrl1.isEmpty()) {
            File file = new File(imageUrl1);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image1 = MultipartBody.Part.createFormData("imageOne", file.getName(), requestFile);
        }

        MultipartBody.Part image2 = null;
        if (imageUrl2 != null && !imageUrl2.isEmpty()) {
            File file = new File(imageUrl2);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image2 = MultipartBody.Part.createFormData("imageTwo", file.getName(), requestFile);
        }

        MultipartBody.Part image3 = null;
        if (imageUrl3 != null && !imageUrl3.isEmpty()) {
            File file = new File(imageUrl3);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image3 = MultipartBody.Part.createFormData("imageThree", file.getName(), requestFile);
        }
        Call<Void> call = getAPI().createDiary(work, place, worktypeId, workTypeName, targetCrop, disease, pesticide, fertilizerId, fertilizer, nitro, phospho, kali, date, image1, image2, image3);
        call.enqueue(listener);
    }

    public static void deleteDiary(String timelineId, Callback<Void> listener) {
        Call<Void> call = getAPI().deleteDiary(new DiaryID(timelineId));
        call.enqueue(listener);
    }

    public static Call<ObjectRecipe> editRecipeById(int id, EditRecipeRequest recipe, Callback<ObjectRecipe> listener) {
        Call<ObjectRecipe> call = getAPIRecipe().editRecipeById(id, recipe);
        call.enqueue(listener);
        return call;
    }

    public static void updateDiary(int diaryId, String workDescription, Integer worktypeId, String worktypeName, String crop,
                                   int workPlace, String diseaseType, String pesticideType, Integer fertilizerId, String fertilizerType,
                                   String N, String P, String K, String issuedDate, String imageList, String imageUrl1, String imageUrl2,
                                   String imageUrl3, Callback<Void> listener) {
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(diaryId));
        RequestBody work = RequestBody.create(MediaType.parse("text/plain"), workDescription);
        RequestBody place = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(workPlace));
        RequestBody disease = RequestBody.create(MediaType.parse("text/plain"), diseaseType);
        RequestBody pesticide = RequestBody.create(MediaType.parse("text/plain"), pesticideType);
        RequestBody fertilizer = RequestBody.create(MediaType.parse("text/plain"), fertilizerType);
        RequestBody nitro = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(N));
        RequestBody phospho = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(P));
        RequestBody kali = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(K));
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), issuedDate);
        RequestBody images = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(imageList));

        RequestBody workTypeName = RequestBody.create(MediaType.parse("text/plain"), worktypeName);
        RequestBody targetCrop = RequestBody.create(MediaType.parse("text/plain"), crop);

        MultipartBody.Part image1 = null;
        if (imageUrl1 != null && !imageUrl1.isEmpty()) {
            File file = new File(imageUrl1);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image1 = MultipartBody.Part.createFormData("imageOne", file.getName(), requestFile);
        }

        MultipartBody.Part image2 = null;
        if (imageUrl2 != null && !imageUrl2.isEmpty()) {
            File file = new File(imageUrl2);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image2 = MultipartBody.Part.createFormData("imageTwo", file.getName(), requestFile);
        }

        MultipartBody.Part image3 = null;
        if (imageUrl3 != null && !imageUrl3.isEmpty()) {
            File file = new File(imageUrl3);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image3 = MultipartBody.Part.createFormData("imageThree", file.getName(), requestFile);
        }

        Call<Void> call = getAPI().updateDiary(id, work, place, worktypeId, workTypeName, targetCrop, disease, pesticide, fertilizerId, fertilizer, nitro, phospho, kali, date, images, image1, image2, image3);
        call.enqueue(listener);
    }

    public static Call<ObjectFilterRecipe> filterData(Callback<ObjectFilterRecipe> listener) {
        Call<ObjectFilterRecipe> call = getAPIRecipe().filterData();
        call.enqueue(listener);
        return call;
    }

    public static void getNotification(int currentPage, int recordPerPage, Callback<SystemNews> listener) {
        Call<SystemNews> call = getAPI().getNotification(currentPage, recordPerPage);
        call.enqueue(listener);
    }

    public static Call<BaseResponse> updateRecipePicture(MultipartBody.Part file, Callback<BaseResponse> listener) {
        Call<BaseResponse> call = getAPIRecipe().updateRecipePicture(file);
        call.enqueue(listener);
        return call;
    }

    public static Call<DeviceDetaildResponse> getDeviceDetail(String id, Callback<DeviceDetaildResponse> listener) {
        Call<DeviceDetaildResponse> call = getAPIField().getDeviceDetail(id);
        call.enqueue(listener);
        return call;
    }

    public static Call<DeviceDetaildResponse> editDeviceDetail(DeviceDetailRequest deviceDetailRequest, Callback<DeviceDetaildResponse> listener) {
        Call<DeviceDetaildResponse> call = getAPIField().editDeviceDetail(deviceDetailRequest);
        call.enqueue(listener);
        return call;
    }

    public static void getMemberList(int fieldId, Callback<ArrayList<MemberListModel>> listener) {
        Call<ArrayList<MemberListModel>> call = getAPI().getMemberList(new MemberListRequest(fieldId));
        call.enqueue(listener);
    }

    public static void editMemberList(EditMemberListRequest memberListRequest, Callback<Void> listener) {
        Call<Void> call = getAPI().editMemberList(memberListRequest);
        call.enqueue(listener);
    }

    public static void deleteMemberList(EditMemberListRequest memberListRequest, Callback<Void> listener) {
        Call<Void> call = getAPI().deleteMemberList(memberListRequest);
        call.enqueue(listener);
    }

    public static void inviteMemberList(InviteMemberRequest request, Callback<Void> listener) {
        Call<Void> call = getAPI().inviteMemberList(request);
        call.enqueue(listener);
    }

    public static void getDevice(int id, Callback<List<DeviceObject>> listener) {
        Call<List<DeviceObject>> call = getAPIField().getDeviceList(id);
        call.enqueue(listener);
    }

    public static void getSensorDetails(int id, Callback<DeviceObject> listener) {
        Call<DeviceObject> call = getAPIField().getSensorNodeDetails(id);
        call.enqueue(listener);
    }

    public static void registerDevice(DeviceObject sensor, Callback<Void> listener) {
        Call<Void> call = getAPIField().registerDevice(sensor);
        call.enqueue(listener);
    }

    public static Call<CategoryResponse> getCategories(Callback<CategoryResponse> listener) {
        Call<CategoryResponse> call = getAPIRecipe().getCategories();
        call.enqueue(listener);
        return call;
    }

    public static Call<SearchRecipeResponse> getMoreRecipes(int currentPage, int recordPerPage, Callback<SearchRecipeResponse> listener) {
        Call<SearchRecipeResponse> call = getAPIRecipe().getMoreRecipes(currentPage, recordPerPage);
        call.enqueue(listener);
        return call;
    }

    public static void sendRegistrationToServer(String deviceToken, Callback<Void> listener) {
        Call<Void> call = getAPI().sendRegistrationToServer(new DeviceTokenRequest(deviceToken));
        call.enqueue(listener);
    }

    public static void getNotificationSetting(Callback<NotificationSettingReponse> listener) {
        Call<NotificationSettingReponse> call = getAPI().getNotificationSetting();
        call.enqueue(listener);
    }

    public static void postNotificationSetting(NotificationSettingRequest settingRequest, Callback<Void> listener) {
        Call<Void> call = getAPI().postNotificationSetting(settingRequest);
        call.enqueue(listener);
    }

    public static void changeStageRecipe(int ekFieldId, int recipeId, int growingStageId, Callback<ChangeStageRecipeResponse> listener) {
        Call<ChangeStageRecipeResponse> call = getAPIField().changeStageRecipe(ekFieldId, recipeId, growingStageId);
        call.enqueue(listener);
    }

    public static void addRecipeForField(int ekFieldId, int recipeId, int currentStageId, Callback<EditRecipeForFieldResponse> listener) {
        AddRecipeForFieldRequest.CurrentStage currentStage = new AddRecipeForFieldRequest.CurrentStage(currentStageId);
        AddRecipeForFieldRequest.Ekfield ekField = new AddRecipeForFieldRequest.Ekfield(ekFieldId);
        AddRecipeForFieldRequest.Recipe recipe = new AddRecipeForFieldRequest.Recipe(recipeId);
        AddRecipeForFieldRequest request = new AddRecipeForFieldRequest(ekField, recipe, currentStage);
        List<AddRecipeForFieldRequest> requestList = new ArrayList<>();
        requestList.add(request);
//        String json = new Gson().toJson(requestList);

        Call<EditRecipeForFieldResponse> call = getAPIField().addRecipeForField(requestList);
        call.enqueue(listener);
    }

    public static void removeRecipeForField(int ekFieldId, int recipeId, int currentRecipeStageId, Callback<EditRecipeForFieldResponse> listener) {
        RemoveRecipeForFieldRequest request = new RemoveRecipeForFieldRequest(ekFieldId, recipeId, currentRecipeStageId);
        Call<EditRecipeForFieldResponse> call = getAPIField().removeRecipeForField(request);
        call.enqueue(listener);
    }

    public static void searchRecipe(SearchRecipeRequest request, int currentPage, int recordPerPage, Callback<SearchRecipeResponse> listener) {
        Call<SearchRecipeResponse> call = getAPIRecipe().searchRecipe(request, currentPage, recordPerPage);
        call.enqueue(listener);
    }

    public static void getListTemplateRecipe(String id, Callback<TemplateRecipe> listener) {
        Call<TemplateRecipe> call = getAPITemplateRecipe().getListTemplateRecipe(id);
        call.enqueue(listener);
    }

    public static void getListTemplateDefault(Callback<TemplateRecipe> listener) {
        Call<TemplateRecipe> call = getAPITemplateRecipe().getListTemplateDefault();
        call.enqueue(listener);
    }

    public static void updateSortTemplate(ObjectRequestUpdateSortNoTemplate objectRequestUpdateSortNoTemplate, Callback<ObjectResponseUpdateSorNoTemplate> listener) {
        Call<ObjectResponseUpdateSorNoTemplate> call = getAPITemplateRecipe().updateSortNoTemplate(objectRequestUpdateSortNoTemplate);
        call.enqueue(listener);
    }

    public static void getCropsByCateId(int categoryId, Callback<List<ObjectCrop>> listener) {
        Call<List<ObjectCrop>> call = getAPIRecipe().getCropsByCateId(categoryId);
        call.enqueue(listener);
    }

    public static void countSystemNews(int userId, Callback<SystemNewsCountResponse> listener) {
        Call<SystemNewsCountResponse> call = getAPI().countSystemNews(new SystemNewsCountRequest(userId));
        call.enqueue(listener);
    }

    public static void updateSystemNews(int userId, Callback<Void> listener) {
        Call<Void> call = getAPI().updateSystemNews(new SystemNewsCountRequest(userId));
        call.enqueue(listener);
    }

    public static void getDataForWidgetGraph(String id, String dateTime, Callback<ObjectDataForWidgetGraph> listener) {
        Call<ObjectDataForWidgetGraph> call = getApiWeatherTemplate().getDataForWidgetGraph(id, dateTime);
        call.enqueue(listener);
    }
}
