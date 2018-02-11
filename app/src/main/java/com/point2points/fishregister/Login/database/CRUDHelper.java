package com.point2points.fishregister.Login.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.point2points.fishregister.Login.provider.ProfileProvider;
import com.point2points.fishregister.Profile.model.Profile;
import com.point2points.fishregister.Utilities.DBHelper;

/**
 * Created by Edwin on 10/2/2018.
 */

public class CRUDHelper {
    public static void insertUser(Context context, Profile profile, String TAG) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.USER_COLUMN_NAME, profile.getName());
            contentValues.put(DBHelper.USER_COLUMN_COMPANY, profile.getCompany());
            contentValues.put(DBHelper.USER_COLUMN_PASSPORT, profile.getPassport());
            contentValues.put(DBHelper.USER_COLUMN_GENDER, profile.getGender());
            contentValues.put(DBHelper.USER_COLUMN_TOKEN, profile.getUserToken());
            contentValues.put(DBHelper.USER_COLUMN_DOB, profile.getDob());
            contentValues.put(DBHelper.USER_COKUMN_DOI, profile.getDoi());
            contentValues.put(DBHelper.USER_COLUMN_DOE, profile.getDoe());
            context.getContentResolver().insert(ProfileProvider.CONTENT_URI, contentValues);
            Log.d(TAG, "Adding Completed");
        } catch (Exception ex) {
            Log.i(TAG, "Content Provider Message Error :" + ex.toString());
        }
    }

    public static Profile getToken(Context context) {
        Cursor cursor = null;
        Profile token = null;
        String mOrder = DBHelper.USER_COLUMN_ID + " ASC " + "LIMIT 1";
        try {
            cursor = context.getContentResolver().query(ProfileProvider.CONTENT_URI, null, null, null, mOrder);
            if (cursor != null && cursor.moveToFirst()) {
                token = Profile.getProfile(cursor, 0);
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return token;
    }
}
