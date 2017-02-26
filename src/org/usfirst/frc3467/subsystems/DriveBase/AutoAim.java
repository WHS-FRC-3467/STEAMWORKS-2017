package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAim extends CommandBase {

	public AutoAim() {
		requires(driveBase);
		requires(pixyCam);
		this.setInterruptible(true);
		SmartDashboard.putString("DriveMode", "Auto Aim");
	}
	
	public void initialize() {
		
	}
	
	public void execute() {
		double[] coordinates = {0.0, 0.0};
		coordinates = pixyCam.boilerDistance();
		
		driveBase.driveAutoAim(coordinates[0], coordinates[1], 2, .1, .07);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void end() {
		
	}
	
	protected void interrupted() {
		end();
	}

}
