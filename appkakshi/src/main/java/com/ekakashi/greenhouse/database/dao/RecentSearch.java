package com.ekakashi.greenhouse.database.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by nquochuy on 1/15/2018.
 */

@DatabaseTable(tableName = "RecentSearch", daoClass = RecentSearchDao.class)
public class RecentSearch implements Parcelable {
    public static final String USER_ID =  "user_id";
    public static final String KEY_SEARCH = "key_search";

    @DatabaseField(id = true, columnName = "id", unique = true)
    private String id;
    @DatabaseField(columnName = USER_ID)
    private int userId;
    @DatabaseField(columnName = KEY_SEARCH)
    private String keySearch;

    public RecentSearch() {
    }

    public RecentSearch(int userId, String keySearch) {
        this.userId = userId;
        this.keySearch = keySearch;
        this.id = userId + keySearch;
    }

    public int getUserId() {
        return userId;
    }

    public String getKeySearch() {
        return keySearch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.keySearch);
    }

    protected RecentSearch(Parcel in) {
        this.id = in.readString();
        this.userId = in.readInt();
        this.keySearch = in.readString();
    }

    public static final Creator<RecentSearch> CREATOR = new Creator<RecentSearch>() {
        @Override
        public RecentSearch createFromParcel(Parcel source) {
            return new RecentSearch(source);
        }

        @Override
        public RecentSearch[] newArray(int size) {
            return new RecentSearch[size];
        }
    };
}
