package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleGearClawState extends CommandBase {

    public ToggleGearClawState() {
        requires (pneumatics);
        requires (gearcatch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean isClosed = gearcatch.getClawState();
    	
    	if (isClosed == true)
    		pneumatics.gearClawRelease();
    	else
    		pneumatics.gearClawHold();

    	gearcatch.setClawState(!isClosed);
    	SmartDashboard.putString("Gear Claw Position", isClosed ? "CLOSED" : "OPEN");
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
