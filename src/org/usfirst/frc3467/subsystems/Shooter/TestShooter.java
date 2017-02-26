package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class TestShooter extends CommandBase {

    double speed = 0.0;
    		
	public TestShooter() {
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		double speed;
    	speed = SmartDashboard.getNumber("Set Shooter Speed", .5);
    	shooter.ShooterRun(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.ShooterRun(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}