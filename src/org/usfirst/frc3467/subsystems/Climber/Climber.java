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
	//Constants for speed
	public static final double kClimb = 0.7;
	public static final double kLower = -0.3;
	public static final double kStop = 0;

	private Servo latchServo;
	CANTalon cTalon1;

	//Roller class objects
//	public CANTalon climbMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Climber(){

		// Climber Latch
		//latchServo = new Servo(RobotMap.climberLatch_Servo);
		
		// Climber motor
		//cTalon1 = DriveBase.getInstance().getMiddleTalon();

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
//		climbMotor.set(speed);
		
	}
	
	public void setLatchServo(double value) {
		latchServo.set(value);;
	}
	
	
}

