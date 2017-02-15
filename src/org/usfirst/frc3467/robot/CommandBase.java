package org.usfirst.frc3467.robot;

import java.util.Vector;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc3467.robot.OI;
import org.usfirst.frc3467.subsystems.Brownout.Brownout;
import org.usfirst.frc3467.subsystems.Claw.Claw;
import org.usfirst.frc3467.subsystems.Climber.Climber;
import org.usfirst.frc3467.subsystems.DriveBase.DriveBase;
import org.usfirst.frc3467.subsystems.FloorIntake.FloorIntake;
import org.usfirst.frc3467.subsystems.Gyro.Gyro;
import org.usfirst.frc3467.subsystems.HighIntake.HighIntake;
import org.usfirst.frc3467.subsystems.Hopper.Hopper;
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
	public static FloorIntake flr_intake;
	public static GearCatcher gearcatch;
	public static Gyro gyro;
	public static HighIntake hi_intake;
	public static Hopper hopper;
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

		// Create new instances of subsystems
		climber = new Climber();
		subsystemList.addElement(climber);
		driveBase = new DriveBase();
		subsystemList.addElement(driveBase);
		flr_intake = new FloorIntake();
		subsystemList.addElement(flr_intake);
		gearcatch = new GearCatcher();
		subsystemList.addElement(gearcatch);
		gyro = new Gyro();
		subsystemList.addElement(gyro);
		hi_intake = new HighIntake();
		subsystemList.addElement(hi_intake);
		hopper = new Hopper();
		subsystemList.addElement(hopper);
		lidar = new LIDAR();
		subsystemList.addElement(lidar);
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