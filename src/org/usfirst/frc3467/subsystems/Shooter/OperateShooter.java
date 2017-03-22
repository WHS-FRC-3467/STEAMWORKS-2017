package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;
import org.usfirst.frc3467.robot.control.Gamepad;
import org.usfirst.frc3467.subsystems.PixyCam.NoTargetException;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class OperateShooter extends CommandBase {

	// Time to continue running shooter wheels after no more user input  
	static final double SECONDS_TIMEOUT = 10.0;
	
    Timer timeOutTimer;
	
	public OperateShooter(){
		
        requires(shooter);

        // Don't allow interruptions; command will have to timeout on its own
        // This will prevent extra Trigger activations from starting a new instance of this command
        this.setInterruptible(false);

        timeOutTimer = new Timer();
	}

    // Called just before this Command runs the first time
    protected void initialize() {

        timeOutTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Run shooter wheels under Velocity Control
    	double shooterVelocity = Shooter.SHOOTER_SPEED_DEFAULT;
    	
    	try {
			// Get distance to target
    		double distance = pixyCamShooter.getBoilerDistance();
    		
        	// Convert distance to velocity
        	// shooterVelocity = fn(distance);

    	} catch (NoTargetException ex) {
    		// Can't get distance; keep ShooterVelocity at default value
    	}
     	
    	shooter.BeltRun(Shooter.BELT_SPEED_DEFAULT);
    	shooter.ShooterRun(shooterVelocity);    	

        // Look for Left Trigger activation
    	double leftTrigger = OI.driverPad.getRawAxis(Gamepad.leftTrigger_Axis);
    	if ( leftTrigger > 0.05) {
    		
            //Activating the Left Trigger does the following:
        
    		// Drop traction plates
    		//driveBase.tractionExtend();

    		// Run spinner at speed determined by Left Trigger movement (0.0 -> 1.0)
        	shooter.SpinnerRun(leftTrigger * Shooter.SPINNER_SPEED_DEFAULT);

        	// Tell the timer that we are still working
        	timeOutTimer.reset();

    	} else {

    		shooter.SpinnerRun(0.0);
    		driveBase.tractionRetract();
    	}

    }

    // Return true when timer expires
    protected boolean isFinished() {
        return timeOutTimer.hasPeriodPassed(SECONDS_TIMEOUT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.SpinnerRun(0.0);
    	shooter.BeltRun(0.0);
    	shooter.ShooterStop();
    	timeOutTimer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
