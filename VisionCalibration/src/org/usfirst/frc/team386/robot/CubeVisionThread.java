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
	camera.setExposureManual(33);
	// camera.setWhiteBalanceManual(10);
	// camera.setWhiteBalanceManual(value);
	camera.setFPS(FPS);
	CvSink cvSink = CameraServer.getInstance().getVideo();
	HSVOutputStream = CameraServer.getInstance().putVideo("Colors", resolutionWidth, resolutionHeight);
	rectOutputStream = CameraServer.getInstance().putVideo("Rectangles", resolutionWidth, resolutionHeight);
	cvSink.grabFrame(mat);
	Mat base = new Mat();
	Mat mat = new Mat();
	Mat grey = new Mat();
	Mat edges = new Mat();

	Size blurSize = new Size(9, 9);

	Size erodeSize = new Size(10, 10);
	Size dilateSize = new Size(10, 10);
	Size edgeDilateSize = new Size(4, 4);

	while (!Thread.interrupted()) {

	    cvSink.grabFrame(base);

	    // SmartDashboard.putString("image", image.toString());
	    // base.copyTo(mat);
	    Imgproc.blur(base, mat, blurSize);
	    // Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new
	    // Size(20, 20));
	    // Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new
	    // Size(20, 20));

	    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
	    /*
	     * SmartDashboard.putNumber("Lower Left 0", mat.get(0, 0)[0]);
	     * SmartDashboard.putNumber("Lower Left 1", mat.get(0, 0)[1]);
	     * SmartDashboard.putNumber("Lower Left 2", mat.get(0, 0)[2]);
	     */
	    Scalar colorStart = new Scalar(SmartDashboard.getNumber("Hue min", 10),
		    SmartDashboard.getNumber("Saturation min", 100), SmartDashboard.getNumber("Value min", 25));
	    Scalar colorEnd = new Scalar(SmartDashboard.getNumber("Hue max", 50),
		    SmartDashboard.getNumber("Saturation max", 255), SmartDashboard.getNumber("Value max", 255));

	    Core.inRange(mat, colorStart, colorEnd, mat);
	    // Imgproc.dilate(mat, mat, dilateElement);
	    // Imgproc.erode(mat, mat, erodeElement);

	    // for (int r = 0; r < base.rows(); r++) {
	    // for (int c = 0; c < base.cols(); c++) {
	    // if (mat.get(r, c)[0] == 0) {
	    // base.put(r, c, new double[] { 0, 0, 0 });
	    // }
	    // }
	    // }
	    Imgproc.erode(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, erodeSize));
	    Imgproc.dilate(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, dilateSize));

	    Imgproc.cvtColor(base, grey, Imgproc.COLOR_BGR2GRAY);
	    // Core.multiply(grey, new Scalar(3), grey);
	    Imgproc.Canny(grey, edges, SmartDashboard.getNumber("Canny max", 100),
		    SmartDashboard.getNumber("Canny min", 200));

	    Imgproc.dilate(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, edgeDilateSize));

	    Imgproc.erode(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)));
	    // new Size(20, 20)));

	    Core.bitwise_not(edges, edges);
	    Core.bitwise_and(mat, edges, mat);
	    HSVOutputStream.putFrame(edges);
	    finalContours.clear();
	    Imgproc.findContours(mat, finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
	    rects.clear();
	    for (int i = 0; i < finalContours.size(); i++) {
		RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(finalContours.get(i).toArray()));
		if (rect.size.height > 10 && rect.size.width > 10 /*
								   * && rect.size.width / rect.size.height > .6 &&
								   * rect.size.width / rect.size.height < 1.5
								   */ && rect.angle < 10)
		    rects.add(rect);
	    }
	    double closest = -1;
	    int smallestI = 0;
	    for (int i = 0; i < rects.size(); i++) {
		if (rects.get(i).size.height * rects.get(i).size.height < closest) {
		    closest = rects.get(i).size.height * rects.get(i).size.height;
		    smallestI = i;
		}
	    }
	    rectChoice = smallestI;
	    SmartDashboard.putNumber("Rects", smallestI);
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
	    // try {
	    // sleep(50);
	    // } catch (InterruptedException e) {
	    //
	    // }
	}
    }

    // TODO: is this used?
    public void setRectangleChoice(int rectIn) {
	rectChoice = rectIn;
    }

    // TODO: is this used?
    public RotatedRect getRectChoice() {
	try {
	    return rects.get(rectChoice);
	} catch (IndexOutOfBoundsException e) {
	    return new RotatedRect();
	}
    }

    /**
     * Used to determine if the cube is centered in the camera's view.
     * 
     * @return The number of pixels the cube is off center
     */
    public int getError() {
	if (rectChoice >= 0 && rects.size() > 0 && rects.get(rectChoice) != null) {
	    try {
		return (int) (160 - rects.get(rectChoice).center.x);
	    } catch (NullPointerException e) {
		return Integer.MAX_VALUE;
	    }
	} else {
	    return 0;
	}
    }

}