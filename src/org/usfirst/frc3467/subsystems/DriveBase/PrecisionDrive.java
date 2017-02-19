package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

public class PrecisionDrive extends CommandBase {
	
	static double scaleFactor = 0.1;
	
	public PrecisionDrive() {
		requires(driveBase);
		this.setInterruptible(true);
	}
	
	protected void initialize() {
	}

	protected void execute() {
		driveBase.driveRobotCentric(oi.getDriveX() * scaleFactor,
									oi.getDriveY() * scaleFactor,
									oi.getDriveRotation() * scaleFactor);
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
