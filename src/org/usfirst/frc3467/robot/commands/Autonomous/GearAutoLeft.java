package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.AutoGear;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.DriveTurn;
import org.usfirst.frc3467.subsystems.GearCatcher.GearDeliver;
import org.usfirst.frc3467.subsystems.GearCatcher.GearHold;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearAutoLeft extends CommandGroup {

    public GearAutoLeft() {
    	addParallel(new ToggleIntakeRamp());
    	addParallel(new GearHold());
    	addSequential(new ZeroGyro());
    	addSequential(new DriveStraight(101400));  
    	addSequential(new DriveTurn(45.0, 0.3));
    	addSequential(new AutoGear());
    	addSequential(new DriveStraight(50000));
    	addSequential(new GearDeliver());
    	addSequential(new DriveStraight(-50000));
    }
}
