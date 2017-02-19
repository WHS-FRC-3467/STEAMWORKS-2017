
package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//
//
//
public class DriveBase extends Subsystem {

	private static CANTalon rTalon1, rTalon2, rTalon3, lTalon1, lTalon2, lTalon3, cTalon1, cTalon2;
	private static final double width = 1;
    
	private static RobotDrive dBase;
	private TalonControlMode 	t_controlMode;
	
	public DriveBase() {
		rTalon1 = new CANTalon(RobotMap.drivebase_LeftTalon);
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
		
		// Set default control Modes for Master CANTalons
		// (This will change to Speed control once encoders are installed and calibrated)
		setControlMode(TalonControlMode.PercentVbus);

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
        setDefaultCommand(new RobotCentricDrive());
    }
    
	/**
	 * @param controlMode Set the control mode of the master CANTalons
	 */
	public void setControlMode(TalonControlMode controlMode) {
		lTalon1.changeControlMode(controlMode);
		rTalon1.changeControlMode(controlMode);
		cTalon1.changeControlMode(controlMode);
		
		// Save control mode so we will know if we have to set it back later
		t_controlMode = controlMode;
	}
	
	public TalonControlMode getControlMode() {
		return t_controlMode;
	}
	
    public void driveRobotCentric(double x, double y, double z) {
    	double left = y + (width/2) * z;
    	double right = y - (width/2) * z;
    	double center = x;
    	
    	lTalon1.set(left);
    	rTalon1.set(right);
    	cTalon1.set(center);
    }
    
    public void driveFieldCentric(double x, double y, double z, double angle) {

    	double radAngle = angle * (3.14159 / 180.0); // Convert degrees to radians
    	
    	double left = x * Math.acos(radAngle) + y * Math.asin(radAngle) + (width/2) * z;
    	double right = x * Math.asin(radAngle) + y * Math.acos(radAngle) - (width/2) * z;
    	double center = x * Math.acos(radAngle) + y * Math.asin(radAngle);
    	
    	lTalon1.set(left);
    	rTalon1.set(right);
    	cTalon1.set(center);
    }
    
	// pass-thru to RobotDrive method (drive using one stick)
    public void driveArcade(double move, double rotate, boolean square) {
    	dBase.arcadeDrive(move, rotate, square);
    }
    
	// pass-thru to RobotDrive method (drive using 2 sticks)
    public void driveTank(double leftStick, double rightStick, boolean square) {
    	dBase.tankDrive(leftStick, rightStick, square);
    }
    
	// pass-thru to RobotDrive method (used in autonomous)
	public void drive(double outputMagnitude, double curve) {
		dBase.drive(outputMagnitude, curve);
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

    
}

