package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

/**
 *
 */
public class VelocityControl extends CommandBase {

	static final double hiIntakeSpeed = 0.5;
	static final double floorIntakeSpeed = 0.5;
	static final double spinnerSpeed = 0.5;
	static final double beltSpeed = 0.5;
	
	double shooterVelocity = 0.0;
	
	public VelocityControl(double speed) {
		
		shooterVelocity = speed;
		
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
    	hi_intake.HIntakeRun(hiIntakeSpeed);
    	flr_intake.DriveAuto(floorIntakeSpeed);
    	shooter.SpinnerRun(spinnerSpeed);
    	shooter.BeltRun(beltSpeed);
    	shooter.ShooterRun(shooterVelocity);    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	hi_intake.HIntakeRun(0.0);
    	flr_intake.DriveAuto(0.0);
    	shooter.SpinnerRun(0.0);
    	shooter.BeltRun(0.0);
    	shooter.ShooterRun(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
