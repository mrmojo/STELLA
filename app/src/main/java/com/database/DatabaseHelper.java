package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.billmastervr.Bill;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mojo on 9/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ABLE.db";
    public static final String USERS_TABLE_NAME = "T_USERS";
    public static final String USERS_COL_1 = "ID";
    public static final String USERS_COL_2 = "EMAIL";
    public static final String USERS_COL_3 = "PASSWORD";
    public static final String USERS_TABLE_CREATE = "CREATE TABLE " + USERS_TABLE_NAME + " (" +
            USERS_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USERS_COL_2 + " TEXT," +
            USERS_COL_3 + " TEXT" +
            ")";

    public static final String TXN_TABLE_NAME = "T_TRANSACTIONS";
    public static final String TXN_COL_1 = "ID";
    public static final String TXN_COL_2 = "TYPE";
    public static final String TXN_COL_3 = "BRANCH";
    public static final String TXN_TABLE_CREATE = "CREATE TABLE " + TXN_TABLE_NAME + " (" +
            TXN_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TXN_COL_2 + " TEXT," +
            TXN_COL_3 + " TEXT" +
            ")";

    public static final String BRANCH_TABLE_NAME = "T_BRANCHES";
    public static final String BRANCH_COL_1 = "ID";
    public static final String BRANCH_COL_2 = "Y_LATITUDE";
    public static final String BRANCH_COL_3 = "X_LONGITUDE";
    public static final String BRANCH_COL_4 = "NAME";
    public static final String BRANCH_COL_5 = "WEIGHT";
    public static final String BRANCH_TABLE_CREATE = "CREATE TABLE " + BRANCH_TABLE_NAME + " (" +
            BRANCH_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BRANCH_COL_2 + " REAL," +
            BRANCH_COL_3 + " REAL," +
            BRANCH_COL_4 + " TEXT," +
            BRANCH_COL_5 + " INT" +
            ")";

    public static final String BILL_TABLE_NAME = "T_BILLS";
    public static final String BILL_COL_1 = "ID";
    public static final String BILL_COL_2 = "MERCHANT";
    public static final String BILL_COL_3 = "AMOUNT";
    public static final String BILL_COL_4 = "MONTH";
    public static final String BILL_COL_5 = "STATUS";
    public static final String BILL_TABLE_CREATE = "CREATE TABLE " + BILL_TABLE_NAME + " (" +
            BILL_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BILL_COL_2 + " TEXT," +
            BILL_COL_3 + " REAL," +
            BILL_COL_4 + " TEXT," +
            BILL_COL_5 + " TEXT" +
            ")";

    SQLiteDatabase db;
    SQLiteDatabase newConn;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(TXN_TABLE_CREATE);
        db.execSQL(BRANCH_TABLE_CREATE);
        db.execSQL(BILL_TABLE_CREATE);
        // Insert initial values if table is empty
        if(isTableEmpty(db, BRANCH_TABLE_NAME, BRANCH_COL_1)) {
            insertInitialMarkers(db);
        }

        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + USERS_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TXN_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + BRANCH_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + BILL_TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public String searchPassword(String email) {
        db = this.getReadableDatabase();
        String query = "SELECT " +
                USERS_COL_2 + ", " + USERS_COL_3 +
                " FROM " + USERS_TABLE_NAME;
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
                USERS_COL_2 +
                " FROM " + USERS_TABLE_NAME +
                " WHERE " + USERS_COL_2 + " = '" + email + "'";

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
        values.put(USERS_COL_2, email);
        values.put(USERS_COL_3, password);

        long returnCode = db.insert(USERS_TABLE_NAME, null, values);

        if(returnCode > -1) {
            return true;
        } else {
            return false;
        }

    }
    // INSERT for Merchants
    public boolean addMerchant(Bill billToInsert)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BILL_COL_2, billToInsert.getBillMerchant());
        values.put(BILL_COL_3, billToInsert.getBillAmount());
        values.put(BILL_COL_4, billToInsert.getBillMonth());
        if(billToInsert.getBillStatus())
        {
            values.put(BILL_COL_5, "Y");
        }else
        {
            values.put(BILL_COL_5, "N");
        }
        long returnCode = db.insert(BILL_TABLE_NAME, null, values);

        if(returnCode > -1) {
            return true;
        } else {
            return false;
        }
    }
    //SELECT for All Bills for one Month
    public ArrayList<Bill> selectBillsForCurrentMonth(String currentMonth, String billStatus)
    {
        db = this.getReadableDatabase();
        ArrayList<Bill> retrievedBillList = new ArrayList<Bill>();
        Boolean billStatusBool = true;
        if(billStatus.equals("N")){
            billStatusBool = false;
        }
        String searchQuery = "SELECT *" +
                " FROM " + BILL_TABLE_NAME +
                " WHERE " + BILL_COL_4 + " = '" + currentMonth + "'" +
                " AND " + BILL_COL_5 + " = '" + billStatus + "'";

        Log.d("One Month Search Query ",searchQuery);
        Cursor cursor = db.rawQuery(searchQuery, null);

        while(cursor.moveToNext())
        {
            Bill retrievedBill = new Bill(cursor.getString(1), cursor.getDouble(2), cursor.getString(3), billStatusBool);
            Log.d("Merchant",cursor.getString(1));
            Log.d("Amount",cursor.getString(2));
            Log.d("Month",cursor.getString(3));
            retrievedBillList.add(retrievedBill);
        }

        return retrievedBillList;
    }

    //SELECT for All Bills for multiple months
    public ArrayList<Bill> selectBillsForDiffMonth(ArrayList<String> BillList, String billStatus)
    {
        db = this.getReadableDatabase();
        ArrayList<Bill> retrievedBillList = new ArrayList<Bill>();
        Boolean billStatusBool = true;
        if(billStatus.equals("N")){
            billStatusBool = false;
        }

        for (int i=0; i < BillList.size(); i++)
        {
            String searchQuery = "SELECT *" +
                    " FROM " + BILL_TABLE_NAME +
                    " WHERE " + BILL_COL_4 + " = '" + BillList.get(i) + "'" +
                    " AND " + BILL_COL_5 + " = '" + billStatus + "'";
            Cursor cursor = db.rawQuery(searchQuery, null);

            while(cursor.moveToNext())
            {
                Bill retrievedBill = new Bill(cursor.getString(1), cursor.getDouble(2), cursor.getString(3), billStatusBool);
                Log.d("Merchant",cursor.getString(1));
                Log.d("Amount",cursor.getString(2));
                Log.d("Month",cursor.getString(3));
                retrievedBillList.add(retrievedBill);
            }
        }


        return retrievedBillList;
    }

//    public void logData() {
//        db = this.getReadableDatabase();
//        Cursor result = db.rawQuery("SELECT * FROM " + BRANCH_TABLE_NAME, null);
//        result.moveToFirst();
//        do {
//            Log.i("COLUMN1", String.valueOf(result.getInt(0)));
//            Log.i("COLUMN2", String.valueOf(result.getDouble(1)));
//            Log.i("COLUMN3", String.valueOf(result.getDouble(2)));
//            Log.i("COLUMN4", result.getString(3));
//            Log.i("COLUMN5", String.valueOf(result.getInt(4)));
//        } while (result.moveToNext());
//    }

    public boolean isTableEmpty(SQLiteDatabase db, String tableName, String primaryKey) {
        String query = "SELECT EXISTS(SELECT 1 FROM " +
                tableName + " WHERE " +
                primaryKey + " = 1)";
        Cursor result = db.rawQuery(query,null);

        result.moveToFirst();
        if (result.getInt(0) == 0) {
            // Table is empty
            return true;
        } else {
            return false;
        }
    }

    public void insertInitialMarkers(SQLiteDatabase db) {
        ContentValues valuesSMB = new ContentValues();
        ContentValues valuesAT = new ContentValues();
        valuesSMB.put(BRANCH_COL_2, 14.4028298);
        valuesSMB.put(BRANCH_COL_3, 121.0392663);
        valuesSMB.put(BRANCH_COL_4, "Marker in SMB");
        valuesSMB.put(BRANCH_COL_5, 10);

        valuesAT.put(BRANCH_COL_2, 14.5567402);
        valuesAT.put(BRANCH_COL_3, 121.0234189);
        valuesAT.put(BRANCH_COL_4, "Ayala Triangle");
        valuesAT.put(BRANCH_COL_5, 10);

        db.insert(BRANCH_TABLE_NAME, null, valuesSMB);
        db.insert(BRANCH_TABLE_NAME, null, valuesAT);
    }

    public Cursor getAllData(String tableName) {
        db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + tableName, null);
        return result;
    }
}
