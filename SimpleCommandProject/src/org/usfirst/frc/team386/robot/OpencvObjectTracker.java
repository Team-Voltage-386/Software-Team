package org.usfirst.frc.team386.robot;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class OpencvObjectTracker implements ObjectTracker {

    public static final int UPDATE_DELAY = 2000;

    private int direction = 0;
    private boolean objectPresent = false;

    private int cameraId;
    private float fps;
    private Scalar hsvMinValues;
    private Scalar hsvMaxValues;

    private VideoCapture camera = new VideoCapture();
    private ScheduledExecutorService timer;

    private Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
    private Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));

    /**
     * Construct a new ObjectTracker. It will use the camera with the ID 0 and an
     * FPS value of 10. The HSV min/max values are for a green cup I have at home.
     */
    public OpencvObjectTracker() {
	this.cameraId = 0;
	this.fps = 10;
	this.hsvMinValues = new Scalar(33, 108, 138);
	this.hsvMaxValues = new Scalar(55, 255, 255);
    }

    /**
     * Construct a new ObjectTracker with the given camera ID and FPS setting.
     * Objects with HSV values between the specified minimum and maximum HSV values
     * will be tracked.
     * 
     * @param cameraId
     *            The camera ID
     * @param fps
     *            The FPS value
     * @param hsvMinValues
     *            The minimum HSV values.
     * @param hsvMaxValues
     *            The maximum HSV values.
     */
    public OpencvObjectTracker(int cameraId, int fps, Scalar hsvMinValues, Scalar hsvMaxValues) {
	this.cameraId = cameraId;
	this.fps = fps;
	this.hsvMinValues = hsvMinValues;
	this.hsvMaxValues = hsvMaxValues;
    }

    /**
     * Return 1, 0, -1 depending on the direction the tracker must turn to follow an
     * object.
     * 
     * @return 1 for right, 0 for stop, -1 for left
     */
    public int getDirection() {
	return direction;
    }

    /**
     * Return true if the object is present somewhere in the camera's view.
     * 
     * @return True if the object is present in the camera view.
     */
    public boolean isObjectPresent() {
	return objectPresent;
    }

    /**
     * Set up the camera and start grabbing frames.
     */
    public void startCapture() {
	System.out.println("Starting camera with ID " + cameraId);
	this.camera.open(cameraId);
	if (this.camera.isOpened()) {
	    System.out.println("Camera is running");
	    Runnable frameGrabber = new Runnable() {
		@Override
		public void run() {
		    processFrame();
		}
	    };
	    System.out.println("Starting frame grabber");
	    this.timer = Executors.newSingleThreadScheduledExecutor();
	    this.timer.scheduleAtFixedRate(frameGrabber, 0, getFrameGrabSchedule(), TimeUnit.MILLISECONDS);
	}
    }

    /**
     * Stop the frame grabber.
     */
    public void stopCapture() {
	this.timer.shutdown();
	System.out.println("Frame grabber stopped");
	this.camera.release();
	System.out.println("Camera is released");
    }

    /**
     * Run the object tracker demonstration. This is an entry point for testing the
     * tracker, it is not intended to be used when the tracker is used within the
     * robot.
     * 
     * @param args
     *            List of command line arguments
     */
    public static void main(String[] args) {
	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	OpencvObjectTracker tracker = new OpencvObjectTracker();
	tracker.startCapture();
	while (true) {
	    if (tracker.isObjectPresent()) {
		System.out.println(tracker.getDirection());
	    } else {
		System.out.println("Object not present");
	    }

	    try {
		Thread.sleep(UPDATE_DELAY);
	    } catch (InterruptedException e) {
		System.out.println("Thread interrupted, continuing");
	    }
	}
    }

    /**
     * Calculate the frame grab schedule. Converts FPS to milliseconds of delay.
     * 
     * @return The number of milliseconds to delay between grabs.
     */
    protected long getFrameGrabSchedule() {
	long frameGrabSchedule = (long) ((1f / fps) * 1000f);

	System.out.println("Current frame grab rate: " + (long) fps + "fps");
	System.out.println("Calculated frame grab schedule: " + frameGrabSchedule + "ms");

	return frameGrabSchedule;
    }

    protected void processFrame() {
	Mat frame = new Mat();

	// check if the capture is open
	if (this.camera.isOpened()) {
	    try {
		// read the current frame
		this.camera.read(frame);

		// if the frame is not empty, process it
		if (!frame.empty()) {
		    // System.out.println("Processing frame");
		    Mat blurredImage = new Mat();
		    Mat hsvImage = new Mat();
		    Mat mask = new Mat();
		    Mat morphOutput = new Mat();
		    List<MatOfPoint> contours = new ArrayList<>();
		    Mat hierarchy = new Mat();

		    try {
			// remove some noise
			Size blurSize = new Size(7, 7);
			Imgproc.blur(frame, blurredImage, blurSize);

			// convert the frame to HSV
			Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

			// fill in the mask that is used to find the objects
			Core.inRange(hsvImage, hsvMinValues, hsvMaxValues, mask);

			// morphological operators
			// dilate with large element, erode with small element
			Imgproc.erode(mask, morphOutput, erodeElement);
			Imgproc.erode(morphOutput, morphOutput, erodeElement);

			Imgproc.dilate(morphOutput, morphOutput, dilateElement);
			Imgproc.dilate(morphOutput, morphOutput, dilateElement);

			// Find contours
			Imgproc.findContours(morphOutput, contours, hierarchy, Imgproc.RETR_CCOMP,
				Imgproc.CHAIN_APPROX_SIMPLE);

			if (contours.size() == 0) {
			    // The object does not appear to be present anywhere in the camera's view
			    this.objectPresent = false;
			} else {
			    // The object is present in the camera's view, but not centered
			    this.objectPresent = true;

			    for (int i = 0; i < contours.size(); i++) {
				// Calculate the bounding rectangle of the contour
				MatOfPoint contour = contours.get(i);
				Rect boundingRect = Imgproc.boundingRect(contour);

				// Calculate the center target bounding rectangle
				Rect centerTarget = getCenterTargetRect(frame);

				// Convert from the opencv Rect to a java.awt.Rectangle to make it possible to
				// use intersects().
				Rectangle r1 = new Rectangle(boundingRect.x, boundingRect.y, boundingRect.width,
					boundingRect.height);
				Rectangle r2 = new Rectangle(centerTarget.x, centerTarget.y, centerTarget.width,
					centerTarget.height);

				// If the bounding rectangle and target intersect, then the direction is 0, the
				// target is centered
				if (r1.intersects(r2)) {
				    this.direction = 0;
				} else {
				    if (boundingRect.x > centerTarget.x + centerTarget.width) {
					// If the bounding rectangle's X value is greater than the center target X +
					// width, then the object is to the right
					this.direction = 1;
				    } else {
					// Otherwise the object is to the left
					this.direction = -1;
				    }
				}

				contour.release();
			    }
			}
		    } finally {
			blurredImage.release();
			hsvImage.release();
			mask.release();
			morphOutput.release();
			hierarchy.release();
		    }
		}
	    } catch (Exception e) {
		System.err.println("Exception during the image elaboration: " + e);
	    }
	}

	frame.release();
    }

    private Rect getCenterTargetRect(Mat frame) {
	int width = 200;
	int height = 100;
	int x = (frame.width() / 2) - (width / 2);
	int y = (frame.height() / 2) - (height / 2);
	return new Rect(x, y, width, height);
    }

}