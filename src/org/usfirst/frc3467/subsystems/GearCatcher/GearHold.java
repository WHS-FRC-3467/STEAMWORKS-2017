package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class GearHold extends CommandBase {

    public GearHold() {
        requires(gearcatch);
    }

    protected void initialize() {
    }

    // Run gear intake as long as we aren't holding a gear (or if we have one and it moves out of position)
    protected void execute() {

    	if(!gearcatch.isGearHeld()) {
        	gearcatch.runGearIntake(gearcatch.GEAR_INTAKE_SPEED);
    	} else {
        	gearcatch.runGearIntake(0.0);
    	}
    }

    // Don't end until another command overrides this one
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	gearcatch.runGearIntake(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
