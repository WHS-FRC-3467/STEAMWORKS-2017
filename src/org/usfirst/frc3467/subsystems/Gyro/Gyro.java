
package org.usfirst.frc3467.subsystems.Gyro;

import java.text.DecimalFormat;

import org.usfirst.frc3467.subsystems.Gyro.BNO055;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Gyro extends Subsystem {
    
	private static BNO055 imu;
	public static double zeroed = 0;

	public Gyro() {
    	imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,
				BNO055.vector_type_t.VECTOR_EULER);
	}
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new imuUpdateDisplay());
    }
    
    /**
	 * The heading of the sensor (x axis) in continuous format. Eg rotating the
	 *   sensor clockwise two full rotations will return a value of 720 degrees.
	 *
	 * @return heading in degrees
     */
    public double getHeading() {
    	return imu.getHeading();
    }
    
    /**
     * Gets a vector representing the sensors position (heading, roll, pitch).
	 * heading:    0 to 360 degrees
	 * roll:     -90 to +90 degrees
	 * pitch:   -180 to +180 degrees
	 *
	 * For continuous rotation heading (doesn't roll over between 360/0) see
	 *   the getHeading() method.
	 *
	 * @return a vector [heading, roll, pitch]
	 */
    public double[] getVector() {
    	return imu.getVector();
    }
    
    public double getAngle() {
    	return this.getVector()[0] - zeroed;
    }
    
    public void zeroGyro() {
    	zeroed = getAngle();
    }
    
    public void reportGyroValues() {
    	
    	DecimalFormat f = new DecimalFormat("+000.000;-000.000");

		SmartDashboard.putBoolean("IMU Comms:", imu.isSensorPresent());
		SmartDashboard.putBoolean("IMU Initialized:", imu.isInitialized());
		SmartDashboard.putBoolean("IMU Calibrated:", imu.isCalibrated());
		
		if (imu.isInitialized()) {

			double[] pos = imu.getVector();

			/* Display the floating point data */
			SmartDashboard.putString("IMU Values", "X: " + f.format(pos[0])
					+ " Y: " + f.format(pos[1]) + " Z: " + f.format(pos[2])
					+ "  H: " + imu.getHeading());

			/* Display calibration status for each sensor. */
			BNO055.CalData cal = imu.getCalibration();
			SmartDashboard.putString("IMU Calibrations", "Sys=" + cal.sys
					+ " Gyro=" + cal.gyro + " Accel=" + cal.accel
					+ " Mag=" + cal.mag);
		}
    }
    
	/**
	 * @return true if the IMU is found on the I2C bus
	 */
	public boolean isSensorPresent() {
		return imu.isSensorPresent();
	}

	/** 
	 * @return true when the IMU is initialized.
	 */
	public boolean isInitialized() {
		return imu.isInitialized();
	}
	
	/**
	 * Gets current IMU calibration state.
	 * @return each value will be set to 0 if not calibrated, 3 if fully
	 *   calibrated.
	 */
	public BNO055.CalData getCalibration() {
		return imu.getCalibration();
	}
	
	/**
	 * Returns true if all required sensors (accelerometer, magnetometer,
	 *   gyroscope) in the IMU have completed their respective calibration
	 *   sequence.
	 * @return true if calibration is complete for all sensors required for the
	 *   mode the sensor is currently operating in. 
	 */
	public boolean isCalibrated() {
		return imu.isCalibrated();
	}
    
    
}

