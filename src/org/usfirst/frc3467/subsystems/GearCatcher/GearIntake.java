package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntake extends CommandBase {

    public GearIntake() {
    	requires(pneumatics);
        requires(gearcatch);
        setTimeout(5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gearcatch.catcherDown();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearcatch.runGearIntake(.3);
    	if(gearcatch.getState()){
    		gearcatch.catcherUp();
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
