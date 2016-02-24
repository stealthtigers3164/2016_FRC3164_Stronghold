package org.usfirst.frc.team3164.robot.movement;

import java.util.ArrayList;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;
import org.usfirst.frc.team3164.robot.input.Gamepad;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm<T extends BasicMotor> extends MotorSet<T> {
	private ArrayList<T> motors;
	private Gamepad pad;
	
	private int motorIndex;
	private int intakeMotorIndex;
	
	public Arm(Gamepad Pad, T Motor, T IntakeMotor) {
		motors = new ArrayList<T>();
		pad = Pad;
		motorIndex = addMotor(Motor);
		intakeMotorIndex = addMotor(IntakeMotor);
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
			SmartDashboard.putNumber("Gamepad2 Right stick data", pad.sticks.RIGHT_Y.getRaw());
			getMotorByIndex(intakeMotorIndex).setPower(pad.sticks.RIGHT_Y.getRaw());
			//getMotorByIndex(motorIndex).setPower(pad.trigger.getLeftVal());
		}
	}

	@Override
	public boolean shouldUpdate() {
		return true;
	}

}
