
package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//
//
//
public class DriveBase extends Subsystem {
	private Servo latchServo;
	private PowerDistributionPanel pdp;

	private static CANTalon rTalon1, rTalon2, rTalon3, lTalon1, lTalon2, lTalon3, cTalon1, cTalon2;
	private static final double width = 1;
    
	private static DriveBase dBInstance;
	private static RobotDrive dBase;
	private TalonControlMode 	t_controlMode;
	private double m_maxOutput = 1.0;

	public boolean tractionFeetState = false; // false = up; true = down
	
	public static final int driveMode_FieldCentric = 0;
	public static final int driveMode_RobotCentric = 1;
	public static final int driveMode_Arcade = 2;
	public static final int driveMode_Precision = 3;
	public static final int driveMode_Tank = 4;
	
	private static final String[] driveModeNames = {
			"Field-Centric",
			"Robot-Centric",
			"Arcade",
			"Precision",
			"Tank"
	};

	private int current_driveMode = driveMode_FieldCentric;
	
	public static DriveBase getInstance() {
		return DriveBase.dBInstance;
	}
	
	public DriveBase() {
	
		dBInstance = this;
		
		rTalon1 = new CANTalon(RobotMap.drivebase_RightTalon);
		rTalon2 = new CANTalon(RobotMap.drivebase_RightTalon2);
		rTalon3 = new CANTalon(RobotMap.drivebase_RightTalon3);
		lTalon1 = new CANTalon(RobotMap.drivebase_LeftTalon);
		lTalon2 = new CANTalon(RobotMap.drivebase_LeftTalon2);
		lTalon3 = new CANTalon(RobotMap.drivebase_LeftTalon3);
		cTalon1 = new CANTalon(RobotMap.drivebase_CenterTalon);
		cTalon2 = new CANTalon(RobotMap.drivebase_CenterTalon2);
		
		lTalon2.changeControlMode(TalonControlMode.Follower);
		lTalon3.changeControlMode(TalonControlMode.Follower);
		rTalon2.changeControlMode(TalonControlMode.Follower);
		rTalon3.changeControlMode(TalonControlMode.Follower);
		cTalon2.changeControlMode(TalonControlMode.Follower);
	
		lTalon2.set(RobotMap.drivebase_LeftTalon);
		lTalon3.set(RobotMap.drivebase_LeftTalon);
		rTalon2.set(RobotMap.drivebase_RightTalon);
		rTalon3.set(RobotMap.drivebase_RightTalon);
		cTalon2.set(RobotMap.drivebase_CenterTalon);

		// Set encoders as feedback devices
		lTalon1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rTalon1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		cTalon1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		latchServo = new Servo(RobotMap.climberLatch_Servo);
		
		pdp = new PowerDistributionPanel();

		
		// Set default control Modes for Master CANTalons
		// (This will change to Speed control once encoders are installed and calibrated)
		t_controlMode = TalonControlMode.PercentVbus;
		lTalon1.changeControlMode(t_controlMode);
		rTalon1.changeControlMode(t_controlMode);
		cTalon1.changeControlMode(t_controlMode);
		m_maxOutput = 1.0;
		
		// Set up a RobotDrive object for normal driving
		dBase = new RobotDrive(lTalon1, rTalon1);
		
		//RobotDrive Parameters
		dBase.setSafetyEnabled(true);
		dBase.setExpiration(1.0);
		dBase.setSensitivity(0.5);
		dBase.setMaxOutput(1.0);

	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveBot(current_driveMode));
    }
    
    /*
     * Drive mode support
     */
    public void setDriveMode(int dMode) {
    	current_driveMode = dMode;
    }
    
    public int getDriveMode() {
    	return current_driveMode;
    }
    
    public String getDriveModeName() {
    	return driveModeNames[current_driveMode];
    }
    
    
	/**
	 * Set Talons to Voltage mode
	 */
	public void setVoltageMode() {
		
		if (t_controlMode != TalonControlMode.PercentVbus) {
			t_controlMode = TalonControlMode.PercentVbus;
			lTalon1.changeControlMode(t_controlMode);
			rTalon1.changeControlMode(t_controlMode);
			cTalon1.changeControlMode(t_controlMode);
		}
		m_maxOutput = 1.0;
		dBase.setMaxOutput(m_maxOutput);
	}
    
	/**
	 * Set Talons to Speed mode
	 */
	public void setSpeedMode() {
		
		if (t_controlMode != TalonControlMode.Speed) {
			t_controlMode = TalonControlMode.Speed;
			lTalon1.changeControlMode(t_controlMode);
			rTalon1.changeControlMode(t_controlMode);
			cTalon1.changeControlMode(t_controlMode);
		}
        /*
         *  Set maximum velocity of our wheels (in counts per 0.1 second)
	     *	
	     *	
	     *	Wheel Circ. = 4 in * 3.14159	
	     *
	     *
	     *	16fps -> ??? counts/0.1 seconds
		 *
		 *	Values computed by the RobotDrive code from the OI input (usually -1 -> +1)
		 *	will be multiplied by this value before being sent to the controllers' set() methods.
		 *
		 *	If drive stick(s) max out too early, lower this value.
	     */
		m_maxOutput = 1.0;
		dBase.setMaxOutput(m_maxOutput);
	}
	
	public TalonControlMode getControlMode() {
		return t_controlMode;
	}
	
    public void driveRobotCentric(double x, double y, double z) {
    	
    	final double xScale = 2;
   
    	if (tractionFeetState == true && 
    		(x > 0.05 || y > 0.05 || z > 0.05 ||
    		x < -0.05 || y < -0.05 || z < -0.05	))
    	{
    		liftFeetBeforeDriving();
    	}
    	
    	z = z*-1;
    	double left = (y + (width/2) * z);
    	double right = y - (width/2) * z;
    	double center = x;
    	
    	center = center*xScale;
    	
    	lTalon1.set(-left * m_maxOutput);
    	rTalon1.set(right * m_maxOutput);
    	cTalon1.set(center * m_maxOutput);
    }
    
    public void driveFieldCentric(double x, double y, double z, double angle) {

    	double radAngle = angle * (3.14159 / 180.0); // Convert degrees to radians
    	
    	double yNet  = y*Math.cos(radAngle)-x*Math.sin(radAngle);
    	
    	double xNet = x * Math.cos(radAngle) + y * Math.sin(radAngle);
    	
    	driveRobotCentric(xNet, yNet, z);
    	
    }
   
	// pass-thru to RobotDrive method (drive using one stick)
    public void driveArcade(double move, double rotate, boolean square) {

    	checkFeetBeforeRobotDrive(move, rotate);    	
    	dBase.arcadeDrive(move, rotate, square);
    }
    
	// pass-thru to RobotDrive method (drive using 2 sticks)
    public void driveTank(double leftStick, double rightStick, boolean square) {
    	checkFeetBeforeRobotDrive(leftStick, rightStick);    	
    	dBase.tankDrive(leftStick, rightStick, square);
    }
    
	// pass-thru to RobotDrive method (used in autonomous)
	public void drive(double outputMagnitude, double curve) {
    	checkFeetBeforeRobotDrive(outputMagnitude, curve);    	
		dBase.drive(outputMagnitude, curve);
	}

	private void checkFeetBeforeRobotDrive(double x, double y) {
		if (tractionFeetState == true && 
    		(x > 0.05 || y > 0.05 ||
    		x < -0.05 || y < -0.05 ))
    	{
    		liftFeetBeforeDriving();
    	}
	}

	/**
	 * @return Average of the encoder values from the left and right encoders
	 */
	public double getDistance() {
		return ((lTalon1.getPosition()) + (rTalon1.getPosition() * -1.0))/2;
	}

	public void reportEncoders() {
		SmartDashboard.putNumber("Left Encoder", lTalon1.getPosition());
		SmartDashboard.putNumber("Right Encoder", rTalon1.getPosition() * -1.0);			
	}

	public void resetEncoders() {
		lTalon1.setPosition(0);
		rTalon1.setPosition(0);
	}
	public CANTalon getMiddleTalon(){
		return cTalon1;
	}
	public CANTalon getRightTalon(){
		return rTalon1;
	}
	public CANTalon getLeftTalon(){
		return lTalon1;
	}
	public PowerDistributionPanel getPDP(){
		return pdp;
	}
	public Servo getLatchServo(){
		return latchServo;
	}

	/*
	 * Traction control
	 */
	public void liftFeetBeforeDriving() {
	   	Pneumatics.getInstance().tractionFeetRetract();
	   	this.tractionFeetState = false;
	}
	
	public void tractionExtend() {
		
		if (this.tractionFeetState == false)
			Pneumatics.getInstance().tractionFeetDeploy();
	   	this.tractionFeetState = true;
	}

	public void tractionRetract() {

		if (this.tractionFeetState == true)
			Pneumatics.getInstance().tractionFeetRetract();
	   	this.tractionFeetState = false;
	}

    
}

