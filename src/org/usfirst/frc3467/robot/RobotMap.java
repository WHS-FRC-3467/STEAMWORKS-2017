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

	// Drivebase
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

	// Climber
	public static final int climbMotor = 9;

	//Solenoid mappings
	
	// Hopper 
	public static final int hopper_solenoid_retract = 0;
	public static final int hopper_solenoid_deploy = 1;

	public static final int IntakeMotorOne = 0;

	public static final int IntakeMotorTwo = 0;

	public static int intake_solenoid_extend;

	public static int intake_solenoid_retract;

	
	
	
}
