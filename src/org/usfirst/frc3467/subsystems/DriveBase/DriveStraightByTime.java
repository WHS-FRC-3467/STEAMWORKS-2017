package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class DriveStraightByTime extends CommandBase {

	double TIME;
	
    public DriveStraightByTime() {
        requires(driveBase);
    	setTimeout(2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveBase.setVoltageMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveBase.driveTank(0.5, 0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveBase.driveTank(0.0, 0.0);
    	driveBase.setSpeedMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
