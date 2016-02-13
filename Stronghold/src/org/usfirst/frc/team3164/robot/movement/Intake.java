package org.usfirst.frc.team3164.robot.movement;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;
import org.usfirst.frc.team3164.robot.input.Gamepad;

public class Intake<T extends BasicMotor> extends MotorSet<T> {
	private ArrayList<T> motors;
	private Gamepad gamePad;

	private int motorIndex;
	
	public Intake(Gamepad Pad, T motor) {
		gamePad = Pad;
		motors = new ArrayList<T>();
		motorIndex = addMotor(motor);
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
			for (T motor : motors) {
				if (gamePad.trigger.getLeftVal() != 0)
					motor.setPower(gamePad.trigger.getLeftVal());
				else if (gamePad.trigger.getRightVal() != 0)
					motor.setPower(gamePad.trigger.getRightVal());
			}
		}
	}

	@Override
	public boolean shouldUpdate() {
		return true;
	}
}
