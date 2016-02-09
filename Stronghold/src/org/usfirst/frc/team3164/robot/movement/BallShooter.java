package org.usfirst.frc.team3164.robot.movement;

import java.util.concurrent.locks.ReentrantLock;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.StartCommand;

public class BallShooter<T extends BasicMotor> {
	private boolean shooterStarted;
	
	private T motor;
	private Encoder motorEncoder;
	private Thread rpmThread;
	private ReentrantLock rpmThreadLock = new ReentrantLock();
	
	private int wantedRPMSpeed;
	
	
	public BallShooter(T flywheelM, int EncoderChannelA, int EncoderChannelB) {
		this.motor = flywheelM;
		motorEncoder = new Encoder(EncoderChannelA, EncoderChannelB);
		shooterStarted = false;
	}
	
	public void initRPMThread() {
		rpmThread = new Thread("") {
			@Override 
			public void run() {
				getRPMMotorLock().lock();
				T Motor = getMotor();
				if (Motor != null) {
					if (isShooting()) {
						
					}
				}
				getRPMMotorLock().unlock();
			}
		};
	}
	
	public T getMotor() {
		return motor;
	}
	
	public ReentrantLock getRPMMotorLock() {
		return rpmThreadLock;
	}
	
	public boolean isShooting() {
		return shooterStarted;
	}
	
	public void setIsShooting(boolean Shooting) {
		shooterStarted = Shooting;
	}
}
