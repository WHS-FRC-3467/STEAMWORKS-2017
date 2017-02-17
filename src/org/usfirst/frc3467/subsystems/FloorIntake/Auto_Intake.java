package org.usfirst.frc3467.subsystems.FloorIntake;

import org.usfirst.frc3467.robot.CommandBase;

public class Auto_Intake extends CommandBase{

	boolean autoIntake;
	
	public Auto_Intake(boolean DriveOut){
		requires(flr_intake);
		autoIntake = DriveOut;
		setTimeout(1);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if (autoIntake) {
			flr_intake.DriveAuto(1.0);
			}
		else {
			flr_intake.DriveAuto(0.0);
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		flr_intake.DriveAuto(0.0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
