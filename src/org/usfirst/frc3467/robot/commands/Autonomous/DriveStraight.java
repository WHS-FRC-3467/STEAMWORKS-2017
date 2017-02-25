package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Drivestraight extends CommandGroup {

    public Drivestraight() {
    	addSequential(new DriveStraight(3000));
    }
}
