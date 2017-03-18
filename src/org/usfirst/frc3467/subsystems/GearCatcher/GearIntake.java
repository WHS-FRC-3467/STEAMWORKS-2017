package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntake extends CommandBase {

    public GearIntake() {
        requires(gearcatch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(gearcatch.getCatcherState()== false){
    		gearcatch.setCatcherState(true);
    	}
    	gearcatch.runGearIntake(.5);
    	if(gearcatch.getState() == true){
    		gearcatch.setCatcherState(false);
    		end();
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
