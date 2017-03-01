package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestSpinner extends CommandBase {
	
	double speed;

    public TestSpinner() {

    	speed = SmartDashboard.getNumber("Set Spinner Speed", .1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	speed = SmartDashboard.getNumber("Set Spinner Speed", .1);
    	shooter.SpinnerRun(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.SpinnerRun(0.0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
