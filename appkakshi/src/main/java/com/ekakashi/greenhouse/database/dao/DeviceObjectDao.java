package com.ekakashi.greenhouse.database.dao;

import com.ekakashi.greenhouse.common.Utils;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nbhung on 12/20/2017.
 */

public class DeviceObjectDao extends BaseDaoImpl<DeviceObject, Integer> {
    public DeviceObjectDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, DeviceObject.class);
    }

    public int addDevice(DeviceObject deviceObject) throws SQLException {
        return this.create(deviceObject);
    }

    public int updateDevice(DeviceObject deviceObject) throws SQLException {
        return this.update(deviceObject);
    }

    public int deleteDevice(DeviceObject deviceObjec) throws SQLException {
        return this.delete(deviceObjec);
    }

    public DeviceObject getByIdDevice(String id) throws SQLException {
        QueryBuilder<DeviceObject, Integer> qb = this.queryBuilder();
        qb.where().eq(Utils.Device.ID_DEVICE, id);
        PreparedQuery<DeviceObject> pq = qb.prepare();
        return this.queryForFirst(pq);
    }

    public List<DeviceObject> getAll() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
