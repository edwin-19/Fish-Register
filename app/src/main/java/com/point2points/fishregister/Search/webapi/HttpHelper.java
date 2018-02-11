package com.point2points.fishregister.Search.webapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.point2points.fishregister.HomeActivity;
import com.point2points.fishregister.Login.database.CRUDHelper;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Search.model.Employee;
import com.point2points.fishregister.Search.provider.SearchProvider;
import com.point2points.fishregister.Utilities.AppController;
import com.point2points.fishregister.Utilities.GsonRequest;
import com.point2points.fishregister.Utilities.Utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edwin on 10/2/2018.
 */

public class HttpHelper {
    public static void getAllUser(Context context, String TAG) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("application-type", "REST");

        GsonRequest<Employee[]> mGsonRequest = new GsonRequest<Employee[]>(
                Request.Method.GET,
                context.getResources().getString(R.string.base_url) + "data/Users",
                Employee[].class,
                headers,
                mResponseUserListener(context, TAG),
                mErrorResponseListener(context, TAG)
        ) {
        };

        mGsonRequest.setRetryPolicy(new DefaultRetryPolicy(Utilities.TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(mGsonRequest, TAG);
    }

    private static Response.Listener<Employee[]> mResponseUserListener(final Context context, final String TAG) {
        return new Response.Listener<Employee[]>() {
            @Override
            public void onResponse(Employee[] response) {
                if (response != null) {
                    if (response.length > 0) {

                        Log.i(TAG, "Succesfully entered data : " + response);
                        context.getContentResolver().delete(SearchProvider.CONTENT_URI, null, null);
                        com.point2points.fishregister.Search.database.CRUDHelper.insertAllUser(context, TAG, response);
                    } else {
                        Toast.makeText(context, "EMPTY",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "NULL",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private static Response.ErrorListener mErrorResponseListener(final Context context, final String TAG) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(TAG);
                intent.putExtra("failed", true);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Toast.makeText(context, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
