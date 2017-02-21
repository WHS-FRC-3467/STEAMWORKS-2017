
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;

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
    	
    	//Shooter Talon 1
    	shooterTalon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterTalon1.reverseSensor(false);
		shooterTalon1.configEncoderCodesPerRev(2048 * 4);
		
		shooterTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterTalon1.configPeakOutputVoltage(+12.0f, 0.0f);
		shooterTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		shooterTalon1.setProfile(0);
		shooterTalon1.setF(.035); //.035
		shooterTalon1.setP(.04); //.04
		shooterTalon1.setI(0); //0
		shooterTalon1.setD(.5); //.5
		shooterTalon1.setIZone(0); //0
		shooterTalon1.changeControlMode(TalonControlMode.Speed);
		
		//Shooter Talon 2
		shooterTalon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterTalon2.reverseSensor(false);
		shooterTalon2.configEncoderCodesPerRev(2048 * 4);
	
		shooterTalon2.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterTalon2.configPeakOutputVoltage(+12.0f, 0.0f);
		shooterTalon2.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		shooterTalon2.setProfile(0);
		shooterTalon2.setF(.035); //.035
		shooterTalon2.setP(.04); //.04
		shooterTalon2.setI(0); //0
		shooterTalon2.setD(.5); //.5
		shooterTalon2.setIZone(0); //0
		shooterTalon2.changeControlMode(TalonControlMode.Speed);
    }

    public void initDefaultCommand() {
    }
    
    public void SpinnerRun(double speed) {
    	spinnerMotor.set(speed);
    }
    
    public void BeltRun(double speed) {
    	beltTalon.set(speed);
    }
    
    public void ShooterRun(double input) {
    	double target = input * 500;
		
    	shooterTalon1.set(target);
    	shooterTalon2.set(target);
    }
    public void tractionExtend() {
    	Pneumatics.getInstance().tractionFeetDeploy();
    }
    public void tractionRetract() {
    	Pneumatics.getInstance().tractionFeetRetract();
    }
}

