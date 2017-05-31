
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBot;
import org.usfirst.frc3467.subsystems.Shooter.RunTurret;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterTurret extends Subsystem {
    
	private boolean flg_tuning = true;   // Set to true to tune PID constants vis SmartDashboard
	
	// Limit Switch @ maximum position
	//DigitalInput atMaxPos;
	//Counter cntMaxPos;
	
    // Turret constants//
    public static double SOFT_MAX_TURRET_ANGLE = 91.0;  //  8704
    public static double SOFT_MIN_TURRET_ANGLE = -91.0; //  -8704

    private static double HARD_MAX_TURRET_ANGLE = 90;    //  9216
    private static double HARD_MIN_TURRET_ANGLE = -90;   //  -9216
    private static double TURRET_ONTARGET_TOLERANCE = 1.0;
    private static double TICKS_PER_TURRET_ROTATION = (2048 * 4 * 4.5);// (2048 * 4) ticks / rotationDriver * 4.5 rotationDriver / rotationTurret
   
	private CANTalon turretMotor;

	private double turretF, turretP, turretI, turretD, turretVel, turretAccel;
	
    public ShooterTurret() {
    	
		// Use a counter on the limit switch to make sure we catch all switch closings
    	//atMaxPos = new DigitalInput(RobotMap.turretMaximum);
	    //cntMaxPos = new Counter(atMaxPos);

    	// Turret Rotation Motor
		turretMotor = new CANTalon(RobotMap.turret_Talon);
    	
    	// Don't set a control mode to start
		//turretMotor.changeControlMode(TalonControlMode.Position);
		//turretMotor.changeControlMode(TalonControlMode.MotionMagic);
    	
		// Set initial PIDF parameters
    	turretF = 5.0;
    	turretP = 3.0;
    	turretI = 0.01;
    	turretD = 0.3;
    	SmartDashboard.putNumber("Turret F", turretF);
	   	SmartDashboard.putNumber("Turret P", turretP);
    	SmartDashboard.putNumber("Turret I", turretI);
    	SmartDashboard.putNumber("Turret D", turretD);
		turretMotor.setProfile(0);
    	turretMotor.setF(turretF);
    	turretMotor.setP(turretP);
    	turretMotor.setI(turretI);
    	turretMotor.setD(turretD); 
		turretMotor.setIZone(0);

    	turretMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		turretMotor.reverseSensor(false);
		
		turretMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		turretMotor.configPeakOutputVoltage(+12.0f, -12.0f);

		// Motion magic parameters
		turretVel = 1100.0;
		turretAccel = 1000.0;
    	SmartDashboard.putNumber("Turret Velocity", turretVel);
	   	SmartDashboard.putNumber("Turret Accel", turretAccel);
		turretMotor.setMotionMagicCruiseVelocity(turretVel);
		turretMotor.setMotionMagicAcceleration(turretAccel);

		// For now, we are starting the Turret in a known position - HARD_MAX_TURRET_ANGLE
        reset(convertDegrees2Ticks(HARD_MAX_TURRET_ANGLE));
		
		// Use soft limits to make sure the turret doesn't try to spin too far
		turretMotor.setForwardSoftLimit(convertDegrees2Ticks(SOFT_MAX_TURRET_ANGLE));
		turretMotor.setReverseSoftLimit(convertDegrees2Ticks(SOFT_MIN_TURRET_ANGLE));
		enableSoftLimits(true);
    }

    public void enableSoftLimits(boolean set) {
		turretMotor.enableForwardSoftLimit(set);
		turretMotor.enableReverseSoftLimit(set);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new RunTurret());
    }
    
    // Run turret manually
    public void runTurret(double speed) {
    	turretMotor.changeControlMode(TalonControlMode.PercentVbus);
    	turretMotor.set(speed);
    	SmartDashboard.putNumber("Turret Position", turretMotor.getPosition());
    	//SmartDashboard.putBoolean("Turret @Max?", atMaxPos.get());    	
    }
    
    // Has the max position limit switch been hit?
    public boolean isMaxPosSwitchSet() {
        //return cntMaxPos.get() > 0;
    	return false;
    }

    // Initialize the max position limit switch
    public void initializeMaxPosSwitch() {
        //cntMaxPos.reset();
    }

    protected double convertDegrees2Ticks(double degrees) {
        return (degrees / 360.) * TICKS_PER_TURRET_ROTATION;
    }
    
    protected double convertTicks2Degrees(double ticks) {
        return (ticks / TICKS_PER_TURRET_ROTATION) * 360.;
    }
    
    // Set the desired angle of the turret
    synchronized void setDesiredAngle(double angle) {
    	
    	double target = convertDegrees2Ticks(angle);
    	
       	if (flg_tuning) {
       		turretMotor.setF( turretF = SmartDashboard.getNumber("Turret F", turretF));
       		turretMotor.setP( turretP = SmartDashboard.getNumber("Turret P", turretP));
       		turretMotor.setI( turretI = SmartDashboard.getNumber("Turret I", turretI));
       		turretMotor.setD( turretD = SmartDashboard.getNumber("Turret D", turretD));
    		turretMotor.setMotionMagicCruiseVelocity(turretVel = SmartDashboard.getNumber("Turret Velocity", turretVel));
    		turretMotor.setMotionMagicAcceleration(turretAccel = SmartDashboard.getNumber("Turret Accel", turretAccel));
       	}
       	
       	turretMotor.changeControlMode(TalonControlMode.MotionMagic);
        turretMotor.set(target);
    }
    
    // Tell the Turret it is at a given position.
    synchronized void reset(double actual_position) {
        turretMotor.setPosition(actual_position);
    }

    public synchronized double getAngle() {
        return convertTicks2Degrees(turretMotor.getPosition());
    }

    public synchronized double getSetpoint() {
        return convertTicks2Degrees(turretMotor.getSetpoint());
    }

    private synchronized double getError() {
        return convertTicks2Degrees(turretMotor.getPosition() - turretMotor.getSetpoint());
    }

    // We are "OnTarget" if we are in position mode and close to the setpoint.
    public synchronized boolean isOnTarget() {
        return (turretMotor.getControlMode() == TalonControlMode.MotionMagic
                && Math.abs(getError()) < TURRET_ONTARGET_TOLERANCE);
    }

    public synchronized void stop() {
        runTurret(0.0);
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("Turret SetPoint", getSetpoint());
    	SmartDashboard.putNumber("Turret Actual", getAngle());  		
        SmartDashboard.putNumber("Turret Error", getError());
        SmartDashboard.putBoolean("Turret on Target?", isOnTarget());
    }

 }


