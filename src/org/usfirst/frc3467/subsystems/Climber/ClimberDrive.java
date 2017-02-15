package org.usfirst.frc3467.subsystems.Climber;

import org.usfirst.frc3467.robot.CommandBase;;

public class ClimberDrive extends CommandBase {

	private double velocity;
	
	public ClimberDrive(){
		requires(climber);
		velocity = 0;
	}
	public ClimberDrive(double speed){
		requires(climber);	
		velocity = speed;
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		double speed = 0;
		
		if(velocity == 0){
			speed = oi.getPrimeX();
			
			//deadband
			if (speed > -0.08 && speed < 0.08) speed = 0;
	       
			// Square the inputs (while preserving the sign) to increase
			// fine control while permitting full power
		if (speed>= 0.0)speed = (speed*speed);
		else speed = -(speed*speed);
		}
		else{
			if (velocity == Climber.kClimb){
				speed = 0;
			}
			else{
				speed = velocity;
			}
			climber.driveAuto(speed);
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		climber.driveAuto(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
