package org.usfirst.frc3467.robot.commands.Autonomous;

import org.usfirst.frc3467.subsystems.DriveBase.AutoAim;
import org.usfirst.frc3467.subsystems.DriveBase.DriveStraight;
import org.usfirst.frc3467.subsystems.DriveBase.Turn;
import org.usfirst.frc3467.subsystems.Shooter.OperateShooter;
import org.usfirst.frc3467.subsystems.Shooter.RunSpinner;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShootRight extends CommandGroup {

    public AutoShootRight() {
    	addSequential(new DriveStraight(2000));
//      addSequential(new WaitCommand(3.0));
//      addSequential(new DriveStraight(-2000));
  	addSequential(new Turn(120));
  	addSequential(new AutoAim());
  	addParallel(new OperateShooter());
  	addParallel(new RunSpinner(0.6));
  	addSequential(new Turn(-120));
  	addSequential(new DriveStraight(2001));
      //addSequential(new AutoAimShooter());
  }
}
