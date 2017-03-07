package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition;
import org.usfirst.frc3467.subsystems.HighIntake.ToggleHighIntakePosition;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RobotSetup extends CommandGroup {

    public RobotSetup() {
        addSequential(new ToggleHighIntakePosition());
        addSequential(new ToggleGearCatcherPosition());
    }
}
