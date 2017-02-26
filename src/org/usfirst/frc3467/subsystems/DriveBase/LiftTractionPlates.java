package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class LiftTractionPlates extends CommandBase {

    public LiftTractionPlates() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveBase.tractionRetract();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
