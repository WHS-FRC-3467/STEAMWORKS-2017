
package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HighIntake extends Subsystem {
    
	public CANTalon upperIntake;
	public DoubleSolenoid hisolenoid;
	
	public HighIntake() {
		upperIntake = new CANTalon(RobotMap.UIntakeMotor);
		hisolenoid = new DoubleSolenoid(RobotMap.U_intake_extend, RobotMap.U_intake_retract);
	}

    public void initDefaultCommand() {
    	
    }
}

