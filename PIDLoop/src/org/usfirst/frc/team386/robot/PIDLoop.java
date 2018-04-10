package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.Timer;

public class PIDLoop {
    double KP, KD, KI;
    private Timer timer = new Timer();

    public PIDLoop(double kp, double kd, double ki) {
	this.KP = kp;
	this.KD = kd;
	this.KI = ki;
	timer.start();
	previousTime = 0;
	integral = 0;
    }

    double derivative, integral, previousTime = 0, previousError = 0;

    public double getValue(double error) {
	double time = timer.get();
	derivative = (error - previousError) / (time - previousTime);
	integral += error * (time - previousTime);
	double value = KP * error + KD * derivative + KI * integral;
	previousTime = time;
	previousError = error;
	return value;
    }

}