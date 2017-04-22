package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunTurret extends CommandBase {

    public RunTurret() {
       requires(shooterTurret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		SmartDashboard.putString("ShooterTurret", "RunTurret");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
     	double velocity = OI.operatorPad.getRightStickX();
     	shooterTurret.runTurret(velocity * 0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterTurret.runTurret(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
