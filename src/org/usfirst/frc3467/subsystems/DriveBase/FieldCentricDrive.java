package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;

public class FieldCentricDrive extends CommandBase {
	
	public FieldCentricDrive() {
		requires(driveBase);
		this.setInterruptible(true);
	}
	
	protected void initialize() {
	}

	protected void execute() {
		driveBase.driveFieldCentric(oi.getPrimeX(), oi.getPrimeY()  );
		
		gyro.reportGyroValues();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
