package com.point2points.fishregister.Login.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.point2points.fishregister.R;
import com.point2points.fishregister.Utilities.DBHelper;

/**
 * Created by Edwin on 10/2/2018.
 */

public class ProfileProvider extends ContentProvider {
    private static String CONTENT_AUTHORITY = "com.point2points.fishregister.login.provider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_CONTACT = "profile";
    private static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + PATH_CONTACT;
    private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + PATH_CONTACT;
    private static final int PROFILE = 1;
    private static final int PROFILE_ID = 2;
    private DBHelper dbHelper;
    private UriMatcher uriMatcher = null;
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT).build();

    public ProfileProvider() {
        super();
    }

    public void setUriMatcher() {
        this.uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CONTACT, PROFILE);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CONTACT + "/#", PROFILE_ID);
    }

    public UriMatcher getUriMatcher() {
        return uriMatcher;
    }

    public static Uri setCalendarUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public Cursor getNewsWithName(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        return queryBuilder.query(
                dbHelper.getReadableDatabase(),//the database to query on
                projection,//A list of which columns to return
                selection,// rows to return
                selectionArgs,
                null,//groupBy
                null,//having
                sortOrder//orderby
        );
    }

    @Override
    public boolean onCreate() {
        CONTENT_AUTHORITY = getContext().getResources().getString(R.string.profile_provider);
        dbHelper = new DBHelper(getContext());
        this.setUriMatcher();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case PROFILE:
                cursor = dbHelper.getReadableDatabase().query(
                        dbHelper.USER_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PROFILE_ID:
                cursor = getNewsWithName(uri, projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PROFILE:
                return CONTENT_TYPE;
            case PROFILE_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = getUriMatcher().match(uri);
        int numRowsInsert = 0;
        switch (match) {
            case PROFILE:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(dbHelper.USER_TABLE_NAME, null, value);
                        if (_id != -1) {
                            numRowsInsert++;
                        }
                    }
                    // To commit the transaction
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }

        if (numRowsInsert > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsInsert;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri = null;
        int match = this.getUriMatcher().match(uri);
        long rowID;
        switch (match) {
            case PROFILE:
                rowID = db.insert(dbHelper.USER_TABLE_NAME, "", values);
                if (rowID > 0) {
                    returnUri = setCalendarUri(rowID);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = getUriMatcher().match(uri);
        int rowDeleted;

        switch (match) {
            case PROFILE:
                rowDeleted = db.delete(dbHelper.USER_TABLE_NAME, selection, selectionArgs);
                break;
            case PROFILE_ID:
                String id = uri.getPathSegments().get(1);
                rowDeleted = db.delete(dbHelper.USER_TABLE_NAME, dbHelper.USER_COLUMN_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case PROFILE:
                count = db.update(dbHelper.USER_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case PROFILE_ID:
                count = db.update(dbHelper.USER_TABLE_NAME, values, dbHelper.USER_COLUMN_ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
