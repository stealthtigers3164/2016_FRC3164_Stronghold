package org.usfirst.frc.team3164.robot.movement;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;
import org.usfirst.frc.team3164.robot.input.Gamepad;

public class DriveTrain<T extends BasicMotor> extends MotorSet<T> {
	
	public static final int FORZA_DRIVE = 0;
	public static final int TANK_DRIVE = 1;
	
	private ArrayList<T> motors;
	
	private int frontLeftMotorIndex;	
	private int frontRightMotorIndex;
	private int backLeftMotorIndex;
	private int backRightMotorIndex;	
	
	private double scaleFactor = 1;
	private double scaleFactorX = 1;
	
	private Gamepad gamePad;
	
	private boolean update;
	private int driveType;
	
	public DriveTrain(T flMotor, T frMotor, T blMotor, T brMotor, Gamepad gamePad) {
		//NOTE if you change the order of which the motors are added then the indices will change as well
		frontLeftMotorIndex = addMotor(flMotor);
		frontRightMotorIndex = addMotor(frMotor);
		backLeftMotorIndex = addMotor(blMotor);
		backRightMotorIndex = addMotor(brMotor);
		this.gamePad = gamePad;
		driveType = FORZA_DRIVE;
	}
	
	public void setRightPower(double pwr) {
		getMotorByIndex(backRightMotorIndex).setPower(pwr);
		getMotorByIndex(frontRightMotorIndex).setPower(pwr);
	}
	
	public void setLeftPower(double pwr) {
		getMotorByIndex(backLeftMotorIndex).setPower(pwr);
		getMotorByIndex(frontLeftMotorIndex).setPower(pwr);
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

	@Override
	public ArrayList<T> getMotors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getMotorByIndex(int Index) {
		return motors.get(Index);
	}

	@Override
	public int addMotor(T Motor) {
		if (motors.add(Motor))
			return motors.size() - 1;
		else
			return -1;
	}

	@Override
	public void updateMotors() {
		
		if (shouldUpdate()) {
			switch(driveType) {
			case FORZA_DRIVE:
				forzaDrive(gamePad.sticks.LEFT_X.getScaled(), gamePad.trigger.getAxis());
				break;
			case TANK_DRIVE:
				tankDrive(gamePad.sticks.LEFT_Y.getScaled(), gamePad.sticks.RIGHT_Y.getScaled());
				break;
			default:
				new IllegalStateException("The Drive Type is not tank drive or forza drive, it may be not initialized");
			}
		}
	}

	@Override
	public boolean shouldUpdate() {
		return update;
	}
	
	public void setUpdate(boolean Update) {
		update = Update;
	}
	
	public void useTankDrive() {
		driveType = TANK_DRIVE;
	}
	
	public void useForzaDrive() {
		driveType = FORZA_DRIVE;
	}
	
	public int getDriveType() {
		return driveType;
	}
}
