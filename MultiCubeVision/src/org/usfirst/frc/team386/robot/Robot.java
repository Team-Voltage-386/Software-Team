/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team386.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.RotatedRect;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
//	Servo xServo = new Servo(0);
//	Servo yServo = new Servo(0);
	CubeVisionThread cubeVision= new CubeVisionThread();
//	public CvSource HSVOutputStream, rectOutputStream;
	@Override
	public void robotInit() {
		
		//HSVOutputStream = CameraServer.getInstance().putVideo("Colors", 480, 300);
        //rectOutputStream = CameraServer.getInstance().putVideo("Rectangles", 480, 300);
	}

	@Override
	public void autonomousInit() {
		cubeVision.start();
	}

	@Override
	public void autonomousPeriodic() {
		//System.out.println("Running");
		List<RotatedRect> tempList = new ArrayList<>();
		for(RotatedRect rect : tempList) {}
		//HSVOutputStream.putFrame(cubeVision.outHSV);
		//rectOutputStream.putFrame(cubeVision.outRectMat);
	}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testPeriodic() {
	}
}
