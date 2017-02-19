package org.usfirst.frc3467.subsystems.FloorIntake;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.usfirst.frc3467.robot.CommandBase;

public class IntakeDrive extends CommandBase {

	private Value UpnDown;
	
	public IntakeDrive(Value upDown) {
		// TODO Auto-generated constructor stub
		requires (flr_intake);
		UpnDown = upDown;
		setTimeout(1.0);
	}
	public IntakeDrive(){
		requires (flr_intake);
	}

	
	public IntakeDrive(double kintake) {
		// TODO Auto-generated constructor stub
	}
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	
	protected void execute() {
		// TODO Auto-generated method stub
		//flr_intake.setIntake(UpnDown);
	}

	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	
	protected void end() {
		// TODO Auto-generated method stub
		//flr_intake.setIntake(Value.kOff);
	}

	
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
