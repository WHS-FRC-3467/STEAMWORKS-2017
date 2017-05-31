package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;
import org.usfirst.frc3467.robot.control.Gamepad;
import org.usfirst.frc3467.subsystems.PixyCam.NoTargetException;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class OperateShooter extends CommandBase {

	// Time to continue running shooter wheels after no more user input  
	double m_seconds_timeout = 10.0;
	
	// Autonomous mode?
	boolean m_autoMode = false;

	// Control on Driver pad?
	boolean m_drvrCtrl = false;
	
    Timer m_timeOutTimer;
	
    public OperateShooter(boolean drvrControl ) {
        requires(shooterFlywheel);
        requires(shooterFeed);
        m_timeOutTimer = new Timer();
        m_drvrCtrl = drvrControl;
        
        // Don't allow interruptions; command will have to timeout on its own
        // This will prevent extra Trigger activations from starting a new instance of this command
        this.setInterruptible(false);
    }
    
    public OperateShooter(boolean inAuto, double seconds_timeout) {
        requires(shooterFlywheel);
        requires(shooterFeed);
        m_timeOutTimer = new Timer();
        m_autoMode = inAuto;
        m_seconds_timeout = seconds_timeout;
        this.setInterruptible(true);
	}

    // Called just before this Command runs the first time
    protected void initialize() {

        m_timeOutTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Run shooter wheels under Velocity Control
    	double shooterVelocity = ShooterFlywheel.SHOOTER_SPEED_DEFAULT;
    	
    	try {
			// Get distance to target
    		double bdist = pixyCamShooter.getBoilerDistance();
    		double distance = 75.28*bdist + 1.0;
    		
        	// Convert distance to velocity
        	//shooterVelocity = 16.4 * (distance) + 4246;  // Grayhill encoder
    		shooterVelocity = 125.27 * (distance) + 27976; // CUI encoder
       		//shooterVelocity = 125.27 * (distance) + 28170; // CUI encoder
       	    		
        	SmartDashboard.putString("OperateShooter", "distance = " + distance + "; speed = " + shooterVelocity);

    	} catch (NoTargetException ex) {
    		// Can't get distance; keep ShooterVelocity at default value
        	SmartDashboard.putString("OperateShooter", "distance = xxx; speed = " + shooterVelocity);
    	}
     	
    	shooterFlywheel.runShooter(shooterVelocity);    	

    	if (!m_autoMode) {

    	   // Look for Left Trigger activation
	       	double leftTrigger = 0.0;
	       	if (m_drvrCtrl == true) {
	       		leftTrigger = OI.operatorPad.getRawAxis(Gamepad.leftTrigger_Axis);
	       	} else {
	       		leftTrigger = OI.operatorPad.getRawAxis(Gamepad.leftTrigger_Axis);
	       	}
	       	if ( leftTrigger > 0.05) {
	       		
	       		// Drop traction plates
	       		//driveBase.tractionExtend();
	
	       		// Run shooter feed at speed determined by Left Trigger movement (0.0 -> 1.0)
	           	shooterFeed.runFeed(leftTrigger);
	        	
	           	// Tell the timer that we are still working
	           	m_timeOutTimer.reset();
	
	       	} else {
	       		shooterFeed.runFeed(0.0);
	       		driveBase.tractionRetract();
	       	}
       } else {

           // Wait a second for shooter to spin up...
    	   if (super.timeSinceInitialized() >= 1.0)
           {
        	   // ... then run feed at max speed
        	   shooterFeed.runFeed(1.0);
           }
       }
    }

    // Return true when timer expires
    protected boolean isFinished() {
        return m_timeOutTimer.hasPeriodPassed(m_seconds_timeout);
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterFeed.runFeed(0.0);
    	shooterFlywheel.stopShooter();
    	m_timeOutTimer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
