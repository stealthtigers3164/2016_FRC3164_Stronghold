package org.usfirst.frc.team3164.robot.movement;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.electrical.LimitSwitch;
import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;
import org.usfirst.frc.team3164.robot.input.Gamepad;

public class Feeder<T extends BasicMotor> extends MotorSet<T> {
	private ArrayList<T> motors;
	private Gamepad gamePad;

	private boolean running;
	
	private LimitSwitch limitSwitch;
	private boolean switched;
	
	private int motorIndex;
	
	public Feeder(Gamepad Pad, T motor, final LimitSwitch limitSwitchPass) {
		gamePad = Pad;
		motors = new ArrayList<T>();
		motorIndex = addMotor(motor);
		limitSwitch = limitSwitchPass;
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

	public void updateMotors(FlyWheel wheel) {
		if (switched) {
			if (wheel.getBallShooter().getMotor().getPower() > 0) {
				shouldUpdate();
			}
		}
		else {
			if (shouldUpdate()) {
				updateMotors();
			}
		}
		
		if (running) {
			getMotorByIndex(motorIndex).setPower(1);
		}
	}
	
	@Override
	public void updateMotors() {
		if (gamePad.buttons.BUTTON_B.isOn()) {
			running = true;
		}
		else if (gamePad.buttons.BUTTON_X.isOn()) {
			running = false;
		}
	}

	@Override
	public boolean shouldUpdate() {
		switched = limitSwitch.isPressed();
		if (switched) {
			running = false;
		}
		return !switched;
	}
}
