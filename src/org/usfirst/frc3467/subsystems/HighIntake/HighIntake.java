
package org.usfirst.frc3467.subsystems.HighIntake;

import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HighIntake extends Subsystem {
    
	public Victor upperIntake;
// public DoubleSolenoid highIntake;
	
	public HighIntake() {
	upperIntake = new Victor(1);
	
//	highIntake = new DoubleSolenoid(3, 4);
//	port numbers, placeholder values currently
		upperIntake = new Victor(8);
	}
	public void extend(){
		Pneumatics.getInstance().highIntakeExtend();
	}
	public void retract(){
		Pneumatics.getInstance().highIntakeRetract();
	}
	public void vixTend(Victor upperIntake){
		upperIntake.set(1.0);
	}
	public void vixTract(Victor upperIntake){
		upperIntake.set(-0.7);
	}
    public void vicStop(Victor upperIntake){
    	upperIntake.set(0);
    }
	
    public void initDefaultCommand() {
    	
    }
    
}

