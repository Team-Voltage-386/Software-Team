package org.usfirst.frc.team386.robot.vision;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class OpencvObjectTracker {

    public static final int UPDATE_DELAY = 2000;

    private int direction = 0;
    private boolean objectPresent = true;

    private Scalar hsvMinValues;
    private Scalar hsvMaxValues;

    private Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(6, 6));
    private Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));

    /**
     * Construct a new OpencvObjectTracker.The HSV min/max values are for a green
     * cup I have at home.
     */
    public OpencvObjectTracker() {
	this.hsvMinValues = new Scalar(33, 108, 138);
	this.hsvMaxValues = new Scalar(55, 255, 255);
	start();
    }

    /**
     * Construct a new ObjectTracker. Objects with HSV values between the specified
     * minimum and maximum HSV values will be tracked.
     *
     * @param hsvMinValues
     *            The minimum HSV values.
     * @param hsvMaxValues
     *            The maximum HSV values.
     */
    public OpencvObjectTracker(Scalar hsvMinValues, Scalar hsvMaxValues) {
	this.hsvMinValues = hsvMinValues;
	this.hsvMaxValues = hsvMaxValues;
	start();
    }

    public void setHsvMinValues(Scalar hsvMinValues) {
	this.hsvMinValues = hsvMinValues;
    }

    public void setHsvMaxValues(Scalar hsvMaxValues) {
	this.hsvMaxValues = hsvMaxValues;
    }

    private void start() {
	new Thread(() -> {
	    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(160, 120);
	    CvSink cvSink = CameraServer.getInstance().getVideo(camera);

	    CvSource videoOut = CameraServer.getInstance().putVideo("Display", 160, 120);
	    CvSource morphOut = CameraServer.getInstance().putVideo("Morph", 160, 120);
	    Mat source = new Mat();
	    Mat dest = new Mat();

	    while (!Thread.interrupted()) {
		long val = cvSink.grabFrame(source);

		if (val != 0 && !source.empty()) {
		    processFrame(source, dest);
		    videoOut.putFrame(source);
		    morphOut.putFrame(dest);
		}
	    }
	}).start();
    }

    public void clearDirection() {
	this.direction = 0;
    }

    public int getDirection() {
	return direction;
    }

    public boolean isObjectPresent() {
	return objectPresent;
    }

    public void processFrame(Mat frame, Mat processedFrame) {
	// System.out.println("Processing frame");
	Mat blurredImage = new Mat();
	Mat hsvImage = new Mat();
	Mat mask = new Mat();
	Mat morphOutput = new Mat();
	List<MatOfPoint> contours = new ArrayList<>();
	Mat hierarchy = new Mat();

	try {
	    // remove some noise
	    Size blurSize = new Size(2, 2);
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

	    if (processedFrame != null) {
		morphOutput.copyTo(processedFrame);
	    }

	    // Find contours
	    Imgproc.findContours(morphOutput, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

	    if (contours.size() == 0) {
		// The object does not appear to be present anywhere in the camera's view
		if (objectPresent) {
		    System.out.println("Object not present");
		}
		this.objectPresent = false;
		// this.direction = 0;
	    } else {
		// The object is present in the camera's view, but not centered
		// System.out.println("Found " + contours.size() + " contours");
		for (int i = 0; i < contours.size(); i++) {
		    MatOfPoint contour = contours.get(i);

		    Rect boundingRect = Imgproc.boundingRect(contour);
		    // System.out.println("Bounding rect: " + boundingRect);
		    Rect centerTarget = getCenterTargetRect(frame);

		    // Draw target
		    Scalar centerTargetColor = new Scalar(0, 0, 255);
		    int centerTargetThickness = 2;
		    Imgproc.rectangle(frame, centerTarget.tl(), centerTarget.br(), centerTargetColor,
			    centerTargetThickness);

		    // If the bounding rectangle and target intersect, then the direction is 0, the
		    // target is centered
		    if (intersects(boundingRect, centerTarget)) {
			centerTargetColor = new Scalar(0, 230, 0);
			centerTargetThickness = 2;
			Imgproc.rectangle(frame, centerTarget.tl(), centerTarget.br(), centerTargetColor,
				centerTargetThickness);
			if (this.direction != 0)
			    System.out.println("Object centered");
			this.direction = 0;
		    } else {
			if (boundingRect.x > centerTarget.x + centerTarget.width) {
			    if (this.direction != 1)
				System.out.println("Object is to the right");
			    // If the bounding rectangle's X value is greater than the center target X +
			    // width, then the object is to the right
			    this.direction = 1;
			} else {
			    if (this.direction != -1)
				System.out.println("Object is to the left");
			    // Otherwise the object is to the left
			    this.direction = -1;
			}
		    }

		    contour.release();
		}
		this.objectPresent = true;
	    }
	} finally {
	    blurredImage.release();
	    hsvImage.release();
	    mask.release();
	    morphOutput.release();
	    hierarchy.release();
	}
    }

    private Rect getCenterTargetRect(Mat frame) {
	int width = frame.width() / 16;
	int height = frame.height();
	int x = (frame.width() / 2) - (width / 2);
	// int y = (frame.height() / 2) - (height / 2);
	Rect rect = new Rect(x, 0, width, height);
	return rect;
    }

    private boolean intersects(Rect a, Rect b) {
	int left = Math.max(a.x, b.x);
	int top = Math.max(a.y, b.y);
	int right = Math.min(a.x + a.width, b.x + b.width);
	int bottom = Math.min(a.y + a.height, b.y + b.height);
	return left <= right && top <= bottom;
    }

}