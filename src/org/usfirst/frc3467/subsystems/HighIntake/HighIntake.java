
package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HighIntake extends Subsystem {
    
	CANTalon upperIntake;
	
	public HighIntake() {
		upperIntake = new CANTalon(RobotMap.highIntake_Victor);
	}

    public void initDefaultCommand() {
    	
    }
    
    public void HIntakeRun(double speed) {
    	upperIntake.set(speed);
    }
    
    public void hiIntakeExtend() {
    	Pneumatics.getInstance().highIntakeExtend();
    }
    
    public void hiIntakeRetract() {
    	Pneumatics.getInstance().highIntakeRetract();
    }
}

