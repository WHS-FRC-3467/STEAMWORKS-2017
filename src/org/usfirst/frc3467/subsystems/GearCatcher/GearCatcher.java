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

	public final double GEAR_INTAKE_SPEED = 0.6;
	public final double GEAR_OUTPUT_SPEED = -0.4;

	private boolean catcherState = true; // true = up
	
	private Victor gearIntake;// = new Victor(RobotMap.gearIntake_Victor);
	private DigitalInput gearIn;// = new DigitalInput(RobotMap.gearTransistor);
	
	public GearCatcher() {
		gearIntake = new Victor(RobotMap.gearIntake_Victor);
		gearIn = new DigitalInput(RobotMap.gearTransistor);
	}
	
    public void initDefaultCommand() {
    }
    
    public void runGearIntake(double speed)
    {
    	gearIntake.set(speed);
    }
    
    public boolean isGearHeld()
    {
    	boolean gearGot = gearIn.get();
    	SmartDashboard.putBoolean("Gear in? :", gearGot);
    	return gearGot;
    }   
    
	public void catcherUp() {
		
		Pneumatics.getInstance().gearIntakeUp();
		this.catcherState = true;
    	SmartDashboard.putString("Gear Catcher Position", "UP");
	}

	public void catcherDown() {

		Pneumatics.getInstance().gearIntakeDown();
		this.catcherState = false;
    	SmartDashboard.putString("Gear Catcher Position", "DOWN");
	}
	
	public void toggleGearCatcherPosition() {
		if (this.catcherState == false) {
			Pneumatics.getInstance().gearIntakeUp();
			this.catcherState = true;
		} else {
			Pneumatics.getInstance().gearIntakeDown();
		   	this.catcherState = false;
		}
    	SmartDashboard.putString("Gear Catcher Position", this.catcherState ? "UP" : "DOWN");
	}
}