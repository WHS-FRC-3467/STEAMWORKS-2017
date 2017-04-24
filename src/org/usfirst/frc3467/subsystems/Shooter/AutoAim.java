package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.subsystems.PixyCam.NoTargetException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAim extends CommandBase {

	// Configurable parameters
	public static final double ANGLE_PRECISION = 1.0;
	
	boolean targetFound = false;
	int targetSearchCount = 1;
	int targetSearchDirection = 1;
	int targetSearchIncrement = 10;
	
	public AutoAim() {
		requires(pixyCamShooter);
		requires(shooterTurret);

		this.setInterruptible(true);
	}
	
	public void initialize() {
		targetFound = false;
		SmartDashboard.putString("ShooterTurret", "Auto Aim");
	}
	
	public void execute() {

    	double [] pixyData = {0.0, 0.0, 0.0};
		try {
    		pixyData = pixyCamShooter.getBoilerLocationData();
    	} catch (NoTargetException ex) {
    		// No target found
    		searchForTarget();
    		return;
    	}
    	
    	// Name the returned data
		double distance = pixyData[0];
    	double angleX = pixyData[1];
    	double angleY = pixyData[2];

		// Command is completed if we are on target in the X direction
		if (Math.abs(angleX) <= ANGLE_PRECISION) {
			targetFound = true;
			return;
		}

		// Move turret to center of object
    	shooterTurret.setDesiredAngle(shooterTurret.getAngle() + angleX);
		
	   	SmartDashboard.putString("Turret AutoAim","distance = " + distance +"   angleX = " + angleX + "   angleY = "+ angleY);
		 		
	}
	
	private void searchForTarget() {
		// Turn back and forth in ever increasing angles  until target is found
		int movement = targetSearchCount * targetSearchIncrement * targetSearchDirection ;
		double target = shooterTurret.getAngle() + movement;
		if (target > ShooterTurret.SOFT_MAX_TURRET_ANGLE || target < ShooterTurret.SOFT_MIN_TURRET_ANGLE) {
			target = 0.0;
			targetSearchCount = 1;
		}
		
		// Move turret 
    	shooterTurret.setDesiredAngle(target);
		
    	// Go the other way next time
    	targetSearchDirection *= -1.0;
    	targetSearchCount++;
	}
	
	protected boolean isFinished() {
		return targetFound;
	}
	
	public void end() {
		shooterTurret.runTurret(0.0);
		
	}
	
	protected void interrupted() {
		end();
	}

}
