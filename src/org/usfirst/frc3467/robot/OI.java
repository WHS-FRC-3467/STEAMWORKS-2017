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
import org.usfirst.frc3467.subsystems.DriveBase.ArcadeDrive;
import org.usfirst.frc3467.subsystems.DriveBase.FieldCentricDrive;
import org.usfirst.frc3467.subsystems.DriveBase.PrecisionDrive;
import org.usfirst.frc3467.subsystems.DriveBase.RobotCentricDrive;
import org.usfirst.frc3467.subsystems.FloorIntake.IntakeDrive;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearClawState;
import org.usfirst.frc3467.subsystems.Hopper.ToggleHopper;

import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.floorExtend;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.floorRetract;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.gearDown;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.gearHold;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.gearRelease;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.gearUp;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.highExtend;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.highRetract;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.hopperContract;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.hopperExpand;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.pusherExtend;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.pusherRetract;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.tractionDeploy;
import org.usfirst.frc3467.subsystems.Pneumatics.testCommands.tractionRetract;

//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;
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
			Driver GamePad
		*/
		
		// Left Trigger: Shoot = run shooter, run spinner, run tower, run bottom intake, run top intake, drop traction plates, actuate bottom intake in, actuate top intake out
		
		// Left Bumper: Intake = bottom intake out, bottom intake running
		
		// Right Trigger: GearCatcher = toggles gear claw angle
		new GamepadRightTrigger(driverPad)
			.whenActive(new ToggleGearCatcherPosition());
		
		// Right Bumper: GearClaw = clamp/release claw
		new JoystickButton(driverPad, Gamepad.rightBumper)
		.whenActive(new ToggleGearClawState());
	
		
		/*
		 * DPad(Directional Pad)
		*/
		// DPad Up = Field Centric Mode
		new DPadUp(driverPad)
			.whenActive(new RobotCentricDrive());
		
		// DPad Down = Robot Centric Mode
 		new DPadDown(driverPad)
 			.whenActive(new FieldCentricDrive());
		
		// DPad Left = Precision Mode
		new DPadLeft(driverPad)
			.whenActive(new PrecisionDrive());
		
		// DPad Right = Robot Centric Mode(No center wheel)
		new DPadRight(driverPad)
			.whenActive(new ArcadeDrive());
		
		//new JoystickButton(operatorPad, Gamepad.yButton)
		//	.whenPressed(new FieldCentricDrive());
		
		// X Button Climber = some sort of automated climbing routine, latches climber axle
		
		// On joystick move = traction plates come up
		
		/*
		 * Operator GamePad
		 */

		//X Button = RunSpinner Left
		//B Button = RunSpinner RIght
		//Y Button = RunTower Up
		//A Button = RunTower Down
		//Start Button = Drop Traction Plate
		//Select Button = Rise Traction Plate
		//DPad
		//DPad Up = Top Intake In
		//DPad Down  Top Intake Out

		new JoystickButton(driverPad, Gamepad.leftBumper)
		.whileHeld(new IntakeDrive(.5));

		// LeftTrigger - Toggle Hopper
		new GamepadLeftTrigger(operatorPad)
		.whenActive(new ToggleHopper());

		//LB = Bottom intake in
		//RB = Bottom intake out

		//RT = Bottom intake run/Top intake run
		/*new GamepadRightTrigger(operatorPad)
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
		new GamepadLeftTrigger(operator)
		.whenActive(new Bar_actuate(UtilityBar.kOut));
		
		//Utility bar down
		new GamepadRightTrigger(operator)
			.whenActive(new Bar_actuate(UtilityBar.kIn));
*/	
		
/*
 	//Intake
 
		//Eject Fast
		new JoystickButton(operator, Gamepad.xButton)
			.whileHeld(new IntakeDrive(Intake.kEjectFast));
		
		//Intake Fast
		new JoystickButton(operator, Gamepad.bButton)
			.whileHeld(new IntakeDrive(Intake.kIntakeFast));
		
		//Intake up
		new JoystickButton(operator, Gamepad.aButton)
			.whenActive(new Roller_Actuate(true));
		
		//Intake down
		new JoystickButton(operator, Gamepad.yButton)
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
 
		new DPadUp(operator)
			.whenActive(new SetBrakeMode(false));

		// DPad Down
		new DPadDown(operator)
			.whenActive(new SetBrakeMode(true));
 		
		 DPad Right
		new DPadRight(operator)
			.whenActive(new ShooterLatch());

		DPad Left
		new DPadLeft(operator)
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
*/
		
		// Pneumatic Test Buttons
		SmartDashboard.putData("tractionFeetRetract", new tractionRetract());
		SmartDashboard.putData("tractionFeetDeploy", new tractionDeploy());
		SmartDashboard.putData("floorIntakeRetract", new floorRetract());
		SmartDashboard.putData("floorIntakeExtend", new floorExtend());
		SmartDashboard.putData("highIntakeRetract", new highRetract());
		SmartDashboard.putData("highIntakeExtend", new highExtend());
		SmartDashboard.putData("gearCatchUp", new gearUp());
		SmartDashboard.putData("gearCatchDown", new gearDown());
		SmartDashboard.putData("gearClawHold", new gearHold());
		SmartDashboard.putData("gearClawRelease", new gearRelease());
		SmartDashboard.putData("hopperContract", new hopperContract());
		SmartDashboard.putData("hopperExpand", new hopperExpand());
		SmartDashboard.putData("pusherBarsRetract", new pusherRetract());
		SmartDashboard.putData("pusherBarsExtend", new pusherExtend());
	}
}