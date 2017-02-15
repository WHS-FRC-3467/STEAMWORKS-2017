
package org.usfirst.frc3467.subsystems.HighIntake;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HighIntake extends Subsystem {
    
	public CANTalon upperIntake;
	public DoubleSolenoid hisolenoid;
	
	public HighIntake() {
		upperIntake = new CANTalon(8);
		hisolenoid = new DoubleSolenoid(3, 4);
	}

    public void initDefaultCommand() {
    	
    	
    	
    	
    }
}

