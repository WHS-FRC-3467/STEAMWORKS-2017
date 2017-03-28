package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

/**
 * GearDelivery - assumes that bot has already driven the gear onto the spring 
 */
public class GearDeliver extends CommandBase {

    public GearDeliver() {
    	requires(gearcatch);
    	setTimeout(2.0);
    }

    // STart by lowering the gear
    protected void initialize() {
	   	gearcatch.catcherDown();
    }

    // After 1/2 second, begin rolling the gear out
    protected void execute() {

		if(super.timeSinceInitialized() >= .3) {
			gearcatch.runGearIntake(gearcatch.GEAR_OUTPUT_SPEED);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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
