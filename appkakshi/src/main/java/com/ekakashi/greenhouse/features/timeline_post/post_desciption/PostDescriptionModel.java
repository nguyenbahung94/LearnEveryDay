package com.ekakashi.greenhouse.features.timeline_post.post_desciption;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.database.model_server_response.FertilizerResponse;
import com.ekakashi.greenhouse.database.model_server_response.WorkTypeResponse;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;

import java.util.ArrayList;

public class PostDescriptionModel implements Parcelable {

    private ArrayList<FilterField> workplaceList;
    private ArrayList<PostDescriptionItem> diseaseList;
    private ArrayList<WorkTypeResponse.WorkType> workTypeList;
    private ArrayList<PostDescriptionItem> targetCropList;
    private ArrayList<PostDescriptionItem> pesticideList;
    private ArrayList<FertilizerResponse.Fertilizer> fertilizerList;

    private int workPlaceID;

    public int getWorkPlaceID() {
        return workPlaceID;
    }

    public void setWorkPlaceID(int workPlaceID) {
        this.workPlaceID = workPlaceID;
    }

    public ArrayList<FilterField> getWorkplaceList() {
        return workplaceList;
    }

    public void setWorkplaceList(ArrayList<FilterField> workplaceList) {
        this.workplaceList = workplaceList;
    }

    public ArrayList<PostDescriptionItem> getDiseaseList() {
        return diseaseList;
    }

    public void setDiseaseList(ArrayList<PostDescriptionItem> diseaseList) {
        this.diseaseList = diseaseList;
    }

    public ArrayList<WorkTypeResponse.WorkType> getWorkTypeList() {
        return workTypeList;
    }

    public void setWorkTypeList(ArrayList<WorkTypeResponse.WorkType> workTypeList) {
        this.workTypeList = workTypeList;
    }

    public ArrayList<PostDescriptionItem> getTargetCropList() {
        return targetCropList;
    }

    public void setTargetCropList(ArrayList<PostDescriptionItem> targetCropList) {
        this.targetCropList = targetCropList;
    }

    public ArrayList<PostDescriptionItem> getPesticideList() {
        return pesticideList;
    }

    public void setPesticideList(ArrayList<PostDescriptionItem> pesticideList) {
        this.pesticideList = pesticideList;
    }

    public ArrayList<FertilizerResponse.Fertilizer> getFertilizerList() {
        return fertilizerList;
    }

    public void setFertilizerList(ArrayList<FertilizerResponse.Fertilizer> fertilizerList) {
        this.fertilizerList = fertilizerList;
    }

    public PostDescriptionModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.workplaceList);
        dest.writeTypedList(this.diseaseList);
        dest.writeTypedList(this.workTypeList);
        dest.writeTypedList(this.targetCropList);
        dest.writeTypedList(this.pesticideList);
        dest.writeTypedList(this.fertilizerList);
        dest.writeInt(this.workPlaceID);
    }

    protected PostDescriptionModel(Parcel in) {
        this.workplaceList = in.createTypedArrayList(FilterField.CREATOR);
        this.diseaseList = in.createTypedArrayList(PostDescriptionItem.CREATOR);
        this.workTypeList = in.createTypedArrayList(WorkTypeResponse.WorkType.CREATOR);
        this.targetCropList = in.createTypedArrayList(PostDescriptionItem.CREATOR);
        this.pesticideList = in.createTypedArrayList(PostDescriptionItem.CREATOR);
        this.fertilizerList = in.createTypedArrayList(FertilizerResponse.Fertilizer.CREATOR);
        this.workPlaceID = in.readInt();
    }

    public static final Creator<PostDescriptionModel> CREATOR = new Creator<PostDescriptionModel>() {
        @Override
        public PostDescriptionModel createFromParcel(Parcel source) {
            return new PostDescriptionModel(source);
        }

        @Override
        public PostDescriptionModel[] newArray(int size) {
            return new PostDescriptionModel[size];
        }
    };
}
