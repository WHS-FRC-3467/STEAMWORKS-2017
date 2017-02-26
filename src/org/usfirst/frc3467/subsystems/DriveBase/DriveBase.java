
package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.PixyCam.PixyCmu5;

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
    
	private static RobotDrive dBase;
	private TalonControlMode 	t_controlMode;

	
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
	
	public DriveBase() {
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
		
		latchServo = new Servo(0);
		
		pdp = new PowerDistributionPanel();

		
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
    	
    	double xScale = 2;
    	
    	z = z*-1;
    	double left = (y + (width/2) * z);
    	double right = y - (width/2) * z;
    	double center = x;
    	
    	center = center*xScale;
    	
    	lTalon1.set(-left);
    	rTalon1.set(right);
    	cTalon1.set(center);
    }
    
    public void driveFieldCentric(double x, double y, double z, double angle) {

    	double radAngle = angle * (3.14159 / 180.0); // Convert degrees to radians
    	
    	double yNet  = y*Math.cos(radAngle)-x*Math.sin(radAngle);
    	
    	double xNet = x * Math.cos(radAngle) + y * Math.sin(radAngle);
    	
    	driveRobotCentric(xNet, yNet, z);
    	
    }
    public boolean driveAutoAim(double x, double y, double targetDistance, double anglePrecision, double distanceTolerance) {
    	double xOut = 0;
    	double yOut = 0;
    	double zOut = 0;
    	final double goalHeight = .5;
    	final double pixyAngle = 0.174;
    	double angleX = x*(2*3.14159/360);//2*x*Math.tan(PixyCmu5.PIXY_X_FOV/(4*3.14159))/PixyCmu5.PIXY_MAX_X;
    	double angleY = -y*(2*3.14159/360)+pixyAngle;//2*y*Math.tan(PixyCmu5.PIXY_Y_FOV/(4*3.14159))/PixyCmu5.PIXY_MAX_Y;
    	final double distance = goalHeight/Math.tan(angleY);
    	final double zScale = 1.7;
    	double distanceScale = Math.abs(distance-targetDistance);
    	if(distanceScale > .7){
    		distanceScale = .7;
    	}
    	if(Math.abs(angleX)>anglePrecision)
    		zOut = angleX*zScale;
    	if(Math.abs(distance-targetDistance) > distanceTolerance){
    		xOut = (distance-targetDistance)*Math.sin(angleX)*distanceScale;
    		yOut = (distance-targetDistance)*Math.cos(angleX)*distanceScale;
    	}
    	else{
    		xOut = 0;
    		yOut = 0;
    	}
    	/*if(xOut > 0){
    	xOut = Math.sqrt(xOut);
    	}else{
    		xOut = -1*Math.sqrt(xOut*-1);
    	}
    	
    	if(yOut > 0){
        	yOut = Math.sqrt(yOut);
        }else{
        	yOut = -1*Math.sqrt(yOut*-1);
        }*/
    	if(xOut > 0){
    		xOut = Math.pow(xOut, 2.0/3);
    	}
    	else{
    		xOut = -Math.pow(xOut, 2.0/3);
    	}
    	if(yOut > 0){
    		yOut = Math.pow(yOut, 2.0/3);
    	}
    	else{
    		yOut = -Math.pow(yOut, 2.0/3);
    	}
    	System.out.println(angleY+"     " +distance+ "     "+targetDistance+"     "+zOut +"     "+angleX+"     "+(angleX*zScale));
    	driveRobotCentric(xOut, -yOut, zOut);
    	return true;
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
	public CANTalon getMiddleTalon(){
		return cTalon1;
	}
	public PowerDistributionPanel getPDP(){
		return pdp;
	}
	public Servo getLatchServo(){
		return latchServo;
	}

    
}

