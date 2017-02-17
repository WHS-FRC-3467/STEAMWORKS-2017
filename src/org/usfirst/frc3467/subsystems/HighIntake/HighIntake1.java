
package org.usfirst.frc3467.subsystems.HighIntake;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HighIntake1 extends Subsystem {
    
	public CANTalon upperIntake;
	public DoubleSolenoid hisolenoid;
	
	public HighIntake1() {
		upperIntake = new CANTalon(8);
		hisolenoid = new DoubleSolenoid(3, 4);
		//port numbers, placeholder values currently
	}
    
    public void deploy(DoubleSolenoid hisolenoid) {
    	hisolenoid.set(DoubleSolenoid.Value.kForward);
    	
    }
    
    public void retract(DoubleSolenoid hisolenoid) {
    	hisolenoid.set(DoubleSolenoid.Value.kReverse);
  	
    }
    
    public void off(DoubleSolenoid hisolenoid) {
    	hisolenoid.set(DoubleSolenoid.Value.kOff);
    }
    
    public void initDefaultCommand() {
    	
    }
    
}

