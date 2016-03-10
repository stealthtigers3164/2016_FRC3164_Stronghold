package org.usfirst.frc.team3164.robot.electrical;

public class ElectricalConfig {
	//Motors
	public static final int wheel_backLeft_motor   = 0; //Practice Bot: 3//Real Bot: 0
	public static final int wheel_frontLeft_motor  = 1; //Practice Bot: 1//Real Bot: 1
	public static final int wheel_frontRight_motor = 2;	//Practice Bot: 2//Real Bot: 2
	public static final int wheel_backRight_motor  = 3; //Practice Bot: 0//Real Bot: 3
	public static final int lift_front_motor       = 4; //Practice Bot: 4//Real Bot: 4
	public static final int lift_back_motor        = 5;	//Practice Bot: 5//Real Bot: 5
	public static final int feeder_motor           = 6; //Practice Bot: 6//Real Bot: 6
	public static final int ball_shooter_motor     = 7; //Practice Bot: 7//Real Bot: 7
	public static final int arm_motor              = 8; //Practice Bot: 8//Real Bot: 8
	public static final int intake_motor           = 9; //Practice Bot: 9//Real Bot: 9
	
	//Analog
	public static final int analog_ultrasonic_port = 0; //Real Bot: 0
	
	//Digital
	public static final int feeder_limit_switch    = 0; //Real Bot: 0
	
	//Reverse Booleans
	public static final boolean wheel_backLeft_rev = false;//Real Bot: false
	public static final boolean wheel_frontLeft_rev = false;//Real Bot: false
	public static final boolean wheel_frontRight_rev = true;//Real Bot: true
	public static final boolean wheel_backRight_rev = true;//Real Bot: true
}
