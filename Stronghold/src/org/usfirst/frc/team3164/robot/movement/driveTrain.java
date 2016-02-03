package org.usfirst.frc.team3164.robot.movement;

import org.usfirst.frc.team3164.robot.electrical.motor.basicMotor;

public class driveTrain {
	
	private basicMotor frontLeftMotor;	
	private basicMotor frontRightMotor;
	private basicMotor backLeftMotor;
	private basicMotor backRightMotor;	
	
	private double scaleFactor = 1;
	private double scaleFactorX = 1;
	
	public driveTrain(basicMotor flMotor, basicMotor frMotor, basicMotor blMotor, basicMotor brMotor) {
		frontLeftMotor = flMotor;
		frontRightMotor = frMotor;
		backLeftMotor = blMotor;
		backRightMotor = brMotor;
	}
	
	public void setRightPower(double pwr) {
		backRightMotor.setPower(pwr);
		frontRightMotor.setPower(pwr);
	}
	
	public void setLeftPower(double pwr) {
		backLeftMotor.setPower(pwr);
		frontLeftMotor.setPower(pwr);
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
