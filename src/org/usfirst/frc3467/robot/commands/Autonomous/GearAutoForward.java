package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.GearCatcher.GearDeliver;
import org.usfirst.frc3467.subsystems.Pneumatics.ReleaseIntakeRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearAutoForward extends CommandGroup {

    public GearAutoForward() {
    	addParallel(new ReleaseIntakeRamp());
    	addSequential(new DriveStraight(78000));
    	addSequential(new GearDeliver());
    	addSequential(new DriveStraight(-2500));
    }
}
