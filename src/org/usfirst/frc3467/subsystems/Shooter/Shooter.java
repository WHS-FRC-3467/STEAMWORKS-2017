
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
    
    public CANTalon beltTalon, shooterTalon1, shooterTalon2;
    public Victor spinnerMotor;
    
    public Shooter() {
    	beltTalon = new CANTalon(RobotMap.shooterConv_Talon3);
    	shooterTalon1 = new CANTalon(RobotMap.shooterWheel_Talon1);
    	shooterTalon2 = new CANTalon(RobotMap.shooterWheel_Talon2);
    	spinnerMotor = new Victor(RobotMap.shooterSpin_Victor);
    }

    public void initDefaultCommand() {
    }
    
    public void SpinnerRun(double speed) {
    	spinnerMotor.set(speed);
    }
    
    public void BeltRun(double speed) {
    	beltTalon.set(speed);
    }
    
    public void ShooterRun(double speed) {
    	shooterTalon1.set(speed);
    	shooterTalon2.set(speed);
    }
}

