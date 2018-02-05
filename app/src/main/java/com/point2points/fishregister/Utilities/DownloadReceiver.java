package com.point2points.fishregister.Utilities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class DownloadReceiver extends BroadcastReceiver {
    private String TAG = "DOWNLOAD RECEIVER";

    public DownloadReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            Intent intent1 = new Intent(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
            Utilities.checkDownloadStatus(id, context);
        }
    }
}