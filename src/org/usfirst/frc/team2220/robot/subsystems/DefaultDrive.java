package org.usfirst.frc.team2220.robot.subsystems;

import org.usfirst.frc.team2220.robot.Robot;
import org.usfirst.frc.team2220.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DefaultDrive extends Subsystem{

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	//Initializing CANTalons
	private static CANTalon rDriveMaster;
	private static CANTalon rDriveSlave;
	private static CANTalon lDriveMaster;
	private static CANTalon lDriveSlave;
	
	//New RobotDrive
	static RobotDrive maindrive;
	
	public static void init() {
	
		//Run at TeleopInit
		rDriveMaster = new CANTalon(RobotMap.rightMaster);
		rDriveSlave = new CANTalon(RobotMap.rightSlave);
		lDriveMaster = new CANTalon(RobotMap.leftMaster);
		lDriveSlave = new CANTalon(RobotMap.leftSlave);
		rDriveMaster.setInverted(false);
		lDriveMaster.setInverted(true);
		
		RobotDrive maindrive = new RobotDrive(lDriveMaster, rDriveMaster);
		
	}
	
	public static void Drive() {
		
		maindrive.tankDrive(Robot.oi.getDriverJoystick().getRawAxis(1),Robot.oi.getDriverJoystick().getRawAxis(5));
		
	}
	
	
	
}
