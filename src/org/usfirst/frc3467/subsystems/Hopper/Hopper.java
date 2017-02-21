package org.usfirst.frc3467.subsystems.Hopper;


import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Hopper extends Subsystem {
	
	boolean hopperState = true; // true = closed
	
	public Hopper(){
	}
	
    public boolean getHopperState() {
    	return hopperState;
    }
    
    public void setHopperState(boolean newState) {
    	hopperState = newState;
    }

	public void expand(){
		Pneumatics.getInstance().hopperExpand();
	}
	public void contract(){
		Pneumatics.getInstance().hopperContract();
	}
	
	protected void initDefaultCommand() {
		
	}

}
