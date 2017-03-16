package org.usfirst.frc3467.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class UpdatePIDFConstants extends InstantCommand {

    public UpdatePIDFConstants() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	DriveBase.getInstance().flagPIDFUpdate();
    }

}
