package org.usfirst.frc3467.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;

/**
 *
 */
public class Climber extends Subsystem {
    // Controls display SmartDashboard
	private static final boolean debugging = true;
	//Constants for speed
	public static final double kClimb = 0.7;
	public static final double kLower = -0.3;
	public static final double kStop = 0;

	//Roller class objects
	public CANTalon climbMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Climber(){
		climbMotor = new CANTalon(RobotMap.climbMotor);
	}
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand()); 
    	this.setDefaultCommand(new ClimberDrive());
    }
	public void driveAuto(double speed) {
		// TODO Auto-generated method stub
		if (debugging) {
	    	SmartDashboard.putNumber("Climbing Speed", speed);
		}
		climbMotor.set(speed);
		
	}
}

