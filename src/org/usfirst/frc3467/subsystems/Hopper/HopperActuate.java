package org.usfirst.frc3467.subsystems.Hopper;

import org.usfirst.frc3467.robot.CommandBase;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class HopperActuate extends CommandBase{
	
	private boolean INandOut;
	
	public HopperActuate(boolean actuate){
		requires(hopper);
		INandOut = actuate;
		setTimeout(1.0);
	}
	
	public HopperActuate(){
		requires(hopper);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
//	if (INandOut) 
//		hopper.kExtend;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
//		hopper.setHopper(Value.kOff);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	
}
