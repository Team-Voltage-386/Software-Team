package org.usfirst.frc.team386.robot;

public interface ObjectTracker {

    /**
     * Return 1, 0, -1 depending on the direction the tracker must turn to follow an
     * object.
     * 
     * @return 1 for right, 0 for stop, -1 for left
     */
    public int getDirection();

    /**
     * Return true if the object is present somewhere in the camera's view.
     * 
     * @return True if the object is present in the camera view.
     */
    public boolean isObjectPresent();

}
