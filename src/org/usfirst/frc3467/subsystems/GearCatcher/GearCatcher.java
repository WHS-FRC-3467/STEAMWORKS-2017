package org.usfirst.frc3467.subsystems.GearCatcher;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearCatcher extends Subsystem {

	boolean catcherState = true; // true = up
	boolean clawState = true; // true = closed
	
    public void initDefaultCommand() {
    }
    
    public boolean getCatcherState() {
    	return catcherState;
    }
    
    public void setCatcherState(boolean newState) {
    	catcherState = newState;
    }

    public boolean getClawState() {
    	return clawState;
    }
    
    public void setClawState(boolean newState) {
    	clawState = newState;
    }
}

