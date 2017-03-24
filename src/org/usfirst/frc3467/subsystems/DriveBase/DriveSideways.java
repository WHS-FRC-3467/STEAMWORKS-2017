package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class DriveSideways extends CommandBase {

	double m_speed = 0.0;
	
    public DriveSideways(double time, double speed) {
        requires(driveBase);
        m_speed = speed;
        setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveBase.driveFieldCentric(m_speed, 0.0, 0.0, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveBase.driveFieldCentric(0.0, 0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
