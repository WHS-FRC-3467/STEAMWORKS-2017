package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Pivot extends Command {

	private boolean ACTUATE;
	
    public Pivot(boolean actuate) {
        ACTUATE = actuate;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(ACTUATE)
    		Pneumatics.getInstance().gearCatchDown();
    	else
    		Pneumatics.getInstance().gearCatchUp();
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
    }
}
