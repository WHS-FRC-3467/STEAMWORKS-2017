package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Drive the given distance straight sideways (negative values go backwards).
 * Uses a local PID controller to run a simple PID loop that is only
 * enabled while this command is running.
 * 
 * BECAUSE WE ARE DRIVING WITH ONLY ONE WHEEL, WE HAVE NO CONTROL OVER CURVATURE OF MOVEMENT
 */
public class DriveSideways extends CommandBase {

	private static final double TOLERANCE = 1000;
	
	private PIDController m_pid;
	private double m_maxSpeed = 0.3;
	private double m_distance = 0.0;
	
	private double KP = 1.5;
	private double KI = 0.0;
	private double KD = 2.0;
	
    public DriveSideways(double distance, double maxSpeed, double kp, double ki, double kd) {
        
    	requires(driveBase);
    	KP = kp; KI = ki; KD = kd;
    	m_maxSpeed = maxSpeed;
    	m_distance = distance;
    	buildController();
    }

    public DriveSideways(double distance, double maxSpeed) {
    
    	requires(driveBase);
    	m_maxSpeed = maxSpeed;
    	m_distance = distance;
    	buildController();
    }
	
	public DriveSideways(double distance) {
    	requires(driveBase);
    	m_distance = distance;
    	buildController();
	}
	
	private void buildController() {
		
		m_pid = new PIDController(KP, KI, KD,
                new PIDSource() {
                    PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

                    public double pidGet() {
                        return driveBase.getDistanceSideways();
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
                		
                		// No twist (z) term; must scale d because it is not scaled in driveSideways
                		// driveBase.driveSideways(d * 4.0);
                		
                		// driveRoboticCentric takes a z-term, which we get from gyro offset
                		// (driveRobotcCentric changes the sign of the z-term)
                		driveBase.driveRobotCentric(d, 0, (gyro.getHeading()/200.));
                }});
		
        m_pid.setAbsoluteTolerance(TOLERANCE);
        m_pid.setOutputRange((m_maxSpeed * -1.0), m_maxSpeed);
        m_pid.setSetpoint(m_distance);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Get everything in a safe starting state.
        driveBase.liftFeetBeforeDriving();
    	driveBase.resetEncoders();
        m_pid.reset();
        m_pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveBase.reportEncoders();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double error = m_pid.getError();
   		return ((error >= 0 && error <= TOLERANCE) || (error < 0 && error >= (-1.0)*TOLERANCE));
    }

    // Called once after isFinished returns true
    protected void end() {
    	// Stop PID and the wheels
    	m_pid.disable();
        driveBase.driveRobotCentric(0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}

