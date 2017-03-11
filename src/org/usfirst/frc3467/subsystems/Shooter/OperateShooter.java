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

	static final double HI_INTAKE_SPEED = 0.2;
	static final double FLOOR_INTAKE_SPEED = 0.2;
	static final double MAX_SPINNER_SPEED = 0.4;
	static final double MAX_BELT_SPEED = -0.6;
	
	// Time to continue running shooter wheels after no more user input  
	static final double SECONDS_TIMEOUT = 10.0;
	
    Timer timeOutTimer;
	
	public OperateShooter(){
		
        requires(shooter);
        requires(flr_intake);
        requires(hi_intake);

        // Don't allow interruptions; command will have to timeout on its own
        // This will prevent extra Trigger activations from starting a new instance of this command
        this.setInterruptible(false);

        timeOutTimer = new Timer();
	}

    // Called just before this Command runs the first time
    protected void initialize() {

		// Actuate High intake out
        hi_intake.hiIntakeExtend();

        // Actuate Lower intake in
        flr_intake.retract();

        timeOutTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	// Run Lower intake in
    	flr_intake.drive(FLOOR_INTAKE_SPEED);

    	// Run High intake in
    	hi_intake.HIntakeRun(HI_INTAKE_SPEED);
    	shooter.BeltRun(MAX_BELT_SPEED);

        // Run shooter wheels under Velocity Control
    	double shooterVelocity = .5;
    	
    	try {
			// Get distance to target
    		double distance = pixyCam.getBoilerDistance();
    		
        	// Convert distance to velocity
        	// shooterVelocity = fn(distance);

    	} catch (NoTargetException ex) {
    		// Can't get distance; keep ShooterVelocity at default value
    	}
     	
    	shooter.ShooterRun(shooterVelocity);    	

        // Look for Left Trigger activation
    	double leftTrigger = OI.driverPad.getRawAxis(Gamepad.leftTrigger_Axis);
    	if ( leftTrigger > 0.05) {
    		
            //Activating the Left Trigger does the following:
        
    		// Drop traction plates
    		//driveBase.tractionExtend();

    		// Run conveyor and spinner at speed determined by Left Trigger movement (0.0 -> 1.0)
        	shooter.SpinnerRun(MAX_SPINNER_SPEED);

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
    	hi_intake.HIntakeRun(0.0);
    	flr_intake.drive(0.0);
    	shooter.SpinnerRun(0.0);
    	shooter.BeltRun(0.0);
    	shooter.ShooterStop(
    			);
    	timeOutTimer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// This should never be called if interruptible is false
    	end();
    }
    
}
