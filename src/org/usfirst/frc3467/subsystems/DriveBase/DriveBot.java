package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBot extends CommandBase {
	
	// Default to Field Centric
	int _driveMode = DriveBase.driveMode_FieldCentric;

	// Scale factor for reducing inputs during Precision Mode
	static final double precision_scaleFactor = 0.1;
	
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
		driveBase.setDriveMode(_driveMode);
		SmartDashboard.putString("Drive Mode", driveBase.getDriveModeName());
	}

	protected void execute() {

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
			driveBase.driveArcade(getY(), getRot(), SQUARE_INPUTS);
			break;
			
		case DriveBase.driveMode_Precision:
			driveBase.driveRobotCentric(getX() * precision_scaleFactor,
					getY() * precision_scaleFactor,
					getRot() * precision_scaleFactor);
			break;
			
		case DriveBase.driveMode_Tank:
			driveBase.driveTank(OI.driverPad.getLeftStickY(), OI.driverPad.getRightStickY(), SQUARE_INPUTS);
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

		double x = adjustStick(oi.getDriveX(), m_lastX);
		if (SQUARE_INPUTS)
				x =  squareInput(x);
		return (m_lastX = x); 

	}

	private double getY() {

		double y = adjustStick(oi.getDriveY(), m_lastY);
		if (SQUARE_INPUTS)
				y =  squareInput(y);
		return (m_lastY = y); 

	}
	
	private double getRot() {

		double rot = adjustStick(oi.getDriveRotation(), m_lastRot);
		if (SQUARE_INPUTS)
				rot =  squareInput(rot);
		return (m_lastRot = rot); 

	}
	
	private double squareInput(double input) {
		if (input >= 0.0) {
	          return (input * input);
        } else {
	          return -(input * input);
        }
	}
	
		private double adjustStick(double input, double lastVal) {
		
		double val = input;
		double change;
		final double changeLimit = 0.20;
		
		/*
		 *  Deadband limit
		 */
		if (val > -0.08 && val < 0.08) {
			return 0.0;
		}

        /*
         *  Square the inputs (while preserving the sign) to increase
		 *  fine control while permitting full power
         */
        if (val > 0.0)
            val = (val * val);
        else
            val = -(val * val);
        
		/*
         *  Slew rate limiter - limit rate of change
         */
        
		change = val - lastVal;
		
		if (change > changeLimit)
			change = changeLimit;
		else if (change < -changeLimit)
			change = -changeLimit;
		
		return (lastVal += change);
		
        //return val;
		
	}
}
