package com.point2points.fishregister.Search.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.point2points.fishregister.Profile.model.Profile;
import com.point2points.fishregister.Search.model.Employee;
import com.point2points.fishregister.Search.provider.SearchProvider;
import com.point2points.fishregister.Utilities.DBHelper;

/**
 * Created by Edwin on 10/2/2018.
 */

public class CRUDHelper {
    public static void insertAllUser(Context context, String TAG, Employee[] employees) {
        DBHelper dbHelper = new DBHelper(context);
        try {
            ContentValues[] contentValueses = new ContentValues[employees.length];
            for (int i = 0; i < employees.length; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_NAME, employees[i].getName());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_COMPANY, employees[i].getCompany());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_PASSPORT, employees[i].getPassport());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_GENDER, employees[i].getGender());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_SERIAL_NUMBER, employees[i].getSerialnumber());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_DOB, employees[i].getDob());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_DOE, employees[i].getDoe());
                contentValues.put(DBHelper.EMPLOYEE_COLUMN_DOI, employees[i].getDoi());
                contentValueses[i] = contentValues;
            }

            boolean isEmpty = true;
            for (ContentValues contentValues : contentValueses) {
                if (contentValues != null) {
                    isEmpty = false;
                }
            }

            if (!isEmpty) {
                context.getContentResolver().bulkInsert(SearchProvider.CONTENT_URI, contentValueses);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Content Provider Message Error :" + ex.toString());
        }
    }
}
