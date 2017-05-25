package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveSideways;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.DriveTurn;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.Shooter.AutoAim;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;
import org.usfirst.frc3467.subsystems.Shooter.RunJustShooterWheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Boiler on Right Side of Field (RED Alliance)
 */
public class kPaAutoRight extends CommandGroup {

    public kPaAutoRight() {

    	addSequential(new ToggleIntakeRamp());
    	
    	addSequential(new ZeroGyro());
    	
    	// Drive sideways out to align with hopper trigger plate
    	// DriveSideways( distance, maxSpeed)
    	
    	//addSequential(new DriveSideways(-60000, 0.4));
    	addSequential(new DriveStraight(104000, 0.4));
    	addSequential(new DriveTurn(0.15, 87.0));
    	
      	// Spin up shooter wheel
    	addParallel(new RunJustShooterWheel());
 
    	// Drive forward to trigger hopper
    	// DriveStraight(distance, maxSpeed)
    	addSequential(new DriveStraight(51000, 0.4));
    	//addSequential(new DriveStraight(-300, 0.2));

    	// Drive sideways to better align with hopper exit
    	// DriveSideways( distance, maxSpeed)
    	//addSequential(new DriveSideways(10500, 0.2));
    	//addSequential(new DriveStraight(300, 0.2));
    	setTimeout(2);
    	addSequential(new DriveStraight(-5000, 0.4));
    	addSequential(new DriveTurn(0.15, 87.0));

      	// Begin to shoot using auto tracking
    	addSequential(new AutoAim());
    	addSequential(new AutoAim());
    	addSequential(new OperateShooter(true, 12.0));
    	
        
    }
}
