package com.ekakashi.greenhouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.dao.AccountDao;
import com.ekakashi.greenhouse.database.dao.DeviceObject;
import com.ekakashi.greenhouse.database.dao.DeviceObjectDao;
import com.ekakashi.greenhouse.database.dao.RecentSearch;
import com.ekakashi.greenhouse.database.dao.RecentSearchDao;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "ericsson22.db";
    private static final int DATABASE_VERSION = 4;

    private AccountDao mAccountDao;
    private DeviceObjectDao mDeviceObject;
    private RecentSearchDao mSearchDao;

    public DatabaseHelper(Context context  ) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        //connectionSource = new AndroidConnectionSource(this);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Account.class);
            TableUtils.createTableIfNotExists(connectionSource, DeviceObject.class);
            TableUtils.createTableIfNotExists(connectionSource, RecentSearch.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Account.class, true);
            TableUtils.dropTable(connectionSource, DeviceObject.class, true);
            TableUtils.dropTable(connectionSource, RecentSearch.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }

    public synchronized AccountDao getAccountDao() {
        if (mAccountDao == null) {
            try {
                mAccountDao = getDao(Account.class);//DaoManager.createDao(connectionSource, Account.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mAccountDao;
    }

    public synchronized DeviceObjectDao getDeviceObject() {
        if (mDeviceObject == null) {
            try {
                mDeviceObject = getDao(DeviceObject.class);//DaoManager.createDao(connectionSource, DeviceObject.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mDeviceObject;
    }

    public synchronized RecentSearchDao getRecentSearch() {
        if (mSearchDao == null) {
            try {
                mSearchDao = getDao(RecentSearch.class);//DaoManager.createDao(connectionSource, DeviceObject.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mSearchDao;
    }

    @Override
    public void close() {
        super.close();
        mAccountDao = null;
        mDeviceObject = null;
        mSearchDao = null;
    }
}
