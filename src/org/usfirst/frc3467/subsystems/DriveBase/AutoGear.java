package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.subsystems.PixyCam.NoTargetException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

public class AutoGear extends CommandBase{
	int target;
	int side = 0;
	double targetAngle;
	public static final double DEFAULT_TARGET_DISTANCE = 0.8;
	public static final double ANGLE_PRECISION = 0.5;
	public static final double DISTANCE_TOLERANCE = 0.05;
	double targetDistance = 0.0;
	boolean posReached = false;
	double xOut = 0;
	double yOut = 0;
	double zOut = 0;
	boolean targetFound = false;
	
	public AutoGear(){
		this(DEFAULT_TARGET_DISTANCE);
	}

	public AutoGear(double defaultTargetDistance) {
		requires(pixyCamGear);
		requires(driveBase);
		
		this.setInterruptible(true);
		SmartDashboard.putString("DriveBase", "Auto Gear");
		
	}

	protected void initialize(){
//		posReached = false;		
	}
	
	protected void execute(){
		double[] gearData = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		try{
			gearData = pixyCamGear.getPegLocationData();
		}catch(NoTargetException ex){
			return;
		}
		double tapePosx = gearData[0];
		double tapePosx2 = gearData[1];
		double height1 = gearData[2];
		double height2 = gearData[3];
		double width1 = gearData[4];
		double width2 = gearData[5];
		double area1 = gearData[6];
		double area2 = gearData[7];
		double center = 0.0;
		double tapeEnd1 = 0.0;
		double tapeEnd2 = 0.0;
		double angle = 0.0;
		double distance1 = 0.0;
		double distance2 =0.0;
		double a = 0.0;
		
		SmartDashboard.putString("AutoGear Input", Double.toString(height1) + " " +
				Double.toString(height2) + " "+
				Double.toString(tapePosx) + " "+
				Double.toString(tapePosx2));
				
/*
 * 		if(tapePosx < 0.0 && tapePosx2 > 0.0){
 
			tapeEnd1 = tapePosx-width1/2.0;
			tapeEnd2 = tapePosx2+width2/2.0;
		}else if(tapePosx > 0.0 && tapePosx2 < 0.0){
			tapeEnd1 = tapePosx+width1/2.0;
			tapeEnd2 = tapePosx2-width2/2.0;
		}
*/		
/*
 		distanceNeo1 = 6.0*228.0/height*4.0*Math.tan(.82/2.0);
 		distanceNeo2 = 6.0*228.0/height*4.0*Math.tan(.82/2.0);
 		centerNeo = Math.pow(distance1Neo, 2.0)/2.0 + Math.pow(distance2Neo, 2.0)/2.0;
 		aNeo = Math.pow(centerNeo, 2.0) + Math.pow(distance1Neo, 2.00) + Math.pow(distance2Neo, 2.0)/2.0*distance1Neo*distance2Neo;
 		angleNeo = Math.atan(29.0*Math.tan(.82/2.0)/228.0) + Math.asin(center*Math.sin(aNeo)/centerNeo);
 		
 		distance1 = 6.0*199.0/height1*4.0*Math.tan(.82/2.0);
		distance2 = 6.0*199.0/height2*4.0*Math.tan(.82/2.0);
		center = Math.pow(distance1, 2.0)/2.0 + Math.pow(distance2,  2.0)/2.0;
		a = Math.pow(center, 2.0) + Math.pow(distance1, 2.0) + Math.pow(distance2, 2.0)/2.0*distance1*distance2;
		angle = Math.atan(29.0*Math.tan(.82/2.0)/199.0) + Math.asin(center*Math.sin(a)/center);
*/		
		angle = (tapePosx + tapePosx2) / 2.0;
		
		if(Math.abs(angle) > ANGLE_PRECISION){
			xOut = angle * -0.05;
			if (xOut > 0.2) xOut = 0.2;
			if (xOut < -0.2) xOut = -0.2;
		}else{
			xOut = 0.0;
			//yOut = 0.0;
		}
		
/*
 * 		if(Math.abs(center-targetDistance) > DISTANCE_TOLERANCE){

			xOut = (center-targetDistance)*Math.sin(angle);
			yOut = (center-targetDistance)*Math.sin(angle);
		}else{
			xOut = 0.0;                                                  
			yOut = 0.0;
		}
*/
		SmartDashboard.putString("AutoGear", "xOut: "+Double.toString(xOut));
		
//		if(xOut == 0.0 && yOut == 0.0 && zOut == 0.0){
		if(xOut == 0.0 ){
//			posReached = true;
			return;
		}
		
/*
 * 		if(xOut > 0.0) {
 
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
 */
		
  //  	driveBase.driveRobotCentric(xOut, -yOut, zOut);
      	driveBase.driveRobotCentric(xOut, 0, 0);
	}
	

	protected void end(){
//      	driveBase.driveRobotCentric(0, 0, 0);		
	}
	
	protected void interrupted(){
		end();
	}
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return posReached;
	}

}
