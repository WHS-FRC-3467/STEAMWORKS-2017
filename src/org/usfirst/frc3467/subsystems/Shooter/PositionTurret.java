package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PositionTurret extends CommandBase {

	double m_angle = 0.0;
	boolean use_sdb = false;
	
	public PositionTurret() {
       requires(shooterTurret);
       SmartDashboard.putNumber("Set Turret Position", 0.0);
       use_sdb = true;
	}
	
	public PositionTurret(double angle) {
       requires(shooterTurret);
       m_angle = angle;
       use_sdb = false;
    }

	public PositionTurret(double angle, boolean relative) {
       requires(shooterTurret);
       if (relative) {
	       m_angle = shooterTurret.getAngle() + angle;
       } else {
	       m_angle = angle;
       }
       use_sdb = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		SmartDashboard.putString("ShooterTurret", "PositionTurret");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (use_sdb) {
    		m_angle = SmartDashboard.getNumber("Set Turret Position", 0.0);
    	}
    	shooterTurret.setDesiredAngle(m_angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	shooterTurret.outputToSmartDashboard();
        return shooterTurret.isOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterTurret.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
