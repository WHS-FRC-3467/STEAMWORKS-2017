package org.usfirst.frc3467.subsystems.Claw;

import org.usfirst.frc3467.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {
	
		DoubleSolenoid claw_wrist = new DoubleSolenoid(RobotMap.claw_extend, RobotMap.claw_retract);
		DoubleSolenoid claw_hand = new DoubleSolenoid(RobotMap.claw_grab, RobotMap.claw_release);
			
		public void initDefaultCommand() {
		} 
		
	public void open() {
		claw_hand.set(DoubleSolenoid.Value.kReverse);
		
	} 
	
	public void close() {
		claw_hand.set(DoubleSolenoid.Value.kForward);
		
	}
	
	public void clawHold() {
		claw_hand.set(DoubleSolenoid.Value.kOff);
	}
	
	public void down() {
		claw_wrist.set(DoubleSolenoid.Value.kForward);
	}
	
	public void up() {
		claw_wrist.set(DoubleSolenoid.Value.kReverse);
	}
	
}