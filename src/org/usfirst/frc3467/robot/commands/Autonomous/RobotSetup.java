package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RobotSetup extends CommandGroup {

    public RobotSetup() {
        addSequential(new ToggleGearCatcherPosition());
    }
}
