
package org.usfirst.frc.team2220.robot;

import org.usfirst.frc.team2220.robot.subsystems.TwilightDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(240, 180);
		camera.setFPS(30);
		
		CvSink cvsink = CameraServer.getInstance().getVideo();
		
		
		
		oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		NavX = new AHRS(SPI.Port.kMXP);
		double yawVal = NavX.getYaw();
		double angleVal = NavX.getAngle();
		SmartDashboard.putNumber("Yaw", yawVal);
		
	
		
	}


	@Override
	public void disabledInit() {
	
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous	
	 */
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
		TwilightDrive.getInstance().controlTwilightDrive();
		SmartDashboard.putNumber("LeftAxis0", oi.getDriverJoystick().getRawAxis(1));
		SmartDashboard.putNumber("RightAxis5", oi.getDriverJoystick().getRawAxis(5));
		Scheduler.getInstance().run();
		
	}


	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
