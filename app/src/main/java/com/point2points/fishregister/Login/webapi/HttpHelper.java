package com.point2points.fishregister.Login.webapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.point2points.fishregister.HomeActivity;
import com.point2points.fishregister.Login.database.CRUDHelper;
import com.point2points.fishregister.Login.model.Token;
import com.point2points.fishregister.Login.provider.ProfileProvider;
import com.point2points.fishregister.Profile.model.Profile;
import com.point2points.fishregister.R;
import com.point2points.fishregister.Utilities.AppController;
import com.point2points.fishregister.Utilities.GsonRequest;
import com.point2points.fishregister.Utilities.Utilities;

import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edwin on 10/2/2018.
 */

public class HttpHelper {
    public static void loginUser(Context context, final String username, final String password, String TAG) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        GsonRequest<Profile> mGsonRequest = new GsonRequest<Profile>(
                Request.Method.POST,
                context.getResources().getString(R.string.base_url) + "users/login",
                Profile.class,
                headers,
                mResponseLoginListener(context, TAG),
                mErrorLoginErrorListener(context, TAG)
        ) {
            @Override
            public String getBodyContentType() {
                return ContentType.APPLICATION_JSON.toString();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                Token token = new Token();
                token.setLogin(username);
                token.setPassword(password);

                String json = gson.toJson(token);
                return json.getBytes();
            }
        };

        mGsonRequest.setRetryPolicy(new DefaultRetryPolicy(Utilities.TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(mGsonRequest, TAG);
    }

    private static Response.Listener<Profile> mResponseLoginListener(final Context context, final String TAG) {
        return new Response.Listener<Profile>() {
            @Override
            public void onResponse(Profile response) {
                if (response != null) {
                    context.getContentResolver().delete(ProfileProvider.CONTENT_URI, null, null);
                    CRUDHelper.insertUser(context, response, TAG);
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        };
    }

    private static Response.ErrorListener mErrorLoginErrorListener(final Context context, final String TAG) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(TAG);
                intent.putExtra("failed", true);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

}
