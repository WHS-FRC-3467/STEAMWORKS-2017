
package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.PIDF_CANTalon;
import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//
//  DriveBase Subsystem
//
public class DriveBase extends Subsystem {

	
	// Speed controllers (H-Drive)
	private CANTalon rTalon1, rTalon2, rTalon3, lTalon1, lTalon2, lTalon3;
	private CANTalon cTalon1, cTalon2;
	private PIDF_CANTalon rightTalon, leftTalon, centerTalon;
	
	// Traction feet state variable
	public boolean tractionFeetState = false; // false = up; true = down
	
	// PIDF Update flag
	private boolean m_updatePIDF = false;
	
	/*
	 * TalonControlModes: Voltage (PercentVBus) & Speed
	 */
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
	
	// Drive stick conversion factors (1.0 for Voltage Control; max speed (ticks/0.1 sec) for Speed Control)
	private double m_outerMaxOutput = 1.0;
	private double m_centerMaxOutput = 1.0;

	// Field- and Robot-centric mode factor
	private static final double robotWidth = 2;
    
	// Scaling
	//form: Max x Accel, Max y Accel, Max z Accel, Max x Vel, Max y Vel, Max z Vel, Loop Period, Robot width
	private DriveMath hScale = new DriveMath(8, 16, 16, 3, .05, robotWidth);
	
	// drive() method turning sensitivity
	double m_sensitivity = 0.5;
	
	// Drive interface mode
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

	// Default drive mode to Robot-centric
	private int current_driveInterfaceMode = driveMode_RobotCentric;
	
	//private DriveMath driveMath;
	
	// Static subsystem reference
	private static DriveBase dBInstance = new DriveBase();

	public static DriveBase getInstance() {
		return DriveBase.dBInstance;
	}
	
	protected DriveBase() {
	
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
		leftTalon = new PIDF_CANTalon("Left Talon", lTalon1, 0.0, true, false);
		rightTalon = new PIDF_CANTalon("Right Talon", rTalon1, 0.0, true, false);
		centerTalon = new PIDF_CANTalon("Center Talon", cTalon1, 0.0, true, false);

		/*
		 * Configure Talons for Speed control
		 */
		lTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		lTalon1.configPeakOutputVoltage(+12.0f, -12.0f);
		lTalon1.setNominalClosedLoopVoltage(12.0);
		lTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		lTalon1.SetVelocityMeasurementWindow(10);
		lTalon1.setProfile(0);
		leftTalon.setPID(OUTERPID_P, OUTERPID_I, OUTERPID_D, OUTERPID_F);
		
		rTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		rTalon1.configPeakOutputVoltage(+12.0f, -12.0f);
		rTalon1.setNominalClosedLoopVoltage(12.0);
		rTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		rTalon1.SetVelocityMeasurementWindow(10);
		rTalon1.setProfile(0);
		rightTalon.setPID(OUTERPID_P, OUTERPID_I, OUTERPID_D, OUTERPID_F);
		
		cTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		cTalon1.configPeakOutputVoltage(+12.0f, -12.0f);
		cTalon1.setNominalClosedLoopVoltage(12.0);
		cTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		cTalon1.SetVelocityMeasurementWindow(10);
		cTalon1.setProfile(0);
		centerTalon.setPID(CENTERPID_P, CENTERPID_I, CENTERPID_D, CENTERPID_F);

		// Limit current through the center wheel (?)
		// cTalon1.EnableCurrentLimit(true);
		// cTalon1.setCurrentLimit(30);

		// Correct encoder counting directions
		//lTalon1.reverseSensor(true);
		//rTalon1.reverseSensor(true);
		
		// All drive Talons should brake
		lTalon1.enableBrakeMode(true);
		rTalon1.enableBrakeMode(true);
		cTalon1.enableBrakeMode(true);
		
		// Set default control Modes for Master CANTalons
		this.setSpeedMode();
		
		// Setup Safety management for CANTalons
		lTalon1.setSafetyEnabled(true);
		rTalon1.setSafetyEnabled(true);
		cTalon1.setSafetyEnabled(true);
		lTalon1.setExpiration(0.5);
		rTalon1.setExpiration(0.5);
		cTalon1.setExpiration(0.5);
		
		// Start in Robot-centric mode
		setDriveInterfaceMode(driveMode_RobotCentric);
		
		//DriveMath
		 //form: Max x Accel, Max y Accel, Max z Accel, Max x Vel, Max y Vel, Max z Vel, Loop Period, Robot width
		//driveMath = new DriveMath(9.0, 12.0, 1.0, 16.0, 16.0, 1.0, .05, 2.0);
		setScaleConstants();

	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveBot(current_driveInterfaceMode));
    }
    
    /*
     * Drive interface mode support
     */
    public void setDriveInterfaceMode(int dMode) {
    	current_driveInterfaceMode = dMode;
    }
    
    public int getDriveInterfaceMode() {
    	return current_driveInterfaceMode;
    }
    
    public String getDriveInterfaceModeName() {
    	return driveModeNames[current_driveInterfaceMode];
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
		 *  Encoders: (2048 * 4) ticks per revolution
	     *	
	     *	Outer Wheel Circumference. = 4 in * 3.14159	
	     *	Center Wheel Circumference = 3.5 in * 3.14159
	     *
	     *	Outer Wheel: 1 wheel rotation per 2 encoder rotations
	     *  Center Wheel: 44 wheel rotation per 33 encoder rotations
	     *
	     *	16fps -> 25,033 counts/0.1 seconds (Left & Right)
		 *			 11,053 counts/0.1 seconds (Center)
		 *
		 *	Values computed by the RobotDrive code from the OI input (usually -1 -> +1)
		 *	will be multiplied by this value before being sent to the controllers' set() methods.
		 *
		 *	If drive stick(s) max out too early, lower this value.
	     */
		m_outerMaxOutput = 4500. ; //25033.;  // encoder counts per 0.1 seconds (native units)
		m_centerMaxOutput = 1500. ;//11053.;
	}
	
	public TalonControlMode getControlMode() {
		return t_controlMode;
	}
	
	private void setScaleConstants(){
		   hScale.enableLinearScale();
		   hScale.setScaleFactor(1);
		   hScale.setRotationFactor(4.5);
		   
		   hScale.setNetAccel(11);
		   
		   hScale.setStepScaleRange(.3);
		  // hScale.setStepScaleFactor(.75);
		  // hScale.enableStepScale();
		   hScale.disableStepScale();
		   
		  }
	
    /*
     *	Drive robot-centric 
     */
	public void driveRobotCentric(double x, double y, double z) {
	     
	     hScale.set(x*16, y*16, z);
	     
	     if (tractionFeetState == true && 
	      (x > 0.05 || y > 0.05 || z > 0.05 ||
	      x < -0.05 || y < -0.05 || z < -0.05 ))
	     {
	      liftFeetBeforeDriving();
	     }
	     
	     x = hScale.getXVal();
	     y = hScale.getYVal();
	     z = hScale.getZVal();
	     
	     //x = x*6000;
	     //y = y*6000;
	     
	     z = z*-1;
	     double left = (y + (robotWidth*hScale.yEncFt/2) * z);
	     double right = y - (robotWidth*hScale.yEncFt/2) * z;
	     
	     lTalon1.set(-1.0 * left);
	     rTalon1.set(right);
	     cTalon1.set(x);
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
   
    /*
     *	Simply Drive Sideways 
     */
    public void driveSideways(double moveValue) {

    	cTalon1.set(moveValue * m_centerMaxOutput);
    	refreshPIDF();
    }

    /*
     *	Drive arcade style (one or two sticks - forward/back and rotate specified separately) 
     */
    public void driveArcade(double moveValue, double rotateValue) {

    	checkFeetBeforeRobotDrive(moveValue, rotateValue);
    	
		double leftMotorSpeed;
		double rightMotorSpeed;
		
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}
		lTalon1.set(limit(leftMotorSpeed) * m_outerMaxOutput);
		rTalon1.set(-1.0 * limit(rightMotorSpeed) * m_outerMaxOutput);
		refreshPIDF();
    }
    
    /*
     *	Drive Tank-style (two sticks) 
     */
    public void driveTank(double leftStick, double rightStick) {
    	
    	checkFeetBeforeRobotDrive(leftStick, rightStick);    	
		lTalon1.set(limit(leftStick) * m_outerMaxOutput);
		rTalon1.set(-1.0 * limit(rightStick) * m_outerMaxOutput);
		refreshPIDF();
    }
    
    /*
     *	Drive "by wire" - used in autonomous 
     */
	  /**
	   * Drive the motors at "outputMagnitude" and "curve". Both outputMagnitude and curve are -1.0 to
	   * +1.0 values, where 0.0 represents stopped and not turning. {@literal curve < 0 will turn left
	   * and curve > 0} will turn right.
	   *
	   * <p>The algorithm for steering provides a constant turn radius for any normal speed range, both
	   * forward and backward. Increasing sensitivity causes sharper turns for fixed values of curve.
	   *
	   * <p>This function will most likely be used in an autonomous routine.
	   *
	   * @param outputMagnitude The speed setting for the outside wheel in a turn, forward or backwards,
	   *                        +1 to -1.
	   * @param curve           The rate of turn, constant for different forward speeds. Set {@literal
	   *                        curve < 0 for left turn or curve > 0 for right turn.} Set curve =
	   *                        e^(-r/w) to get a turn radius r for wheelbase w of your robot.
	   *                        Conversely, turn radius r = -ln(curve)*w for a given value of curve and
	   *                        wheelbase w.
	   *                        
	   */
	public void drive(double outputMagnitude, double curve) {
	
		final double leftOutput;
		final double rightOutput;
		    	
		checkFeetBeforeRobotDrive(outputMagnitude, curve);    	
		
		if (curve < 0) {
			double value = Math.log(-curve);
			double ratio = (value - m_sensitivity) / (value + m_sensitivity);
			if (ratio == 0) {
				ratio = .0000000001;
			}
			leftOutput = outputMagnitude / ratio;
			rightOutput = outputMagnitude;

		} else if (curve > 0) {
			double value = Math.log(curve);
			double ratio = (value - m_sensitivity) / (value + m_sensitivity);
			if (ratio == 0) {
				ratio = .0000000001;
			}
			leftOutput = outputMagnitude;
			rightOutput = outputMagnitude / ratio;

		} else {
			leftOutput = outputMagnitude;
			rightOutput = outputMagnitude;
		}

		lTalon1.set(limit(leftOutput) * m_outerMaxOutput);
		rTalon1.set(-1.0 * limit(rightOutput) * m_outerMaxOutput);
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
		return ((lTalon1.getPosition()) + ((-1.0)*rTalon1.getPosition()))/2;
	}

	/* 
	 * Get the distance driven sideways
	 */
	public double getDistanceSideways() {
		return (cTalon1.getPosition());
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

	/**
	 * Limit motor values to the -1.0 to +1.0 range.
	 */
	protected static double limit(double num) {
		if (num > 1.0) {
			return 1.0;
	    }
	    if (num < -1.0) {
	    	return -1.0;
	    }
	    return num;
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

