package com.gesture_image_view;

/**
 * @author Jason Polites
 *
 */
public interface FlingAnimationListener {

    public void onMove(float x, float y);

    public void onComplete();

}