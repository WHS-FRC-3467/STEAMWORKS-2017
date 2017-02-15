package org.usfirst.frc3467.subsystems.GearCatcher;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {
	
		DoubleSolenoid piston = new DoubleSolenoid(0, 1);
			
		public void initDefaultCommand() {
		} 
		
	public void open(){
		piston.set(DoubleSolenoid.Value.kForward);
		
	} 
	
	public void close(){
		piston.set(DoubleSolenoid.Value.kReverse);
		
	}
	
	public boolean isOpen(){
		return piston.get().equals(DoubleSolenoid.Value.kForward);
		
	}
	
	public boolean isClosed() {
		return piston.get().equals(DoubleSolenoid.Value.kReverse);
	}
	
}