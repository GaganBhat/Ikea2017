package org.usfirst.frc.team2220.robot.subsystems;

import org.usfirst.frc.team2220.robot.Robot;
import org.usfirst.frc.team2220.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class TwilightDrive extends Subsystem {
	
	private static CANTalon rDriveMaster;
	private CANTalon rDriveSlave;
	private static CANTalon lDriveMaster;
	private CANTalon lDriveSlave;
	double position;
	double currentPos;
	public int rDriveSetpoint,
	lDriveSetpoint;
	private static TwilightDrive instance = new TwilightDrive();
	
	public TwilightDrive() {
		//Initialize CANTalon Configuration
		rDriveMaster = new CANTalon(RobotMap.rightMaster);
		rDriveSlave = new CANTalon(RobotMap.rightSlave);
		lDriveMaster = new CANTalon(RobotMap.leftMaster);
		lDriveSlave = new CANTalon(RobotMap.leftSlave);
		
		//Initialize Talon Control Mode
		rDriveSlave.changeControlMode(TalonControlMode.Follower);
		rDriveSlave.set(rDriveMaster.getDeviceID());
		
		lDriveSlave.changeControlMode(TalonControlMode.Follower);
		lDriveSlave.set(lDriveMaster.getDeviceID());
		
		lDriveMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		lDriveMaster.configEncoderCodesPerRev(256);
		lDriveMaster.reverseSensor(false);
		
		rDriveMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rDriveMaster.configEncoderCodesPerRev(256);
		rDriveMaster.reverseSensor(false);

		
	}

	 
	public double getRPosition() {
	
	return rDriveMaster.getPosition();
	
	}
	
	public double getLPosition() {
		
		return lDriveMaster.getPosition();
		
	}
	
	public void setPercentVBus() {
		
		rDriveMaster.changeControlMode(TalonControlMode.PercentVbus);
		lDriveMaster.changeControlMode(TalonControlMode.PercentVbus);
		
	}
	
	public static void setLValue(double value) {
		
		lDriveMaster.setPosition(value);
		
	}
	
	public static void setRValue(double value) {
		
		rDriveMaster.setPosition(value);
		
	}
	
	public void addRPos(double value) {
		
	rDriveSetpoint += value;
	rDriveMaster.setPosition(rDriveSetpoint);
		
	}
	
	public void addLpos(double value) {
		
		lDriveSetpoint += value;
		lDriveMaster.setPosition(lDriveSetpoint);
		
	}
	
	public void controlTwilightDrive() {
		
		rDriveMaster.setPosition(Robot.oi.getDriverJoystick().getRawAxis(1));
		lDriveMaster.setPosition(Robot.oi.getDriverJoystick().getRawAxis(5));
		
	}
	
	
	public static TwilightDrive getInstance() {
		
		return instance;
		
	}
	


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new TwilightDrive());
	}


	private void setDefaultCommand(TwilightDrive twilightDrive) {
		// TODO Auto-generated method stub
		
	}

}
