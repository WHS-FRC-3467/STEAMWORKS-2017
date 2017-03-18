package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class GearOut extends CommandBase {

    public GearOut() {
    	requires(gearcatch);
    	setTimeout(.2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearcatch.setCatcherState(true);
    	if(isTimedOut()){
    		if(super.timeSinceInitialized() <= .5)
    			gearcatch.runGearIntake(.5);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	gearcatch.runGearIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
