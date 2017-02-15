package org.usfirst.frc.team3467.robot.claw;

import org.usfirst.frc.team3467.robot.RobotMap;

public class Claw extends Subsystem {
	
		Solenoid piston = new Solenoid();
			
		public void initDefaultCommand() {
		} 
		
	public void open(){
		piston.set(RobotMap.OPEN);
		
	} 
	
	public void close(){
		piston.set(RobotMap.CLOSED);
		
	}
	
	public boolean isOpen(){
		return piston.get().equals(RobotMap.OPEN);
		
	}
	
	public boolean isClosed() {
		return piston.get().equals(RobotMap.CLOSED);
	}
	
}