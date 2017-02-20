package org.usfirst.frc3467.subsystems.GearCatcher;

import org.usfirst.frc3467.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {
	
		ClawSolenoid piston = new ClawSolenoid();
			
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