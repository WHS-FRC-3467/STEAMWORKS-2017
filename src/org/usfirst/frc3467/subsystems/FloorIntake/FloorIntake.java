
package org.usfirst.frc3467.subsystems.FloorIntake;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import com.ctre.CANTalon;

public class FloorIntake extends Subsystem {
	
	// Controls display to SmartDashboard
	private static final boolean debugging = true;
	
	public CANTalon Lo_intake;
	
	public FloorIntake() {
		Lo_intake = new CANTalon(RobotMap.floorIntake_Victor);
	}

	protected void initDefaultCommand() {
		this.setDefaultCommand(new IntakeDrive());
	}
	public void DriveAuto(double speed) {
		// TODO Auto-generated method stub
		if (debugging) {
	    	SmartDashboard.putNumber("Floor Intake Speed", speed);
		}
		Lo_intake.set(speed);
	}
	
	// Extend or Retract Intake
	public void extend() {
		Pneumatics.getInstance().floorIntakeExtend();
	}
	
	public void retract() {
		Pneumatics.getInstance().floorIntakeRetract();
	}
	
	
	
}
