package org.usfirst.frc3467.subsystems.Hopper;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleHopper extends CommandBase {

    public ToggleHopper() {
        requires (pneumatics);
        requires (hopper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean isClosed = hopper.getHopperState();
    	
    	if (isClosed == true)
    		pneumatics.hopperExpand();
    	else
    		pneumatics.hopperContract();

    	hopper.setHopperState(!isClosed);
    	SmartDashboard.putString("Hopper Position", isClosed ? "CLOSED" : "OPEN");
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
