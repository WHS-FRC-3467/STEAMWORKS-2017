package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.subsystems.FloorIntake.FloorIntake;
import org.usfirst.frc3467.subsystems.HighIntake.HighIntake;

public class AutoHigh_intake extends CommandBase{

	boolean autoHigh;
	
	public AutoHigh_intake(boolean DriveOut){
		requires(hi_intake);
		autoHigh = DriveOut;
		setTimeout(1);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if (autoHigh) {
			hi_intake.DriveAuto(FloorIntake.kIntake);
			}
		else {
			hi_intake.DriveAuto((FloorIntake.kStop));
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
		hi_intake.DriveAuto(0.0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}

