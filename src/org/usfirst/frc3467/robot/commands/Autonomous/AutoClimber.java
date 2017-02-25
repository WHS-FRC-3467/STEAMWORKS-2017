package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.robot.CommandBase;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoClimber  extends CommandBase{
	PowerDistributionPanel pdp;
	int state = 0;
	final double ANGLE_THRESHOLD = 90;
	final double CLIMB_HEIGHT = 1000;
	final int TOLERANCE = 5;
	final double CURRENT_THRESHOLD = 2;
	boolean firstRepeat = true;
	boolean done = false;
	CANTalon cTalon1;
	public AutoClimber(){
		requires(driveBase);
		requires(gyro);
	}
	protected void initialize(){
		//release servo latch
		cTalon1 = driveBase.getMiddleTalon();
		pdp = driveBase.getPDP();
	}
	protected void execute(){
		if(state == 0){
			if(firstRepeat){
				cTalon1.set(1);
				firstRepeat = false;
			}
			else if(gyro.getVector()[2] > ANGLE_THRESHOLD){
				state = 1;
				firstRepeat = true;
			}
		}
		else if(state == 1){
			if(firstRepeat){
				cTalon1.setSetpoint(CLIMB_HEIGHT);
				firstRepeat = false;
			}
			else if(cTalon1.getSetpoint()-cTalon1.getPosition() < TOLERANCE){
				cTalon1.set(0);
				state = 2;
				firstRepeat = true;
			}
		}
		else if(state == 2){
			if(firstRepeat){
				cTalon1.set(.1);
				firstRepeat = false;
			}
			else if(pdp.getCurrent(0) > CURRENT_THRESHOLD){
				cTalon1.setSetpoint(cTalon1.getPosition());
				done = true;
			}
		}
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
