package org.usfirst.frc3467.subsystems.Pneumatics.testCommands;

import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class floorRetract extends InstantCommand {

    public floorRetract() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	Pneumatics.getInstance().floorIntakeRetract();
    }

}
