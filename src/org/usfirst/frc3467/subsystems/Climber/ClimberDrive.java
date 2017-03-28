package org.usfirst.frc3467.subsystems.Climber;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;;

public class ClimberDrive extends CommandBase {

	public ClimberDrive(){
		requires(climber);
		requires(driveBase);
	}
	
	protected void initialize() {
		climber.setLatchServo(0);
		driveBase.setVoltageMode();
		driveBase.getMiddleTalon().EnableCurrentLimit(true);
		driveBase.getMiddleTalon().setCurrentLimit(50);
		driveBase.tractionExtend();
	}

	protected void execute() {
	
		double speed = 0;
		
		speed = -1*OI.driverPad.getRightStickY();
		
		//deadband
		if (speed > -0.2 && speed < 0.2) speed = 0.0;
       
		// Square the inputs (while preserving the sign) to increase
		// fine control while permitting full power
		if (speed>= 0.0)
			speed = (speed*speed);
		else
			speed = -(speed*speed);

		climber.driveClimber(speed);
	}
	
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		climber.driveClimber(0);
		driveBase.setSpeedMode();
		driveBase.tractionRetract();
	}

	protected void interrupted() {
		end();
	}

}
