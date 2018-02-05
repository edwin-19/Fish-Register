package com.gesture_image_view;

/**
 * @author Jason Polites
 *
 */
public interface ZoomAnimationListener {
    public void onZoom(float scale, float x, float y);
    public void onComplete();
}