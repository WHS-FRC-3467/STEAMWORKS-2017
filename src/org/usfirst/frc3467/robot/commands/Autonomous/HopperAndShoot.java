package org.usfirst.frc3467.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.SidewaysDrive;
import org.usfirst.frc3467.subsystems.DriveBase.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HopperAndShoot extends CommandGroup {

    public HopperAndShoot() {
    	addSequential(new SidewaysDrive(5));
    	addSequential(new DriveStraight(2000));
        addSequential(new WaitCommand(3.0));
        addSequential(new DriveStraight(-2000));
        addSequential(new Turn());
        addSequential(new AutoAimShooter());
    }
}
