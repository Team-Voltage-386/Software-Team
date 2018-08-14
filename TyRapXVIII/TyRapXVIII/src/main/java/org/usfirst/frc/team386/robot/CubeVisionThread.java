package org.usfirst.frc.team386.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a thread that is responsible for reading images from the camera and
 * detecting the cubes in the camera's view. If it detects cubes in the view, it
 * will render the contours of the cubes, highlighting all of the detected
 * cubes.
 */
public class CubeVisionThread extends Thread {

    public CvSink cvSink;
    // public CvSource HSVOutputStream, rectOutputStream;
    public List<RotatedRect> rects = new ArrayList<>();
    // public List<RotatedRect> outputRects = new ArrayList<>();
    public Mat hierarchy = new Mat(), mat = new Mat(), image = new Mat();
    public List<MatOfPoint> finalContours = new ArrayList<>();
    public CvSource HSVOutputStream, rectOutputStream;
    int rectChoice;
    public SelectorType selectionMethod = SelectorType.bottom;

    int smallestI = -1;

    /**
     * The method by which the vision algorithm selects a cube for the getError
     * method
     */
    public enum SelectorType {
	rightmost, leftmost, bottom, largest;
    }

    public CubeVisionThread() {

    }

    public int resolutionWidth = 320;
    public int resolutionHeight = 240;

    /**
     * Update the smart dashboard with diagnostics values.
     */
    public void updateDiagnostics() {
	// place smart dashboard output here to refresh regularly in either auto or
	// teleop modes.
	// SmartDashboard.putNumber(Robot.VISION_ERROR, getError());
    }

    public static final int FPS = 7;

    /**
     * Run the vision thread.
     */
    @Override
    public void run() {
	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	camera.setResolution(resolutionWidth, resolutionHeight);
	camera.setExposureAuto();
	// camera.setWhiteBalanceManual(10);
	// camera.setWhiteBalanceManual(value);
	camera.setFPS(30);
	CvSink cvSink = CameraServer.getInstance().getVideo();
	HSVOutputStream = CameraServer.getInstance().putVideo("Edges", resolutionWidth, resolutionHeight);
	rectOutputStream = CameraServer.getInstance().putVideo("Final", resolutionWidth, resolutionHeight);
	cvSink.grabFrame(mat);
	Mat base = new Mat();
	Mat mat = new Mat();
	Mat grey = new Mat();
	Mat edges = new Mat();

	Size blurSize = new Size(9, 9);
	Scalar colorStart = new Scalar(20, 108, 139);
	Scalar colorEnd = new Scalar(35, 255, 255);
	Size erodeSize = new Size(10, 10);
	Size dilateSize = new Size(10, 10);
	Size edgeDilateSize = new Size(4, 4);
	boolean previousState = true;
	while (!Thread.interrupted()) {
	    boolean state = SmartDashboard.getBoolean("Enable processing", true);
	    if (state) {
		if (!previousState) {
		    camera.setFPS(FPS);
		    camera.setExposureManual(33);
		}
		cvSink.grabFrame(base);
		Imgproc.blur(base, mat, blurSize);
		Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
		Core.inRange(mat, colorStart, colorEnd, mat);
		Imgproc.erode(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, erodeSize));
		Imgproc.dilate(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, dilateSize));

		Imgproc.cvtColor(base, grey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.Canny(grey, edges, 100, 200);

		Imgproc.dilate(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, edgeDilateSize));

		Imgproc.erode(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)));

		Core.bitwise_not(edges, edges);
		Core.bitwise_and(mat, edges, mat);
		HSVOutputStream.putFrame(edges);
		finalContours.clear();
		Imgproc.findContours(mat, finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		rects.clear();
		for (int i = 0; i < finalContours.size(); i++) {
		    RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(finalContours.get(i).toArray()));
		    if (rect.size.height > 10 && rect.size.width > 10 && rect.angle < 10)
			rects.add(rect);
		}

		switch (selectionMethod) {
		case bottom: {
		    double closest = -1;
		    smallestI = -1;
		    for (int i = 0; i < rects.size(); i++) {
			double y = rects.get(i).center.y;
			if (y > closest) {
			    closest = y;
			    smallestI = i;
			}
		    }
		    break;
		}
		case rightmost: {
		    double closest = Integer.MIN_VALUE;
		    for (int i = 0; i < rects.size(); i++) {
			if (rects.get(i).center.x > closest) {
			    closest = rects.get(i).center.x;
			    smallestI = i;
			}
		    }
		    break;
		}
		case leftmost: {
		    double closest = Integer.MAX_VALUE;
		    for (int i = 0; i < rects.size(); i++) {
			if (rects.get(i).center.x < closest) {
			    closest = rects.get(i).center.x;
			    smallestI = i;
			}
		    }
		    break;
		}
		case largest: {
		    double closest = -1;
		    for (int i = 0; i < rects.size(); i++) {
			if (rects.get(i).size.height * rects.get(i).size.height < closest) {
			    closest = rects.get(i).size.height * rects.get(i).size.height;
			    smallestI = i;
			}
		    }
		}
		}

		rectChoice = smallestI;
		for (int i = 0; i < rects.size(); i++) {
		    Point[] vertices = new Point[4];
		    rects.get(i).points(vertices);
		    MatOfPoint points = new MatOfPoint(vertices);
		    if (i != smallestI)
			Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(255, 255, 255), 5);
		    else
			Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(0, 255, 0), 5);
		}
		rectOutputStream.putFrame(base);

		Thread.yield();
	    } else {
		if (previousState) {
		    camera.setFPS(30);
		    camera.setExposureAuto();
		}
		cvSink.grabFrame(base);
		rectOutputStream.putFrame(base);
	    }
	    previousState = state;
	}
    }

    /**
     * Used to determine the displacement of the chosen cube
     * 
     * @return The number of pixels the cube is off center
     */
    public int getError() {
	if (rectChoice >= 0 && rects.size() > rectChoice && rects.get(rectChoice) != null) {
	    try {
		return (int) (160 - rects.get(rectChoice).center.x);
	    } catch (NullPointerException e) {
		return Integer.MAX_VALUE;
	    }
	} else {
	    return 0;
	}
    }

    /**
     * Sets the selector method to be used to select a cube
     * 
     * @param method
     *            The selection method to be used
     */

    public void setSelectionMethod(SelectorType method) {
	selectionMethod = method;
    }

}
