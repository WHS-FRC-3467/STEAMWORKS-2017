
package org.usfirst.frc3467.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3467.subsystems.FieldCamera.FieldCamera;
import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.commands.Autonomous.AutoShootLeft;
import org.usfirst.frc3467.robot.commands.Autonomous.AutoShootRight;
import org.usfirst.frc3467.robot.commands.Autonomous.DoNothing;
import org.usfirst.frc3467.robot.commands.Autonomous.JustDrive;
import org.usfirst.frc3467.robot.commands.Autonomous.GearAutoForward;
import org.usfirst.frc3467.robot.commands.Autonomous.GearAutoRight;
import org.usfirst.frc3467.robot.commands.Autonomous.kPaAutoLeft;
import org.usfirst.frc3467.robot.commands.Autonomous.kPaAutoRight;

	
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;
	SendableChooser autoChooser;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void robotInit() {

		// Start the FieldCamera
		new FieldCamera();
        
		// Initialize all subsystems
		CommandBase.init();	
    	
		// Add autonomous selector
		autoChooser = new SendableChooser();
		autoChooser.addDefault("GearAutoForward", new GearAutoForward());
		autoChooser.addObject("Go Straight", new JustDrive());
		autoChooser.addObject("Do Nothing", new DoNothing());
		autoChooser.addObject("Shoot Left (BLUE)", new AutoShootLeft());
		autoChooser.addObject("Shoot Right (RED)", new AutoShootRight());
		autoChooser.addObject("Auto Gear Right", new GearAutoRight());
		autoChooser.addObject("Hopper shoot Right (RED)", new kPaAutoRight());
		autoChooser.addObject("Hopper shoot Left (BLUE)", new kPaAutoLeft());
		
			SmartDashboard.putData("Autonomous Command Chooser", autoChooser);

    }
	
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	SmartDashboard.putString("Robot", "Robot Disabled");
    }

	public void disabledPeriodic() {
		
		// Scheduler can continue to run while robot is disabled
		Scheduler.getInstance().run();
		
		autonomousCommand = (Command)autoChooser.getSelected();
		if (autonomousCommand != null)
			SmartDashboard.putString("Selected Autonomous Cmd", autonomousCommand.toString());
	}

    public void autonomousInit() {

    	// schedule the autonomous command
 		//autonomousCommand = autoChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
    
    	SmartDashboard.putString("Robot", "Autonomous Started");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		
    	/* This makes sure that the autonomous stops running when
        	teleop starts running. If you want the autonomous to 
        	continue until interrupted by another command, remove
         	this line or comment it out.
         */
        if (autonomousCommand != null) autonomousCommand.cancel();
    
    	SmartDashboard.putString("Robot", "Teleop Enabled");

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        // Show running command(s) on SmartDash
        SmartDashboard.putData(Scheduler.getInstance());
        
        // Show status of running commands for each subsystem
        for (Subsystem s : CommandBase.subsystemList) {
        	SmartDashboard.putData(s);
        }

        SmartDashboard.putString("Robot", "Teleop Periodic");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}

