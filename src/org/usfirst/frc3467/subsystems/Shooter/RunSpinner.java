package org.usfirst.frc3467.subsystems.Shooter;


import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

public class RunSpinner extends CommandBase {

    public RunSpinner() {
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	shooter.SpinnerRun(.7);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.SpinnerRun(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

