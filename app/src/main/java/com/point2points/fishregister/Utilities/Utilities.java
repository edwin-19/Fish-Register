package com.point2points.fishregister.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.point2points.fishregister.R;

import org.joda.time.DateTimeZone;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import eu.janmuller.android.simplecropimage.CropImage;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * -- =============================================
 * -- Author     : Edwin Cheong
 * -- Create date: 19/1/2016
 * -- Description: AssigneeList .java
 * -- =============================================
 * HISTORY OF UPDATE
 * <p/>
 * NO     DEVELOPER         DATETIME                      DESCRIPTION
 * *******************************************************************************
 * 1
 * 2
 */
public class Utilities {
    public static final int TIMEOUT = 30000;
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final int SIZE_DEFAULT = 2048;
    public static final int SIZE_LIMIT = 4096;
    public static final int SELECT_PHOTO_REQUEST_CODE = 100;
    public static final int CROP_IMAGE_REQUEST_CODE = 32;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 101;
    public static final String TAG = Utilities.class.getSimpleName();
    private static final String[] sizeSuffixes = {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
    private static String ACTIVE = "ACTIVE";
    private static DownloadManager mgr;

    //System Message (Not in Server)
    public static final String CONNECTION = "No connection, please try again.";
    public static final String CONNECTION_TIMEOUT = "Sorry, connection timeout";
    public static final String PROCEED_FAILED = "Unable to proceed, please try again.";
    public static final String CAMERA_SUPPORT = "Sorry, you don't have supported camera.";
    public static final String LOCAL_SIZE_EXCEED = "The file is exceeded the local storage size.";
    public static final String SUCCESS_UPDATED = "Success Updated.";
    public static final String SUCCESS_CREATED = "Success Created.";
    public static final String UPLOAD_PROGRESS = "Upload in progress.";
    public static final String UPLOAD_COMPLETE = "Upload complete.";
    public static final String IMAGE_TYPE = "The image must be jpg or png.";
    public static final String IMAGE_LARGE = "The image is too large.";
    public static final String AUTHORIZATION_INVALID = "Authorization has been denied for this request.";
    public static final String EMAIL_FIELD = "Email field is required.";
    public static final String PASSWORD_FIELD = "Password field is required.";
    public static final String PASSWORD_NOT_MATCH = "Password does not match.";
    public static final String SUCCESS_SENT = "Successfully Sent.";
    public static final String END_AFTER_START_DATE = "End date should be later than ";

    //System Message (In Server)
    public static final String CODE_CONNECTION = "101";
    public static final String CODE_CONNECTION_TIMEOUT = "122";
    public static final String CODE_PROCEED_FAILED = "145";
    public static final String CODE_CAMERA_SUPPORT = "169";
    public static final String CODE_LOCAL_SIZE_EXCEEDED = "124";
    public static final String CODE_SUCCESS_UPDATED = "170";
    public static final String CODE_UPLOAD_PROGRESS = "171";
    public static final String CODE_UPLOAD_COMPLETE = "172";
    public static final String CODE_IMAGE_TYPE = "173";
    public static final String CODE_IMAGE_LARGE = "174";
    public static final String CODE_AUTHORIZATION_INVALID = "175";
    public static final String CODE_SUCCESS_CREATED = "176";
    public static final String CODE_EMAIL_FIELD = "177";
    public static final String CODE_PASSWORD_FIELD = "178";
    public static final String CODE_PASSWORD_NOT_MATCH = "179";
    public static final String CODE_SUCCESS_SENT = "180";
    public static final String CODE_END_AFTER_START_DATE = "181";

    public static DownloadManager getMgr() {
        return mgr;
    }

    public static void setMgr(DownloadManager mgr) {
        Utilities.mgr = mgr;
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children.length > 0) {
                for (int i = 0; i < children.length - 1; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        assert dir != null;
        return dir.delete();
    }


    /*public static void serverHandlingError(Context context, VolleyError error) {
        if (error instanceof AuthFailureError || error instanceof ServerError) {
            NetworkResponse response = error.networkResponse;
            String json = new String(response.data);
            Log.i(TAG, "Server Error: " + json);
            if (response.statusCode != 200) {
                if (response.statusCode == 401) {
                    *//*Utilities.showError(context, CODE_AUTHORIZATION_INVALID, AUTHORIZATION_INVALID);
                    context.getContentResolver().delete(TokenProvider.CONTENT_URI, null, null);
                    Intent intent = new Intent(context, LoginPage.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();*//*
                } else if (json.contains("Incorrect password.")) {
                    Toast.makeText(context, "Incorrect Password", Toast.LENGTH_SHORT).show();
                } else {
                    //Utilities.showError(context, Utilities.CODE_PROCEED_FAILED, Utilities.PROCEED_FAILED);
                }
            }
        } else if (error instanceof NetworkError) {
            String errorConnectionMessage = Utilities.getMessage(context, Utilities.CODE_CONNECTION);
            if (errorConnectionMessage != null) {
                Toast.makeText(context, errorConnectionMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, Utilities.CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } else if (error instanceof TimeoutError) {
            String errorTimeOutMessage = Utilities.getMessage(context, Utilities.CODE_CONNECTION_TIMEOUT);
            if (errorTimeOutMessage != null) {
                Toast.makeText(context, errorTimeOutMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, Utilities.CONNECTION_TIMEOUT, Toast.LENGTH_SHORT).show();
            }
        }
    }*/

   /* public static void serverHandlingError(Context context, VolleyError error, TextView txtError) {
        if (error instanceof AuthFailureError || error instanceof ServerError) {
            NetworkResponse response = error.networkResponse;
            String json = new String(response.data);
            Log.i(TAG, "Server Error: " + json);
            if (response.statusCode != 200) {
                if (response.statusCode == 401) {
                    *//*Utilities.showError(context, CODE_AUTHORIZATION_INVALID, AUTHORIZATION_INVALID);
                    context.getContentResolver().delete(TokenProvider.CONTENT_URI, null, null);
                    Intent intent = new Intent(context, LoginPage.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();*//*
                } else {
                    txtError.setText(Utilities.getDialogMessage(context, Utilities.CODE_PROCEED_FAILED, Utilities.PROCEED_FAILED));
                    txtError.setVisibility(View.VISIBLE);
                }
            }
        } else if (error instanceof NetworkError) {
            String errorConnectionMessage = Utilities.getMessage(context, Utilities.CODE_CONNECTION);
            if (errorConnectionMessage != null) {
                txtError.setText(errorConnectionMessage);
                txtError.setVisibility(View.VISIBLE);
            } else {
                txtError.setText(Utilities.CONNECTION);
                txtError.setVisibility(View.VISIBLE);
            }
        } else if (error instanceof TimeoutError) {
            String errorTimeOutMessage = Utilities.getMessage(context, Utilities.CODE_CONNECTION_TIMEOUT);
            if (errorTimeOutMessage != null) {
                txtError.setText(errorTimeOutMessage);
            } else {
                txtError.setText(Utilities.CONNECTION_TIMEOUT);
                txtError.setVisibility(View.VISIBLE);
            }
        }
    }*/

    /*public static String getDialogMessage(Context context, String code, String error) {
        String errorMessage = Utilities.getMessage(context, code);
        if (errorMessage != null) {
            return errorMessage;
        } else {
            return error;
        }
    }*/

    public static void alertDialog(String errorMessage, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(errorMessage);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* context.getContentResolver().delete(TokenProvider.CONTENT_URI, null, null);
                Intent intent = new Intent(context, LoginPage.class);
                context.startActivity(intent);
                ((Activity) context).finish();*/
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //region Set Date Time
    public static String setToLocalDateTime(String prevFormat, String newFormat, String data) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat(prevFormat, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat print = new SimpleDateFormat(newFormat, Locale.getDefault());
        print.setTimeZone(TimeZone.getDefault());
        if (data != null) {
            Date date = null;
            try {
                date = format.parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                if (date == null) {
                    try {
                        org.joda.time.DateTime dt = new org.joda.time.DateTime(data, DateTimeZone.UTC);
                        dt.toDateTime(DateTimeZone.getDefault());
                        result = dt.toString(newFormat);
                    } catch (Exception e) {
                        e.fillInStackTrace();
                    } finally {
                        if (data.contains("GMT")) {
                            org.joda.time.DateTime dt = new org.joda.time.DateTime();
                            dt.plusDays(3);
                            result = dt.toString(newFormat);
                        }
                    }
                } else {
                    result = print.format(date);
                }
            }
        } else {
            result = null;
        }
        return result;
    }

    public static String setToUTCDate(String prevFormat, String newFormat, String data) {
        SimpleDateFormat format = new SimpleDateFormat(prevFormat, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat print = new SimpleDateFormat(newFormat, Locale.getDefault());
        print.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = format.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (date == null) {
                org.joda.time.DateTime dt = new org.joda.time.DateTime(data, DateTimeZone.getDefault());
                dt.toDateTime(DateTimeZone.UTC);
                date = Utilities.setCalendarDate(Utilities.DATE_FORMAT, dt.toString(Utilities.DATE_FORMAT));
            }
        }
        return print.format(date);
    }

    public static Date setCalendarDate(String dateFormat, String data) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date == null) {
                org.joda.time.DateTime dt = new org.joda.time.DateTime(data);
                date = dt.toDate();
            }
        }
        return date;
    }

    public static String setCalendarDate(String newFormat, long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(newFormat, Locale.getDefault());
        return sdf.format(d);
    }

    public static String setCalendarDate(String prevFormat, String newFormat, String data) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat(prevFormat, Locale.getDefault());
        SimpleDateFormat print = new SimpleDateFormat(newFormat, Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (data == null) {
                org.joda.time.DateTime dt = new org.joda.time.DateTime(date);
                result = dt.toString(newFormat);
            } else {
                result = print.format(date);
            }
        }
        return result;
    }

    public static String setCalendarDate(String dateFormat, Date date) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            result = format.format(date);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        } finally {
            if (result == null) {
                org.joda.time.DateTime dt = new org.joda.time.DateTime(date);
                result = dt.toString(dateFormat);
            }
        }
        return result;
    }
    //endregion

    //region Load Image
    public static Uri getOutputMediaFileUri(Context context, boolean isDelete, File file) {
        return Uri.fromFile(getOutputMediaFile(context, isDelete, file));
    }

    public static File getOutputMediaFile(Context context, boolean isDelete, File mediaStorageDir) {
        if (mediaStorageDir == null) {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/" + context.getResources().getString(R.string.folder_name) + "/" +
                    context.getResources().getString(R.string.temp));
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        } else {
            if (isDelete) {
                boolean isDirDeleted = Utilities.deleteDir(mediaStorageDir);
                if (isDirDeleted) {
                    mediaStorageDir.mkdirs();
                }
            }
        }
        String timeStamp = Utilities.setCalendarDate("yyyyMMdd_HHmmss_SSS", new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    public static File previewCapturedImage(Context context, Uri fileUri, final ImageView img, final boolean isCrop, int requestedCode, boolean isDelete, File direct, int maxLength, boolean isRound) {
        ImageUtilities.normalizeImageForUri(context, fileUri);
        File file = new File(fileUri.getPath());
        file = Utilities.convertImageToSmall(context, file, isDelete, direct, maxLength);
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        final int mHeight = mDisplayMetrics.heightPixels;
        final int mWidth = mDisplayMetrics.widthPixels;
        if (!isCrop) {
            if (img != null) {
                if (isRound) {

                    Glide.with(context)
                            .asBitmap()
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .transform(new CircleTransform(context))
                                    .placeholder(R.drawable.empty_photo))
                            .into(new BaseTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    img.setImageBitmap(resource);
                                }

                                @Override
                                public void getSize(SizeReadyCallback cb) {
                                    cb.onSizeReady(mWidth, mHeight);
                                }

                                @Override
                                public void removeCallback(SizeReadyCallback cb) {

                                }
                            });

                } else {
                    Glide.with(context)
                            .asBitmap()
                            .load(file.getAbsolutePath())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.empty_photo))
                            .into(new BaseTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    img.setImageBitmap(resource);
                                }

                                @Override
                                public void getSize(SizeReadyCallback cb) {
                                    cb.onSizeReady(mWidth, mHeight);
                                }

                                @Override
                                public void removeCallback(SizeReadyCallback cb) {

                                }
                            });
                }
            }
        } else {
            boolean isSuccess = performCrop(fileUri, context, requestedCode);
            if (!isSuccess) {
                if (img != null) {
                    if (isRound) {
                        Glide.with(context)
                                .asBitmap()
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .transform(new CircleTransform(context))
                                        .placeholder(R.drawable.empty_photo))
                                .into(new BaseTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        img.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void getSize(SizeReadyCallback cb) {
                                        cb.onSizeReady(mWidth, mHeight);
                                    }

                                    @Override
                                    public void removeCallback(SizeReadyCallback cb) {

                                    }
                                });
                    } else {

                        Glide.with(context)
                                .asBitmap()
                                .load(file.getAbsolutePath())
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.empty_photo)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .into(new BaseTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        img.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void getSize(SizeReadyCallback cb) {
                                        cb.onSizeReady(mWidth, mHeight);
                                    }

                                    @Override
                                    public void removeCallback(SizeReadyCallback cb) {

                                    }
                                });
                    }
                }
            }
        }
        return file;
    }

    public static File previewSelectedImage(Context context, Uri selectedImage, final ImageView img, final boolean isCrop, int requestedCode, boolean isDelete, File direct, int maxLength, boolean isRound) {
        if (getRealPathFromURI(context, selectedImage) != null) {
            File file = new File(getRealPathFromURI(context, selectedImage));
            file = Utilities.convertImageToSmall(context, file, isDelete, direct, maxLength);
            DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
            final int mHeight = mDisplayMetrics.heightPixels;
            final int mWidth = mDisplayMetrics.widthPixels;
            if (!isCrop) {
                if (img != null) {
                    if (isRound) {
                        Glide.with(context)
                                .asBitmap()
                                .load(file.getAbsolutePath())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.empty_photo)
                                        .transform(new CircleTransform(context)))
                                .into(new BaseTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        img.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void getSize(SizeReadyCallback cb) {
                                        cb.onSizeReady(mWidth, mHeight);
                                    }

                                    @Override
                                    public void removeCallback(SizeReadyCallback cb) {

                                    }
                                });
                    } else {
                        Glide.with(context)
                                .asBitmap()
                                .load(file.getAbsolutePath())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.drawable.empty_photo))
                                .into(new BaseTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        img.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void getSize(SizeReadyCallback cb) {
                                        cb.onSizeReady(mWidth, mHeight);
                                    }

                                    @Override
                                    public void removeCallback(SizeReadyCallback cb) {

                                    }
                                });
                    }
                }
            } else {
                boolean isSuccess = performCrop(selectedImage, context, requestedCode);
                if (!isSuccess) {
                    if (img != null) {
                        if (isRound) {
                            Glide.with(context)
                                    .asBitmap()
                                    .load(file.getAbsolutePath())
                                    .apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .transform(new CircleTransform(context))
                                            .placeholder(R.drawable.empty_photo))
                                    .into(new BaseTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                            img.setImageBitmap(resource);
                                        }

                                        @Override
                                        public void getSize(SizeReadyCallback cb) {
                                            cb.onSizeReady(mWidth, mHeight);
                                        }

                                        @Override
                                        public void removeCallback(SizeReadyCallback cb) {

                                        }
                                    });
                        } else {
                            Glide.with(context)
                                    .asBitmap()
                                    .load(file.getAbsolutePath())
                                    .apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .placeholder(R.drawable.empty_photo))
                                    .into(new BaseTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                            img.setImageBitmap(resource);
                                        }

                                        @Override
                                        public void getSize(SizeReadyCallback cb) {
                                            cb.onSizeReady(mWidth, mHeight);
                                        }

                                        @Override
                                        public void removeCallback(SizeReadyCallback cb) {

                                        }
                                    });
                        }
                    }
                }
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * this function does the crop operation.
     */
    public static boolean performCrop(Uri picUri, Context context, int requestCode) {
        boolean isSuccess = false;
        // take care of exceptions
        try {
            ImageUtilities.normalizeImageForUri(context, picUri);
            Intent cropIntent = new Intent(context, CropImage.class);
            // tell CropImage activity to look for image to crop
            cropIntent.putExtra(CropImage.IMAGE_PATH, new File(getRealPathFromURI(context, picUri)).getAbsolutePath());

            // allow CropImage activity to rescale image
            cropIntent.putExtra(CropImage.SCALE, true);

            // if the aspect ratio is fixed to ratio 3/2
            cropIntent.putExtra(CropImage.ASPECT_X, 2);
            cropIntent.putExtra(CropImage.ASPECT_Y, 2);
            // start the activity - we handle returning in onActivityResult
            ((Activity) context).startActivityForResult(cropIntent, requestCode);
            isSuccess = true;
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            //Utilities.showError(context, Utilities.CODE_CAMERA_SUPPORT, Utilities.CAMERA_SUPPORT);
        }
        return isSuccess;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String result = null;
        try {
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (contentUri != null && result == null) {
            try {
                File file = new File(new URI(contentUri.toString()));
                result = file.getAbsolutePath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static File convertImageToSmall(Context context, File file, boolean isDelete, File direct, int maxLength) {
        if (file != null) {
            InputStream is = null;
            int sampleSize = 0;
            try {
                sampleSize = Utilities.calculateBitmapSampleSize(Uri.fromFile(file), context, maxLength);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is = context.getContentResolver().openInputStream(Uri.fromFile(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = sampleSize;
            Bitmap b = BitmapFactory.decodeStream(is, null, option);
            return getFile(context, b, isDelete, direct);
        } else return null;
    }

    public static File getFile(Context mContext, Bitmap result, boolean isDelete, File direct) {
        OutputStream fOut = null;
        File file = getOutputMediaFile(mContext, isDelete, direct);
        assert file != null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert result != null;
            result.compress(Bitmap.CompressFormat.JPEG, 85, fOut);// saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            try {
                assert fOut != null;
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close(); // do not forget to close the stream
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return file;
    }

    public static int calculateBitmapSampleSize(Uri bitmapUri, Context context, int maxLength) throws IOException {
        InputStream is = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            is = context.getContentResolver().openInputStream(bitmapUri);
            BitmapFactory.decodeStream(is, null, options); // Just get image size
        } finally {
            closeSilently(is);
        }

        int maxSize = getMaxImageSize(maxLength);
        int sampleSize = 1;
        while (options.outHeight / sampleSize > maxSize || options.outWidth / sampleSize > maxSize) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    public static void closeSilently(@Nullable Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public static int getMaxImageSize(int max) {
        int textureLimit = getMaxTextureSize();
        if (textureLimit == 0) {
            return SIZE_DEFAULT;
        } else {
            return Math.min(textureLimit, max);
        }
    }

    public static int getMaxTextureSize() {
        // The OpenGL texture size is the maximum size that can be drawn in an ImageView
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
        return maxSize[0];
    }

    public static boolean isDeviceSupportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getUsableSpace() {
        File savePath = Environment.getExternalStorageDirectory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return savePath.getUsableSpace();

        } else {
            StatFs stats = new StatFs(savePath.getAbsolutePath());
            return stats.getAvailableBlocksLong() * stats.getBlockSizeLong();
        }
    }

    public static boolean isValidImage(File file, Context context) {
        boolean isValid = false;
        if (file != null) {
            if (file.length() < 3 * 1000000) {
                String[] result = file.getName().split("\\.");
                if (result[result.length - 1].equalsIgnoreCase("jpg") || result[result.length - 1].
                        equalsIgnoreCase("png") || result[result.length - 1].equalsIgnoreCase("jpeg")) {
                    isValid = true;
                } else {
                    //Utilities.showError(context, Utilities.CODE_IMAGE_TYPE, Utilities.IMAGE_TYPE);
                }
            } else {
                //Utilities.showError(context, Utilities.CODE_IMAGE_LARGE, Utilities.IMAGE_LARGE);
            }
        }
        return isValid;
    }
    //endregion

    public static void setFadeProgressBarVisibility(boolean isVisible, RelativeLayout rl, ProgressBar progressBar) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
            rl.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            rl.setVisibility(View.GONE);
        }
    }

    //region hide keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //endregion

    public static String encode(Object obj) {
        /*try {
            return URLEncoder.encode(String.valueOf(obj), "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return String.valueOf(obj);
        }*/
        return String.valueOf(obj);
    }

    public static boolean localFileExists(String path) {
        if (path == null) {
            return false;
        }
        File direct = new File(path);
        return direct.exists();
    }

    public static String bytesIntoHumanReadable(long bytes) {
        double result = bytes;
        int attachedsuff = 0;
        while (result > 1024 && attachedsuff < sizeSuffixes.length) {
            result /= 1024.;
            attachedsuff++;
        }
        result = ((int) (result * 100)) / 100.;
        return result + " " + sizeSuffixes[attachedsuff];
    }

    public static void navigateUpFromSameTask(Activity sourceActivity, Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("Activity " +
                    sourceActivity.getClass().getSimpleName() +
                    " does not have a parent activity name specified." +
                    " (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> " +
                    " element in your manifest?)");
        }

        NavUtils.navigateUpTo(sourceActivity, intent);
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        android.view.animation.Animation a = new android.view.animation.Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        android.view.animation.Animation a = new android.view.animation.Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void downloadFile(String url, Context context, String folderName) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/" + context.getResources().getString(R.string.folder_name) + "/" + folderName);
        if (!direct.exists()) {
            direct.mkdirs();
        }
        String[] result = url.split("/");
        String fileName = result[result.length - 1];
        if (!Utilities.localFileExists(direct.getAbsolutePath() + "/" + fileName)) {
            if (getMgr() == null) {
                DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                setMgr(mgr);
            }
            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(
                    downloadUri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setDestinationInExternalPublicDir("/" + context.getResources().getString(R.string.folder_name) + "/" + folderName, fileName);
            getMgr().enqueue(request);
            Toast.makeText(context, "Downloading the file", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "The file is exsits at " + direct.getAbsolutePath() + "/" + fileName, Toast.LENGTH_SHORT).show();
        }
    }

    public static void checkDownloadStatus(long id, Context context) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor = getMgr().query(query);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor
                    .getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            int columnReason = cursor
                    .getColumnIndex(DownloadManager.COLUMN_REASON);
            int reason = cursor.getInt(columnReason);
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));

            switch (status) {
                case DownloadManager.STATUS_FAILED:
                    String failedReason = "";
                    switch (reason) {
                        case DownloadManager.ERROR_CANNOT_RESUME:
                            failedReason = "Error cannot resume";
                            break;
                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                            failedReason = "Error device not found";
                            break;
                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                            failedReason = "Error file already exists";
                            break;
                        case DownloadManager.ERROR_FILE_ERROR:
                            failedReason = "Error file error";
                            break;
                        case DownloadManager.ERROR_HTTP_DATA_ERROR:
                            failedReason = "Error HTTP data error";
                            break;
                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                            failedReason = "Error insufficient space";
                            break;
                        case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                            failedReason = "Error too many redirects";
                            break;
                        case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                            failedReason = "Error unhandled HTTP code";
                            break;
                        case DownloadManager.ERROR_UNKNOWN:
                            failedReason = "Error unknown";
                            break;
                    }

                    Toast.makeText(context, "Download " + title + " is failed: " + failedReason,
                            Toast.LENGTH_LONG).show();
                    break;
                case DownloadManager.STATUS_PAUSED:
                    String pausedReason = "";
                    switch (reason) {
                        case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                            pausedReason = "Paused queued for wifi";
                            break;
                        case DownloadManager.PAUSED_UNKNOWN:
                            pausedReason = "Paused unknown";
                            break;
                        case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                            pausedReason = "Paused waiting for network";
                            break;
                        case DownloadManager.PAUSED_WAITING_TO_RETRY:
                            pausedReason = "Paused waiting to retry";
                            break;
                    }
                    Toast.makeText(context, "Download " + title + " is paused: " + pausedReason,
                            Toast.LENGTH_LONG).show();
                    break;
                case DownloadManager.STATUS_PENDING:
                    Toast.makeText(context, "PENDING", Toast.LENGTH_LONG).show();
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Toast.makeText(context, "RUNNING", Toast.LENGTH_LONG).show();
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Toast.makeText(context, "Download " + title + " is completed", Toast.LENGTH_SHORT).show();
                    break;
            }
            cursor.close();
        }
    }


    public static void openDocument(String path, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(path);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            intent.setDataAndType(Uri.fromFile(file), "text/*");
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }
        // custom message for the intent
        context.startActivity(Intent.createChooser(intent, "Choose an Application:"));
    }

    public static boolean getIsActivityOpen(Context context) {
        SharedPreferences sp = context.getSharedPreferences("IS_OPEN_ACTION", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.apply();
        return sp.getBoolean(ACTIVE, false);
    }

    public static void setIsActivityOpen(boolean isActivityOpen, Context context) {
        ShortcutBadger.removeCount(context);
        SharedPreferences sp = context.getSharedPreferences("IS_OPEN_ACTION", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(ACTIVE, isActivityOpen);
        ed.apply();
    }

    public static void requestForCameraPermission(Context context) {
        MarshMallowPermission marshMallowPermission = new MarshMallowPermission((Activity) context);
        if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera(context);
        }

        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage(context);
        }
    }

    public static String convertStringToDate(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
   /*you can also use DateFormat reference instead of SimpleDateFormat
    * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
    */
        try {
            dateString = sdfr.format(indate);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dateString;
    }
}
