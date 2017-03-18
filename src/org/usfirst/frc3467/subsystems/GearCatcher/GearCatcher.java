package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearCatcher extends Subsystem {

	boolean catcherState = true; // true = up
	
	private Victor gearIntake = new Victor(RobotMap.gearIntake_Victor);
	private DigitalInput gearIn = new DigitalInput(RobotMap.gearTransistor);
	
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
    	return gearIn.get();
    }    
}

