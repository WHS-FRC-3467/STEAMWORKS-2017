package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;


/**
 *
 */
public class RunShooter extends CommandBase {

    double velocity = 0.0;
    		
	public RunShooter(double speed) {
        requires(shooter);
        
        velocity = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	shooter.ShooterRun(velocity);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.ShooterRun(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
