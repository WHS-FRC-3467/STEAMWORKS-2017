package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Drive the given distance straight (negative values go backwards).
 * Uses a local PID controller to run a simple PID loop that is only
 * enabled while this command is running. The input is the averaged
 * values of the left and right encoders.
 */
public class DriveStraight extends CommandBase {

	private static final double TOLERANCE = 2000;
	
	private PIDController m_pid;
	private double m_maxSpeed = 0.3;
	private double m_distance = 0.0;
	private boolean m_manualCurve = true;
	private double m_curveValue = 0.0;
	private double m_initialHeading = 0.0;
	private double m_pastDistance = 0.0;
	private int m_count = 0;
	
	private double KP = 1.0;
	private double KI = 0.0;
	private double KD = 2.0;
	
    public DriveStraight(double distance, double maxSpeed, double kp, double ki, double kd) {
        
    	requires(driveBase);
    	KP = kp; KI = ki; KD = kd;
    	m_maxSpeed = maxSpeed;
    	m_distance = distance;
    	buildController();
    }

    public DriveStraight(double distance, double maxSpeed) {
    
    	requires(driveBase);
    	m_maxSpeed = maxSpeed;
    	m_distance = distance;
    	buildController();
    }
	
	public DriveStraight(double distance) {
    	requires(driveBase);
    	m_distance = distance;
    	buildController();
	}
	
	public DriveStraight(double distance, boolean curve, double curveValue) {
		requires(driveBase);
		
		m_distance = distance;
		m_manualCurve = curve;
		m_curveValue = curveValue;
		
		buildController();
	}
	
	public DriveStraight(double distance, double maxSpeed, double timeOut) {
		requires(driveBase);
		m_distance = distance;
		m_maxSpeed = maxSpeed;
		
		setTimeout(timeOut);
	}
	
	private void buildController() {
		
		m_pid = new PIDController(KP, KI, KD,
                new PIDSource() {
                    PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

                    public double pidGet() {
                        return driveBase.getDistance();
                    }

                    public void setPIDSourceType(PIDSourceType pidSource) {
                      m_sourceType = pidSource;
                    }

                    public PIDSourceType getPIDSourceType() {
                        return m_sourceType;
                    }
                },
                new PIDOutput() {
                	
                	public void pidWrite(double d) {
                		driveStraight(d);
                }});
		
        m_pid.setAbsoluteTolerance(TOLERANCE);
        m_pid.setOutputRange((m_maxSpeed * -1.0), m_maxSpeed);
        m_pid.setSetpoint(m_distance);
    }

	//If the robot has hit a wall, SAY SOMETHING!
	public boolean hasStalled() {
		if (driveBase.getDistance() - m_pastDistance <= 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Drive straight with the magnitude returned by the PID calculation, 
	public void driveStraight(double d) {
		if (m_manualCurve) {
			// Curve using the preset value
			driveBase.drive(d, m_curveValue);
		} else {
			// Get the current heading reading differential and curve to oppose the change 
			// (Divide degree differential by a factor so as to normalize to numbers less than -1.0 / + 1.0)
			driveBase.drive(d, ((m_initialHeading - gyro.getHeading())/240.));
		}
	}
	
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	// Get everything in a safe starting state.
        driveBase.liftFeetBeforeDriving();
    	driveBase.resetEncoders();
        //gyro.zeroGyro(); // Zeroing should be done separately before this command is run
    	// Save the initial gyro heading - we want to keep that heading throughout the command
    	m_initialHeading = gyro.getHeading();
    	m_pid.reset();
        m_pid.enable();
        m_count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveBase.reportEncoders();
    	
    	if (hasStalled()) {
    		m_count++;
    	}
    	
    	m_pastDistance = driveBase.getDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double error = m_pid.getError();
    	
    	if (m_count >= 100) {
    		return true;
    	}
    	else {
       		return ((error >= 0 && error <= TOLERANCE) || (error < 0 && error >= (-1.0)*TOLERANCE));
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	// Stop PID and the wheels
    	m_pid.disable();
        driveBase.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}

