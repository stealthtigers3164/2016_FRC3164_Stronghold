package org.usfirst.frc.team3164.robot.motors;

import edu.wpi.first.wpilibj.Jaguar;

public class DriveTrain {
	private static int DRIVETRAIN_MOTOR_FRONTLEFT = 1;//0
	private static int DRIVETRAIN_MOTOR_FRONTRIGHT = 0; //1
	private static int DRIVETRAIN_MOTOR_REARLEFT = 3; //2
	private static int DRIVETRAIN_MOTOR_REARRIGHT = 2; //3
	
	private Jaguar rightFrontMotor;
	private Jaguar rightBackMotor;
	private Jaguar leftFrontMotor;
	private Jaguar leftBackMotor;
	
	private double scaleFactor = 1;
	
	public DriveTrain() {
		rightBackMotor = new Jaguar(DRIVETRAIN_MOTOR_REARRIGHT);
		rightFrontMotor = new Jaguar(DRIVETRAIN_MOTOR_FRONTRIGHT);
		leftBackMotor = new Jaguar(DRIVETRAIN_MOTOR_REARLEFT);
		leftFrontMotor = new Jaguar(DRIVETRAIN_MOTOR_FRONTLEFT);
		
		rightBackMotor.setSafetyEnabled(true);
		rightFrontMotor.setSafetyEnabled(true);
		leftFrontMotor.setSafetyEnabled(true);
		leftBackMotor.setSafetyEnabled(true);
		
		leftBackMotor.setInverted(true);
		leftFrontMotor.setInverted(true);
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
		setLeftPower(scaleJoysticks(leftJoy));
		setRightPower(scaleJoysticks(rightJoy));
	}
	
	public void forzaDrive(double axisX, double axisY) {
		axisX = 100 * scaleJoysticks(axisX);
		axisY = -100 * scaleJoysticks(axisY);
		
		double v = (100 - Math.abs(axisX)) * (axisY/100) + axisY;
		double w = (100 - Math.abs(axisY)) * (axisX/100) + axisX;
		
		double r = (v+w)/200;
		double l = (v-w)/200;
		
		setLeftPower(l);
		setRightPower(r);
		
	}
	private double scaleJoysticks(double number) {
		return number * scaleFactor;
	}
	
	public void setScaleFactor(double sf) {
		this.scaleFactor = sf;
	}
	
}
