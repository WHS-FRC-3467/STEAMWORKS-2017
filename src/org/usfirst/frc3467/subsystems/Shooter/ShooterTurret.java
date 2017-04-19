
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterTurret extends Subsystem {
    
	private boolean flg_tuning = true;   // Set to true to tune PID constants vis SmartDashboard
	
    // Turret constants
    private static double HARD_MAX_TURRET_ANGLE = 109.5;
    private static double HARD_MIN_TURRET_ANGLE = -116.5;
    private static double SOFT_MAX_TURRET_ANGLE = 108.0;
    private static double SOFT_MIN_TURRET_ANGLE = -115.0;
    private static double TURRET_ONTARGET_TOLERANCE = 1.0;
    private static double TICKS_PER_TURRET_ROTATION = (2048 * 4 * 6);// (2048 * 4) ticks / rotationDriver * 6 rotationDriver / rotationTurret
   
	private CANTalon turretMotor;

	private double turretF, turretP, turretI, turretD, turretVel, turretAccel;
	
    public ShooterTurret() {
    	
    	// Turret Rotation Motor
		turretMotor = new CANTalon(RobotMap.turret_Talon);
    	
    	//turretMotor.changeControlMode(TalonControlMode.Position);
		turretMotor.changeControlMode(TalonControlMode.MotionMagic);
    	
		// TODO: Change these
    	turretF = 0.0;
    	turretP = 0.0;
    	turretI = 0.0;
    	turretD = 0.0;
    	SmartDashboard.putNumber("Turret F", turretF);
	   	SmartDashboard.putNumber("Turret P", turretP);
    	SmartDashboard.putNumber("Turret I", turretI);
    	SmartDashboard.putNumber("Turret D", turretD);

    	turretMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		turretMotor.reverseSensor(false);
		
		turretMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		turretMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		turretMotor.setProfile(0);
    	turretMotor.setF(turretF);
    	turretMotor.setP(turretP);
    	turretMotor.setI(turretI);
    	turretMotor.setD(turretD); 
		turretMotor.setIZone(0);

		// TODO: Change these
		turretVel = 0.0;
		turretAccel = 0.0;
    	SmartDashboard.putNumber("Turret Velocity", turretVel);
	   	SmartDashboard.putNumber("Turret Accel", turretAccel);
		turretMotor.setMotionMagicCruiseVelocity(turretVel);
		turretMotor.setMotionMagicAcceleration(turretAccel);

        // Use soft limits to make sure the turret doesn't try to spin too far
		turretMotor.enableForwardSoftLimit(true);
		turretMotor.enableReverseSoftLimit(true);
		turretMotor.setForwardSoftLimit(convertDegrees2Ticks(SOFT_MAX_TURRET_ANGLE));
		turretMotor.setReverseSoftLimit(convertDegrees2Ticks(SOFT_MIN_TURRET_ANGLE));

    }

    public void initDefaultCommand() {
    }
    
    // Run turret manually
    public void runTurret(double speed) {
    	turretMotor.changeControlMode(TalonControlMode.PercentVbus);
    	turretMotor.set(speed);
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
    	
    	turretMotor.changeControlMode(TalonControlMode.MotionMagic);
       	if (flg_tuning) {
       		turretMotor.setF( turretF = SmartDashboard.getNumber("Turret F", turretF));
       		turretMotor.setP( turretP = SmartDashboard.getNumber("Turret P", turretP));
       		turretMotor.setI( turretI = SmartDashboard.getNumber("Turret I", turretI));
       		turretMotor.setD( turretD = SmartDashboard.getNumber("Turret D", turretD));
    		turretMotor.setMotionMagicCruiseVelocity(turretVel = SmartDashboard.getNumber("Turret Velocity", turretVel));
    		turretMotor.setMotionMagicAcceleration(turretAccel = SmartDashboard.getNumber("Turret Accel", turretAccel));
       	}
        turretMotor.set(target);

        SmartDashboard.putNumber("Turret Target:", angle);
    	SmartDashboard.putNumber("Turret Actual:", convertTicks2Degrees(turretMotor.get()));  		
    }
    
    // Set the desired angle relative to current angle
    synchronized void setRelativeAngle(double diffAngle) {
    	setDesiredAngle(getAngle() + diffAngle); 
    }
    
    // Tell the Turret it is at a given position.
    synchronized void reset(double actual_position) {
        turretMotor.setPosition(actual_position);
    }

    public synchronized double getAngle() {
        return convertTicks2Degrees(turretMotor.getPosition());
    }

    public synchronized boolean getForwardLimitSwitch() {
        return turretMotor.isFwdLimitSwitchClosed();
    }

    public synchronized boolean getReverseLimitSwitch() {
        return turretMotor.isRevLimitSwitchClosed();
    }

    public synchronized double getSetpoint() {
        return convertTicks2Degrees(turretMotor.getSetpoint());
    }

    private synchronized double getError() {
        return convertTicks2Degrees(turretMotor.getPosition() - turretMotor.getSetpoint());
    }

    // We are "OnTarget" if we are in position mode and close to the setpoint.
    public synchronized boolean isOnTarget() {
        return (turretMotor.getControlMode() == TalonControlMode.Position
                && Math.abs(getError()) < TURRET_ONTARGET_TOLERANCE);
    }

    public synchronized void stop() {
        runTurret(0.0);
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("turret_error", getError());
        SmartDashboard.putNumber("turret_angle", getAngle());
        SmartDashboard.putNumber("turret_setpoint", getSetpoint());
        SmartDashboard.putBoolean("turret_fwd_limit", getForwardLimitSwitch());
        SmartDashboard.putBoolean("turret_rev_limit", getReverseLimitSwitch());
        SmartDashboard.putBoolean("turret_on_target", isOnTarget());
    }

    public synchronized void zeroTurret() {
        reset(0);
    }
    
    public synchronized void resetTurretAtMax() {
        reset(convertDegrees2Ticks(HARD_MAX_TURRET_ANGLE));
    }

    public synchronized void resetTurretAtMin() {
        reset(convertDegrees2Ticks(HARD_MIN_TURRET_ANGLE));
    }
 }


