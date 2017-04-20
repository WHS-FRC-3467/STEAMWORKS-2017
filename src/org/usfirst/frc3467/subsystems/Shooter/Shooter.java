
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
    
	public final static double SPINNER_SPEED_DEFAULT = 0.4;
	public final static double BELT_SPEED_DEFAULT = -0.45;
	public final static double SHOOTER_SPEED_DEFAULT = 4.7;
	//public final static double SHOOTER_SPEED_DEFAULT = .62;

	private boolean flg_tuning = true;   // Set to true to tune PID constants vis SmartDashboard
	
    private CANTalon beltTalon, shooterTalon1, shooterTalon2;
    private Victor spinnerMotor;

    private static final double BELT_SPEED_FACTOR = 5000.0;
	private static final double SHOOTER_SPEED_FACTOR = 10000.0/8.0;
	//private static final double SHOOTER_SPEED_FACTOR = 1.0;
	
	private double shooterF, shooterP, shooterI, shooterD;
	private double beltF, beltP, beltI, beltD;
	
    public Shooter() {
    	
    	shooterTalon1 = new CANTalon(RobotMap.shooterWheel_Talon1);
		shooterTalon1.changeControlMode(TalonControlMode.Speed);
		//shooterTalon1.changeControlMode(TalonControlMode.PercentVbus);

    	//shooterTalon2 = new CANTalon(RobotMap.shooterWheel_Talon2);
		//shooterTalon2.changeControlMode(TalonControlMode.Follower);
		//shooterTalon2.set(RobotMap.shooterWheel_Talon1);

		beltTalon = new CANTalon(RobotMap.shooterFeedTower_Talon);
    	spinnerMotor = new Victor(RobotMap.shooterSpin_Victor);
    	
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
		
    	// Tower Belt
/*
  		beltF = 0.4;
    	beltP = 0.01;
    	beltI = 0.0;
    	beltD = 0.0;
    	SmartDashboard.putNumber("Belt F", beltF);
    	SmartDashboard.putNumber("Belt P", beltP);
    	SmartDashboard.putNumber("Belt I", beltI);
    	SmartDashboard.putNumber("Belt D", beltD);

		beltTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//beltTalon.reverseSensor(true);
*/		
		beltTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		beltTalon.configPeakOutputVoltage(+12.0f, -12.0f);
/*		beltTalon.setNominalClosedLoopVoltage(12.0);
		beltTalon.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_5Ms);
		beltTalon.SetVelocityMeasurementWindow(10);
		beltTalon.setProfile(0);
		beltTalon.setF(beltF);
		beltTalon.setP(beltP);
		beltTalon.setI(beltI);
		beltTalon.setD(beltD);
		beltTalon.setIZone(0);
		beltTalon.changeControlMode(TalonControlMode.Speed);
*/
		beltTalon.changeControlMode(TalonControlMode.PercentVbus);
		
		shooterTalon1.enableBrakeMode(false);
//		shooterTalon2.enableBrakeMode(false);
		beltTalon.enableBrakeMode(false);

    }

    public void initDefaultCommand() {
    }
    
    public void SpinnerRun(double speed) {
    	spinnerMotor.set(speed);
    }
    
    public void BeltRun(double speed) {
    	
/*
       	if (flg_tuning) {
       		beltTalon.setF( beltF = SmartDashboard.getNumber("Belt F", beltF));
			beltTalon.setP( beltP = SmartDashboard.getNumber("Belt P", beltP));
			beltTalon.setI( beltI = SmartDashboard.getNumber("Belt I", beltI));
			beltTalon.setD( beltD = SmartDashboard.getNumber("Belt D", beltD));
       	}

       	double beltTarget = speed * Shooter.BELT_SPEED_FACTOR;
    	beltTalon.set(beltTarget);
 */
       	beltTalon.set(speed);
           	
//    	SmartDashboard.putNumber("ShooterBelt Target:", beltTarget);
//       	SmartDashboard.putNumber("ShooterBelt Speed:", beltTalon.get());
    	
    }
    
    public void ShooterRun(double speed) {
    	
    	double shooterTarget = speed * Shooter.SHOOTER_SPEED_FACTOR;
    
    	// Only run Shooter one way!
    	if (shooterTarget < 0.0) shooterTarget = 0.0;

       	if (flg_tuning) {
       		shooterTalon1.setF( shooterF = SmartDashboard.getNumber("Shooter F", shooterF));
       		shooterTalon1.setP( shooterP = SmartDashboard.getNumber("Shooter P", shooterP));
       		shooterTalon1.setI( shooterI = SmartDashboard.getNumber("Shooter I", shooterI));
       		shooterTalon1.setD( shooterD = SmartDashboard.getNumber("Shooter D", shooterD));
       	}

       	SmartDashboard.putNumber("ShooterWheel Target:", shooterTarget);
    	SmartDashboard.putNumber("ShooterWheel1 Actual:", shooterTalon1.get());
//       	SmartDashboard.putNumber("ShooterWheel2 Actual:", shooterTalon2.get());
       		
       	shooterTalon1.set(shooterTarget);
 	
    }
    
    public void ShooterStop() {
      	shooterTalon1.set(0.0);
    }
 }

