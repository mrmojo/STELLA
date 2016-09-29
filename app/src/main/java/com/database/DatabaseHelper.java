package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mojo on 9/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ABLE.db";
    public static final String TABLE_NAME = "T_USERS";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "PASSWORD";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_2 + " TEXT," +
            COL_3 + " TEXT" +
            ")";
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public String searchPassword(String email) {
        db = this.getReadableDatabase();
        String query = "SELECT " +
                COL_2 + ", " + COL_3 +
                " FROM " + TABLE_NAME;
        String password = null;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                if (email.equalsIgnoreCase(cursor.getString(0))) {
                    password = cursor.getString(1);
                }
            } while (cursor.moveToNext());
        }

        return password;

    }

    public boolean checkAccountExistence(String email) {
        db = this.getReadableDatabase();

        String searchQuery = "SELECT " +
                COL_2 +
                " FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " = '" + email + "'";

        Cursor cursor = db.rawQuery(searchQuery, null);

        if(cursor.moveToFirst()) {
            //email exist
            return true;
        } else {
            return false;
        }
    }
    public boolean registerAccount(String email, String password) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, email);
        values.put(COL_3, password);

        long returnCode = db.insert(TABLE_NAME, null, values);
        db.close();

        if(returnCode > -1) {
            return true;
        } else {
            return false;
        }

    }

    public Cursor getAllData() {
        db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }
}
