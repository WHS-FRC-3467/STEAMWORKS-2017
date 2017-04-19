
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
public class ShooterFeed extends Subsystem {
    
	public final static double SPINNER_SPEED_DEFAULT = 0.4;
	public final static double BELT_SPEED_DEFAULT = -0.45;

	private boolean flg_tuning = true;   // Set to true to tune PID constants vis SmartDashboard
	
    private CANTalon beltTalon;
    private Victor spinnerMotor;

    private static final double BELT_SPEED_FACTOR = 5000.0;
	private double beltF, beltP, beltI, beltD;
	
    public ShooterFeed() {
    	
    	beltTalon = new CANTalon(RobotMap.shooterFeedTower_Talon);
    	spinnerMotor = new Victor(RobotMap.shooterSpin_Victor);
    	
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
		
		beltTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		beltTalon.configPeakOutputVoltage(+12.0f, -12.0f);
		beltTalon.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_5Ms);
		beltTalon.SetVelocityMeasurementWindow(10);
		beltTalon.setProfile(0);
		beltTalon.setF(beltF);
		beltTalon.setP(beltP);
		beltTalon.setI(beltI);
		beltTalon.setD(beltD);
		beltTalon.setIZone(0);
		beltTalon.changeControlMode(TalonControlMode.Speed);

		beltTalon.enableBrakeMode(false);

    }

    public void initDefaultCommand() {
    }
    
    public void SpinnerRun(double speed) {
    	spinnerMotor.set(speed);
    }
    
    public void BeltRun(double speed) {
    	
       	if (flg_tuning) {
       		beltTalon.setF( beltF = SmartDashboard.getNumber("Belt F", beltF));
			beltTalon.setP( beltP = SmartDashboard.getNumber("Belt P", beltP));
			beltTalon.setI( beltI = SmartDashboard.getNumber("Belt I", beltI));
			beltTalon.setD( beltD = SmartDashboard.getNumber("Belt D", beltD));
       	}

       	double beltTarget = speed * ShooterFeed.BELT_SPEED_FACTOR;
    	beltTalon.set(beltTarget);
       	
    	SmartDashboard.putNumber("ShooterBelt Target:", beltTarget);
       	SmartDashboard.putNumber("ShooterBelt Speed:", beltTalon.get());
    	
    }
    
 }

