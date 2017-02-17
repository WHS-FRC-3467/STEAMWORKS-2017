package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

public class FieldCentricDrive extends CommandBase {
	
	public FieldCentricDrive() {
		requires(driveBase);
		this.setInterruptible(true);
	}
	
	protected void initialize() {
	}

	protected void execute() {
		driveBase.driveFieldCentric(oi.getDriveX(), oi.getDriveY(), oi.getDriveRotation(), gyro.getAngle() - 180.0);
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
