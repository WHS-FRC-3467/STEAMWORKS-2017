package org.usfirst.frc3467.subsystems.Claw.commands;

import org.usfirst.frc3467.robot.CommandBase;

public class Pivot extends CommandBase {

	private boolean wrist;
	
    public Pivot(boolean flip) {
        wrist = flip;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(wrist)
    		claw.down();
    	else
    		claw.up();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
