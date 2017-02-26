package org.usfirst.frc3467.subsystems.PixyCam;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3467.subsystems.PixyCam.PixyCmu5;

/**
 *
 */
public class PixyCam extends Subsystem {

	PixyCmu5 pixyCamera;
	List<PixyCmu5.PixyFrame> pixyFrame;
	
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
    		
    	} catch (RuntimeException ex ) {
	        DriverStation.reportError("Error instantiating Pixy:  " + ex.getMessage(), true);
	    }
    	
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new displayPixyData());
    }
    
    
    public void detectTarget() {

    	double degFromCenterX = 0;
    	double degFromCenterY = 0;

    	// If an object is detected in the frame
		if(!pixyCamera.getCurrentframes().isEmpty())
		{
			SmartDashboard.putBoolean("Target Detected", true);
			
			try
			{
				// Calculate the number of degrees from the center the current frame 
				degFromCenterX = PixyCmu5.degreesXFromCenter(pixyCamera.getCurrentframes().get(0));
				degFromCenterY = PixyCmu5.degreesYFromCenter(pixyCamera.getCurrentframes().get(0));
				SmartDashboard.putString("AimX", Double.toString(degFromCenterX) + " degrees from target");
				SmartDashboard.putString("AimY", Double.toString(degFromCenterY) + " degrees from target");
			
			} catch  (RuntimeException ex ){

			}
		} else {
			SmartDashboard.putBoolean("Target Detected", false);
			SmartDashboard.putString("AimX", "No visible target");
			SmartDashboard.putString("AimY", "No visible target");
		}

    }
    public double[] boilerDistance() {
    	double tapePosx = 0;
    	double tapePosy = 0;
    	try
    	{
    		tapePosy = PixyCmu5.degreesYFromCenter(pixyCamera.getCurrentframes().get(0));
    		tapePosx = PixyCmu5.degreesXFromCenter(pixyCamera.getCurrentframes().get(0));
    	} catch (RuntimeException ex ) {
    	}
		return new double[] {tapePosx, tapePosy};
    }
}

