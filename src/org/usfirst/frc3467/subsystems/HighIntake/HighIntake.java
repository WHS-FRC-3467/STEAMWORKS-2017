
package org.usfirst.frc3467.subsystems.HighIntake;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.usfirst.frc3467.subsystems.FloorIntake.IntakeDrive;
import com.ctre.CANTalon;

public class HighIntake extends Subsystem {
    static final double kIntake = 1;
	public static final double kStop = 0;
	
	public CANTalon upperIntake;
	public DoubleSolenoid hisolenoid;
	
	public HighIntake() {
		upperIntake = new CANTalon(8);
		hisolenoid = new DoubleSolenoid(3, 4);
	}
	
	 protected void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    	this.setDefaultCommand(new IntakeDrive());
	    }
	    public void DriveAuto(double Speed){
	    	upperIntake.set(Speed);
	    }
	    public void extend(){
	    	hisolenoid.set(DoubleSolenoid.Value.kForward);
	    }
	    public void retract() {
			hisolenoid.set(DoubleSolenoid.Value.kReverse);
		}
		
		public void hold() {
			hisolenoid.set(DoubleSolenoid.Value.kOff);		
		}

		public void setIntake(Value InnOut) {
			// TODO Auto-generated method stub
			
		}
	}

