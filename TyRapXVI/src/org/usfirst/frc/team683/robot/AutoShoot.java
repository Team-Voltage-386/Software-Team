package org.usfirst.frc.team683.robot;
/*package org.usfirst.frc.team386.robot;

import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoShoot {
	
	DriverStation ds = DriverStation.getInstance();

	//A structure to hold measurements of a particle
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>
	{
		double PercentAreaToImageArea;
		double Area;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		double Center;
			
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	    };
		//Structure to represent the scores for the various tests used for target identification
		public class Scores {
			double Area;
			double Aspect;
	};

	//Images
	Image frame;
	Image binaryFrame;
	int imaqError;
	//Constants
	NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(80, 150);	/**needs to be changed. Probably*///Default hue range for yellow target 
	/*NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(0, 140);	/**needs to be changed. Probably*///Default saturation range for yellow target
	/*NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(130, 255);	/**needs to be changed. Probably*///Default value range for yellow target
	/*double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 5/3; //Target long side = 1 ft 8 in = 5/3 ft / Target height = 1 ft = 5/3 ft
	double SHORT_RATIO = 3/5;
	double SCORE_MIN = 65.0;  //Minimum score to be considered a target
	double VIEW_ANGLE = 52; //View angle for camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	Scores scores = new Scores();
	
	public void shoot()
	{
	    
	}
	
	public double pray()
	{
		// create images
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_HSL, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);

		//Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Target hue min", TARGET_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Target hue max", TARGET_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Target sat min", TARGET_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Target sat max", TARGET_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Target val min", TARGET_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Target val max", TARGET_VAL_RANGE.maxValue);
		//SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
		
		while (ds.isAutonomous() && ds.isEnabled())
		{
		    	Robot.usbCam.startCapture();
		    	Robot.usbCam.getImage(frame);
			
			//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
			TARGET_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Target hue min", TARGET_HUE_RANGE.minValue);
			TARGET_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Target hue max", TARGET_HUE_RANGE.maxValue);
			TARGET_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Target sat min", TARGET_SAT_RANGE.minValue);
			TARGET_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Target sat max", TARGET_SAT_RANGE.maxValue);
			TARGET_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Target val min", TARGET_VAL_RANGE.minValue);
			TARGET_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Target val max", TARGET_VAL_RANGE.maxValue);

			//Threshold the image looking for yellow (target color)
			NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE, TARGET_VAL_RANGE);

			//Send particle count to dashboard
			int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Masked particles", numParticles);

			//Send masked image to dashboard to assist in tweaking mask.
			CameraServer.getInstance().setImage(binaryFrame);

			//filter out small particles
			float areaMin = (float)AREA_MINIMUM;
			criteria[0].lower = areaMin;
			imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

			//Send particle count after filtering to dashboard
			numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Filtered particles", numParticles);

			if(numParticles > 0)
			{
				//Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
				{
					ParticleReport par = new ParticleReport();
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
					par.Center = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
					particles.add(par);
				}
				particles.sort(null);

				//This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
				//for the reader. Note that this scores and reports information about a single particle (single L shaped target). To get accurate information 
				//about the location of the target (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the target.
				scores.Aspect = AspectScore(particles.elementAt(0));
				SmartDashboard.putNumber("Aspect", scores.Aspect);
				scores.Area = AreaScore(particles.elementAt(0));
				SmartDashboard.putNumber("Area", scores.Area);
				boolean isTarget = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;

				//Send distance and target status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a target
				SmartDashboard.putBoolean("IsTarget", isTarget);
				SmartDashboard.putNumber("Distance", computeDistance(binaryFrame, particles.elementAt(0)));
				SmartDashboard.putNumber("X", particles.elementAt(0).Center-165);//165 is center of view
				return particles.elementAt(0).Center-165;
			} 
			else 
			{
				SmartDashboard.putBoolean("IsTarget", false);
			}
		}
		return -1;
	}

	/**
	 * Method to score if the area ratio of the particle appears to match the retro-reflective target.
	 */
	/*double AreaScore(ParticleReport report)
	{
		double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
		double ratio = report.Area/boundingArea;
		double optimalRatio = 0.23; //Obtained using smartdashboard
		return ratioToScore(ratio/optimalRatio);
	}
	
	/**
	 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. 
	 */
	/*double AspectScore(ParticleReport report)
	{
		//Target is 20"x12" so aspect should be 5/3
	    	double ratio = ((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop));
	    	double optimalRatio = 1.24; //Obtained using smartdashboard
		return ratioToScore(ratio/optimalRatio);
	}
	
	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	/*double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}
	
	/**
	 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
	 * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
	 *
	 * @param image The image to use for measuring the particle estimated rectangle
	 * @param report The Particle Analysis Report for the particle
	 * @param isLong Boolean indicating if the target is believed to be the long side of a target
	 * @return The estimated distance to the target in feet.
	 */
	/*double computeDistance (Image image, ParticleReport report) 
	{
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;
		size = NIVision.imaqGetImageSize(image);
		normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
		targetWidth = 18;
		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}
			
}*/
