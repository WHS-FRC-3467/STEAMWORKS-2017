package org.usfirst.frc3467.subsystems.Pneumatics.testCommands;

import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class tractionDeploy	 extends InstantCommand {

    public tractionDeploy() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	Pneumatics.getInstance().tractionFeetDeploy();
    	DriveBase.getInstance().tractionFeetState = true;
    }

}
