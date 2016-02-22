package org.usfirst.frc.team3164.robot.movement;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.electrical.LimitSwitch;
import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;
import org.usfirst.frc.team3164.robot.input.Gamepad;

public class Feeder<T extends BasicMotor> extends MotorSet<T> {
	private ArrayList<T> motors;
	private Gamepad gamePad;

	private LimitSwitch limitSwitch;
		
	private int motorIndex;
	
	public Feeder(Gamepad Pad, T motor, final int limitSwitchPort) {
		gamePad = Pad;
		motors = new ArrayList<T>();
		motorIndex = addMotor(motor);
		limitSwitch = new LimitSwitch(limitSwitchPort);
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
		if (wheel.getBallShooter().getMotor().getPower() > 0 || shouldUpdate()) {
			updateMotors();
		}
	}
	
	@Override
	public void updateMotors() {
		if (gamePad.trigger.getLeftVal() != 0)
			motors.get(motorIndex).setPower(gamePad.trigger.getLeftVal());
		else if (gamePad.trigger.getRightVal() != 0)
			motors.get(motorIndex).setPower(gamePad.trigger.getRightVal());
	}

	@Override
	public boolean shouldUpdate() {
		if (limitSwitch.isPressed()) {
			
		}
		return !limitSwitch.isPressed();
	}
}
