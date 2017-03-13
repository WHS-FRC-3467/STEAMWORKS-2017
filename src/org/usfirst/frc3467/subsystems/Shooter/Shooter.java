
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		shooterTalon1.reverseSensor(true);
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
		//shooterTalon1.changeControlMode(TalonControlMode.PercentVbus);
		
		//Shooter Talon 2
		shooterTalon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterTalon2.reverseSensor(true);
		//shooterTalon2.configEncoderCodesPerRev(2048 * 4);
	
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
		//shooterTalon2.changeControlMode(TalonControlMode.PercentVbus);
		
		beltTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		beltTalon.reverseSensor(true);
		beltTalon.configEncoderCodesPerRev(2048 * 4);
		
		beltTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		beltTalon.configPeakOutputVoltage(+12.0f, -12.0f);
		beltTalon.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		beltTalon.setProfile(0);
		beltTalon.setF(1.0); //.035
		beltTalon.setP(.4); //.04
		beltTalon.setI(0); //0
		beltTalon.setD(0); //.5
		beltTalon.setIZone(0); //0
		beltTalon.changeControlMode(TalonControlMode.Speed);
    }

    public void initDefaultCommand() {
    }
    
    public void SpinnerRun(double speed) {
    	spinnerMotor.set(speed);
    }
    
    public void BeltRun(double speed) {
    	
    	beltTalon.set(speed * 1000.);
       	SmartDashboard.putNumber("ShooterBelt:", beltTalon.getPosition());
    	//double target = speed*1000;
 /*   	if(target >= 0){
    		if(beltTalon.getControlMode() == CANTalon.TalonControlMode.PercentVbus){
    			beltTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
    		}
    		beltTalon.set(target);
    	}
    	else{
    		if(beltTalon.getControlMode() == CANTalon.TalonControlMode.Speed){
    			beltTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    		}
    		beltTalon.set(target);
    	}
   */
    	}
    
    public void ShooterRun(double input) {
    	
    	double target = input * 1000;
		
    
    	//System.out.println("Shooter Run: " + target + "  spd1: " + shooterTalon1.getSpeed() + "  spd2: " + shooterTalon2.getSpeed());
    	
    	if (target < 0.0) target = 0.0;
       	SmartDashboard.putNumber("Shooter1:", shooterTalon1.getPosition());
       	SmartDashboard.putNumber("Shooter2:", shooterTalon2.getPosition());
       	SmartDashboard.putNumber("Shooter Target Speed:", target);
       	
       	shooterTalon1.set(target);
    	shooterTalon2.set(target);
 	
    }
    
    public void ShooterStop() {
      	shooterTalon1.set(0.0);
      	shooterTalon2.set(0.0);
		//shooterTalon1.changeControlMode(TalonControlMode.Speed);
		//shooterTalon2.changeControlMode(TalonControlMode.Speed);
    }
 }

