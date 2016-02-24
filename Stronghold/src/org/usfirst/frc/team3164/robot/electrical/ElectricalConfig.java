package org.usfirst.frc.team3164.robot.electrical;

public class ElectricalConfig {
	//Motors
	public static final int wheel_backLeft_motor   = 0;
	public static final int wheel_frontLeft_motor  = 1;
	public static final int wheel_frontRight_motor = 2;	
	public static final int wheel_backRight_motor  = 3;
	public static final int lift_front_motor       = 4;
	public static final int lift_back_motor        = 5;	
	public static final int feeder_motor           = 6;
	public static final int ball_shooter_motor     = 7;
	public static final int arm_motor              = 8;
	public static final int intake_motor           = 9;
	
	//Analog
	public static final int analog_ultrasonic_port = 0;
	
	//Digital
	public static final int feeder_limit_switch    = 0;
	
	//Reverse Booleans
	public static final boolean wheel_backLeft_rev = false;
	public static final boolean wheel_frontLeft_rev = false;
	public static final boolean wheel_frontRight_rev = true;
	public static final boolean wheel_backRight_rev = true;
}
