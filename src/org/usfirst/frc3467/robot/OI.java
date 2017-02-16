package org.usfirst.frc3467.robot;

/*
import org.usfirst.frc3467.robot.triggers.DPadDown;
import org.usfirst.frc3467.robot.triggers.DPadUp;
import org.usfirst.frc3467.robot.triggers.GamepadLeftTrigger;
import org.usfirst.frc3467.robot.triggers.GamepadRightTrigger;
import org.usfirst.frc3467.subsystems.Example.ExampleCommand;
*/

import org.usfirst.frc3467.robot.control.Gamepad;

import edu.wpi.first.wpilibj.Joystick;
/*
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
*/

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick PrimaryStick;
	public static Gamepad operator;
	
/*
 * Joystick Mappings (done elsewhere in code)
 * 
 * Joystick PrimaryStick - used for Robot-centric. Field-centric, or Arcade Drive
 * 
 * Gamepad getRightStickX() - used for manual drive of Intake rollers
 * Gamepad getLeftStickY() - used for manual drive of Catapult reset bar
 * 
 */
	
	
	public OI(){
		PrimaryStick = new Joystick(0);
		operator = new Gamepad(1);
	}
	
	
	//Joystick Methods that return values for specific joystick axes
	public double getPrimeY(){
		return PrimaryStick.getY();
	}
	
	public double getPrimeX(){
		return PrimaryStick.getX();
	}
	
	public double getPrimeTwist() {
		return PrimaryStick.getTwist();
	}
	
	
	//Method that binds certain commands to certain buttons
	public void BindCommands() {


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
		
		//Test Buttons
		SmartDashboard.putData("Test AutoTarget", new AutoTarget());
		SmartDashboard.putData("Test Motion Profiling", new DriveMotionProfiling(90, 0.1, 0.1, 3, true));
*/
	}

}