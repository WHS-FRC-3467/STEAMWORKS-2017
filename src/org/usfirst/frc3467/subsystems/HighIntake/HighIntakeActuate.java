package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class HighIntakeActuate extends CommandBase {

	private boolean ACTUATE;
	
    public HighIntakeActuate(boolean actuate) {
       ACTUATE  = actuate;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(ACTUATE)
    		hi_intake.hiIntakeExtend();
    	else
    		hi_intake.hiIntakeRetract();
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
