package com.ekakashi.greenhouse.database.dao;

import android.text.TextUtils;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nquochuy on 1/15/2018.
 */

public class RecentSearchDao extends BaseDaoImpl<RecentSearch, Integer> {

    private String KEY_SEARCH = "key_search";
    private String USER_ID = "user_id";
    private String RECENT_SEARCH = "RecentSearch";

    public RecentSearchDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RecentSearch.class);
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(getConnectionSource(), RecentSearch.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RecentSearch> getAll() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RecentSearch> getTextSearchByUserIdAndLikeKeySearch(int userId, String keySearch) {
        try {
            // get our query builder from the DAO
            QueryBuilder<RecentSearch, Integer> queryBuilder = this.queryBuilder();

            if (TextUtils.isEmpty(keySearch)) {
                queryBuilder.where()
                        .eq(RecentSearch.USER_ID, userId);
            } else {
                queryBuilder.where()
                        .eq(RecentSearch.USER_ID, userId)
                        .and()
                        .like(RecentSearch.KEY_SEARCH, "%" + keySearch + "%");
            }
            queryBuilder.orderBy(RecentSearch.KEY_SEARCH, true);
            // prepare our sql statement
            PreparedQuery<RecentSearch> preparedQuery = queryBuilder.prepare();
            // query for all accounts that have "qwerty" as a password
            return this.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<RecentSearch> getSearchByUser(int user_id) {
        // get our query builder from the DAO
        QueryBuilder<RecentSearch, Integer> queryBuilder = this.queryBuilder();
        // the 'password' field must be equal to "qwerty"
        try {
            queryBuilder.where().eq(USER_ID, user_id);
            // prepare our sql statement
            PreparedQuery<RecentSearch> preparedQuery = queryBuilder.prepare();
            // query for all accounts that have "qwerty" as a password
            return this.query(preparedQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
