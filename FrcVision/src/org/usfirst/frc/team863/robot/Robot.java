/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team863.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public UsbCamera camera;
	public CvSink cvSink;
	public CvSource outputStream;
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(1); 		/* device IDs here (1 of 2) */
	WPI_TalonSRX frontRight = new WPI_TalonSRX(4);

	/* extra talons for six motor drives */
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(2);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(5);
	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(3);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(6);
	@Override
	public void robotInit() {
		//SmartDashboard.putNumber("Hue", 0);
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = CameraServer.getInstance().getVideo();
		SmartDashboard.putNumber("HMin", 0);
		SmartDashboard.putNumber("SMin", 0);
		SmartDashboard.putNumber("VMin", 0);
		SmartDashboard.putNumber("HueMax", 180);
		SmartDashboard.putNumber("SaturationMax", 255);//10
		SmartDashboard.putNumber("ValueMax", 255);
		leftSlave1.follow(frontLeft);
    	leftSlave2.follow(frontLeft);
    	rightSlave1.follow(frontRight);
    	rightSlave2.follow(frontRight);
		frontRight.configContinuousCurrentLimit(20, 0);
		frontLeft.configContinuousCurrentLimit(20, 0);
		frontRight.enableCurrentLimit(true);
		frontLeft.enableCurrentLimit(true);
		
		frontRight.configOpenloopRamp(.1, 0);
		frontLeft.configOpenloopRamp(.1, 0);
         outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */

	Mat image = new Mat();
	ArrayList<Rect> rects = new ArrayList<Rect>();
	@Override
	public void teleopPeriodic(){
		
         

          
             cvSink.grabFrame(image);
             Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
 			 Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
 			 Imgproc.blur(image, image, new Size(7, 7));
 			 Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2HSV);
             Scalar minValues = new Scalar(SmartDashboard.getNumber("HMin", 0), SmartDashboard.getNumber("SMin", 0), SmartDashboard.getNumber("VMin", 0));
 			 Scalar maxValues = new Scalar(SmartDashboard.getNumber("HueMax", 180), SmartDashboard.getNumber("SaturationMax", 255), SmartDashboard.getNumber("ValueMax", 255));
 			 Core.inRange(image, minValues, maxValues, image);
 			 
 			Imgproc.erode(image, image, erodeElement);//Once or twice
 			/*Imgproc.erode(image, image, erodeElement);
			Imgproc.dilate(image, image, dilateElement);*/
			Imgproc.dilate(image, image, dilateElement);
			outputStream.putFrame(image);
			List<MatOfPoint> contours = new ArrayList<>();
			Mat hierarchy = new Mat();

			// find contours
			Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
			MatOfPoint2f approxCurve = new MatOfPoint2f();
			//find bounding rectangle
			rects.clear();
			for (int i=0; i<contours.size(); i++)
		    {
		        MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(0).toArray() );
		        double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
		        Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
		        MatOfPoint points = new MatOfPoint( approxCurve.toArray() );
		        Rect rect = Imgproc.boundingRect(points);
		        rects.add(rect);
		    }
			String rectsStr = "";
			for(int i = 0; i < rects.size(); i++) {
				rectsStr += rects.get(i).toString() + "\n";
			}
			
			SmartDashboard.putString("Rect M8", rectsStr);
			//double diff = 
 			//System.out.println(cvSink.getError());
         
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
