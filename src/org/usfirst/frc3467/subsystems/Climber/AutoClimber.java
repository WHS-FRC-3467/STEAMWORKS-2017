package org.usfirst.frc3467.subsystems.Climber;

import org.usfirst.frc3467.robot.CommandBase;

public class AutoClimber  extends CommandBase{

	int state;
	boolean done;

	final double ANGLE_THRESHOLD = 45;
	//2048 ticks per rev 44-34 rpm
	final double CLIMB_HEIGHT = 5000;
	final int TOLERANCE = 5;
	final double CURRENT_THRESHOLD = 2;
	
	public AutoClimber(){
		requires(climber);
		requires(gyro);
	}
	
	protected void initialize() {
		state = 0;
		done = false;
	}
	
	protected void execute(){

/*		switch(state) {
		case 0:
			climber.setLatchServo(1);
			climber.startPIDControl();
			state = 1;
			break;
		
		case 1:
			if(gyro.getVector()[2] > ANGLE_THRESHOLD) {
				state = 2;
			}
			break;
			
		case 2:
			cTalon1.enableControl();
			cTalon1.setSetpoint(CLIMB_HEIGHT);
			break;

		case 3:
			if(cTalon1.getSetpoint() - cTalon1.getPosition() < TOLERANCE){
				cTalon1.disableControl();
				cTalon1.set(0);
			}
		else if(state == 2){
			if(firstRepeat){
				cTalon1.set(.1);
				firstRepeat = false;
			}
			else if(pdp.getCurrent(0) > CURRENT_THRESHOLD){
				cTalon1.enableControl();
				cTalon1.setSetpoint(cTalon1.getPosition());
				done = true;
			}
		}
*/
		}

	protected boolean isFinished(){
		return done;
		
	}
	protected void end(){
		
	}
	
	protected void interrupted(){
		end();
	}

}
