package org.usfirst.frc3467.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoClimber  extends CommandGroup{
	
	protected boolean isFinished(double time) {
		//requires(driveBase);
		return false;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
