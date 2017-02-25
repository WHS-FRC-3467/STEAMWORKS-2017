package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunBothIntakes extends CommandBase {

	private double SPEED;
	
    public RunBothIntakes(double speed) {
        requires(hi_intake);
        requires(flr_intake);
        SPEED = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	hi_intake.hiIntakeExtend();
    	flr_intake.extend();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	flr_intake.DriveAuto(SPEED);
    	hi_intake.HIntakeRun(SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	flr_intake.DriveAuto(0);
    	hi_intake.HIntakeRun(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
