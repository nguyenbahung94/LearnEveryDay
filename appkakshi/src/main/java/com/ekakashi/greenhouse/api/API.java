package com.ekakashi.greenhouse.api;

import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.dao.DeviceObject;
import com.ekakashi.greenhouse.database.model_server_request.AddFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.AddRecipeForFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.DeviceDetailRequest;
import com.ekakashi.greenhouse.database.model_server_request.DeviceTokenRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditFieldRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditMemberListRequest;
import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
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
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMemberModel;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by paduy on 11/8/2017.
 */

public interface API {
    /**
     * Login api
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/token")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType, @Field("client_id") String clientID);

    @GET("oauth/revoke-token")
    Call<Void> logout();

    /**
     * Refresh token api
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/token")
    Call<LoginResponse> refreshToken(@Field("grant_type") String grantType, @Field("client_id") String clientID, @Field("refresh_token") String refresh_token);

    /**
     * reset password api
     */
//    @FormUrlEncoded
    @POST("api/v2/user/reset_password")
    Call<Void> resetPassword(@Body ResetPassRequest username);

    /**
     * get user account
     */
//    @FormUrlEncoded
    @GET("api/v2/account/account")
    Call<Account> getUserAccount();

    /**
     * update user account
     */
//    @Headers({"Content-Type: */*", "Accept: application/json"})
    @Multipart
    @POST("api/v2/account/account/update")
    Call<Void> updateUserAccount(@Part("fullName") RequestBody fullName,
                                 @Part("email") RequestBody email, @Part("nickName") RequestBody nickName, @Part("file\"; filename=\"avatar.png\" ") RequestBody file);

    /**
     * update image
     */
    @Multipart
    @POST("api/fileUpload")
    Call<BaseResponse> updateAvatar(@Part MultipartBody.Part file);

    /**
     * get recipe detail
     */
    @GET("detail/{id}")
    Call<ObjectRecipe> getRecipe(@Path("id") int id);

    /**
     * get recipe default list
     */
    @GET("list")
    Call<SearchRecipeResponse> getRecipeList();

    /**
     * get fields
     */
    // @GET("example/getFields")
/*    @POST("list")
    Call<List<ResponseGetFields>> getListFields(@Header("Authorization") String authorization,@Body GetListFieldRequest getListFieldRequest);*/
    @GET("list?currentPage=1&recordPerPage=100")
    Call<ResponseGetFields> getListFields();

    /*
     * get detail a field
     * */
    @GET("detail/{id}")
    Call<ResponseDetailField> getDetailField(@Path("id") int id);

    /**
     * add field
     * *
     */
    @POST("add")
    Call<ResponseAddField> addFiled(@Body AddFieldRequest addFieldRequest);

    /*
     * edit field
     * */
    @POST("edit/{id}")
    Call<ResponseEditField> editField(@Path("id") String id, @Body EditFieldRequest editFieldRequest);

    /*remove field
     * */
    @GET("remove/{id}")
    Call<ResponseRemoveField> removeField(@Path("id") int id);

    /**
     * get timeline
     */
    @POST("api/v2/timeline/getTimelinelist")
    Call<TimelineResponse> getTimeline(@Body TimelineRequest apiModel);

    /**
     * get weather
     */
    @GET("data/2.5/weather")
    Call<ObjectWeatherResponse> getWeather(@Query("lat") String lat, @Query("lon") String longt, @Query("units") String units, @Query("appid") String key);

    @POST("remove/{id}")
    Call<BaseResponse> deleteRecipe(@Path("id") int id);

    @POST("clone/{id}")
    Call<ObjectRecipe> cloneRecipe(@Path("id") int recipeId);

    @POST("clone/{id}")
    Call<ObjectRecipe> cloneRecipeForFiled(@Path("id") String id, @Body AddFieldRequest.Recipe recipe);

    /**
     * get filter
     */
    @GET("api/v2/timeline/getEkfieldList")
    Call<ArrayList<FilterField>> getFilterPlace();

    /**
     * get filter
     */
    @POST("api/v2/timeline/getMemberList")
    Call<ArrayList<FilterMember>> getFilterMember(@Body FilterMemberModel model);

    /**
     * get work type
     */
    @GET("api/v2/work_type/list/{id}")
    Call<WorkTypeResponse> getWorkType(@Path("id") int id);

    /**
     * get work type
     */
    @GET("api/v2/ekfield/crop/{id}")
    Call<ArrayList<String>> getTargetCrop(@Path("id") int id);

    /**
     * get pesticide
     */
    @GET("api/v2/pesticide")
    Call<ArrayList<String>> getPesticide();

    /**
     * get fertilizer
     */
    @GET("api/v2/fertilizer")
    Call<FertilizerResponse> getFertilizer();

    /**
     * get disease
     */
    @GET("api/v2/disease")
    Call<ArrayList<String>> getDisease();

    /**
     * create timeline diary
     */
    @Multipart
    @POST("api/v2/diary/create")
    Call<Void> createDiary(@Part("workDescription") RequestBody workDescription, @Part("workPlace") RequestBody workPlace,
                           @Part("workTypeId") Integer workTypeId, @Part("workTypeName") RequestBody workTypeName, @Part("crop") RequestBody crop,
                           @Part("diseaseType") RequestBody diseaseType, @Part("pesticideType") RequestBody pesticideType,
                           @Part("fertilizerId") Integer fertilizerId, @Part("fertilizerType") RequestBody fertilizerType,
                           @Part("nitro") RequestBody nitro, @Part("photpho") RequestBody photpho, @Part("kali") RequestBody kali, @Part("issuedDate") RequestBody issuedDate,
                           @Part MultipartBody.Part image1, @Part MultipartBody.Part image2, @Part MultipartBody.Part image3);


    @POST("api/v2/diary/delete")
    Call<Void> deleteDiary(@Body DiaryID diaryId);

    //    @POST("recipe/edit/{id}")
    @POST("edit/{id}")
    Call<ObjectRecipe> editRecipeById(@Path("id") int id, @Body EditRecipeRequest recipe);

    @Multipart
    @POST("api/v2/diary/edit")
    Call<Void> updateDiary(@Part("timelineId") RequestBody diaryId, @Part("workDescription") RequestBody workDescription,
                           @Part("workPlace") RequestBody workPlace, @Part("workTypeId") Integer workTypeId, @Part("workTypeName") RequestBody workTypeName,
                           @Part("crop") RequestBody crop, @Part("diseaseType") RequestBody diseaseType, @Part("pesticideType") RequestBody pesticideType,
                           @Part("fertilizerId") Integer fertilizerId, @Part("fertilizerType") RequestBody fertilizerType,
                           @Part("nitro") RequestBody nitro, @Part("photpho") RequestBody photpho, @Part("kali") RequestBody kali, @Part("issuedDate") RequestBody issuedDate,
                           @Part("imageList") RequestBody imageList, @Part MultipartBody.Part image1, @Part MultipartBody.Part image2, @Part MultipartBody.Part image3);

    @GET("filterData")
    Call<ObjectFilterRecipe> filterData();

    @GET("api/v2/notification/get")
    Call<SystemNews> getNotification(@Query("currentPage") int currentPage, @Query("recordPerPage") int recordPerPage);

    @Multipart
    @POST("upload")
    Call<BaseResponse> updateRecipePicture(@Part MultipartBody.Part file);

    @GET("sensornode/detail/{id}")
    Call<DeviceDetaildResponse> getDeviceDetail(@Path("id") String id);

    @POST("sensornode/edit")
    Call<DeviceDetaildResponse> editDeviceDetail(@Body DeviceDetailRequest deviceDetailRequest);

    @POST("api/v2/manage/user/list")
    Call<ArrayList<MemberListModel>> getMemberList(@Body MemberListRequest memberListRequest);

    @POST("api/v2/manage/user/edit")
    Call<Void> editMemberList(@Body EditMemberListRequest memberListRequest);

    @POST("api/v2/manage/user/remove")
    Call<Void> deleteMemberList(@Body EditMemberListRequest memberListRequest);

    @POST("api/v2/manage/user/invite")
    Call<Void> inviteMemberList(@Body InviteMemberRequest request);

    @GET("sensornodes/{id}")
    Call<List<DeviceObject>> getDeviceList(@Path("id") int id);

    @GET("sensornode/detail/{id}")
    Call<DeviceObject> getSensorNodeDetails(@Path("id") int id);

    @POST("sensornode/add")
    Call<Void> registerDevice(@Body DeviceObject sensor);

    @GET("recipeCategories")
    Call<CategoryResponse> getCategories();

    @GET("list")
    Call<SearchRecipeResponse> getMoreRecipes(@Query("currentPage") int currentPage, @Query("recordPerPage") int recordPerPage);

    @POST("api/v2/user/token")
    Call<Void> sendRegistrationToServer(@Body DeviceTokenRequest request);

    @GET("api/v2/notification/setting/get")
    Call<NotificationSettingReponse> getNotificationSetting();

    @POST("api/v2/notification/setting")
    Call<Void> postNotificationSetting(@Body NotificationSettingRequest settingRequest);

    @FormUrlEncoded
    @POST("field/currentStage")
    Call<ChangeStageRecipeResponse> changeStageRecipe(@Field("ekfieldId") int ekfieldId, @Field("recipeId") int recipeId, @Field("growingStageId") int growingStageId);

    @POST("add/recipe")
    Call<EditRecipeForFieldResponse> addRecipeForField(@Body List<AddRecipeForFieldRequest> recipeRequest);

    @POST("remove/recipe")
    Call<EditRecipeForFieldResponse> removeRecipeForField(@Body RemoveRecipeForFieldRequest recipeRequest);

    @POST("searchRecipe")
    Call<SearchRecipeResponse> searchRecipe(@Body SearchRecipeRequest recipeRequest, @Query("currentPage") int currentPage, @Query("recordPerPage") int recordPerPage);

    @GET("api/v2/recipe/{id}/getTemplateList")
    Call<TemplateRecipe> getListTemplateRecipe(@Path("id") String id);

    @GET("api/v2/graph/getDefaultTemplate")
    Call<TemplateRecipe> getListTemplateDefault();

    @POST("api/v2/recipe/templateConfig/updateSortNumber")
    Call<ObjectResponseUpdateSorNoTemplate> updateSortNoTemplate(@Body ObjectRequestUpdateSortNoTemplate sortNoTemplate);

    @GET("recipeCategoryCrop/{id}")
    Call<List<ObjectCrop>> getCropsByCateId(@Path("id") int categoryId);

    @POST("api/v2/publish_system_news/count")
    Call<SystemNewsCountResponse> countSystemNews(@Body SystemNewsCountRequest request);

    @POST("api/v2/publish_system_news/update")
    Call<Void> updateSystemNews(@Body SystemNewsCountRequest request);

    @FormUrlEncoded
    @POST("api/v2/recipe/{id}/getDataForWidgetGraph")
    Call<ObjectDataForWidgetGraph> getDataForWidgetGraph(@Path("id") String id, @Field("dateTime") String datatime);
}
