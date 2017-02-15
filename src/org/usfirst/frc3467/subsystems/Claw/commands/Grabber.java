package org.usfirst.frc3467.subsystems.Claw.commands;

import org.usfirst.frc3467.robot.CommandBase;

public class Grabber extends CommandBase {
	
	public boolean CLOSE;

    public Grabber(boolean close) {
        CLOSE = close;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if (CLOSE)
    		claw.close();
    	else
    		claw.open();
    	claw.clawHold();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
