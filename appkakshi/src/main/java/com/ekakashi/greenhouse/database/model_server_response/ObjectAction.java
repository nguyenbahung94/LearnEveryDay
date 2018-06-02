package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 12/26/2017.
 */

public class ObjectAction implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String name;
    @SerializedName("sortNo")
    private int sortNo;
    @SerializedName("actionType")
    private int actionType;
    @SerializedName("url")
    private String url;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    private ObjectActionContent objectActionContent;

    public ObjectAction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectActionContent getObjectActionContent() {
        return objectActionContent;
    }

    public void setObjectActionContent(ObjectActionContent objectActionContent) {
        this.objectActionContent = objectActionContent;
    }

    public static class ObjectActionContent implements Parcelable {
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("url")
        private String url;
        @Expose
        @SerializedName("images")
        private String image;
        @Expose
        @SerializedName("content")
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.url);
            dest.writeString(this.image);
            dest.writeString(this.content);
        }

        public ObjectActionContent() {
        }

        protected ObjectActionContent(Parcel in) {
            this.title = in.readString();
            this.url = in.readString();
            this.image = in.readString();
            this.content = in.readString();
        }

        public static final Creator<ObjectActionContent> CREATOR = new Creator<ObjectActionContent>() {
            @Override
            public ObjectActionContent createFromParcel(Parcel source) {
                return new ObjectActionContent(source);
            }

            @Override
            public ObjectActionContent[] newArray(int size) {
                return new ObjectActionContent[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.image);
        dest.writeString(this.name);
        dest.writeInt(this.sortNo);
        dest.writeInt(this.actionType);
        dest.writeString(this.url);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeParcelable(this.objectActionContent, flags);
    }

    protected ObjectAction(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
        this.name = in.readString();
        this.sortNo = in.readInt();
        this.actionType = in.readInt();
        this.url = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.objectActionContent = in.readParcelable(ObjectActionContent.class.getClassLoader());
    }

    public static final Creator<ObjectAction> CREATOR = new Creator<ObjectAction>() {
        @Override
        public ObjectAction createFromParcel(Parcel source) {
            return new ObjectAction(source);
        }

        @Override
        public ObjectAction[] newArray(int size) {
            return new ObjectAction[size];
        }
    };
}
