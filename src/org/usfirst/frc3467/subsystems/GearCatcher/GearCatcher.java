package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearCatcher extends Subsystem {

	boolean catcherState = true; // true = up
	
	private Victor gearIntake;// = new Victor(RobotMap.gearIntake_Victor);
	private DigitalInput gearIn;// = new DigitalInput(RobotMap.gearTransistor);
	
	public GearCatcher() {
		gearIntake = new Victor(RobotMap.gearIntake_Victor);
		gearIn = new DigitalInput(RobotMap.gearTransistor);
	}
	
    public void initDefaultCommand() {
    }
    
    public boolean getCatcherState() {
    	return catcherState;
    }
    
    public void setCatcherState(boolean newState) {
    	catcherState = newState;
    }

    public void runGearIntake(double speed)
    {
    	gearIntake.set(speed);
    }
    
    public boolean getState()
    {
    	SmartDashboard.putBoolean("Gear in: ", gearIn.get());
    	return gearIn.get();
    }   
    
	public void catcherUp() {
		
		if (this.catcherState == false)
			Pneumatics.getInstance().gearIntakeUp();
	   	this.catcherState = true;
	}

	public void catcherDown() {

		if (this.catcherState == true)
			Pneumatics.getInstance().gearIntakeDown();
	   	this.catcherState = false;
	}
}

