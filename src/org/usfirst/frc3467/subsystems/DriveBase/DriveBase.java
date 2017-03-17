
package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.PIDF_CANTalon;
import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//
//  DriveBase Subsystem
//
public class DriveBase extends Subsystem {
	private Servo latchServo;
	private PowerDistributionPanel pdp;

	private static CANTalon rTalon1, rTalon2, rTalon3, lTalon1, lTalon2, lTalon3;
	private CANTalon cTalon1, cTalon2;
	private PIDF_CANTalon rightTalon, leftTalon, centerTalon;
	private static final double width = 1;
    
	private static DriveBase dBInstance;
	private static RobotDrive dBase;
	// PIDF Update flag
	private boolean m_updatePIDF = false;
	private TalonControlMode 	t_controlMode;

	/* Speed mode PID constants */
	private double OUTERPID_P = 0.1;
	private double OUTERPID_I = 0.0;
	private double OUTERPID_D = 0.0;
	private double OUTERPID_F = 0.5;
	
	private double CENTERPID_P = 0.1;
	private double CENTERPID_I = 0.0;
	private double CENTERPID_D = 0.0;
	private double CENTERPID_F = 0.5;
	
	// Drive stick conversion factors (1.0 for Voltage Control)
	private double m_outerMaxOutput = 1.0;
	private double m_centerMaxOutput = 1.0;

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

	// Default drive mode to Field-centric
	private int current_driveMode = driveMode_FieldCentric;
	
	public static DriveBase getInstance() {
		return DriveBase.dBInstance;
	}
	
	public void straightTime(double speed) {
		rTalon1.set(-speed);
		lTalon1.set(speed);
	}
	
	public DriveBase() {
	
		dBInstance = this;
		
		// Initialize Talons
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
		
		// Create PIDF_CANTalon wrappers for adjusting PIDF constants on SmartDash
		leftTalon = new PIDF_CANTalon("Left Talon", lTalon1, 0.0, true, true);
		rightTalon = new PIDF_CANTalon("Right Talon", rTalon1, 0.0, true, true);
		centerTalon = new PIDF_CANTalon("Center Talon", cTalon1, 0.0, true, true);

		/*
		 * COnfigure Talons for Speed control
		 */
		lTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		lTalon1.configPeakOutputVoltage(+12.0f, -12.0f);
		lTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		lTalon1.setProfile(0);
		leftTalon.setPID(OUTERPID_P, OUTERPID_I, OUTERPID_D, OUTERPID_F);
		
		rTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		rTalon1.configPeakOutputVoltage(+12.0f, -12.0f);
		rTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		rTalon1.setProfile(0);
		rightTalon.setPID(OUTERPID_P, OUTERPID_I, OUTERPID_D, OUTERPID_F);
		
		cTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		cTalon1.configPeakOutputVoltage(+12.0f, -12.0f);
		cTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		cTalon1.setProfile(0);
		centerTalon.setPID(CENTERPID_P, CENTERPID_I, CENTERPID_D, CENTERPID_F);

		// Limit current through the center wheel (?)
		// cTalon1.EnableCurrentLimit(true);
		// cTalon1.setCurrentLimit(30);

		// Correct encoder counting directions
		//lTalon1.reverseSensor(true);
		//rTalon1.reverseSensor(true);
		
		// All drive Talons should coast
		lTalon1.enableBrakeMode(true);
		rTalon1.enableBrakeMode(true);
		cTalon1.enableBrakeMode(true);
		
		// Climber Latch
		latchServo = new Servo(RobotMap.climberLatch_Servo);
		
		pdp = new PowerDistributionPanel();
		
		// Setup Safety management for CANTalons
		lTalon1.setSafetyEnabled(true);
		rTalon1.setSafetyEnabled(true);
		cTalon1.setSafetyEnabled(true);
		lTalon1.setExpiration(0.5);
		rTalon1.setExpiration(0.5);
		cTalon1.setExpiration(0.5);
		
		// Set up a RobotDrive object for normal driving
		dBase = new RobotDrive(lTalon1, rTalon1);
		
		//RobotDrive Parameters
		dBase.setSafetyEnabled(false);  // Will turn this on once we actually start using it
		dBase.setExpiration(1.0);
		dBase.setSensitivity(0.5);
		dBase.setMaxOutput(1.0);

		// Set default control Modes for Master CANTalons
		this.setSpeedMode();
		
		// Start in Field-centric mode
		setDriveMode(driveMode_FieldCentric);
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

    	// Manage speed controller safety
    	switch (current_driveMode) {
			case driveMode_FieldCentric:
			case driveMode_RobotCentric:
			case driveMode_Precision:
				lTalon1.setSafetyEnabled(true);
				rTalon1.setSafetyEnabled(true);
				cTalon1.setSafetyEnabled(true);
				dBase.setSafetyEnabled(false);
				break;
				
			case driveMode_Arcade:
			case driveMode_Tank:
				dBase.setSafetyEnabled(true);
				lTalon1.setSafetyEnabled(false);
				rTalon1.setSafetyEnabled(false);
				cTalon1.setSafetyEnabled(false);
				break;
		}
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
		m_outerMaxOutput = 1.0;
		m_centerMaxOutput = 1.0;
		dBase.setMaxOutput(m_outerMaxOutput);
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
			
			// Right side is flipped?
			//rTalon1.reverseOutput(true);
		}
        /*
         *  Set maximum velocity of our wheels (in counts per 0.1 second)
	     *	
		 *  Encoders: 2048 ticks per revolution
	     *	
	     *	Outer Wheel Circumference. = 4 in * 3.14159	
	     *	Center Wheel Circumference = 3.5 in * 3.14159
	     *
	     *	Outer Wheel: 1 wheel rotation per 2 encoder rotations
	     *  Center Wheel: 44 wheel rotation per 33 encoder rotations
	     *
	     *	16fps -> 6,258 counts/0.1 seconds (Left & Right)
		 *			 2,763 counts/0.1 seconds (Center)
		 *
		 *	Values computed by the RobotDrive code from the OI input (usually -1 -> +1)
		 *	will be multiplied by this value before being sent to the controllers' set() methods.
		 *
		 *	If drive stick(s) max out too early, lower this value.
	     */
		m_outerMaxOutput = 1000.; //6258.0;  // encoder counts per 0.1 seconds (native units)
		m_centerMaxOutput = 500. ;//2763.0;
		dBase.setMaxOutput(m_outerMaxOutput);
	}
	
	public TalonControlMode getControlMode() {
		return t_controlMode;
	}
	
    /*
     *	Drive robot-centric 
     */
	public void driveRobotCentric(double x, double y, double z) {
    	
    	final double xScale = 1.5;
   
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
    	
    	lTalon1.set(-left * m_outerMaxOutput);
    	rTalon1.set(right * m_outerMaxOutput);
    	cTalon1.set(center * m_centerMaxOutput);
    	refreshPIDF();
    }
    
    /*
     *	Drive field-centric 
     */
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
		refreshPIDF();
    }
    
	// pass-thru to RobotDrive method (drive using 2 sticks)
    public void driveTank(double leftStick, double rightStick, boolean square) {
    	checkFeetBeforeRobotDrive(leftStick, rightStick);    	
    	dBase.tankDrive(leftStick, rightStick, square);
		refreshPIDF();
    }
    
	// pass-thru to RobotDrive method (used in autonomous)
	public void drive(double outputMagnitude, double curve) {
    	checkFeetBeforeRobotDrive(outputMagnitude, curve);    	
		dBase.drive(outputMagnitude, curve);
		refreshPIDF();
	}

	/*
	 * Refresh PIDF constants?
	 */
	public void flagPIDFUpdate() {
		m_updatePIDF = true;
	}
	
	private void refreshPIDF() {

		if (m_updatePIDF == false) return;
		
		leftTalon.updatePIDF();
		rightTalon.updatePIDF();
		centerTalon.updatePIDF();
		
		m_updatePIDF = false;
	}
	
    /*
     *	Make sure traction feet are up before driving 
     */
	private void checkFeetBeforeRobotDrive(double x, double y) {
		if (tractionFeetState == true && 
    		(x > 0.05 || y > 0.05 ||
    		x < -0.05 || y < -0.05 ))
    	{
    		liftFeetBeforeDriving();
    	}
	}

	/**
	 * Get the distance traveled in a straight line since encoders were last reset
	 * @return Average of the encoder values from the left and right encoders
	 */
	public double getDistance() {
		return ((lTalon1.getPosition()) + (rTalon1.getPosition()))/2;
	}

	public void reportEncoders() {
		SmartDashboard.putNumber("Left Encoder", lTalon1.getPosition());
		SmartDashboard.putNumber("Right Encoder", rTalon1.getPosition());			
		SmartDashboard.putNumber("Center Encoder", cTalon1.getPosition());			
	}

	public void resetEncoders() {
		lTalon1.setPosition(0);
		rTalon1.setPosition(0);
		cTalon1.setPosition(0);
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

