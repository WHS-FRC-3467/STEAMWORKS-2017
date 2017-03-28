package org.usfirst.frc3467.subsystems.Climber;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;

import com.ctre.CANTalon;

/**
 *
 */
public class Climber extends Subsystem {

	// Controls display SmartDashboard
	private static final boolean debugging = true;

	private Servo latchServo;
	private CANTalon climbMotor;

    // Constructor
	public Climber() {

		// Climber Latch
		latchServo = new Servo(RobotMap.climberLatch_Servo);
		
		// Climber motor
		climbMotor = DriveBase.getInstance().getMiddleTalon();
		
		setLatchServo(RobotMap.climberLatch_DISENGAGED);

	}
	
    public void initDefaultCommand() {
		// No default command!
    }

    public void driveClimber (double speed) {

    	if (debugging) {
	    	SmartDashboard.putNumber("Climbing Speed", speed);
		}
		climbMotor.set(speed);
	}
	
	public void setLatchServo(double value) {
		latchServo.set(value);;
	}
	
	
}

