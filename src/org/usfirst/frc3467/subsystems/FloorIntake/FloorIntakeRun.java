package org.usfirst.frc3467.subsystems.FloorIntake;

import org.usfirst.frc3467.robot.CommandBase;

public class FloorIntakeRun extends CommandBase {

	private double m_speed;
	
	public FloorIntakeRun(double speed) {
		requires (flr_intake);
		m_speed = speed;
	}

	protected void initialize() {

	}
	
	protected void execute() {
		flr_intake.drive(m_speed);
	}

	
	protected boolean isFinished() {
		return isTimedOut();
	}

	
	protected void end() {
		flr_intake.drive(0.0);
	}

	
	protected void interrupted() {
		end();
	}

}
