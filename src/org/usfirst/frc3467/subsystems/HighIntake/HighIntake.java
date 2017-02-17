
package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HighIntake extends Subsystem {
    
	CANTalon upperIntake;
	DoubleSolenoid hisolenoid;
	
	public HighIntake() {
		upperIntake = new CANTalon(8);
	}

    public void initDefaultCommand() {
    	
    }
}

