package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BaseResponse implements Parcelable {

    @SerializedName("editSuccess")
    @Expose
    protected boolean success;

    @SerializedName("status")
    @Expose
    protected int status;

    @SerializedName("code")
    @Expose
    protected int code;

    @SerializedName("message")
    @Expose
    protected String message;

    @SerializedName("result")
    @Expose
    protected boolean result;

    @SerializedName("error_description")
    @Expose
    protected String errorDescription;

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public BaseResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status);
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeByte(this.result ? (byte) 1 : (byte) 0);
        dest.writeString(this.errorDescription);
        dest.writeString(this.url);
    }

    protected BaseResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.status = in.readInt();
        this.code = in.readInt();
        this.message = in.readString();
        this.result = in.readByte() != 0;
        this.errorDescription = in.readString();
        this.url = in.readString();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };
}
