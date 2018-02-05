package com.point2points.fishregister.Utilities;

import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.point2points.fishregister.R;

import java.io.File;

/**
 -- =============================================
 -- Author     : Edwin Cheong
 -- Create date: 19/1/2016
 -- Description: AssigneeList .java
 -- =============================================
 HISTORY OF UPDATE

 NO     DEVELOPER         DATETIME                      DESCRIPTION
 ********************************************************************************
 1
 2
 */
public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sendCacheReport();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "HelveticaLTStd-Light.otf");
    }

    private void sendCacheReport() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.folder_name) + "/" + getResources().getString(R.string.log));
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CacheReportHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CacheReportHandler(file.getPath(), this));
        }
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        //cancelPendingRequests(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}