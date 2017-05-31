package org.usfirst.frc3467.robot;

import org.usfirst.frc3467.robot.control.Gamepad;
import org.usfirst.frc3467.robot.control.triggers.DPadDown;
import org.usfirst.frc3467.robot.control.triggers.DPadLeft;
import org.usfirst.frc3467.robot.control.triggers.DPadRight;
import org.usfirst.frc3467.robot.control.triggers.DPadUp;
import org.usfirst.frc3467.robot.control.triggers.GamepadLeftTrigger;
import org.usfirst.frc3467.robot.control.triggers.GamepadRightTrigger;
import org.usfirst.frc3467.subsystems.Climber.ClimberDrive;
import org.usfirst.frc3467.subsystems.Climber.ToggleLatchPosition;
import org.usfirst.frc3467.subsystems.DriveBase.AutoGear;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBot;
import org.usfirst.frc3467.subsystems.DriveBase.DriveTurn;
import org.usfirst.frc3467.subsystems.DriveBase.DropTractionPlates;
import org.usfirst.frc3467.subsystems.DriveBase.LiftTractionPlates;
import org.usfirst.frc3467.subsystems.DriveBase.ResetEncoders;
import org.usfirst.frc3467.subsystems.DriveBase.UpdatePIDFConstants;
import org.usfirst.frc3467.subsystems.GearCatcher.GearIntake;
import org.usfirst.frc3467.subsystems.GearCatcher.GearDeliver;
import org.usfirst.frc3467.subsystems.GearCatcher.IntakeWithoutTransistor;
import org.usfirst.frc3467.subsystems.GearCatcher.TestGearIntake;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.tractionDeploy;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.tractionRetract;
import org.usfirst.frc3467.subsystems.Shooter.AutoAim;
import org.usfirst.frc3467.subsystems.Shooter.CalibrateTurret;
import org.usfirst.frc3467.subsystems.Shooter.HaltShooter;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;
import org.usfirst.frc3467.subsystems.Shooter.PositionTurret;
import org.usfirst.frc3467.subsystems.Shooter.RunFeed;
import org.usfirst.frc3467.subsystems.Shooter.RunTurret;
import org.usfirst.frc3467.subsystems.Shooter.TestShooter;
import org.usfirst.frc3467.subsystems.Shooter.TestFeed;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Gamepad driverPad;
	public static Gamepad operatorPad;

	// Normal: Left Stick: X/Y Translation; Right Stick X: Rotation
	// Reverse: Right Stick: X/Y Translation; Left Stick X: Rotation
	public static boolean reverseDriveSticks = false; 
	
	public OI(){
		driverPad = new Gamepad(0);
		operatorPad = new Gamepad(1);
	}
	
	// Methods that return values for specific driving parameters
	public double getDriveX(){
		if (reverseDriveSticks)
			return driverPad.getRightStickX();
		else
			return driverPad.getLeftStickX();
	}
	
	public double getDriveY(){
		if (reverseDriveSticks)
			return driverPad.getRightStickY();
		else
			return driverPad.getLeftStickY();
	}
	
	public double getDriveRotation() {
		if (reverseDriveSticks)
			return driverPad.getLeftStickX();
		else
			return driverPad.getRightStickX();
	}
	
	//Method that binds certain commands to certain buttons
	public void BindCommands() {
		
		/*
		 *
		 * Driver GamePad
		 * 
		 */
		
		new JoystickButton(driverPad, Gamepad.leftBumper).whenActive(new ToggleGearCatcherPosition());
		new GamepadRightTrigger(driverPad).whenActive(new GearIntake());
		new JoystickButton(driverPad, Gamepad.rightBumper).whenActive(new GearDeliver());
		new JoystickButton(driverPad, Gamepad.startButton).whenActive(new ZeroGyro());
		new JoystickButton(driverPad, Gamepad.yButton).whenActive(new ClimberDrive());
		new JoystickButton(driverPad, Gamepad.bButton).whenActive(new DriveBot(DriveBase.driveMode_FieldCentric));
		new JoystickButton(driverPad, Gamepad.aButton).whenActive(new DriveBot(DriveBase.driveMode_Precision));
		new GamepadLeftTrigger(driverPad).whileActive(new IntakeWithoutTransistor());
		//new JoystickButton(driverPad, Gamepad.leftStickPress).whenActive(new CenterRobot());

		new JoystickButton(driverPad, Gamepad.xButton).whenActive(new DriveBot(DriveBase.driveMode_RobotCentric));
		//new DPadLeft(driverPad).whenActive(new AutoAim()));
		new DPadRight(driverPad).whenActive(new DriveBot(DriveBase.driveMode_Arcade));
		//new DPadUp(driverPad).whenActive(new DriveBot(DriveBase.driveMode_FieldCentric));
		
		
		/*
		 * 
		 * Operator GamePad
		 * 
		 */

		//new GamepadLeftTrigger(operatorPad).whileActive(new AutoAim());
		new GamepadLeftTrigger(operatorPad).whenActive(new OperateShooter(true));
		new JoystickButton(operatorPad, Gamepad.leftBumper).whenActive(new TestGearIntake(true));
		new JoystickButton(operatorPad, Gamepad.rightBumper).whenActive(new TestGearIntake(false));

		new JoystickButton(operatorPad, Gamepad.xButton).whileActive(new RunFeed(1.0));
		new JoystickButton(operatorPad, Gamepad.bButton).whileActive(new RunFeed(-1.0));
		//new JoystickButton(operatorPad, Gamepad.yButton).whileActive(new RunBelt(Shooter.BELT_SPEED_DEFAULT));
		//new JoystickButton(operatorPad, Gamepad.aButton).whileActive(new RunBelt(-1 * Shooter.BELT_SPEED_DEFAULT));
		new JoystickButton(operatorPad, Gamepad.aButton).whenActive(new AutoAim());
		new JoystickButton(operatorPad, Gamepad.yButton).whileActive(new RunTurret());

		new DPadUp(operatorPad).whenActive(new LiftTractionPlates());
		new DPadDown(operatorPad).whenActive(new DropTractionPlates());
		new DPadLeft(operatorPad).whenActive(new HaltShooter());
		new DPadRight(operatorPad).whenActive(new ToggleIntakeRamp());
		

		
		//
		// Pneumatic Test Buttons
		//
		SmartDashboard.putData("tractionFeetRetract", new tractionRetract());
		SmartDashboard.putData("tractionFeetDeploy", new tractionDeploy());

		SmartDashboard.putData("Test Shooter Feed", new TestFeed());
		SmartDashboard.putData("Test Shooter Wheels", new TestShooter());
		SmartDashboard.putData("Zero Encoders", new ResetEncoders());
		//SmartDashboard.putData("Update PIDF Constants", new UpdatePIDFConstants());
		SmartDashboard.putData("Calibrate Turret", new CalibrateTurret());
		SmartDashboard.putData("Position Turret", new PositionTurret());
		SmartDashboard.putData("Drive Turn", new DriveTurn(45.0, 0.15));
		SmartDashboard.putData("Zero Gyro", new ZeroGyro());
		
		
	}
}