package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveSideways;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.Shooter.AutoAim;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;
import org.usfirst.frc3467.subsystems.Shooter.RunJustShooterWheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class kPaAutoRight extends CommandGroup {

    public kPaAutoRight() {

    	addSequential(new ToggleIntakeRamp());
    	
       	// Spin up shooter wheel
    	addParallel(new RunJustShooterWheel());
 
    	addSequential(new ZeroGyro());
    	
    	// Drive sideways out to align with hopper trigger plate
    	// DriveSideways( distance, maxSpeed)
    	
    	addSequential(new DriveSideways(-62000, 0.2));
    	
    	// Drive forward to trigger hopper
    	// DriveStraight(distance, maxSpeed)
    	addSequential(new DriveStraight(52000, 0.4));
    	//addSequential(new DriveStraight(-300, 0.2));
    	
    	// Drive sideways to better align with hopper exit
    	// DriveSideways( distance, maxSpeed)
    	addSequential(new DriveSideways(10500, 0.2));
    	//addSequential(new DriveStraight(300, 0.2));
    	
      	// Begin to shoot using auto tracking
    	addSequential(new AutoAim());
    	addSequential(new AutoAim());
    	addSequential(new OperateShooter(true, 12.0));
        
    }
}
