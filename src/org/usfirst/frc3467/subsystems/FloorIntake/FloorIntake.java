
package org.usfirst.frc3467.subsystems.FloorIntake;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

public class FloorIntake extends Subsystem {
	
	// Controls display to SmartDashboard
	private static final boolean debugging = false;
	
	//boolean state =  true;
	
	public Victor Lo_intake;
	
	public FloorIntake() {
		Lo_intake = new Victor(RobotMap.floorIntake_Victor);
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
	
	//public boolean getFloorState() {
	//	return state;
	//}
	
	// Extend or Retract Intake
	public void extend() {
		Pneumatics.getInstance().floorIntakeExtend();
	}
	
	public void retract() {
		Pneumatics.getInstance().floorIntakeRetract();
	}
	
	
	
}
