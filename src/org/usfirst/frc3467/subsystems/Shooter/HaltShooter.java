package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class HaltShooter extends CommandBase {

    double speed;
    		
	public HaltShooter() {
        requires(shooterFlywheel);
        requires(shooterFeed);
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
		SmartDashboard.putString("ShooterFlywheel", "HaltShooter");
		SmartDashboard.putString("ShooterFeed", "HaltShooter");
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   		driveBase.tractionRetract();
   		shooterFlywheel.stopShooter();
        shooterFeed.runFeed(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
