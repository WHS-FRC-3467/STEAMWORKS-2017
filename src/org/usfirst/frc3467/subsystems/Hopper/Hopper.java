package org.usfirst.frc3467.subsystems.Hopper;

import org.usfirst.frc3467.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hopper extends Subsystem {
	
	public static DoubleSolenoid hopperSolenoid;
	private static Hopper instance;
	
	public static Hopper getInstance(){
		return instance;
	}
	public Hopper(){
		instance = this;
		hopperSolenoid = new DoubleSolenoid(RobotMap.hopper_solenoid_deploy,
				RobotMap.hopper_solenoid_retract);
	}
	public void extend(){
		hopperSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	public void retract(){
		hopperSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
