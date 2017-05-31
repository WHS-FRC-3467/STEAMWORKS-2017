package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class CalibrateTurret extends CommandBase {

	public CalibrateTurret() {
       requires(shooterTurret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//shooterTurret.enableSoftLimits(false);
		SmartDashboard.putString("ShooterTurret", "CalibrateTurret");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       	//shooterTurret.setDesiredAngle(0.0);
    	shooterTurret.runTurret(-0.2);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       	shooterTurret.outputToSmartDashboard();
    	return (shooterTurret.getAngle() < 0.0);
       	//return shooterTurret.isOnTarget();
   }

    // Called once after isFinished returns true
    protected void end() {
    	shooterTurret.stop();
		//shooterTurret.enableSoftLimits(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
