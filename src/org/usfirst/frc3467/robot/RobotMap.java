package org.usfirst.frc3467.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Pneumatics
	public static final int pneumatics_sensor_port = 1;

	// DRIVEBASE
	//CANTalon Ports (best to not start with 1, because that is the number assigned to a CANTalon by default)
	public static final int drivebase_LeftTalon = 2;
	public static final int drivebase_RightTalon = 5;
	public static final int drivebase_CenterTalon = 9;
	
	// Slave CANTalons
	public static final int drivebase_LeftTalon2 = 3;
	public static final int drivebase_LeftTalon3 = 4;
	public static final int drivebase_RightTalon2 = 6;
	public static final int drivebase_RightTalon3 = 7;
	public static final int drivebase_CenterTalon2 = 10;

	//MOTORS
	// Climber
	public static final int climbMotor = 12;
	
	//Upper Intake
	public static final int LIntakeMotor = 13;
	
	//Lower Intake
	public static final int UIntakeMotor = 16;
	
	//Shooter
	public static final int shooterMotor = 11;
	public static final int beltMotor = 14;
	public static final int spinnerMotor = 15;

	//SOLENOIDS
	// Hopper 
	public static final int hopper_solenoid_retract = 0;
	public static final int hopper_solenoid_deploy = 1;

	//Upper Intake
	public static final int U_intake_extend = 2;
	public static final int U_intake_retract = 3;

	//Lower Intake
	public static final int l_intake_extend = 4;
	public static final int l_intake_retract = 5;
	
	//GearCatcher
	public static final int claw_extend = 6;
	public static final int claw_retract = 7;
	public static final int claw_grab = 8;
	public static final int claw_release = 9;
	
}
