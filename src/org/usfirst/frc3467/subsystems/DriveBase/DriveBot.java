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
	static final boolean SQUARE_INPUTS = true;
	
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
		if (SQUARE_INPUTS) {
			return squareInput(oi.getDriveX());
		} else {
			return oi.getDriveX();
		}
	}

	private double getY() {
		if (SQUARE_INPUTS) {
			return squareInput(oi.getDriveY());
		} else {
			return oi.getDriveX();
		}
	}
	
	private double getRot() {
		if (SQUARE_INPUTS) {
			return squareInput(oi.getDriveRotation());
		} else {
			return oi.getDriveX();
		}
	}
	
	private double squareInput(double input) {
		if (input >= 0.0) {
	          return (input * input);
        } else {
	          return -(input * input);
        }
	}
}
