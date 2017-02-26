package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class JustDrive extends CommandGroup {

    public JustDrive() {
    	addSequential(new DriveStraight(3000));
    }
}
