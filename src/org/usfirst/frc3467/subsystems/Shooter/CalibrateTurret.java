package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;


/**
 *
 */
public class CalibrateTurret extends CommandBase {

	public CalibrateTurret() {
       requires(shooterTurret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooterTurret.enableSoftLimits(false);
    	shooterTurret.initializeMaxPosSwitch();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	shooterTurret.runTurret(0.2);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shooterTurret.isMaxPosSwitchSet();
    }

    // Called once after isFinished returns true
    protected void end() {
		shooterTurret.resetTurretAtMax();

		// TODO: uncomment once you are sure setDesiredAngle() works
		//	shooterTurret.setDesiredAngle(0.0);
		
		shooterTurret.enableSoftLimits(true);
    	shooterTurret.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
