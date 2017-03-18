package org.usfirst.frc3467.robot;

import java.util.Vector;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc3467.robot.OI;
import org.usfirst.frc3467.subsystems.Brownout.Brownout;
import org.usfirst.frc3467.subsystems.Climber.Climber;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.GearCatcher.GearCatcher;
import org.usfirst.frc3467.subsystems.Gyro.Gyro;
import org.usfirst.frc3467.subsystems.LIDAR.LIDAR;
import org.usfirst.frc3467.subsystems.PixyCam.PixyCam;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;
import org.usfirst.frc3467.subsystems.Shooter.Shooter;

public abstract class CommandBase extends Command {
	
	// Create universal instances of subsystems
	public static OI oi;
	public static Brownout brownout;
	public static Climber climber;
	public static DriveBase driveBase;
	public static GearCatcher gearcatch;
	public static Gyro gyro;
	public static LIDAR lidar;
	public static PixyCam pixyCam;
	public static Pneumatics pneumatics;
	public static Shooter shooter;

	
	// Create vector with subsystems as elements for global subsystem commands
	public static Vector <Subsystem> subsystemList;
	
	
	public static void init() {

		System.out.println("Initializing CommandBase ...");
		
		//Make instance of vector known as subsystemList
		subsystemList = new Vector<Subsystem>();
		
		//Add instances of subsystems
		pneumatics = Pneumatics.getInstance();
		subsystemList.addElement(pneumatics);		
		brownout = Brownout.getInstance();
		subsystemList.addElement(brownout);
		driveBase = DriveBase.getInstance();
		subsystemList.addElement(driveBase);

		// Create new instances of subsystems
		climber = new Climber();
		subsystemList.addElement(climber);
		gearcatch = new GearCatcher();
		subsystemList.addElement(gearcatch);
		gyro = new Gyro();
		subsystemList.addElement(gyro);
		//lidar = new LIDAR();
		//subsystemList.addElement(lidar);
		pixyCam = new PixyCam();
		subsystemList.addElement(pixyCam);
		shooter = new Shooter();
		subsystemList.addElement(shooter);
		
		//Initial Commands
		oi = new OI();
		oi.BindCommands();	
	}
	
	public CommandBase() {
		super();
	}
	public CommandBase (String name) {
		super(name);
	}
}