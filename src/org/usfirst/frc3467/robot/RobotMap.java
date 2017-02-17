package org.usfirst.frc3467.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Pneumatics
	public static final int pneumatics_sensor_port = 1;

	//
	// CANTalons - CAN Control
	//
	// Ports: Do not start with 0, because that is the number assigned to a CANTalon by default)

	// Drivebase Controllers
	public static final int drivebase_LeftTalon = 1;
	public static final int drivebase_RightTalon = 4;
	public static final int drivebase_CenterTalon = 7;
	
	// Drivebase Slaves
	public static final int drivebase_LeftTalon2 = 2;
	public static final int drivebase_LeftTalon3 = 3;
	public static final int drivebase_RightTalon2 = 5;
	public static final int drivebase_RightTalon3 = 6;
	public static final int drivebase_CenterTalon2 = 8;

	// Climber - Same as Center Drivebase Talon
	
	// Shooter - Contact Wheels
	public static final int shooterWheel_Talon1 = 9;
	public static final int shooterWheel_Talon2 = 10;
	
	//
	// VICTORS - PWM Control
	//
	// Floor Intake
	public static final int floorIntake_Victor = 0;
	
	// High Intake
	public static final int highIntake_Victor = 1;
	
	// Shooter - Spinner & Conveyor
	public static final int shooterSpin_Victor = 2;
	public static final int shooterConv_Victor = 3;
	
	
	// SOLENOIDS
	
	// Traction
	public static final int traction_solenoid_retract = 0;
	public static final int traction_solenoid_deploy = 1;

	// Floor Intake
	public static final int floorintake_solenoid_retract = 2;
	public static final int floorintake_solenoid_extend = 3;
	
	// High Intake
	public static final int highintake_solenoid_retract = 4;
	public static final int highintake_solenoid_extend = 5;

	// GearCatcher
	public static final int gearcatch_solenoid__up = 6;
	public static final int gearcatch_solenoid__down = 7;
	
	// GearClaw
	public static final int gearclaw_solenoid__hold = 8;
	public static final int gearclaw_solenoid__release = 9;

	// Hopper (Single) 
	public static final int hopper_solenoid = 10;

	// Pushers 
	public static final int pusher_solenoid_retract = 11;
	public static final int pusher_solenoid_extend = 12;
	
}
