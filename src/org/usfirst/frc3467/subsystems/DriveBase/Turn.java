package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class Turn extends CommandBase {

	double LEFTSPEED;
	double RIGHTSPEED;
	
    public Turn(double leftspeed, double rightspeed) {
    	requires(driveBase);
    	LEFTSPEED = leftspeed;
    	RIGHTSPEED = rightspeed;
    	setTimeout(3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveBase.setVoltageMode();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveBase.driveTank(-.3, .3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveBase.driveTank(0,0);
    	driveBase.setSpeedMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
