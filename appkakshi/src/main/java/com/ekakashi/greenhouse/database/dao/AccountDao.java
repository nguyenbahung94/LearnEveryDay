package com.ekakashi.greenhouse.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by paduy on 11/15/2017.
 */

public class AccountDao extends BaseDaoImpl<Account, Integer> {
    /**
     * @param connectionSource :
     * @throws SQLException : must use public, if not -> Could not find public constructor with ConnectionSource
     *                      and optional Class parameters class com.ekakashi.greenhouse.database.dao.AccountDao. Missing static on class?
     */
    public AccountDao(ConnectionSource connectionSource) throws SQLException {

        super(connectionSource, Account.class);
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(getConnectionSource(), Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(){
        try {
            return this.queryForAll().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertOrUpdate(Account account) {
        try {
            this.createOrUpdate(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

