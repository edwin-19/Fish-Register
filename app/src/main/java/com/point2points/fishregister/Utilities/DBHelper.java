package com.point2points.fishregister.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Edwin on 9/2/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;
    public static final String DATABASE_NAME = "point2points.db";
    public static final int DATABASE_VERSION = 1;

    //Users table name
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NAME = "user_name";
    public static final String USER_COLUMN_GENDER = "user_gender";
    public static final String USER_COLUMN_DOB = "user_dob";
    public static final String USER_COLUMN_COMPANY = "user_company";
    public static final String USER_COLUMN_PASSPORT = "user_passport";
    public static final String USER_COLUMN_DOE = "user_doe";
    public static final String USER_COKUMN_DOI = "user_doi";
    public static final String USER_COLUMN_TOKEN = "user_token";

    //Employee table name
    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String EMPLOYEE_COLUMN_ID = "id";
    public static final String EMPLOYEE_COLUMN_NAME = "employee_name";
    public static final String EMPLOYEE_COLUMN_GENDER = "employee_gener";
    public static final String EMPLOYEE_COLUMN_SERIAL_NUMBER = "employee_serial_num";
    public static final String EMPLOYEE_COLUMN_DOB = "employee_dob";
    public static final String EMPLOYEE_COLUMN_PASSPORT = "employee_passport";
    public static final String EMPLOYEE_COLUMN_DOI = "employee_doi";
    public static final String EMPLOYEE_COLUMN_DOE = "employee_doe";
    public static final String EMPLOYEE_COLUMN_COMPANY = "employee_company";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                " CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME +
                        "(" + USER_COLUMN_ID + " integer primary key, " +
                        "" + USER_COLUMN_COMPANY + " text, " +
                        "" + USER_COLUMN_GENDER + " text, " +
                        "" + USER_COLUMN_NAME + " text, " +
                        "" + USER_COLUMN_TOKEN + " text, " +
                        "" + USER_COLUMN_PASSPORT + " text, " +
                        "" + USER_COLUMN_DOB + " text, " +
                        "" + USER_COLUMN_DOE + " text, " +
                        "" + USER_COKUMN_DOI + " text) "
        );

        sqLiteDatabase.execSQL(
                " CREATE TABLE IF NOT EXISTS " + EMPLOYEE_TABLE_NAME +
                        "(" + EMPLOYEE_COLUMN_ID + " integer primary key, " +
                        "" + EMPLOYEE_COLUMN_COMPANY + " text, " +
                        "" + EMPLOYEE_COLUMN_GENDER + " text, " +
                        "" + EMPLOYEE_COLUMN_NAME + " text, " +
                        "" + EMPLOYEE_COLUMN_SERIAL_NUMBER + " text, " +
                        "" + EMPLOYEE_COLUMN_PASSPORT + " text, " +
                        "" + EMPLOYEE_COLUMN_DOB + " text, " +
                        "" + EMPLOYEE_COLUMN_DOE + " text, " +
                        "" + EMPLOYEE_COLUMN_DOI + " text) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE_NAME);
    }
}
