package org.usfirst.frc3467.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Analog Ports 
	public static final int pneumatics_sensor_port = 1;	//Pneumatics pressure sensor (not pressure switch!)

	// Digital Ports
	public static final int gearTransistor = 1; 	// Input for gear sensing transistor in gear pickup
	public static final int turretMaximum = 2; // Limit switch at turret maximum position
	
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
	public static final int shooterWheel_Talon1 = 10; // ("Shooter 3 on bot")
//	public static final int shooterWheel_Talon2 = 9; // ("Shooter 2 on bot")
	public static final int shooterWheel_Talon2 = 12;

	// Shooter - Turret
	//	public static final int turret_Talon = 12; //tower rotation
	public static final int turret_Talon = 9; // tower rotation

	// Shooter - Feed
	public static final int shooterFeedTower_Talon = 11; // tower conveyer belt ("Shooter 1 on bot")

	
	// 9 - sHOOTER 2 - POWERING TURRET, NO ENCODER WIRE
	// 10 - sHOOTER 3 - POWERING MOTOR W/ ENCODER
	// 11 - shOOTER 1 - POWERING tOWER W/ ENCODER
	
	
	//
	// VICTORS - PWM Control
	//
	// Gear Intake

	public static final int gearIntake_Victor = 0;
	
	// Shooter - Spinner
	public static final int shooterSpin_Victor = 2;
	
	//
	// SERVOS - PWM Control
	//
	public static final int climberLatch_Servo = 3;
	
	public static final int climberLatch_ENGAGED = 0;
	public static final int climberLatch_DISENGAGED = 1;
	
	// SOLENOIDS

	// Traction
	public static final int traction_solenoid_deploy = 5;
	public static final int traction_solenoid_retract = 0;

	// Floor Intake
	public static final int gearintake_solenoid_retract = 1;
	public static final int gearintake_solenoid_extend = 4;
	
	// Extra 
	public static final int intakeRamp_solenoid_retract = 2;
	public static final int intakeRamp_solenoid_extend = 3;
	
}
