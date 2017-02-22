package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBot extends CommandBase {
	
	int _driveMode;
	static double precision_scaleFactor = 0.1;
	
	public DriveBot(int driveMode) {
		requires(driveBase);
		this.setInterruptible(true);
	
		_driveMode = driveMode;
		driveBase.setDriveMode(driveMode);
		SmartDashboard.putString("Drive Mode", driveBase.getDriveModeName());
	}
	
	protected void initialize() {
	}

	protected void execute() {

		switch (_driveMode) {
		
		default:
		case DriveBase.driveMode_FieldCentric:
			driveBase.driveRobotCentric(oi.getDriveX(), oi.getDriveY(), oi.getDriveRotation());
			break;
			
		case DriveBase.driveMode_RobotCentric:
			driveBase.driveRobotCentric(oi.getDriveX(), oi.getDriveY(), oi.getDriveRotation());
			break;
			
		case DriveBase.driveMode_Arcade:
			driveBase.driveArcade(oi.getDriveY(), oi.getDriveRotation(), true);
			break;
			
		case DriveBase.driveMode_Precision:
			driveBase.driveRobotCentric(oi.getDriveX() * precision_scaleFactor,
					oi.getDriveY() * precision_scaleFactor,
					oi.getDriveRotation() * precision_scaleFactor);
			break;
			
		case DriveBase.driveMode_Tank:
			driveBase.driveTank(OI.driverPad.getLeftStickY(), OI.driverPad.getRightStickY(), true);
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
}
