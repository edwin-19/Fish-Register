package com.point2points.fishregister.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.point2points.fishregister.HomeActivity;
import com.point2points.fishregister.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

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
public class CacheReportHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultUEH;

    private String mLocalPath;

    private String mTag;

    private Context mContext;

    private final String LINE_SEPARATOR = "\n";

    /*
     * if any of the parameters is null, the respective functionality
     * will not be used
     */
    public CacheReportHandler(String path, Context context) {
        this.mTag = getClass().getSimpleName();
        this.mLocalPath = path;
        this.mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = context;
    }

    public void deletePrevFiles() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/" + mContext.getResources().getString(R.string.folder_name) + "/" +
                mContext.getResources().getString(R.string.log));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        } else {
            boolean isDirDeleted = Utilities.deleteDir(mediaStorageDir);
            if (isDirDeleted) {
                mediaStorageDir.mkdirs();
            }
        }
    }

    public void uncaughtException(Thread t, Throwable ex) {
        String timestamp = String.valueOf(new Date().getTime());
        String filename = timestamp + ".txt";
        StringWriter stackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());
        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        if (mLocalPath != null) {
            writeToFile(errorReport.toString(), filename);
        }

        if (!handleException(ex, errorReport.toString()) && mDefaultUEH != null) {
            mDefaultUEH.uncaughtException(t, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(mTag, "error : ", e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex, final String error) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "Sorry, error occur, will now exit.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Looper.loop();
            }
        }.start();
        return true;
    }

    private void writeToFile(String stacktrace, String filename) {
        deletePrevFiles();
        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(
                    mLocalPath + "/" + filename));
            bos.write(stacktrace);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}