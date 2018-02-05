package com.point2points.fishregister.Utilities;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.entity.ContentType;

import java.io.UnsupportedEncodingException;
import java.util.Map;
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

/**
 * Volley adapter for JSON requests with POST method that will be parsed into Java objects by Gson.
 */
public class GsonRequest<T> extends Request<T> {
    private Gson mGson = new Gson();
    private Class<T> clazz;
    private Map<String, String> headers;
    private Listener<T> listener;
    private String TAG = "GSON";

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        mGson = new Gson();
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return ContentType.APPLICATION_FORM_URLENCODED.toString();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return super.getBody();
    }

    @Override
    protected String getParamsEncoding() {
        return "UTF-8";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i(TAG, "StatusCode: " + response.statusCode);
            Log.i(TAG, "Response Data: " + json);
            return Response.success(mGson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.i(TAG, "Failure 1: " + e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.i(TAG, "Failure 2: " + e.getMessage());
            return Response.error(new ParseError(e));
        }
    }
}