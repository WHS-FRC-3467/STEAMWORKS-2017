package org.usfirst.frc3467.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * Class PIDF_CANTalon
 * 
 * Essentially a wrapper around a CANTalon class instance
 * 
 * Manages PIDF constants and updates SmartDashboard
 * with information useful for running a CANTalon in closed-loop mode.
 * 
 * Displayed on SmartDashboard:
 * P:
 * I:
 * I*100:
 * D:
 * F:
 * Setpoint:
 * Current Position:
 * Tolerance:
 * Current Error:
 * Output:
 * Enabled:
 * 
 */

public class PIDF_CANTalon {
	
	private String m_name;
	private CANTalon m_talon;
    private double m_tolerance;  // position range used to check if on target
	private boolean m_hasFeedForward;
    
    private double m_P;     // factor for "proportional" control
    private double m_I;     // factor for "integral" control
    private double m_D;     // factor for "derivative" control
    private double m_F;     // factor for feedforward term
    private double m_setpoint = 0.0;
    
	// Controls display to SmartDashboard
	private boolean m_debugging = false;
	

	public PIDF_CANTalon(String name, CANTalon talon, double tolerance,
							boolean hasFeedForward, boolean m_debugging) {
		this.m_name = name;
		this.m_talon = talon;
	    this.m_tolerance = tolerance;
		this.m_hasFeedForward = hasFeedForward;
		this.m_debugging = m_debugging;
		
		if (m_debugging)
			initSmartDashboard();
	}

	public CANTalon getTalon() {
		return m_talon;
	}
	
	private void initSmartDashboard () {
		
		SmartDashboard.putNumber(m_name + " P", m_talon.getP());
		SmartDashboard.putNumber(m_name + " I", m_talon.getI());
		SmartDashboard.putNumber(m_name + " D", m_talon.getD());
		SmartDashboard.putNumber(m_name + " F", m_talon.getF());
		SmartDashboard.putNumber(m_name + " I*100", (m_talon.getI())*100);
		
    	SmartDashboard.putNumber(m_name + " Talon Setpoint", m_setpoint);
    	SmartDashboard.putNumber(m_name + " Position", m_talon.getPosition());
    	SmartDashboard.putNumber(m_name + " Tolerance", m_tolerance);
    	SmartDashboard.putNumber(m_name + " Error", m_talon.getClosedLoopError());
    	SmartDashboard.putNumber(m_name + " Output", m_talon.get());
    	SmartDashboard.putBoolean(m_name + " Enabled", m_talon.isControlEnabled());

	}
	
    /**
     * Set the PID Controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     */
    public synchronized void setPID(double p, double i, double d) {
        m_P = p;
        m_I = i;
        m_D = d;

        m_talon.setProfile(0);
        m_talon.setP(p);
        m_talon.setI(i);
        m_talon.setD(d);
        
        if (m_debugging) {
    		SmartDashboard.putNumber(m_name + " P", m_talon.getP());
    		SmartDashboard.putNumber(m_name + " I", m_talon.getI());
    		SmartDashboard.putNumber(m_name + " D", m_talon.getD());
        }
    }

    /**
    * Set the PID Controller gain parameters.
    * Set the proportional, integral, and differential coefficients.
    * @param p Proportional coefficient
    * @param i Integral coefficient
    * @param d Differential coefficient
    * @param f Feed forward coefficient
    */
    public synchronized void setPID(double p, double i, double d, double f) {
        m_P = p;
        m_I = i;
        m_D = d;
        m_F = f;

        m_talon.setProfile(0);
        m_talon.setP(p);
        m_talon.setI(i);
        m_talon.setD(d);
        m_talon.setF(f);
        
        if (m_debugging) {
    		SmartDashboard.putNumber(m_name + " P", m_talon.getP());
    		SmartDashboard.putNumber(m_name + " I", m_talon.getI());
    		SmartDashboard.putNumber(m_name + " D", m_talon.getD());
    		SmartDashboard.putNumber(m_name + " F", m_talon.getF());
        }
    }

    /**
     * Get the Proportional coefficient
     * @return proportional coefficient
     */
    public synchronized double getP() {
        return m_P;
    }

    /**
     * Get the Integral coefficient
     * @return integral coefficient
     */
    public synchronized double getI() {
        return m_I;
    }

    /**
     * Get the Differential coefficient
     * @return differential coefficient
     */
    public synchronized double getD() {
        return m_D;
    }

    /**
     * Get the Feed forward coefficient
     * @return feed forward coefficient
     */
    public synchronized double getF() {
        return m_F;
    }

    /**
     * Set the setpoint for the PIDController
     * @param setpoint the desired setpoint
     */
    public synchronized void setSetpoint(double setpoint) {

    	m_setpoint = setpoint;
    	m_talon.set(setpoint);;

    	reportPosition(m_talon.getPosition());
    	
        if (m_debugging)
        	SmartDashboard.putNumber(m_name + " Talon Setpoint", m_setpoint);

    }

    /**
     * Returns the current setpoint of the PIDController
     * @return the current setpoint
     */
    public synchronized double getSetpoint() {

    	m_setpoint = m_talon.getSetpoint();
    	return m_setpoint;
    }

    /**
     * Set the position of the associated encoder
     * @param position - the desired position
     */
    public synchronized void setPosition(double position) {
    	m_talon.setPosition(position);
        reportPosition(position);

    }

    /**
     * Returns the current position of the associated encoder
     * @return the current position
     */
    public synchronized double getPosition() {
    	double pos = m_talon.getPosition();
    	reportPosition(pos);	
    	return pos;

    }

    private void reportPosition(double position) {
        
        if (m_debugging)
        	SmartDashboard.putNumber(m_name + " Position", position);

    }
    
    /**
     * Tolerance is the total range of acceptable values on either side
     * of the desired setpoint.
     */
    public void setTolerance (double tolerance) {
        m_tolerance = tolerance;
        
        if (m_debugging)
        	SmartDashboard.putNumber(m_name + " Tolerance", m_tolerance);

    }

    public synchronized boolean onTarget() {

    	return (Math.abs(getSetpoint() - m_talon.getPosition()) <= m_tolerance);

    }

    /**
     * Begin running the PIDController
     */
    public synchronized void enable() {
        m_talon.ClearIaccum();        
    	m_talon.enableControl();

        if (m_debugging)
        	SmartDashboard.putBoolean(m_name + " Enabled", true);
    }

    /**
     * Stop running the PIDController, this sets the output to zero before stopping.
     */
    public synchronized void disable() {
        m_talon.ClearIaccum();
    	m_talon.disableControl();

        if (m_debugging)
        	SmartDashboard.putBoolean(m_name + " Enabled", false);
    }

    /**
     * Return true if PIDController is enabled.
     */
    public synchronized boolean isEnabled() {

    	return m_talon.isControlEnabled();
    }

	/*
	 * Code to support update of PIDF constants from a button on the SmartDashboard
	 */
    public void updatePIDF() {

    	// Assign
		double p = SmartDashboard.getNumber(m_name + " P", 0.0);
		double i = SmartDashboard.getNumber(m_name + " I", 0.0);
		double d = SmartDashboard.getNumber(m_name + " D", 0.0);
		if (m_hasFeedForward) {
			double f = SmartDashboard.getNumber(m_name + " F", 0.0);
			setPID(p, i, d, f);
		} else
			setPID(p, i, d);
		
    	// Update Tolerance
		setTolerance(SmartDashboard.getNumber(m_name + " Tolerance", 0.0));
	
	}

    
}

