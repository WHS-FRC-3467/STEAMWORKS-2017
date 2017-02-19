
package org.usfirst.frc3467.subsystems.Pneumatics;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc3467.robot.RobotMap;
import org.usfirst.frc3467.subsystems.Brownout.Brownout;
import org.usfirst.frc3467.subsystems.Brownout.PowerConsumer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics extends Subsystem implements PowerConsumer {

	private Compressor scorpionCompressor;
	private AnalogInput pressureSensor;
	private boolean compressorActive = true;
	
	// Solenoids
	public DoubleSolenoid tractionFeet;
	public DoubleSolenoid floorIntake;
	public DoubleSolenoid highIntake;
	public DoubleSolenoid gearCatch;
	public DoubleSolenoid gearClaw;
	public Solenoid hopperSides;
	public DoubleSolenoid pusherBars;
	
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
		instance = this;
		
		scorpionCompressor = new Compressor();
		pressureSensor = new AnalogInput(RobotMap.pneumatics_sensor_port);

		initSolenoids();
		
		scorpionCompressor.start();
		compressorActive = true;
		
		Brownout.getInstance().registerCallback(this);
	}
	
	private void initSolenoids() {
		tractionFeet = new DoubleSolenoid(RobotMap.traction_solenoid_retract, RobotMap.traction_solenoid_deploy);
		floorIntake = new DoubleSolenoid(RobotMap.floorintake_solenoid_retract, RobotMap.floorintake_solenoid_extend);
		highIntake = new DoubleSolenoid(RobotMap.highintake_solenoid_retract, RobotMap.highintake_solenoid_extend);
		gearCatch = new DoubleSolenoid(RobotMap.gearcatch_solenoid__up, RobotMap.gearcatch_solenoid__down);
		gearClaw = new DoubleSolenoid(RobotMap.gearclaw_solenoid__hold, RobotMap.gearclaw_solenoid__release);
		hopperSides = new Solenoid(RobotMap.hopper_solenoid);
		pusherBars = new DoubleSolenoid(RobotMap.pusher_solenoid_retract, RobotMap.pusher_solenoid_extend);
		
		tractionFeet.set(DoubleSolenoid.Value.kForward);
		floorIntake.set(DoubleSolenoid.Value.kForward);
		highIntake.set(DoubleSolenoid.Value.kForward);
		gearCatch.set(DoubleSolenoid.Value.kForward);
		gearClaw.set(DoubleSolenoid.Value.kForward);
		hopperSides.set(false);
		pusherBars.set(DoubleSolenoid.Value.kForward);
		
	}
	
	/*
	 * Custom Pneumatics Helper methods
	 */
		
	public void tractionFeetRetract() {
		tractionFeet.set(DoubleSolenoid.Value.kForward);
	}
	public void tractionFeetDeploy() {
		tractionFeet.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void floorIntakeRetract() {
		floorIntake.set(DoubleSolenoid.Value.kForward);
	}
	public void floorIntakeExtend() {
		floorIntake.set(DoubleSolenoid.Value.kReverse);
	}

	public void highIntakeRetract() {
		highIntake.set(DoubleSolenoid.Value.kForward);
	}
	public void highIntakeExtend() {
		highIntake.set(DoubleSolenoid.Value.kReverse);
	}

	public void gearCatchUp() {
		gearCatch.set(DoubleSolenoid.Value.kForward);
	}
	public void gearCatchDown() {
		gearCatch.set(DoubleSolenoid.Value.kReverse);
	}

	public void gearClawHold() {
		gearClaw.set(DoubleSolenoid.Value.kForward);
	}
	public void gearClawRelease() {
		gearClaw.set(DoubleSolenoid.Value.kReverse);
	}

	public void hopperContract() {
		hopperSides.set(false);
	}
	public void hopperExpand() {
		hopperSides.set(true);
	}

	public void pusherBarsRetract() {
		pusherBars.set(DoubleSolenoid.Value.kForward);
	}
	public void pusherBarsExtend() {
		pusherBars.set(DoubleSolenoid.Value.kReverse);
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
