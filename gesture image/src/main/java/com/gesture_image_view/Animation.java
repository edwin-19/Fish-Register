package com.gesture_image_view;

/**
 * @author Jason Polites
 *
 */
public interface Animation {

    /**
     * Transforms the view.
     * @param view
     * @param time
     * @return true if this animation should remain active.  False otherwise.
     */
    public boolean update(GestureImageView view, long time);

}