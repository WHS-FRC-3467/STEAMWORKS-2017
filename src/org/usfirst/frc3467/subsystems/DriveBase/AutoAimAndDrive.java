package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.subsystems.PixyCam.NoTargetException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAimAndDrive extends CommandBase {

	// Configurable parameters
	public static final double DEFAULT_TARGET_DISTANCE = .8;   // Default desired distance from goal
	public static final double ANGLE_PRECISION = 0.1;
	public static final double DISTANCE_TOLERANCE = 0.05;
	public static final double Z_SCALE = 1.7;
	
	double targetDistance = 0.0;
	double xOut = 0;
	double yOut = 0;
	double zOut = 0;
	boolean targetFound = false;

	public AutoAimAndDrive() {
		this(DEFAULT_TARGET_DISTANCE);
	}
	
	public AutoAimAndDrive(double targDist) {
		requires(driveBase);
		requires(pixyCamShooter);

		targetDistance = targDist;
		
		this.setInterruptible(true);
		SmartDashboard.putString("Drive Mode", "Auto Aim");
	}
	
	public void initialize() {
		targetFound = false;
	}
	
	public void execute() {

    	double [] pixyData = {0.0, 0.0, 0.0};
		try {
    		pixyData = pixyCamShooter.getBoilerLocationData();
    	} catch (NoTargetException ex) {
    		// No target found
    		// Need to do something to try to find target
    		return;
    	}
    	
    	// Name the returned data
		double distance = pixyData[0];
    	double angleX = pixyData[1];
    	double angleY = pixyData[2];

    	// Scale the distance based on the error (but clip it if it's too large)
    	double distanceScale = 0;
    	distanceScale = .5*Math.pow(Math.pow(Math.abs(distance-targetDistance), 3), 1.0/4);
    	if(distanceScale > .8){
    		distanceScale = .8;
    	}
   	
    	// Determine and scale the Z term
    	if(Math.abs(angleX) > ANGLE_PRECISION)
    		zOut = angleX * Z_SCALE;
    	else
    		zOut = 0.0;
    	
    	// Determine and scale the X & Y terms
    	if(Math.abs(distance-targetDistance) > DISTANCE_TOLERANCE) {
    		xOut = (distance-targetDistance)*Math.sin(angleX)*distanceScale;
    		yOut = (distance-targetDistance)*Math.cos(angleX)*distanceScale;
    	}
    	else {
    		xOut = 0;
    		yOut = 0;
    	}
    	
		// Command is completed if we are on target (i.e. all three terms are 0.0)
		if (xOut == 0.0 && yOut == 0.0 && zOut == 0.0) {
			targetFound = true;
			return;
		}

		/*if(xOut > 0){
    	xOut = Math.sqrt(xOut);
    	}else{
    		xOut = -1*Math.sqrt(xOut*-1);
    	}
    	
    	if(yOut > 0){
        	yOut = Math.sqrt(yOut);
        }else{
        	yOut = -1*Math.sqrt(yOut*-1);
        }*/
    	
    	// Modulate the X & Y terms
    	if(xOut > 0.0) {
    		xOut = Math.pow(xOut*xOut, 1.0/3);
    	}
    	else if (xOut < 0.0) {
    		xOut = -Math.pow(xOut*xOut, 1.0/3);
    	}
    	
    	if(yOut > 0.0 ) {
    		yOut = Math.pow(yOut*yOut, 1.0/3);
    	}	
    	else if (yOut < 0.0) {
    		yOut = -Math.pow(yOut*yOut, 1.0/3);
    	}
    	
    	SmartDashboard.putString("Vision Tracking", angleY+"     " +distance+ "     "+targetDistance+"     "+zOut +"     "+angleX+"     "+(angleX*Z_SCALE));
    	SmartDashboard.putString("Vision Distance Scale", "dScale " + distanceScale);

    	// Send command to the drivebase
    	driveBase.driveRobotCentric(xOut, -yOut, zOut);
	}
	
	private void searchForTarget() {
		// Turn in place until target is found
    	driveBase.driveRobotCentric(0.0, 0.0, 0.4);
	}
	
	protected boolean isFinished() {
		return targetFound;
	}
	
	public void end() {
		
	}
	
	protected void interrupted() {
		end();
	}

}
