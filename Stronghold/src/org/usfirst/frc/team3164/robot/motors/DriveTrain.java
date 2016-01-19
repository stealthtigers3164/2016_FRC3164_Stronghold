package org.usfirst.frc.team3164.robot.motors;

import edu.wpi.first.wpilibj.Jaguar;

public class DriveTrain {
	private static int DRIVETRAIN_MOTOR_FRONTLEFT = 0;
	private static int DRIVETRAIN_MOTOR_FRONTRIGHT = 1;
	private static int DRIVETRAIN_MOTOR_REARLEFT = 2;
	private final static int DRIVETRAIN_MOTOR_REARRIGHT = 3;
	
	private Jaguar rightFrontMotor;
	private Jaguar rightBackMotor;
	private Jaguar leftFrontMotor;
	private Jaguar leftBackMotor;
	
	public DriveTrain() {
		rightBackMotor = new Jaguar(DRIVETRAIN_MOTOR_REARRIGHT);
		rightFrontMotor = new Jaguar(DRIVETRAIN_MOTOR_FRONTRIGHT);
		leftBackMotor = new Jaguar(DRIVETRAIN_MOTOR_REARLEFT);
		leftFrontMotor = new Jaguar(DRIVETRAIN_MOTOR_FRONTLEFT);
		rightBackMotor.setSafetyEnabled(true);
		rightFrontMotor.setSafetyEnabled(true);
		leftFrontMotor.setSafetyEnabled(true);
		leftBackMotor.setSafetyEnabled(true);
	}
	
	public void setRightPower(double pwr) {
		rightBackMotor.set(pwr);
		rightFrontMotor.set(pwr);
	}
	
	public void setLeftPower(double pwr) {
		leftBackMotor.set(pwr);
		leftFrontMotor.set(pwr);
	}
	
	public void tankDrive(double leftJoy, double rightJoy) {
		setLeftPower(leftJoy);
		setRightPower(rightJoy);
	}
}
