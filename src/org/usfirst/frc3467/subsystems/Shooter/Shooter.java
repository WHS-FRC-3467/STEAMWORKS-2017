
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
    
	private static final double BELT_SPEED_FACTOR = 5000.0;
	private static final double SHOOTER_SPEED_FACTOR = 5000.0;
	
    public CANTalon beltTalon, shooterTalon1, shooterTalon2;
    public Victor spinnerMotor;
    
    public Shooter() {
    	
    	beltTalon = new CANTalon(RobotMap.shooterConv_Talon3);
    	shooterTalon1 = new CANTalon(RobotMap.shooterWheel_Talon1);
    	shooterTalon2 = new CANTalon(RobotMap.shooterWheel_Talon2);
    	spinnerMotor = new Victor(RobotMap.shooterSpin_Victor);
    	
    	SmartDashboard.putNumber("Shooter F", 0.5);
	   	SmartDashboard.putNumber("Shooter P", 0.08);
    	SmartDashboard.putNumber("Shooter I", 0.0);
    	SmartDashboard.putNumber("Shooter D", 0.5);

    	//Shooter Talon 1
    	shooterTalon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterTalon1.reverseSensor(true);
		
		shooterTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterTalon1.configPeakOutputVoltage(+12.0f, 0.0f);
		shooterTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		shooterTalon1.setProfile(0);
		shooterTalon1.setF(0.5); 
		shooterTalon1.setP(0.08); 
		shooterTalon1.setI(0); 
		shooterTalon1.setD(0.5); 
		shooterTalon1.setIZone(0);
		shooterTalon1.changeControlMode(TalonControlMode.Speed);
		//shooterTalon1.changeControlMode(TalonControlMode.PercentVbus);
		
		//Shooter Talon 2
		shooterTalon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterTalon2.reverseSensor(true);
	
		shooterTalon2.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterTalon2.configPeakOutputVoltage(+12.0f, 0.0f);
		shooterTalon2.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		shooterTalon2.setProfile(0);
		shooterTalon2.setF(0.5); 
		shooterTalon2.setP(0.08);
		shooterTalon2.setI(0.0);
		shooterTalon2.setD(0.5);
		shooterTalon2.setIZone(0);
		shooterTalon2.changeControlMode(TalonControlMode.Speed);
		//shooterTalon2.changeControlMode(TalonControlMode.PercentVbus);
		
		beltTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//beltTalon.reverseSensor(true);
		
		beltTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		beltTalon.configPeakOutputVoltage(+12.0f, -12.0f);
		beltTalon.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		beltTalon.setProfile(0);
	   	SmartDashboard.putNumber("Belt P", 0.07);
    	SmartDashboard.putNumber("Belt I", 0.0);
    	SmartDashboard.putNumber("Belt D", 0.5);
    	SmartDashboard.putNumber("Belt F", 0.5);
		beltTalon.setF(0.5);
		beltTalon.setP(0.07);
		beltTalon.setI(0);
		beltTalon.setD(0.5);
		beltTalon.setIZone(0);
		beltTalon.changeControlMode(TalonControlMode.Speed);
		
		shooterTalon1.enableBrakeMode(false);
		shooterTalon2.enableBrakeMode(false);
		beltTalon.enableBrakeMode(false);

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
    	//SmartDashboard.putNumber("ShooterBelt Position:", beltTalon.getPosition());
    	
    	double beltP = SmartDashboard.getNumber("Belt P", 0);
    	double beltI = SmartDashboard.getNumber("Belt I", 0);
    	double beltD = SmartDashboard.getNumber("Belt D", 0);
    	double beltF = SmartDashboard.getNumber("Belt F", 0);
		beltTalon.setF(beltF);
		beltTalon.setP(beltP);
		beltTalon.setI(beltI);
		beltTalon.setD(beltD);
    	
    }
    
    public void ShooterRun(double speed) {
    	
    	double shooterTarget = speed * Shooter.SHOOTER_SPEED_FACTOR;
    
    	// Only run Shooter one way!
    	if (shooterTarget < 0.0) shooterTarget = 0.0;

       	SmartDashboard.putNumber("ShooterWheel Target:", shooterTarget);
    	SmartDashboard.putNumber("ShooterWheel1 Actual:", shooterTalon1.getSpeed());
       	SmartDashboard.putNumber("ShooterWheel2 Actual:", shooterTalon2.getSpeed());
    	//SmartDashboard.putNumber("ShooterWheel1 Position:", shooterTalon1.getPosition());
       	//SmartDashboard.putNumber("ShooterWheel2 Position:", shooterTalon2.getPosition());
       		
    	double shooterF = SmartDashboard.getNumber("Shooter F", 0.035);
      	double shooterP = SmartDashboard.getNumber("Shooter P", 0.04);
    	double shooterI = SmartDashboard.getNumber("Shooter I", 0.0);
    	double shooterD = SmartDashboard.getNumber("Shooter D", 0.5);
    	shooterTalon1.setF(shooterF); 
    	shooterTalon1.setP(shooterP); 
    	shooterTalon1.setI(shooterI); 
    	shooterTalon1.setD(shooterD);
    	shooterTalon2.setF(shooterF);
    	shooterTalon2.setP(shooterP);
    	shooterTalon2.setI(shooterI);
    	shooterTalon2.setD(shooterD); 
          	
       	shooterTalon1.set(shooterTarget);
    	shooterTalon2.set(shooterTarget);
 	
    }
    
    public void ShooterStop() {
      	shooterTalon1.set(0.0);
      	shooterTalon2.set(0.0);
    }
 }

