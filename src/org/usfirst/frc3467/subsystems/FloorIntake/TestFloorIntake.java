package org.usfirst.frc3467.subsystems.FloorIntake;

import org.usfirst.frc3467.robot.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestFloorIntake extends CommandBase {
	
	double speed;

    public TestFloorIntake() {
    	requires (flr_intake);
    	SmartDashboard.putNumber("Set Floor Intake Speed", .1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	speed = SmartDashboard.getNumber("Set Floor Intake Speed", .1);
    	flr_intake.drive(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	flr_intake.drive(0.0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
