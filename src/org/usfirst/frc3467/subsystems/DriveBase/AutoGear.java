package org.usfirst.frc3467.subsystems.DriveBase;

import org.usfirst.frc3467.robot.CommandBase;
import org.usfirst.frc3467.subsystems.PixyCam.NoTargetException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoGear extends CommandBase{
	int target;
	int side = 0;
	double targetAngle;
	public static final double DEFAULT_TARGET_DISTANCE = 0.8;
	double targetDistance = 0.0;
	boolean posReached = false;
	
	
	/*public AutoGear(int gear){
		requires(driveBase);
		requires(pixyCam);
		requires(gyro);
		target = gear;
	}*/
	
	public AutoGear(){
		this(DEFAULT_TARGET_DISTANCE);
	}

	public AutoGear(double defaultTargetDistance) {
		requires(pixyCamShooter);
		requires(driveBase);
		
		this.setInterruptible(true);
		SmartDashboard.putString("Drive Base", "Auto Gear");
		
	}

	protected void initialize(){
		
	}
	
	protected void execute(){
		double[] gearData = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		try{
			gearData = pixyCamShooter.getPegLocationData();
		}catch(NoTargetException ex){
			return;
		}
		double tapePosx = gearData[0];
		double tapePosx2 = gearData[1];
		double height1 = gearData[2];
		double height2 = gearData[3];
		double width1 = gearData[4];
		double width2 = gearData[5];
		double area1 = gearData[6];
		double area2 = gearData[7];
		double center = 0;
		double tapeEnd1 = 0;
		double tapeEnd2 = 0;
		
		if(tapePosx < 0 && tapePosx2 > 0){
			tapeEnd1 = tapePosx-width1/2;
			tapeEnd2 = tapePosx2+width2/2;
		}else if(tapePosx > 0 && tapePosx2 < 0){
			tapeEnd1 = tapePosx+width1/2;
			tapeEnd2 = tapePosx2-width2;
		}
		
		
		
	}
	
	protected void end(){
		
	}
	
	protected void interrupted(){
		end();
	}
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return posReached;
	}

}
