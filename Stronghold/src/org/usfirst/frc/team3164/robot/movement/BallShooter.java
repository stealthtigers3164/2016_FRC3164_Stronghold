package org.usfirst.frc.team3164.robot.movement;

import java.util.concurrent.locks.ReentrantLock;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.thread.ThreadMethod;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallShooter<T extends BasicMotor> {
	
	private WorkerThread workerThread;
	private boolean shooterStarted;
	
	private T motor;
	private Encoder motorEncoder;
	private Thread rpmThread;
	private ReentrantLock rpmThreadLock = new ReentrantLock();
	
	private int wantedRPMSpeed;
	private boolean threadInitialized = false;
	
	public BallShooter(T flywheelM, int EncoderChannelA, int EncoderChannelB) {
		this.motor = flywheelM;
		motorEncoder = new Encoder(EncoderChannelA, EncoderChannelB);
		shooterStarted = false;
		
		workerThread = new WorkerThread(false, "BallShooterThread");
		workerThread.setThreadMethod(new ThreadMethod() {
			@Override 
			public void run() {
				getRPMMotorLock().lock();
				T Motor = getMotor();
				if (Motor != null) {
					if (isShooting()) {
						if (motorEncoder.getRate() > wantedRPMSpeed) 
							motor.addPower(.1);
						else if (motorEncoder.getRate() < wantedRPMSpeed) 
							motor.addPower(-.1);
						
						SmartDashboard.putBoolean("Correct RPM", motorEncoder.getRate() == wantedRPMSpeed);
					}
				}
				getRPMMotorLock().unlock();
			}
		});

	}
	
	public void initRPMThread(ThreadQueue<WorkerThread> threadQueue) {		
		threadInitialized = true;
		threadQueue.add(workerThread, true);		
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
	
	public void shoot(float distance) {
		
		if (!threadInitialized) {
			throw new IllegalStateException("Ball wheel shooter thread has not been started yet");
		}
		
		boolean usedLock = false;
		if (getRPMMotorLock().isLocked()) {
			
		}
		else {
			usedLock = true;
			getRPMMotorLock().lock();
		}
		setIsShooting(true);
		calculateRPM(distance);
		if (usedLock)
			getRPMMotorLock().unlock();
	}
	
	public void calculateRPM(float distance) {
		//TODO Test the robot to get to the wanted rpm for the distance passed in
		SmartDashboard.putNumber("Distance from target", distance);
		int calculatedRPM = (int) distance;
		wantedRPMSpeed = calculatedRPM;
	}
}
