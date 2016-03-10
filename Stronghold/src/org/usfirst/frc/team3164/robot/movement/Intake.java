package org.usfirst.frc.team3164.robot.movement;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;
import org.usfirst.frc.team3164.robot.input.Gamepad;

public class Intake<T extends BasicMotor> extends MotorSet<T> {
	private ArrayList<T> motors;
	
	private int motorIndex;
	
	private Gamepad pad;
	
	public Intake(T Motor, Gamepad Pad) {
		motors = new ArrayList<T>();
		pad = Pad;
		motorIndex = addMotor(Motor);
	}
	
	@Override
	public ArrayList<T> getMotors() {
		return motors;
	}

	@Override
	public T getMotorByIndex(int Index) {
		return motors.get(Index);
	}

	@Override
	public int addMotor(T Motor) {
		motors.add(Motor);
		return motors.size() - 1;
	}

	@Override
	public void updateMotors() {
		if (shouldUpdate()) {
			getMotorByIndex(motorIndex).setPower(1);
		}
	}

	@Override
	public boolean shouldUpdate() {
		return pad.buttons.BUTTON_A.isOn();
	}

}
