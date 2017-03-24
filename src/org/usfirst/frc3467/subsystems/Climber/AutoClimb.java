package org.usfirst.frc3467.subsystems.Climber;

import org.usfirst.frc3467.robot.CommandBase;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class AutoClimb extends CommandBase {

   	CANTalon cTalon; 
   	double runningCurrent;
   	static final double DONE_CURRENT_MULTIPLIER = 3.0;
   	
    public AutoClimb() {
    	requires(driveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cTalon = driveBase.getMiddleTalon();
    	cTalon.changeControlMode(TalonControlMode.Voltage);
    	cTalon.setVoltageCompensationRampRate(24.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       	cTalon.set(-2.40);
       	runningCurrent = cTalon.getOutputCurrent();       	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (cTalon.getOutputCurrent() > runningCurrent * DONE_CURRENT_MULTIPLIER)
        	return true;
        else
        	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
       	cTalon.set(0.0);
     }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
