package org.usfirst.frc.team2220.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	double wheelDiameter = 2.75;
	static double circumference = 8.365;
	double encRot;
	
	public static final int leftMaster = 8,
			leftSlave = 2,
			rightMaster = 6,
			rightSlave = 15;
	
	public static double InchesToEncRot(double in) {
		//Converts Inches to Encoder Rotations
		double encRot = (360 / circumference) * in;
		return encRot;
		
	}
	
	public static double FeetToEncRot(double feet) {
		//Uses InchesToEncRot to convert feet into EncRot
		return InchesToEncRot(feet*12);
		
	}
	
	
}
