
package org.usfirst.frc.team2220.robot;
import java.util.*;

import org.usfirst.frc.team2220.robot.subsystems.DefaultDrive;
import org.usfirst.frc.team2220.robot.subsystems.TwilightDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	private AHRS NavX;
	private static CANTalon rDriveMaster;
	private static CANTalon rDriveSlave;
	private static CANTalon lDriveMaster;
	private static CANTalon lDriveSlave;
	long millisToRun = 1000;
	double [] defaultVal = new double[0];
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	
	//NetworkTable Vals
	NetworkTable visionTable;
	//double[] widthA = visionTable.getNumberArray("width", defaultVal);
	//double width = widthA[0];

	@Override
	public void robotInit() {
		//USB Camera Initialization and Setup
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
		camera.setResolution(240, 180);
		camera.setFPS(10);
		CvSink cvsink = CameraServer.getInstance().getVideo();
		camera.setBrightness(0);
		camera.setExposureManual(1);
		
		//NavX Initialization and Setup
		NavX = new AHRS(SPI.Port.kMXP);
		double yawVal = NavX.getYaw();
		double angleVal = NavX.getAngle();
		SmartDashboard.putNumber("Yaw", yawVal);
		//Initializing the values of the Vision Contours report
		visionTable = NetworkTable.getTable("GRIP/myContoursReport");
		
		oi = new OI();
		SmartDashboard.putData("Auto mode", chooser);
		
		rDriveMaster = new CANTalon(RobotMap.rightMaster);
		rDriveSlave = new CANTalon(RobotMap.rightSlave);
		lDriveMaster = new CANTalon(RobotMap.leftMaster);
		lDriveSlave = new CANTalon(RobotMap.leftSlave);
		
		lDriveSlave.changeControlMode(TalonControlMode.Follower);
		lDriveSlave.set(lDriveMaster.getDeviceID());
		
		rDriveSlave.changeControlMode(TalonControlMode.Follower);
		rDriveSlave.set(rDriveMaster.getDeviceID());
		
		rDriveMaster.setInverted(false);
		lDriveMaster.setInverted(true);
		//RobotDrive maindrive = new RobotDrive(lDriveMaster, rDriveMaster);
		
		
	}


	@Override
	public void disabledInit() {
	
		
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//SmartDashboard.putNumber("Width", width);
	}


	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		
		if (autonomousCommand != null)
			autonomousCommand.start();
		
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		
	}

	@Override
	public void teleopInit() {

		if (autonomousCommand != null)
			autonomousCommand.cancel();
		
		Scheduler.getInstance().removeAll();
	
		
		
	}


	@Override
	public void teleopPeriodic() {
		//TwilightDrive.getInstance().controlTwilightDrive();
		//DefaultDrive.Drive();
		lDriveMaster.set(oi.getDriverJoystick().getRawAxis(1));
		rDriveMaster.set(oi.getDriverJoystick().getRawAxis(5));
		SmartDashboard.putNumber("LeftAxis0", oi.getDriverJoystick().getRawAxis(1));
		SmartDashboard.putNumber("RightAxis5", oi.getDriverJoystick().getRawAxis(5));
		//SmartDashboard.putNumber("Width", width);
		
		//maindrive.tankDrive(Robot.oi.getDriverJoystick().getRawAxis(1),Robot.oi.getDriverJoystick().getRawAxis(5));
		Scheduler.getInstance().run();
		
	}


	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
