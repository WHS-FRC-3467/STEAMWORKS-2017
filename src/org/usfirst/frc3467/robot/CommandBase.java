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
import org.usfirst.frc3467.subsystems.PixyCam.PixyCam;
import org.usfirst.frc3467.subsystems.Pneumatics.Pneumatics;
import org.usfirst.frc3467.subsystems.Shooter.ShooterFeed;
import org.usfirst.frc3467.subsystems.Shooter.ShooterFlywheel;
import org.usfirst.frc3467.subsystems.Shooter.ShooterTurret;

public abstract class CommandBase extends Command {
	
	// Create universal instances of subsystems
	public static OI oi;
	public static Brownout brownout;
	public static Climber climber;
	public static DriveBase driveBase;
	public static GearCatcher gearcatch;
	public static Gyro gyro;
	public static PixyCam pixyCamShooter;
	public static PixyCam pixyCamGear;
	public static Pneumatics pneumatics;
	//public static Shooter shooter;
	public static ShooterTurret shooterTurret;
	public static ShooterFlywheel shooterFlywheel;
	public static ShooterFeed shooterFeed;
	
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
		//shooter = new Shooter();
		//subsystemList.addElement(shooter);
		shooterFlywheel = new ShooterFlywheel();
		subsystemList.addElement(shooterFlywheel);		
		shooterTurret = new ShooterTurret();
		subsystemList.addElement(shooterTurret);		
		shooterFeed = new ShooterFeed();
		subsystemList.addElement(shooterFeed);		
		pixyCamShooter = new PixyCam(PixyCam.PIXY_I2C_SHOOTER_ADDR);
		subsystemList.addElement(pixyCamShooter);
		pixyCamGear = new PixyCam(PixyCam.PIXY_I2C_GEAR_ADDR);
		subsystemList.addElement(pixyCamGear);
		
		//Initialize Operator Interface Commands
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