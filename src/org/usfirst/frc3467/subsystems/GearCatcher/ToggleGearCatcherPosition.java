package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ToggleGearCatcherPosition extends CommandBase {

    public ToggleGearCatcherPosition() {
        requires (pneumatics);
        requires (gearcatch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	boolean isUp = gearcatch.getCatcherState();
    	
    	if (isUp == true)
    		pneumatics.gearCatchDown();
    	else
    		pneumatics.gearCatchUp();

    	gearcatch.setCatcherState(!isUp);
    	SmartDashboard.putString("Gear Catcher Position", isUp ? "UP" : "DOWN");
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
