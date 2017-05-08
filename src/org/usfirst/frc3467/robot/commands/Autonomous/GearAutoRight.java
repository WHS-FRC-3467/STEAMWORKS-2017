package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.AutoGear;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBot;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.DriveTurn;
import org.usfirst.frc3467.subsystems.GearCatcher.GearDeliver;
import org.usfirst.frc3467.subsystems.GearCatcher.GearHold;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.DriveBase.DiagonalDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearAutoRight extends CommandGroup {

    public GearAutoRight() {
    	addParallel(new ToggleIntakeRamp());
    	addParallel(new GearHold());
    	addSequential(new ZeroGyro());
    	addSequential(new DriveStraight(101400));  
    	//addSequential(new DriveStraight(true));
    	/*addSequential(new AutoGear());
    	addSequential(new DriveStraight(101400));
    	addSequential(new GearDeliver());
    	addSequential(new DriveStraight(-50000));*/
    }
}
