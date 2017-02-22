package org.usfirst.frc3467.subsystems.FloorIntake;

import org.usfirst.frc3467.robot.CommandBase;

public class IntakeDrive extends CommandBase {

	private double UpnDown;
	
	public IntakeDrive(double upDown) {
		// TODO Auto-generated constructor stub
		requires (flr_intake);
		UpnDown = upDown;
		setTimeout(1.0);
	}
	public IntakeDrive(){
		requires (flr_intake);
	}

	protected void initialize() {
		// TODO Auto-generated method stub

	}
	
	protected void execute() {
		// TODO Auto-generated method stub
		flr_intake.DriveAuto(UpnDown);
	}

	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	
	protected void end() {
		// TODO Auto-generated method stub
		flr_intake.DriveAuto(0);
	}

	
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
