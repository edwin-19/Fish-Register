package com.point2points.fishregister.Profile.webapi;

import android.content.Context;

import com.point2points.fishregister.Utilities.GsonRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edwin on 10/2/2018.
 */

public class HttpHelper {
    public static void getUser(Context context, String TAG) {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("application-type", "REST");
    }
}
