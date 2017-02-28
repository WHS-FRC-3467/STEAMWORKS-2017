package org.usfirst.frc3467.subsystems.Pneumatics.testCommands;

import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class tractionRetract extends InstantCommand {

    public tractionRetract() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	Pneumatics.getInstance().tractionFeetRetract();
    	DriveBase.getInstance().tractionFeetState = false;
    }

}
