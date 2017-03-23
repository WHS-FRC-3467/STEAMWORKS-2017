package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class GearIntake extends CommandBase {

	boolean gearHeld = false;
	
    public GearIntake() {
        requires(gearcatch);
    }

    protected void initialize() {
        // Make sure the catcher is down before we try to intake a gear
    	gearcatch.catcherDown();
    	gearHeld = false;
    }

    // Run gear intake as long as we aren't holding a gear
    protected void execute() {

    	if(!gearcatch.isGearHeld()) {
        	gearcatch.runGearIntake(gearcatch.GEAR_INTAKE_SPEED);
    	} else {
        	gearcatch.runGearIntake(0.0);
    		gearHeld = true;
    	}
    }

    // Finish once we are holding a gear
    protected boolean isFinished() {
        return gearHeld;
    }

    // Stow gear catcher (and hopefully a gear!)
    protected void end() {
    	gearcatch.catcherUp();
    	gearcatch.runGearIntake(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
