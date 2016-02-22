package org.usfirst.frc.team3164.robot.electrical.motor;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.input.Gamepad;

public class Arm<T extends BasicMotor> extends MotorSet<T> {
	private ArrayList<T> motors;
	private Gamepad pad;
	
	private int motorIndex;
	
	public Arm(Gamepad Pad, T Motor) {
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
		if (motors.add(Motor)) 
			return motors.size() - 1;
		else
			return -1;
	}

	@Override
	public void updateMotors() {
		if (shouldUpdate()) {
			getMotorByIndex(motorIndex).setPower(pad.trigger.getLeftVal());
		}
	}

	@Override
	public boolean shouldUpdate() {
		return true;
	}

}
