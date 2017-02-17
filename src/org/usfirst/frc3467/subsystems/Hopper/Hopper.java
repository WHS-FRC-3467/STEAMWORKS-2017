package org.usfirst.frc3467.subsystems.Hopper;


import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Hopper extends Subsystem {
	
	public Hopper(){
	}

	public void expand(){
		Pneumatics.getInstance().hopperExpand();
	}
	public void contract(){
		Pneumatics.getInstance().hopperContract();
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
