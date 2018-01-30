package org.usfirst.frc.team386.robot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeVisionThread extends Thread{
	Mat image = new Mat();
	static Mat mat = new Mat();
	static Mat invMat;
	public CvSink cvSink;
//	public CvSource HSVOutputStream, rectOutputStream;
	public List<RotatedRect> rects = new ArrayList<>();
	//public List<RotatedRect> outputRects = new ArrayList<>();
	public Mat outHSV = new Mat(), outRectMat = new Mat(), hierarchy = new Mat();
	public List<MatOfPoint> finalContours = new ArrayList<>();
	public CvSource HSVOutputStream, rectOutputStream;
	DriverStation ds;
	int rectChoice;
	
	public CubeVisionThread() {
		
        //ds = DriverStation.getInstance();
	}
	@Override
	public void run() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 240);
    	CvSink cvSink = CameraServer.getInstance().getVideo();
		HSVOutputStream = CameraServer.getInstance().putVideo("Colors", 320, 240);
        rectOutputStream = CameraServer.getInstance().putVideo("Rectangles", 320, 240);
        cvSink.grabFrame(mat);
		while(!Thread.interrupted()) {	
//			try {
			Mat base = new Mat();
			cvSink.grabFrame(base);
			
			SmartDashboard.putString("image", image.toString());
			//System.out.println("Im in");
			
			Mat mat = new Mat();
			Imgproc.blur(base, mat, new Size(9, 9));
			Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 20));
			Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 20));

			Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
			Core.inRange(mat, new Scalar(20, 100, 105), new Scalar(40, 255, 255), mat);
			Imgproc.dilate(mat, mat, dilateElement);
			Imgproc.erode(mat, mat, erodeElement);
			Mat backdrop = new Mat(new Size(mat.width(), mat.height()), CvType.CV_8UC1);
			mat.copyTo(backdrop);
			for(int r = 0; r < base.rows(); r++) {
				for(int c = 0; c < base.cols(); c++) {
					if(mat.get(r, c)[0] == 0){
						base.put(r, c, new double[] {0, 0, 0});
					}
				}
			}

			Mat grey = new Mat();
			Imgproc.cvtColor(base, grey, Imgproc.COLOR_BGR2GRAY);
		    			
			Mat edges = new Mat();
			Imgproc.Canny(grey, edges, 100, 200);
			Imgproc.dilate(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 30)));
			Imgproc.erode(edges, edges, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 20)));
			Core.bitwise_not(edges, edges);
			Core.bitwise_and(mat, edges, mat);
			HSVOutputStream.putFrame(mat);
			finalContours.clear();
			Imgproc.findContours(mat, finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
			rects.clear();
			for (int i=0; i<finalContours.size(); i++)
		    {
				RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(finalContours.get(i).toArray()));
				if(rect.size.height > 20 && rect.size.width > 20 && rect.size.width < mat.width())
					rects.add(rect);
		    }
			SmartDashboard.putNumber("Rects", finalContours.size());
			for(int i = 0; i < rects.size(); i++) {
				Point[] vertices = new Point[4];
			    rects.get(i).points(vertices);
			    MatOfPoint points = new MatOfPoint(vertices);
			    if(i != rectChoice)
			    	Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(255, 255, 255), 9);
			    else
			    	Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(0, 255, 0), 9);
			}
//			for(RotatedRect r : rects) {
//				Point[] vertices = new Point[4];
//			    r.points(vertices);
//			    MatOfPoint points = new MatOfPoint(vertices);
//			    Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(255, 255, 255), 9);
//			}
			rectOutputStream.putFrame(base);
			
//			rectOutputStream.putFrame(edges);
//			Mat colorEdges = new Mat();
//			Mat contours = new Mat();
//			Mat hierarchy = new Mat();
//			Mat lineMat = new Mat(new Size(mat.width(), mat.height()), CvType.CV_8UC1);
//			
//			Imgproc.HoughLinesP(edges, contours, mat.width() / mat.height() , Math.PI/180, 1);
//			//System.out.println(contours.rows());
//			for (int i = 0; i < contours.rows(); i++) {
//		        double data[] = contours.get(i, 0);
//		        Imgproc.line(lineMat, new Point(data[0], data[1]), new Point(data[2], data[3]), new Scalar(255));
//		        }
//
//			for(int r = 0; r < base.rows(); r++) {
//				for(int c = 0; c < base.cols(); c++) {
//					if(lineMat.get(r, c)[0] == 255){
//						mat.put(r, c, new double[] {0});
//					}
//				}
//			}
//			Imgproc.erode(mat, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 30)));
//
//			//ImageIO.write(matToBufferedImage(mat), "PNG", new File("lines.png"));
//			List<MatOfPoint> finalContours = new ArrayList<MatOfPoint>();
//
//			Imgproc.findContours(mat, finalContours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//			rects.clear();
//
//			for (int i=0; i<finalContours.size(); i++)
//		    {
//				RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(finalContours.get(i).toArray()));
//				if(rect.size.height > 20 && rect.size.width > 20 && rect.size.width < mat.width())
//					rects.add(rect);
//		    }
//			for(RotatedRect r : rects) {
//				Point[] vertices = new Point[4];
//			    r.points(vertices);
//			    MatOfPoint points = new MatOfPoint(vertices);
//			    Imgproc.drawContours(base, Arrays.asList(points), -1, new Scalar(255, 255, 255), 9);
//			}
//			outputRects = rects;
//			rectOutputStream.putFrame(base);
//			}
//			catch(Exception e) {
//				System.out.println("oops");
//			}
		}
		//cvSink.setEnabled(false);
	}
	public void setRectangleChoice(int rectIn) {
		try {
			rectChoice = rectIn;
		}
		catch(IndexOutOfBoundsException e) {
		}
	}
	public RotatedRect getRectChoice() {
		try {
		return rects.get(rectChoice);
	}
	catch(IndexOutOfBoundsException e) {
		return new RotatedRect();
	}
	}
	public int getError() {
		try {
		return (int) (160 - rects.get(rectChoice).center.x);
		}
		catch(IndexOutOfBoundsException e) {
			return 0;
		}
	}
}