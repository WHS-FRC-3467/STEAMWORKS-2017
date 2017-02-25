package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class ToggleHighIntakePosition extends CommandBase {

	private static boolean ACTUATE = false;
	
    public ToggleHighIntakePosition() {
    	requires(hi_intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(ACTUATE){
    		hi_intake.hiIntakeExtend();
    		ACTUATE = true;
    	}
    	else {
    		hi_intake.hiIntakeRetract();
    		ACTUATE = false;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
