package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;


/**
 * Drive the given distance straight (negative values go backwards).
 * Uses a local PID controller to run a simple PID loop that is only
 * enabled while this command is running. The input is the averaged
 * values of the left and right encoders.
 */
public class DriveMotionMagic extends CommandBase {
	CANTalon rightTalon;
	CANTalon leftTalon;
	final double P = 2;
	final double I = 0;
	final double D = 1;
	final double F = 0;
	final double MaxSpeed;//rpm
	final double Accel;//rpm per second
	double distanceGoal;
	boolean done = false;
	public DriveMotionMagic(double distance){
		requires(driveBase);
		distanceGoal = distance;
		MaxSpeed = 0;
		Accel = 0;
	}
	public DriveMotionMagic(double distance, double maxSpeed, double accel){
		requires(driveBase);
		distanceGoal = distance;
		MaxSpeed = maxSpeed;
		Accel = accel;
	}
	protected void initialize(){
		driveBase.liftFeetBeforeDriving();
		leftTalon = driveBase.getLeftTalon();
		rightTalon = driveBase.getRightTalon();
		leftTalon.changeControlMode(TalonControlMode.MotionMagic);
		rightTalon.changeControlMode(TalonControlMode.MotionMagic);
		leftTalon.setPID(P, I, D);
		rightTalon.setPID(P, I, D);
		leftTalon.setF(F);
		rightTalon.setF(F);
		leftTalon.setMotionMagicAcceleration(Accel);
		rightTalon.setMotionMagicAcceleration(Accel);
		leftTalon.setMotionMagicCruiseVelocity(MaxSpeed);
		rightTalon.setMotionMagicCruiseVelocity(MaxSpeed);
		leftTalon.set(distanceGoal);
		rightTalon.set(distanceGoal);
	}
	protected void execute(){
		if(Math.abs(leftTalon.getPosition()-distanceGoal) < 5 && Math.abs(rightTalon.getPosition()-distanceGoal) < 5){
			done = true;
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return done;
	}
	protected void end(){
		leftTalon.changeControlMode(TalonControlMode.PercentVbus);
		rightTalon.changeControlMode(TalonControlMode.PercentVbus);
		leftTalon.set(0);
		rightTalon.set(0);
	}
	protected void interrupted(){
		end();
	}
	
}