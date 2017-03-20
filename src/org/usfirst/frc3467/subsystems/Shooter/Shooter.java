
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
    
	private static final double BELT_SPEED_FACTOR = 1000.0;
	private static final double SHOOTER_SPEED_FACTOR = 1000.0;
	
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
    	
    	double beltTarget = speed * Shooter.BELT_SPEED_FACTOR;
    	beltTalon.set(beltTarget);
       	SmartDashboard.putNumber("ShooterBelt Target:", beltTarget);
       	SmartDashboard.putNumber("ShooterBelt Speed:", beltTalon.getSpeed());
    }
    
    public void ShooterRun(double speed) {
    	
    	double shooterTarget = speed * Shooter.SHOOTER_SPEED_FACTOR;
    
    	// Only run Shooter one way!
    	if (shooterTarget < 0.0) shooterTarget = 0.0;

       	SmartDashboard.putNumber("ShooterWheel Target:", shooterTarget);
    	SmartDashboard.putNumber("ShooterWheel1 Actual:", shooterTalon1.getSpeed());
       	SmartDashboard.putNumber("ShooterWheel2 Actual:", shooterTalon2.getSpeed());
       	
       	shooterTalon1.set(shooterTarget);
    	shooterTalon2.set(shooterTarget);
 	
    }
    
    public void ShooterStop() {
      	shooterTalon1.set(0.0);
      	shooterTalon2.set(0.0);
    }
 }

