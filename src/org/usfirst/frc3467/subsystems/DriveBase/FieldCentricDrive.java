package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FieldCentricDrive extends CommandBase {
	
	public FieldCentricDrive() {
		requires(driveBase);
		this.setInterruptible(true);
		SmartDashboard.putString("Drive Mode", "Field Centric");
	}
	
	protected void initialize() {
	}

	protected void execute() {
		driveBase.driveFieldCentric(oi.getDriveX()*2, oi.getDriveY(), oi.getDriveRotation(), gyro.getAngle());
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
