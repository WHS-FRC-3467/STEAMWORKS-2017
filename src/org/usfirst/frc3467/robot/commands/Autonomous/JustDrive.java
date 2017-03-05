package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.StraightDrive;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition;
import org.usfirst.frc3467.subsystems.HighIntake.ToggleHighIntakePosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class JustDrive extends CommandGroup {

    public JustDrive() {
    	addSequential(new ToggleHighIntakePosition());
        addSequential(new ToggleGearCatcherPosition());
    	//addSequential(new DriveStraight(80000));
    	addSequential(new StraightDrive());
    }
}
