package org.usfirst.frc3467.subsystems.Pneumatics;

import org.usfirst.frc3467.robot.CommandBase;


/**
 *
 */
public class ToggleIntakeRamp extends CommandBase {

	public ToggleIntakeRamp() {
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Pneumatics.getInstance().toggleIntakeRamp();
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
