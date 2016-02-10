package org.usfirst.frc.team3164.robot.movement;

import org.usfirst.frc.team3164.robot.electrical.ElectricalConfig;
import org.usfirst.frc.team3164.robot.electrical.motor.JaguarMotor;
import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;

public class FlyWheel {
	
	private BallShooter<JaguarMotor> shooter;
	private Gamepad gamePad;
	
	public FlyWheel(ThreadQueue<WorkerThread> Queue, Gamepad pad) {
        shooter = new BallShooter<JaguarMotor>(
        		new JaguarMotor(ElectricalConfig.ball_shooter_motor), 
        		ElectricalConfig.ball_shooter_encoder_channel_a, 
        		ElectricalConfig.ball_shooter_encoder_channel_b);
        gamePad = pad;
        shooter.initRPMThread(Queue);
	}
	
	public void update() {/*
    	if (gamePad.buttons.BUTTON_A.isOn()) {
    		shoot(); 
    	}*/
	}
	
	public void shoot() {
		float distance = calculateDistanceBySensor();
		shooter.shoot(distance);
	}
	
	public float calculateDistanceBySensor() {
		return 0;
	}
	
	public BallShooter<JaguarMotor> getBallShooter() {
		return shooter;
	}
}
