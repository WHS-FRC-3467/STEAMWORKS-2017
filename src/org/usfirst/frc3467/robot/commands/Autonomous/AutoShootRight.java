package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveSideways;
import org.usfirst.frc3467.subsystems.Pneumatics.ToggleIntakeRamp;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootRight extends CommandGroup {

    public AutoShootRight() {
    	addParallel(new ToggleIntakeRamp());
    	addSequential(new OperateShooter());
    	addSequential(new DriveSideways(3.0, -0.5));
  }
}
