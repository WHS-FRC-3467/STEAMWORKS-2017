
package org.usfirst.frc3467.subsystems.Shooter;

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
    	
    }

    public void initDefaultCommand() {
    }
    
    public void SpinnerRun() {
    	spinnerMotor.set(.7);
    }
    
    public void BeltRun() {
    	beltTalon.set(.7);
    }
    
    public void ShooterRun() {
    	shooterTalon1.set(.7);
    	shooterTalon2.set(.7);
    }
}

