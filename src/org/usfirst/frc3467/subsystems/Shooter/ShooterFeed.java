
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterFeed extends Subsystem {
    
	public final static double SPINNER_SPEED_DEFAULT = 1.0;
	public final static double BELT_SPEED_DEFAULT = -0.45;

    private CANTalon beltTalon;
    private Victor spinnerMotor;

    public ShooterFeed() {
    	
    	beltTalon = new CANTalon(RobotMap.shooterFeedTower_Talon);
    	spinnerMotor = new Victor(RobotMap.shooterSpin_Victor);
    	
		beltTalon.changeControlMode(TalonControlMode.PercentVbus);
		beltTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		beltTalon.configPeakOutputVoltage(+12.0f, -12.0f);
		beltTalon.enableBrakeMode(false);

    }

    public void initDefaultCommand() {
    }
    
    public void runSpinner(double speed) {
    	spinnerMotor.set(speed);
    }
    
    public void runBelt(double speed) {
    	beltTalon.set(speed);
    }

    public void runFeed(double speed) {
    	spinnerMotor.set(speed * SPINNER_SPEED_DEFAULT);
    	beltTalon.set(speed * BELT_SPEED_DEFAULT);
    }
}

