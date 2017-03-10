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
import org.usfirst.frc3467.robot.control.triggers.DoubleButton;
import org.usfirst.frc3467.robot.control.triggers.GamepadLeftTrigger;
import org.usfirst.frc3467.robot.control.triggers.GamepadRightTrigger;
import org.usfirst.frc3467.subsystems.Climber.AutoClimb;
import org.usfirst.frc3467.subsystems.Climber.AutoClimber;
import org.usfirst.frc3467.subsystems.Climber.ToggleLatchPosition;
import org.usfirst.frc3467.subsystems.DriveBase.AutoAim;
import org.usfirst.frc3467.subsystems.DriveBase.AutoGear;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBot;
import org.usfirst.frc3467.subsystems.DriveBase.DropTractionPlates;
import org.usfirst.frc3467.subsystems.DriveBase.LiftTractionPlates;
import org.usfirst.frc3467.subsystems.DriveBase.ResetEncoders;
import org.usfirst.frc3467.subsystems.FloorIntake.FloorIntakeRun;
import org.usfirst.frc3467.subsystems.FloorIntake.TestFloorIntake;
import org.usfirst.frc3467.subsystems.FloorIntake.ToggleFloorIntakeOperation;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearClawState;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.HighIntake.HighIntakeRun;
import org.usfirst.frc3467.subsystems.HighIntake.RunBothIntakes;
import org.usfirst.frc3467.subsystems.HighIntake.ToggleHighIntakePosition;
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
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;
import org.usfirst.frc3467.subsystems.Shooter.RunBelt;
import org.usfirst.frc3467.subsystems.Shooter.RunShooter;
import org.usfirst.frc3467.subsystems.Shooter.RunSpinner;
import org.usfirst.frc3467.subsystems.Shooter.TestBelt;
import org.usfirst.frc3467.subsystems.Shooter.TestShooter;
import org.usfirst.frc3467.subsystems.Shooter.TestSpinner;






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
		
		final double FLOOR_INTAKE_SPEED_NORMAL = 0.2;
		final double FLOOR_INTAKE_SPEED_BACKWARD = -0.2;
		final double HIGH_INTAKE_SPEED_NORMAL = 0.2;

		final double SPINNER_SPEED_NORMAL = 0.3;
		final double SPINNER_SPEED_BACKWARD = -0.3;
		final double BELT_SPEED_NORMAL = 0.3;
		final double BELT_SPEED_BACKWARD = -0.3;
		
		/*
		 *
		 * Driver GamePad
		 * 
		 */
		
		new GamepadLeftTrigger(driverPad).whenActive(new OperateShooter());
		new GamepadRightTrigger(driverPad).whenActive(new ToggleGearCatcherPosition());
		new JoystickButton(driverPad, Gamepad.leftBumper).whenActive(new ToggleFloorIntakeOperation());
		new JoystickButton(driverPad, Gamepad.rightBumper).whenActive(new ToggleGearClawState());

		new JoystickButton(driverPad, Gamepad.xButton).whenActive(new ZeroGyro());
		//new DoubleButton(driverPad, Gamepad.yButton, Gamepad.bButton).whenActive(new ToggleLatch);
		new JoystickButton(driverPad, Gamepad.yButton).whenActive(new ToggleLatchPosition());
		new JoystickButton(driverPad, Gamepad.aButton).whenActive(new AutoAim());
		new JoystickButton(driverPad, Gamepad.bButton).whenActive(new AutoClimb());
		new JoystickButton(driverPad, Gamepad.startButton).whenActive(new AutoGear());

		new DPadDown(driverPad).whenActive(new DriveBot(DriveBase.driveMode_RobotCentric));
		new DPadLeft(driverPad).whenActive(new DriveBot(DriveBase.driveMode_Precision));
		new DPadRight(driverPad).whenActive(new DriveBot(DriveBase.driveMode_Arcade));
		new DPadUp(driverPad).whenActive(new DriveBot(DriveBase.driveMode_FieldCentric));
		
		
		/*
		 * 
		 * Operator GamePad
		 * 
		 */

		//Changed how the toggles work, may need to change back, but I want to try to see if this works
		new GamepadLeftTrigger(operatorPad).whenActive(new ToggleHopper());
		new GamepadRightTrigger(operatorPad).whileActive(new RunBothIntakes(FLOOR_INTAKE_SPEED_NORMAL));
		new JoystickButton(operatorPad, Gamepad.leftBumper).whileActive(new FloorIntakeRun(FLOOR_INTAKE_SPEED_BACKWARD));
		new JoystickButton(operatorPad, Gamepad.rightBumper).whenActive(new ToggleFloorIntakeOperation());

		new JoystickButton(operatorPad, Gamepad.xButton).whileActive(new RunSpinner(SPINNER_SPEED_NORMAL));
		new JoystickButton(operatorPad, Gamepad.bButton).whileActive(new RunSpinner(SPINNER_SPEED_BACKWARD));
		new JoystickButton(operatorPad, Gamepad.yButton).whileActive(new RunBelt(BELT_SPEED_NORMAL));
		new JoystickButton(operatorPad, Gamepad.aButton).whileActive(new RunBelt(BELT_SPEED_BACKWARD));

		new DPadUp(operatorPad).whenActive(new LiftTractionPlates());
		new DPadDown(operatorPad).whenActive(new DropTractionPlates());
		new DPadLeft(operatorPad).whenActive(new ToggleHighIntakePosition());
		new DPadRight(operatorPad).whileActive(new HighIntakeRun(HIGH_INTAKE_SPEED_NORMAL));

		
		//
		// Pneumatic Test Buttons
		//
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

		SmartDashboard.putData("Run Shooter", new RunShooter(0.1));
		SmartDashboard.putData("Test Shooter Belt", new TestBelt());
		SmartDashboard.putData("Test Shooter Spinner", new TestSpinner());
		SmartDashboard.putData("Test Shooter Wheels", new TestShooter());
		SmartDashboard.putData("Test Floor Intake", new TestFloorIntake());
		SmartDashboard.putData("Zero Encoders", new ResetEncoders());
		SmartDashboard.putData("Test Auto Climb", new AutoClimb());
	}
}