package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestFeed extends CommandBase {
	
	double speed;

    public TestFeed() {
        requires(shooterFeed);
    	SmartDashboard.putNumber("Set Shooter Feed Speed", 1.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		SmartDashboard.putString("ShooterFeed", "TestFeed");
    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	speed = SmartDashboard.getNumber("Set Shooter Feeder Speed", 1.0);
    	shooterFeed.runFeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterFeed.runFeed(0.0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
