package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.GearCatcher.GearDeliver;
import org.usfirst.frc3467.subsystems.GearCatcher.GearHold;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearAutoForward extends CommandGroup {

    public GearAutoForward() {
    	addParallel(new ToggleIntakeRamp());
    	addParallel(new GearHold());
    	addSequential(new ZeroGyro());
    	addSequential(new DriveStraight(101400));
    	addSequential(new GearDeliver());
    	addSequential(new DriveStraight(-50000));
    }
}
