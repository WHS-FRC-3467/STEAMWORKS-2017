
package org.usfirst.frc3467.subsystems.Gyro;

import java.text.DecimalFormat;
import org.usfirst.frc3467.subsystems.Gyro.BNO055;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Gyro extends Subsystem {
    
	private static BNO055 imu;
	private static BNO055 gyro2;
	public static double zeroed = 0;
	public static DigitalOutput gyro1DIO;

	public Gyro() {
    	imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER, Port.kOnboard, BNO055.BNO055_ADDRESS_A);

    	//gyro2 = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER, Port.kOnboard, BNO055.BNO055_ADDRESS_B);
    	gyro1DIO = new DigitalOutput(0);
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
    
    public double getGyro2Heading() {
    	return gyro2.getHeading();
    }
    /**
     * Gets a vector representing the sensors position (heading, roll, pitch).
	 * heading:    0 to 360 degrees
	 * roll:     -90 to +90 degrees (front to back)
	 * pitch:   -180 to +180 degrees (side to side)
	 *
	 * For continuous rotation heading (doesn't roll over between 360/0) see
	 *   the getHeading() method.
	 *
	 * @return a vector [heading, roll, pitch]
	 */
    public double[] getVector() {
    	return imu.getVector();
    }
    
    public double[] getGyro2Vector() {
    	return gyro2.getVector();
    }
    
    public void hardReset() {
    	gyro1DIO.set(false); //may need delay
    	gyro1DIO.set(true);
    	imu.reInitialize(0, false, false);
    	
    }
    
    public boolean gyroSetup() {
    	if(imu.isCalibrated())
    		imu.getVector()[0] = gyro2.getVector()[0];
    	return imu.isCalibrated();
    }
    
    public double getAngle() {
    	return imu.getVector()[0] - zeroed;
    	/*if (compareGyros()) 
    		return imu.getVector()[0] - zeroed;
    	else 
    		return gyro2.getVector()[0] - zeroed;*/
    } 
    
    public void zeroGyro() {
    	zeroed = this.getVector()[0];
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
    
 public void reportGyro2Values() {
    	
    	DecimalFormat f = new DecimalFormat("+000.000;-000.000");

		SmartDashboard.putBoolean("Gyro2 Comms:", gyro2.isSensorPresent());
		SmartDashboard.putBoolean("Gyro2 Initialized:", gyro2.isInitialized());
		SmartDashboard.putBoolean("Gyro2 Calibrated:", gyro2.isCalibrated());
		
		if (gyro2.isInitialized()) {

			double[] pos = getGyro2Vector();

			/* Display the floating point data */
			SmartDashboard.putString("Gyro2 Values", "X: " + f.format(pos[0])
					+ " Y: " + f.format(pos[1]) + " Z: " + f.format(pos[2])
					+ "  H: " + gyro2.getHeading());

			/* Display calibration status for each sensor. */
			BNO055.CalData cal = imu.getCalibration();
			SmartDashboard.putString("Gyro2 Calibrations", "Sys=" + cal.sys
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
    
	public boolean compareGyros() {
		boolean equals = false;
		if(gyroSetup()){
			if (imu.getVector()[0] <= gyro2.getVector()[0] + 5 && imu.getVector() [0] >= gyro2.getVector() [0] - 5) {
				equals = false;
			}
		}
		return equals;
	}
    
}

