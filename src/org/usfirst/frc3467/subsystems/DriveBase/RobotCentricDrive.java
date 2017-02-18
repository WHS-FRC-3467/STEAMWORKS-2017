package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotCentricDrive extends CommandBase {
	
	public RobotCentricDrive() {
		requires(driveBase);
		this.setInterruptible(true);
		SmartDashboard.putString("Drive Mode", "Robot Centric");
		
	}
	
	protected void initialize() {
	}

	protected void execute() {
		driveBase.driveRobotCentric(oi.getDriveX(), oi.getDriveY(), oi.getDriveRotation());
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
