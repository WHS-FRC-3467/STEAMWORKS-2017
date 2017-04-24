
package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterFlywheel extends Subsystem {
    
	// Grayhil Encoder @ 256 cnts/rev
	//public final static double SHOOTER_SPEED_DEFAULT = 5875;

	// CUI Encoder @ 2048 cnts/rev
	public final static double SHOOTER_SPEED_DEFAULT = 39000;
	
	private boolean flg_tuning = false;   // Set to true to tune PID constants vis SmartDashboard
	
    private CANTalon shooterTalon1, shooterTalon2;

	private double shooterF, shooterP, shooterI, shooterD;
	
    public ShooterFlywheel() {
    	
    	shooterTalon1 = new CANTalon(RobotMap.shooterWheel_Talon1);
		shooterTalon1.changeControlMode(TalonControlMode.Speed);
		//shooterTalon1.changeControlMode(TalonControlMode.PercentVbus);

    	shooterTalon2 = new CANTalon(RobotMap.shooterWheel_Talon2);
		shooterTalon2.changeControlMode(TalonControlMode.Follower);
		shooterTalon2.set(RobotMap.shooterWheel_Talon1);

    	shooterF = 0.015;
    	shooterP = 0.55;
    	shooterI = 0.0;
    	shooterD = 0.4;
    	SmartDashboard.putNumber("Shooter F", shooterF);
	   	SmartDashboard.putNumber("Shooter P", shooterP);
    	SmartDashboard.putNumber("Shooter I", shooterI);
    	SmartDashboard.putNumber("Shooter D", shooterD);

    	// Shooter Talons - Settings for 1 will be passed to 2
    	shooterTalon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterTalon1.reverseSensor(true);
		
		shooterTalon1.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterTalon1.configPeakOutputVoltage(+12.0f, 0.0f);
		shooterTalon1.setNominalClosedLoopVoltage(12.0);
		shooterTalon1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_5Ms);
		shooterTalon1.SetVelocityMeasurementWindow(10);
		shooterTalon1.setProfile(0);
    	shooterTalon1.setF(shooterF);
    	shooterTalon1.setP(shooterP);
    	shooterTalon1.setI(shooterI);
    	shooterTalon1.setD(shooterD); 
		shooterTalon1.setIZone(0);
		
		shooterTalon1.enableBrakeMode(false);
		shooterTalon2.enableBrakeMode(false);

    }

    public void initDefaultCommand() {
    }
    
    public void runShooter(double speed) {
    	
    	double shooterTarget = speed;
    
    	// Only run Shooter one way!
    	if (shooterTarget < 0.0) shooterTarget = 0.0;

       	if (flg_tuning) {
       		shooterTalon1.setF( shooterF = SmartDashboard.getNumber("Shooter F", shooterF));
       		shooterTalon1.setP( shooterP = SmartDashboard.getNumber("Shooter P", shooterP));
       		shooterTalon1.setI( shooterI = SmartDashboard.getNumber("Shooter I", shooterI));
       		shooterTalon1.setD( shooterD = SmartDashboard.getNumber("Shooter D", shooterD));
       	}

       	shooterTalon1.set(shooterTarget);

       	SmartDashboard.putNumber("ShooterWheel Target:", shooterTarget);
    	SmartDashboard.putNumber("ShooterWheel1 Actual:", shooterTalon1.get());
       	SmartDashboard.putNumber("ShooterWheel2 Actual:", shooterTalon2.get());
       		
 	
    }
    
    public void stopShooter() {
      	shooterTalon1.set(0.0);
    }
 }

