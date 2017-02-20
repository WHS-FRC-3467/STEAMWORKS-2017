package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class VelocityControl extends CommandBase {

    public VelocityControl() {
        requires(shooter);
        requires(flr_intake);
        requires(hi_intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	hi_intake.hiIntakeRetract();
    	flr_intake.retract();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	hi_intake.HIntakeRun(.5);
    	flr_intake.DriveAuto(.5);
    	shooter.SpinnerRun(.5);
    	shooter.BeltRun(.5);
    	shooter.ShooterRun(.5);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	hi_intake.HIntakeRun(0);
    	flr_intake.DriveAuto(0);
    	shooter.SpinnerRun(0);
    	shooter.BeltRun(0);
    	shooter.ShooterRun(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
