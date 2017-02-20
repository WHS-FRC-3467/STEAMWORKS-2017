package org.usfirst.frc3467.subsystems.Pneumatics.testCommands;

import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class gearDown extends InstantCommand {

    public gearDown() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	Pneumatics.getInstance().gearCatchDown();
    }

}
