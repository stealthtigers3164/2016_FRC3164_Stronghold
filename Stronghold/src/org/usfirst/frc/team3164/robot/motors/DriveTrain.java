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
	private double scaleFactorX = 1;
	
	public DriveTrain() {
		rightBackMotor = new Jaguar(DRIVETRAIN_MOTOR_REARRIGHT);
		rightFrontMotor = new Jaguar(DRIVETRAIN_MOTOR_FRONTRIGHT);
		leftBackMotor = new Jaguar(DRIVETRAIN_MOTOR_REARLEFT);
		leftFrontMotor = new Jaguar(DRIVETRAIN_MOTOR_FRONTLEFT);
		
		
		rightBackMotor.setSafetyEnabled(false);
		rightFrontMotor.setSafetyEnabled(false);
		leftFrontMotor.setSafetyEnabled(false);
		leftBackMotor.setSafetyEnabled(false);
		
		rightBackMotor.setExpiration(0.5f);
		rightFrontMotor.setExpiration(0.5f);
		leftBackMotor.setExpiration(0.5f);
		leftFrontMotor.setExpiration(0.5f);
		
		rightBackMotor.setInverted(true);//left
		rightFrontMotor.setInverted(true);//left
	}
	
	public void setRightPower(double pwr) {
		/*if(rightBackMotor.get() != pwr)*/rightBackMotor.set(pwr);
		/*if(rightFrontMotor.get() != pwr)*/rightFrontMotor.set(pwr);
	}
	
	public void setLeftPower(double pwr) {
		/*if(leftBackMotor.get() != pwr)*/leftBackMotor.set(pwr);
		/*if(leftFrontMotor.get() != pwr)*/leftFrontMotor.set(pwr);
	}
	
	public void tankDrive(double leftJoy, double rightJoy) {
		setLeftPower(scaleJoysticks(leftJoy));
		setRightPower(scaleJoysticks(rightJoy));
	}
	
	/**
	 * Drive train made to mimic video games.
	 * Intended to use the triggers as the Y axis
	 * @param axisX used to turn [-1,1]
	 * @param axisY used to go forward [-1,1]
	 */
	public void forzaDrive(double axisX, double axisY) {
		axisX = -100 * scaleJoysticks(axisX, true);
		axisY = 100 * scaleJoysticks(axisY);
		
		double v = (100 - Math.abs(axisX)) * (axisY/100) + axisY;
		double w = (100 - Math.abs(axisY)) * (axisX/100) + axisX;
		
		double r = (v+w)/200;
		double l = (v-w)/200;
		
		setLeftPower(l);
		setRightPower(r);
		
	}
	
	/**
	 * Multiplies the axis by the scale factor
	 * @param number joystick axis 
	 * @return
	 */
	private double scaleJoysticks(double number) {
		return number * scaleFactor;
	}
	
	/**
	 * Multiplies the axis by the scale factor
	 * @param number joystick axis
	 * @param onlyx send true to get the scale factor for the forza drive turning
	 * @return
	 */
	private double scaleJoysticks(double number, boolean onlyx) {
		if(onlyx) {
			return number * scaleFactorX;
		} else {
			return number * scaleFactor;
		}
	}
	
	/**
	 * Sets the scaling factor for the speed of the joysticks
	 * @param sf double [0, 1]
	 */
	public void setScaleFactor(double sf) {
		this.scaleFactor = Math.min(Math.abs(sf), 1) * Math.signum(sf);
	}
	
	/**
	 * Sets the scaling factor for the speed of the joysticks
	 * Allows for rotation to be scaled is true is sent
	 * @param sf double [0, 1]
	 * @param onlyx send true to set the scale factor for the forza drive turning
	 */
	public void setScaleFactor(double sf, boolean onlyx) {
		if(onlyx) {
			this.scaleFactorX = Math.min(Math.abs(sf), 1) * Math.signum(sf);
		} else { 
			this.scaleFactor = Math.min(Math.abs(sf), 1) * Math.signum(sf);
		}
	}
	
	
}
