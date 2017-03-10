package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

public class AutoGear extends CommandBase{
	int target;
	int side = 0;
	double targetAngle;
	public AutoGear(int gear){
		requires(driveBase);
		requires(pixyCam);
		requires(gyro);
		target = gear;
	}

	protected void initialize(){
		if(target == 0){
			targetAngle = 60;
			side = 0;
		}
		else if(target == 1){
			targetAngle = 0;
			side = 1;
		}
		else if(target == 2){
			targetAngle  = -60;
			side = 1;
		}
	}
	
	protected void execute(){
		double distance = pixyCam.getBoilerLocationData()[0];
		double angle = pixyCam.getBoilerLocationData()[1];
		double sidewaysMove = 0;
		if(side == 0){
			double insideAngle1 = angle + 90 + gyro.getAngle();
			double longSide = Math.sqrt(distance*distance + 8*8 - 2*distance*8*Math.cos(insideAngle1));
			double farAngle = Math.sin(insideAngle1)/longSide;
			sidewaysMove = Math.acos(0);
		}
		else{
			
		}
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		end();
	}
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
