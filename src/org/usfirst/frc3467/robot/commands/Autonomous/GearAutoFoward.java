package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.GearCatcher.GearOut;
import org.usfirst.frc3467.subsystems.GearCatcher.ToggleGearCatcherPosition; 
import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearAutoFoward extends CommandGroup {

    public GearAutoFoward() {
    //	addSequential(new ToggleGearCatcherPosition());
    	addSequential(new DriveStraight(3000));
    	addSequential(new GearOut());
    	addSequential(new DriveStraight(-2500));
    	//placeholder number 
    }
}
