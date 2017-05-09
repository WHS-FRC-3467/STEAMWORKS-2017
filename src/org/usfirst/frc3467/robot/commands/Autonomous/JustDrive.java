package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.Gyro.ZeroGyro;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class JustDrive extends CommandGroup {

    public JustDrive() {
    	addParallel(new ToggleIntakeRamp());
    	addSequential(new ZeroGyro());
    	addSequential(new DriveStraight(120000));
    }
}
