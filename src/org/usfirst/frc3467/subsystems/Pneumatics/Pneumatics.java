
package org.usfirst.frc3467.subsystems.Pneumatics;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Brownout.Brownout;
import org.usfirst.frc3467.subsystems.Brownout.PowerConsumer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics extends Subsystem implements PowerConsumer {

	private Compressor scorpionCompressor;
	private AnalogInput pressureSensor;
	private boolean compressorActive = true;
	private static boolean  intakePistonState = true;
	
	// Solenoids
	public DoubleSolenoid tractionFeet;
	public DoubleSolenoid gearIntake;
	public DoubleSolenoid intakeRamp;
	
	// Pneumatics is a singleton
	private static Pneumatics instance = new Pneumatics();

	public static Pneumatics getInstance() {
		return Pneumatics.instance;
	}

	/*
	 * Pneumatics Class Constructor
	 *
	 * The singleton instance is created statically with
	 * the instance static member variable.
	 */
	protected Pneumatics() {
				
		scorpionCompressor = new Compressor();
		pressureSensor = new AnalogInput(RobotMap.pneumatics_sensor_port);

		initSolenoids();
		
		scorpionCompressor.start();
		compressorActive = true;
		
		Brownout.getInstance().registerCallback(this);
	}
	
	private void initSolenoids() {
		gearIntake = new DoubleSolenoid(RobotMap.gearintake_solenoid_retract,
										 RobotMap.gearintake_solenoid_extend);

		tractionFeet = new DoubleSolenoid(RobotMap.traction_solenoid_deploy,
										 RobotMap.traction_solenoid_retract);

		intakeRamp = new DoubleSolenoid(RobotMap.intakeRamp_solenoid_retract,
			  							RobotMap.intakeRamp_solenoid_extend);

		gearIntake.set(DoubleSolenoid.Value.kForward);
		tractionFeet.set(DoubleSolenoid.Value.kReverse);
		intakeRamp.set(DoubleSolenoid.Value.kReverse);
	}
	
	/*
	 * Custom Pneumatics Helper methods
	 */
		
	public void tractionFeetDeploy() {
		tractionFeet.set(DoubleSolenoid.Value.kForward);
	}
	public void tractionFeetRetract() {
		tractionFeet.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void gearIntakeUp() {
		gearIntake.set(DoubleSolenoid.Value.kForward);
	}
	public void gearIntakeDown() {
		gearIntake.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void intakeRampRelease() {
		intakeRamp.set(DoubleSolenoid.Value.kForward);
	}
	public void intakeRampHold() {
		intakeRamp.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void toggleIntakeRamp() {
		if (intakePistonState) {
			intakeRamp.set(DoubleSolenoid.Value.kForward);
			intakePistonState = false;
		}
		else {
			intakeRamp.set(DoubleSolenoid.Value.kReverse);
			intakePistonState = true;
		}
	}
	
	/*
	 * Standard Pneumatics methods	
	 */
	
	public double getPressure() {
		return pressureSensor.getVoltage();
	}
	
	public void compressorStop() {
		scorpionCompressor.stop();
		compressorActive = false;
	}
	
	public void compressorStart() {
		scorpionCompressor.start();
		compressorActive = true;
	}
	
	public void callbackAlert(Brownout.PowerLevel level) {
		switch (level) {
		case Critical:
			if (compressorActive) {
				scorpionCompressor.stop();
				compressorActive = false;
			}
			break;
		
		case Normal:
		default:
			if (!compressorActive) {
				scorpionCompressor.start();
				compressorActive = true;
			}
			break;
		}
	}

	// Set up a default command to regularly call reportPressure()
	protected void initDefaultCommand() {
		this.setDefaultCommand(new Compressor_ReportStatus());
	}

	public void reportPressure() {
		SmartDashboard.putBoolean("Compressor Active?", compressorActive);
		SmartDashboard.putNumber("Pressure (voltage)", pressureSensor.getVoltage());		
	}
}
