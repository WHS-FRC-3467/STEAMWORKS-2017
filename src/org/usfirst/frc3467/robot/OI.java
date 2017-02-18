package org.usfirst.frc3467.robot;

/*
import org.usfirst.frc3467.robot.triggers.DPadDown;
import org.usfirst.frc3467.robot.triggers.DPadUp;
import org.usfirst.frc3467.robot.triggers.GamepadLeftTrigger;
import org.usfirst.frc3467.robot.triggers.GamepadRightTrigger;
import org.usfirst.frc3467.subsystems.Example.ExampleCommand;
*/

import org.usfirst.frc3467.robot.control.Gamepad;
import org.usfirst.frc3467.robot.control.triggers.DPadDown;
import org.usfirst.frc3467.robot.control.triggers.DPadLeft;
import org.usfirst.frc3467.robot.control.triggers.DPadRight;
import org.usfirst.frc3467.robot.control.triggers.DPadUp;
import org.usfirst.frc3467.robot.control.triggers.GamepadLeftTrigger;
import org.usfirst.frc3467.robot.control.triggers.GamepadRightTrigger;
import org.usfirst.frc3467.subsystems.Climber.Climber;
import org.usfirst.frc3467.subsystems.DriveBase.DropTractionPlate;
import org.usfirst.frc3467.subsystems.DriveBase.FieldCentricDrive;
import org.usfirst.frc3467.subsystems.DriveBase.RobotCentricDrive;
import org.usfirst.frc3467.subsystems.GearCatcher.GearCatcher;
import org.usfirst.frc3467.subsystems.HighIntake.HighIntake;
import org.usfirst.frc3467.subsystems.Hopper.Hopper;
import org.usfirst.frc3467.subsystems.Shooter.RunSpinner;
import org.usfirst.frc3467.subsystems.Shooter.Shooter;

/*
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
*/

/**
 * This class is the glue that binds the controls on the physical operatorPad
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

		new DPadDown(driverPad)
		.whenActive(new FieldCentricDrive());
		new DPadUp(driverPad)
		.whenActive(new RobotCentricDrive());
/*		new DPadLeft(driverPad)
		.whenActive(new PrecisionMode());
		new DPadRight(driverPad)
		.whenActive(new RobotCentricDrive2());
		new GamepadLeftTrigger(driverPad)
		.whenActive(new RunSpinner());
		new GamepadRightTrigger(driverPad)
		.whenActive(new GearCatcher());
		new JoystickButton(driverPad, Gamepad.leftBumper)
		.whenPressed(new LowIntake());
		new JoystickButton(driverPad, Gamepad.rightBumper)
		.whenPressed(new GearCatcher2());
		new JoystickButton(driverPad, Gamepad.xButton)
		.whenPressed(new Climber());
		
		new GamepadLeftTrigger(operatorPad)
		.whenActive(new Hopper());
		new GamepadRightTrigger(operatorPad)
		.whenActive(new LowIntake());
		new JoystickButton(operatorPad, Gamepad.leftBumper)
		.whenPressed(new LowIntake());
		new JoystickButton(operatorPad, Gamepad.rightBumper)
		.whenPressed(new LowIntake());
		new JoystickButton(operatorPad, Gamepad.xButton)
		.whenPressed(new RunSpinner());
		new JoystickButton(operatorPad, Gamepad.bButton)
		.whenPressed(new RunSpinner());
		new JoystickButton(operatorPad, Gamepad.yButton)
		.whenPressed(new RunTower());
		new JoystickButton(operatorPad, Gamepad.aButton)
		.whenPressed(new RunTower());
		new JoystickButton(operatorPad, Gamepad.startButton)
		.whenPressed(new DropTractionPlate());
		new DPadUp(operatorPad)
		.whenActive(new HighIntake());
		new DPadDown(operatorPad)
		.whenActive(new HighIntake());
*/		

	    //// CREATING BUTTONS
	    // One type of button is a joystick button which is any button on a joystick.
	    // You create one by telling it which joystick it's on and which button
	    // number it is.
	    // Joystick stick = new Joystick(port);
	    // Button button = new JoystickButton(stick, buttonNumber);
	    
	    // There are a few additional built in buttons you can use. Additionally,
	    // by subclassing Button you can create custom triggers and bind those to
	    // commands the same as any other Button.
	    
	    //// TRIGGERING COMMANDS WITH BUTTONS
	    // Once you have a button, it's trivial to bind it to a button in one of
	    // three ways:
	    
	    // Start the command when the button is pressed and let it run the command
	    // until it is finished as determined by it's isFinished method.
	    // button.whenPressed(new ExampleCommand());
	    
	    // Run the command while the button is being held down and interrupt it once
	    // the button is released.
	    // button.whileHeld(new ExampleCommand());
	    
	    // Start the command when the button is released  and let it run the command
	    // until it is finished as determined by it's isFinished method.
	    // button.whenReleased(new ExampleCommand());
		
		
		
		
/*
 	//DriveBase

		//Toggle in and out of precision angle mode
		new JoystickButton(PrimaryStick, 3)
			.whenPressed(new PreciseRotateToAngle());
		
		new JoystickButton(PrimaryStick, 4)
			.whenPressed(new RobotCentricDrive());
		
		//Toggle in and out of AimBot
		new JoystickButton(PrimaryStick, 1)
			.whileHeld(new AimBot());
		
		new JoystickButton(PrimaryStick, 2)
			.whenPressed(new RobotCentricDrive());
*/
		
/*
  	//Utility Bar
 
		//Utility bar up
		new GamepadLeftTrigger(operatorPad)
		.whenActive(new Bar_actuate(UtilityBar.kOut));
		
		//Utility bar down
		new GamepadRightTrigger(operatorPad)
			.whenActive(new Bar_actuate(UtilityBar.kIn));
*/	
		
/*
 	//Intake
 
		//Eject Fast
		new JoystickButton(operatorPad, Gamepad.xButton)
			.whileHeld(new IntakeDrive(Intake.kEjectFast));
		
		//Intake Fast
		new JoystickButton(operatorPad, Gamepad.bButton)
			.whileHeld(new IntakeDrive(Intake.kIntakeFast));
		
		//Intake up
		new JoystickButton(operatorPad, Gamepad.aButton)
			.whenActive(new Roller_Actuate(true));
		
		//Intake down
		new JoystickButton(operatorPad, Gamepad.yButton)
			.whenActive(new Roller_Actuate(false));
		
		/*
		//Intake Extend
		new JoystickButton(SecondaryStick, 1)
		.whenPressed(new Roller_Actuate(true));
		
		new JoystickButton(SecondaryStick, 2)
		.whenPressed(new Roller_Actuate(false));	
*/

		
/*
 		// Gamepad DPad actions
 
		new DPadUp(operatorPad)
			.whenActive(new SetBrakeMode(false));

		// DPad Down
		new DPadDown(operatorPad)
			.whenActive(new SetBrakeMode(true));
 		
		 DPad Right
		new DPadRight(operatorPad)
			.whenActive(new ShooterLatch());

		DPad Left
		new DPadLeft(operatorPad)
			.whenActive(new ShooterClear());
 */
		
		
/*
 		// SmartDashboard Buttons

		SmartDashboard.putData("Drivebase: Reset Encoders", new ResetDriveEncoders());
		SmartDashboard.putData("Shooter: Calibrate", new ShooterOneWayCalibrate());
		SmartDashboard.putData("AHRS: Reset Gyro", new ResetGyro());
		SmartDashboard.putData("Vision: Target Goal", new TargetGoal());
		SmartDashboard.putData("Vision: AimBot", new AimBot());
		SmartDashboard.putData("Shooter MP", new ShootMP());
		
		//Test Buttons
		SmartDashboard.putData("Test AutoTarget", new AutoTarget());
		SmartDashboard.putData("Test Motion Profiling", new DriveMotionProfiling(90, 0.1, 0.1, 3, true));
*/
	}

}