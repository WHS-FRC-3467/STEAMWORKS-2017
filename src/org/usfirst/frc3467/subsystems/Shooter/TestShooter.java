package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class TestShooter extends CommandBase {

    double speed;
    		
	public TestShooter() {
        requires(shooter);
    	SmartDashboard.putNumber("Set Shooter Speed", Shooter.SHOOTER_SPEED_DEFAULT);
    	SmartDashboard.putNumber("Set Belt Speed", Shooter.BELT_SPEED_DEFAULT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = SmartDashboard.getNumber("Set Shooter Speed", Shooter.SHOOTER_SPEED_DEFAULT);
    	shooter.ShooterRun(speed);
    	speed = SmartDashboard.getNumber("Set Belt Speed", Shooter.BELT_SPEED_DEFAULT);
    	shooter.BeltRun(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.ShooterStop();
    	shooter.BeltRun(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
