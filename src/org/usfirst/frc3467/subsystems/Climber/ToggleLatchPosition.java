package org.usfirst.frc3467.subsystems.Climber;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleLatchPosition extends CommandBase {

	private static boolean latched = false;
	
    public ToggleLatchPosition() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (latched == true) {
    		climber.setLatchServo(RobotMap.climberLatch_DISENGAGED);
    		latched = false;
    	}
    	else {
    		climber.setLatchServo(RobotMap.climberLatch_ENGAGED);
    		latched = true;
    	}
    	SmartDashboard.putString("Climber latch",  latched ? "NOT ACTUATED" : "ACTUATED");
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
    }
}
