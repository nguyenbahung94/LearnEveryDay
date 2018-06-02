package com.tma.stockmarket.ui.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tma.stockmarket.ui.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nbhung on 8/2/2017.
 */

public class DBmanager {
    private final static String DATA_NAME = "Login.sqlite";
    private static final String SQL_SQUERRY_LOGIN = "SELECT *FROM login";
    private Context mContext;
    private SQLiteDatabase msSqLiteDatabase;

    @Inject
    public DBmanager(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        coppyDataBase();
    }

    private void coppyDataBase() {
        String outFileName = mContext.getApplicationInfo().dataDir + "/databases/" + DATA_NAME;
        File f = new File(outFileName);
        if (!f.exists()) {
            try {
                InputStream input = mContext.getAssets().open(DATA_NAME);
                File folder = new File(mContext.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream output = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while (((length = input.read(buffer)) > 0)) {
                    output.write(buffer, 0, length);
                }
                Log.e("success", "success");
                output.flush();
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openDB() {
        if (msSqLiteDatabase == null || !msSqLiteDatabase.isOpen()) {
            msSqLiteDatabase = SQLiteDatabase.openDatabase(mContext.getApplicationInfo().dataDir + "/databases/" + DATA_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        }
    }

    private void closeDB() {
        if (msSqLiteDatabase != null && msSqLiteDatabase.isOpen()) {
            msSqLiteDatabase.close();
        }
    }

    public List<String> getListIdUser() {
        openDB();
        Cursor c = msSqLiteDatabase.rawQuery(SQL_SQUERRY_LOGIN, null);
        if (c == null) {
            return null;
        }
        int intId = c.getColumnIndex("id");
        int intName = c.getColumnIndex("name");
        int intPassword = c.getColumnIndex("password");
        int intSex = c.getColumnIndex("sex");
        int intAnh = c.getColumnIndex("anh");
        String id, name, password;
        int sex;
        List<String> nameUser = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            id = c.getString(intId);
            nameUser.add(id);
            c.moveToNext();
        }
        Log.e("user::::", nameUser.toString());
        c.close();

        closeDB();
        return nameUser;
    }

    public Boolean CheckUser(String id) {
        openDB();
        Cursor c = msSqLiteDatabase.rawQuery("SELECT * FROM login WHERE id=?", new String[]{id});
        if (c == null) {
            return false;
        }
        c.moveToFirst();
        boolean exit = (c.getCount() > 0);
        c.close();
        closeDB();
        return exit;
    }

    public User getAccountUser(String id) {
        if (CheckUser(id)) {
            openDB();
            Cursor c = msSqLiteDatabase.rawQuery("SELECT * FROM login WHERE id=?", new String[]{id});
            if (c == null) {
                return null;
            }
            c.moveToFirst();
            String iduser = c.getString(c.getColumnIndex("id"));
            String pass = c.getString(c.getColumnIndex("password"));
            String name = c.getString(c.getColumnIndex("name"));
            byte[] image = c.getBlob(c.getColumnIndex("image"));
            int phone = c.getInt(c.getColumnIndex("phone"));
            int sex = c.getInt(c.getColumnIndex("sex"));
            User userData = new User(id, name, pass, sex, image, String.valueOf(phone));
            Log.e("userData", userData.toString());
            closeDB();
            c.close();
            return userData;


        } else {
            return null;
        }
    }

    public void deleteUser(String id) {
        openDB();

//        msSqLiteDatabase.delete("login", "id=?", new String[]{"id"});
        closeDB();
    }

    public boolean addUser(String[] colums, String[] dataColums, byte[] image) {
        openDB();
        ContentValues values = new ContentValues();
        values.put(colums[0], dataColums[0]);
        values.put(colums[1], dataColums[1]);
        values.put(colums[2], dataColums[2]);
        values.put(colums[3], dataColums[3]);
        values.put(colums[4], dataColums[4]);
        values.put("image", image);

        long result = msSqLiteDatabase.insert("login", null, values);
        closeDB();
        return result > -1;
    }

    public boolean updateUser(String[] colums, String[] columsvalue, String whereClause, String[] whereArgs, byte[] image) {
        openDB();
        ContentValues values = new ContentValues();
        values.put(colums[0], columsvalue[0]);
        values.put(colums[1], columsvalue[1]);
        values.put(colums[2], columsvalue[2]);
        values.put(colums[3], columsvalue[3]);
        values.put(colums[4], columsvalue[4]);
        values.put("image", image);
        long result = msSqLiteDatabase.update("login", values, whereClause, whereArgs);

        closeDB();
        return result > 0;
    }
}
