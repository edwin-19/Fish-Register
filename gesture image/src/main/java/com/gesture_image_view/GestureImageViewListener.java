package com.gesture_image_view;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author jasonpolites
 *
 */
public interface GestureImageViewListener {

    public void onTouch(float x, float y);

    public void onScale(float scale);

    public void onPosition(float x, float y);
}