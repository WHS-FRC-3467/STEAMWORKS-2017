package org.usfirst.frc3467.subsystems.Shooter;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.robot.OI;
import org.usfirst.frc3467.robot.control.Gamepad;

/**
 *
 */
public class OperateShooter extends CommandBase {

	static final double hiIntakeSpeed = 0.5;
	static final double floorIntakeSpeed = 0.5;
	static final double spinnerSpeed = 0.5;
	static final double beltSpeed = 0.5;
	
	boolean m_autoAim = false;
	double m_shooterVelocity = 0.0;
	
	public OperateShooter(){
		this(false, 0.0);
	}
	
	public OperateShooter(boolean autoAim, double speed){
		
        requires(shooter);
        requires(flr_intake);
        requires(hi_intake);

        m_autoAim = autoAim;
		m_shooterVelocity = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	if (m_autoAim) {
        	// Start Pixy camera high boiler goal tracking
        	// Turn drivebase to aim at high boiler goal
        		// report tracking progress on Smartdash

    	}

		// Actuate High intake out
        hi_intake.hiIntakeExtend();

        // Actuate Lower intake in
        flr_intake.retract();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	// Run Lower intake in
    	flr_intake.drive(floorIntakeSpeed);

    	// Run High intake in
    	hi_intake.HIntakeRun(hiIntakeSpeed);

		if (m_autoAim) {
			
			// driveAutoAim(x, y, distance, tolerance_angle)
			
			// any stick movement will cancel tracking, but not interrupt shooting
			// Tracking may be resumed by entering Precision Drive mode (Y-button))
	        // Shooter velocity dependent on image in Pixy camers)
		}

        // Run shooter wheels under Velocity Control
    	shooter.ShooterRun(m_shooterVelocity);    	

        // When on target, start spinner (slow) & drop traction plates
    	shooter.SpinnerRun(spinnerSpeed);
    	shooter.BeltRun(beltSpeed);
        	
        // Look for Left Trigger activation
    	if (OI.driverPad.getRawAxis(Gamepad.leftTrigger_Axis) > 0.05) {
    		
            //Activating the Left Trigger does the following:
        	// Run conveyor and spinner at speed determined by Left Trigger movement (0.0 -> 1.0)

    	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	hi_intake.HIntakeRun(0.0);
    	flr_intake.drive(0.0);
    	shooter.SpinnerRun(0.0);
    	shooter.BeltRun(0.0);
    	shooter.ShooterRun(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
