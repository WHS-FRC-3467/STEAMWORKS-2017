package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;
import org.usfirst.frc3467.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBot extends CommandBase {
	
	// Default to Field Centric
	int _driveMode = DriveBase.driveMode_FieldCentric;

	// Scale factor for reducing inputs during Precision Mode
	static final double precision_scaleFactor = 0.3;
	static final double precision_scaleFactor2 = 0.3;
	static final double precision_turningFactor = .4;
	
	// Don't adjust the sticks
	static final boolean NO_STICK_ADJUSTMENT = true;
	
	// square the inputs (while preserving the sign) to increase fine control
    // while permitting full power
	static final boolean SQUARE_INPUTS = false;
	
	double m_lastX = 0.0, m_lastY = 0.0, m_lastRot = 0.0;
	
	public DriveBot(int driveMode) {
		requires(driveBase);
		this.setInterruptible(true);
	
		_driveMode = driveMode;
	}
	
	protected void initialize() {

		// Don't set drive mode in DriveBase until this command is actually underway
		// Otherwise, all the instances of this command instantiated in OI will change the drive mode prematurely
		driveBase.setDriveInterfaceMode(_driveMode);
		SmartDashboard.putString("Drive Mode", driveBase.getDriveInterfaceModeName());
	}

	protected void execute() {

		// Make sure climber latch is unlatched before driving
		climber.setLatchServo(RobotMap.climberLatch_DISENGAGED);

		driveBase.reportEncoders();
		
		switch (_driveMode) {
		
		default:
		case DriveBase.driveMode_FieldCentric:
			driveBase.driveFieldCentric(getX(), getY(), getRot(), gyro.getAngle());
			break;
			
		case DriveBase.driveMode_RobotCentric:
			driveBase.driveRobotCentric(getX(), getY(), getRot());
			break;
			
		case DriveBase.driveMode_Arcade:
			driveBase.driveArcade(getY(), getRot());
			break;
			
		case DriveBase.driveMode_Precision:
			driveBase.driveRobotCentric(getX() * precision_scaleFactor2,
					getY() * precision_scaleFactor,
					getRot() * precision_turningFactor);
			break;
			
		case DriveBase.driveMode_Tank:
			driveBase.driveTank(OI.driverPad.getLeftStickY(), OI.driverPad.getRightStickY());
			break;
		}
		
		
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
	
	private double getX() {

		return (m_lastX = adjustStick(oi.getDriveX(), m_lastX, 0.20)); 

	}

	private double getY() {

		return (m_lastY = adjustStick(oi.getDriveY(), m_lastY, 0.20)); 

	}
	
	private double getRot() {

		return (m_lastRot = adjustStick(oi.getDriveRotation(), m_lastRot, 0.10)); 

	}
	
	private double adjustStick(double input, double lastVal, double changeLimit) {
		
		double val = input;
		double change;
		
		/*
		 *  Deadband limit
		 */
		//if (val > -0.01 && val < 0.01) {
			//return 0.0;
		//}

		if (NO_STICK_ADJUSTMENT) {
			return input;
		}
        /*
         *  Square the inputs (while preserving the sign) to increase
		 *  fine control while permitting full power
         */
		if (SQUARE_INPUTS) {
	        if (val > 0.0)
	            val = (val * val);
	        else
	            val = -(val * val);
		}
        
		/*
         *  Slew rate limiter - limit rate of change
         */
        
		change = val - lastVal;
		
		if (change > changeLimit)
			change = changeLimit;
		else if (change < -changeLimit)
			change = -changeLimit;
		
		return (lastVal += change);
		
	}
}
