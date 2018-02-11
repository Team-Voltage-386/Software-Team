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

    @Override
    public void run() {

	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	camera.setResolution(resolutionWidth, resolutionHeight);
	camera.setExposureManual(25);
	camera.setWhiteBalanceManual(10);
	// camera.setWhiteBalanceManual(value);
	camera.setFPS(10);
	CvSink cvSink = CameraServer.getInstance().getVideo();
	HSVOutputStream = CameraServer.getInstance().putVideo("Colors", resolutionWidth, resolutionHeight);
	rectOutputStream = CameraServer.getInstance().putVideo("Rectangles", resolutionWidth, resolutionHeight);
	cvSink.grabFrame(mat);
	Mat base = new Mat();
	Mat mat = new Mat();
	Mat grey = new Mat();
	Mat edges = new Mat();

	Size blurSize = new Size(9, 9);
	Scalar colorStart = new Scalar(10, 100, 0);
	Scalar colorEnd = new Scalar(50, 255, 255);
	Size erodeSize = new Size(10, 10);
	Size dilateSize = new Size(10, 10);
	Size edgeDilateSize = new Size(2, 2);

	while (!Thread.interrupted()) {

	    cvSink.grabFrame(base);

	    SmartDashboard.putString("image", image.toString());

	    Imgproc.blur(base, mat, blurSize);
	    // Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new
	    // Size(20, 20));
	    // Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new
	    // Size(20, 20));

	    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
	    SmartDashboard.putNumber("Lower Left 0", mat.get(0, 0)[0]);
	    SmartDashboard.putNumber("Lower Left 1", mat.get(0, 0)[1]);
	    SmartDashboard.putNumber("Lower Left 2", mat.get(0, 0)[2]);
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
	    Core.multiply(grey, new Scalar(3), grey);
	    Imgproc.Canny(grey, edges, 120, 200);

	    Imgproc.dilate(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, edgeDilateSize));
	    HSVOutputStream.putFrame(edges);
	    Imgproc.erode(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3)));
	    // new Size(20, 20)));

	    Core.bitwise_not(edges, edges);
	    Core.bitwise_and(mat, edges, mat);

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
	    try {
		sleep(50);
	    } catch (InterruptedException e) {

	    }
	}
    }

    public void setRectangleChoice(int rectIn) {
	rectChoice = rectIn;
    }

    public RotatedRect getRectChoice() {
	try {
	    return rects.get(rectChoice);
	} catch (IndexOutOfBoundsException e) {
	    return new RotatedRect();
	}
    }

    public int getError() {
	try {
	    return (int) (160 - rects.get(rectChoice).center.x);
	} catch (IndexOutOfBoundsException e) {
	    return 0;
	}
    }

}
