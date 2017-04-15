package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveSideways;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Boiler on Left Side of Field (BLUE Alliance)
 */
public class kPaAutoLeft extends CommandGroup {

    public kPaAutoLeft() {
    	
    	addSequential(new ToggleIntakeRamp());
    	
    	// Drive sideways out to align with hopper trigger plate
    	// DriveSideways( distance, maxSpeed)
    	addSequential(new DriveSideways(10000, 0.5));
    	
    	// Drive forward to trigger hopper
    	// DriveStraight(distance, maxSpeed)
    	addSequential(new DriveStraight(5000, 0.7));
    	
    	// Drive sideways to better align with hopper exit
    	// DriveSideways( distance, maxSpeed)
    	addSequential(new DriveSideways(-2000, 0.5));
    	
    	// Begin to shoot using auto tracking
    	addSequential(new OperateShooter(true));
    }
}
