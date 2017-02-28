package org.usfirst.frc3467.subsystems.FloorIntake;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class ToggleFloorIntakeOperation extends CommandBase {
	
	public static boolean isIn = true;

    public ToggleFloorIntakeOperation() {
        requires(flr_intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//boolean isIn = flr_intake.getFloorState();
    	
    	if (isIn == true) {
    		pneumatics.floorIntakeExtend();
    		isIn = false;
    	}
    	else {
    		pneumatics.floorIntakeRetract();
    		isIn = true;
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
    }
}
