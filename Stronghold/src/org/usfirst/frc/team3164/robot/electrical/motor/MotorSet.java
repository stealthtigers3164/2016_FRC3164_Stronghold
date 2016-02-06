package org.usfirst.frc.team3164.robot.electrical.motor;

import java.util.ArrayList;

public abstract class MotorSet<T extends BasicMotor>
{
	public abstract ArrayList<T> getMotors();
	public abstract T getMotorByIndex(int Index);
	public abstract int AddMotor(T Motor);
	public abstract void UpdateMotors();
	public abstract boolean shouldUpdate();
}
