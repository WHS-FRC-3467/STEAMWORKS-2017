package org.usfirst.frc3467.subsystems.PixyCam;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3467.subsystems.PixyCam.PixyCmu5;
import org.usfirst.frc3467.subsystems.PixyCam.PixyCmu5.PixyFrame;

/**
 *
 */
public class PixyCam extends Subsystem {

	PixyCmu5 pixyCamera;
	List<PixyCmu5.PixyFrame> pixyFrame;
	boolean cameraPresent = false;
	
	public PixyCam() {
    	try
    	{
    		// Pixy Port Assignment (Final & Practice Robots)
    		
    		/*
    		 * Instantiate a new Pixy object at address 168 and schedule it to read data
    		 * at a 1 second period. This data will be accessible by calling pixyCamera.getCurrentframes()
    		 */
    		pixyFrame = new LinkedList<PixyCmu5.PixyFrame>();
    	    pixyCamera = new PixyCmu5(168, .25);
    	    cameraPresent = true;
	        SmartDashboard.putString("Pixy Cam", "Running normally");
    		
    	} catch (RuntimeException ex ) {
	        DriverStation.reportError("Error instantiating Pixy:  " + ex.getMessage(), true);
	        SmartDashboard.putString("Pixy Cam", "NOT DETECTED");
	    }
    	
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new displayPixyData());
    }
    
    
    public void detectTarget() {

    	double degFromCenterX = 0;
    	double degFromCenterX2 = 0;
    	double degFromCenterY = 0;
    	double degFromCenterY2 = 0;

    	// If an object is detected in the frame
		if(cameraPresent && !pixyCamera.getCurrentframes().isEmpty())
		{
			SmartDashboard.putBoolean("Target Detected", true);
			
			try
			{
				// Calculate the number of degrees from the center the current frame 
				degFromCenterX = PixyCmu5.degreesXFromCenter(pixyCamera.getCurrentframes().get(0));
				degFromCenterY = PixyCmu5.degreesYFromCenter(pixyCamera.getCurrentframes().get(0));
				degFromCenterX2 = PixyCmu5.degreesXFromCenter(pixyCamera.getCurrentframes().get(1));
				degFromCenterY2 = PixyCmu5.degreesYFromCenter(pixyCamera.getCurrentframes().get(1));
				SmartDashboard.putString("AimX", Double.toString(degFromCenterX) + " degrees from target");
				SmartDashboard.putString("AimY", Double.toString(degFromCenterY) + " degrees from target");
				SmartDashboard.putString("AimX2", Double.toString(degFromCenterX2) + " degrees from target");
				SmartDashboard.putString("AimY2", Double.toString(degFromCenterY2) + " degrees from target");
			
			} catch  (RuntimeException ex ){

			}
		} else {
			SmartDashboard.putBoolean("Target Detected", false);
			SmartDashboard.putString("AimX", "No visible target");
			SmartDashboard.putString("AimY", "No visible target");
		}

    }
    
    /******************************
     * SteamWorks Boiler Tracking
     ******************************/
	
    // Height of the goal above ???
    static final double GOAL_HEIGHT = .5;
	
    // Angle of Pixy Cam mounting
    static final double PIXY_ANGLE = 0.174;
    
    // Array of data calculated from values returned from Pixy
    double[] distanceAndAngles = {0.0, 0.0, 0.0};  // distance, AngleX, AngleY
    double[] gearData = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; //tapePosx, tapePosx2, height1, height2, width1, width2, area1, area2
    
    /**
     * Calculate the current X & Y Angles and the distance from boiler
     * and store the values in a local array for pickup by helper routines below.
     * 
     * @throws NoTargetException
     */
    protected void calculateBoilerDistance() throws NoTargetException {

    	double tapePosx = 0;
    	double tapePosy = 0;

    	// If an object is detected in the frame
		if(cameraPresent && !pixyCamera.getCurrentframes().isEmpty())
		{
			SmartDashboard.putBoolean("Target Detected", true);
			
			try
			{
	    		tapePosy = PixyCmu5.degreesYFromCenter(pixyCamera.getCurrentframes().get(0));
	    		tapePosx = PixyCmu5.degreesXFromCenter(pixyCamera.getCurrentframes().get(0));
			} catch (RuntimeException ex ) { }

		} else {
			SmartDashboard.putBoolean("Target Detected", false);
			throw new NoTargetException("No Boiler Target Found");
		}

		distanceAndAngles[1] = tapePosx * (2 * 3.14159/360);
    	distanceAndAngles[2] = -tapePosy * (2 * 3.14159/360) + PIXY_ANGLE;
    	distanceAndAngles[0] = GOAL_HEIGHT/Math.tan(distanceAndAngles[2]);
    	
    }
    
    /**
     * Helper routine to return current distance and angles
     * 
     * @throws NoTargetException
     */
    public double[] getBoilerLocationData() throws NoTargetException {
    	calculateBoilerDistance();
    	return distanceAndAngles;
    }
    
    
    /**
     * Helper routine to return current distance
     * 
     * @throws NoTargetException
     */
    public double getBoilerDistance() throws NoTargetException {
    	calculateBoilerDistance();
    	return distanceAndAngles[0];
    }
    
    protected void calculateGearStuff() throws NoTargetException {
    	double tapePosx = 0;
    	double tapePosx2 = 0;
    	PixyFrame tape1 = pixyFrame.get(0);
    	PixyFrame tape2 = pixyFrame.get(1);
    	double height1 = 0;
    	double height2 = 0;
    	double width1 = 0;
    	double width2 = 0;
    	double area1 = 0;
    	double area2 = 0;
    	
    	// If an object is detected in the frame
		if(cameraPresent && !pixyCamera.getCurrentframes().isEmpty())
		{
			SmartDashboard.putBoolean("Target Detected", true);
			
			try
			{
	    		tapePosx = PixyCmu5.degreesXFromCenter(pixyCamera.getCurrentframes().get(0));
	    		tapePosx2 = PixyCmu5.degreesYFromCenter(pixyCamera.getCurrentframes().get(1));
	    		height1 = tape1.height;
	        	height2 = tape2.height;
	        	width1 = tape1.width;
	        	width2 = tape2.width;
	        	area1 = height1*width1;
	        	area2 = height2*width2;
	    		
			} catch (RuntimeException ex ) { }

		} else {
			SmartDashboard.putBoolean("Target Detected", false);
			throw new NoTargetException("No Gear Target Found");
		}
		gearData[0] = tapePosx;
		gearData[1] = tapePosx2;
		gearData[2] = height1;
		gearData[3] = height2;
		gearData[4] = width1;
		gearData[5] = width2;
		gearData[6] = area1;
		gearData[7] = area2;
		
    }
    
    public double[] getPegLocationData() throws NoTargetException {
		calculateGearStuff();
    	return gearData;
    }
    
}

