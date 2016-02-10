package org.usfirst.frc.team3164.robot.movement;

import org.usfirst.frc.team3164.robot.electrical.ElectricalConfig;
import org.usfirst.frc.team3164.robot.electrical.motor.JaguarMotor;
import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	public void update(float DistanceSensorData) {
    	if (gamePad.buttons.BUTTON_A.isOn()) {
    		//shoot(DistanceSensorData); 
    	}
    	
    	SmartDashboard.putBoolean("Shooting", false);//gamePad.buttons.BUTTON_A.isOn());
	}
	
	public void shoot(float RawData) {
		float distance = calculateDistanceBySensor(RawData);
		shooter.shoot(distance);
	}
	
	public float calculateDistanceBySensor(float RawData) {
		return 0;
	}
	
	public BallShooter<JaguarMotor> getBallShooter() {
		return shooter;
	}
}
