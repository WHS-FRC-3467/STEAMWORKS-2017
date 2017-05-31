package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveSideways;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.DriveTurn;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.Shooter.AutoAim;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;
import org.usfirst.frc3467.subsystems.Shooter.PositionTurret;
import org.usfirst.frc3467.subsystems.Shooter.RunJustShooterWheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Boiler on Left Side of Field (BLUE Alliance)
 */
public class kPaAutoLeft extends CommandGroup {

    public kPaAutoLeft() {
    	
    	addSequential(new ToggleIntakeRamp());
    	
    	addSequential(new ZeroGyro());
    	
    	// Turn turret to give auto aim a fighting chance
    	addParallel(new PositionTurret(-60.0));
    	
    	// Drive straight out
    	// DriveStraight( distance, maxSpeed)
    	addSequential(new DriveStraight(104000, 0.4));

    	// Turn toward hopper
    	// DriveTurn( angle, maxSpeed)
    	addSequential(new DriveTurn(-89.0, 0.15));
    	
      	// Spin up shooter wheel
    	addParallel(new RunJustShooterWheel());
 
    	// Drive forward to trigger hopper
    	addSequential(new DriveStraight(51000, 0.4));

    	// Back off slightly from hopper 
    	//addSequential(new DriveStraight(-300, 0.2));
    	// Drive sideways to better align with hopper exit
    	// DriveSideways( distance, maxSpeed)
    	//addSequential(new DriveSideways(-10500, 0.2));
    	// drive back towards hopper
    	//addSequential(new DriveStraight(300, 0.2));
    	
    	// Begin to shoot using auto tracking
    	addSequential(new AutoAim());
    	addSequential(new OperateShooter(true, 12.0));
        

   }
}

