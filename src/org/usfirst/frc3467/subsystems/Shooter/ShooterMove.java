package org.usfirst.frc3467.subsystems.Shooter;

//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.Talon;
//import edu.wpi.first.wpilibj.SampleRobot;
//import edu.wpi.first.wpilibj.Timer;
//import com.ctre.CANTalon.TalonControlMode;
//import com.ctre.CANTalon.MotionProfileStatus;
//import com.ctre.CANTalon.TrajectoryPoint;
import edu.wpi.first.wpilibj.Victor;
import com.ctre.CANTalon;


// THIS IS NOT IN USE


	public class ShooterMove /*extends Subsystem implements*/{
	
		static Victor Feeder = new Victor(1);
		static CANTalon belt = new CANTalon(2); // the number is the port it is in
	
		
	public static Victor disable(Victor m) {
		m.set(0);
	
		return m;
}// void statements

	public static CANTalon disable(CANTalon m){
		
		m.set(0);
		
		return m;
}

	public static CANTalon enable(CANTalon r){
	
		r.set(1);
		
		return r;
}
	public static Victor enable(Victor r){
	
		r.set(1);
	
		return r;
	
}

	public void PlaceHolder(String[] args){

		enable(Feeder);
		enable(belt);
	}
	}


