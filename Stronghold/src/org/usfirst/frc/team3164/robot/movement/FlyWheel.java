package org.usfirst.frc.team3164.robot.movement;

import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;
import org.usfirst.frc.team3164.robot.vision.GoalAlign;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel {
	
	private BallShooter shooter;
	private Gamepad gamePad;
	
	public FlyWheel(ThreadQueue<WorkerThread> Queue, Gamepad pad) {
        /*shooter = new BallShooter<SparkMotor>(
        		new SparkMotor(ElectricalConfig.ball_shooter_motor), 
        		ElectricalConfig.ball_shooter_encoder_channel_a, 
        		ElectricalConfig.ball_shooter_encoder_channel_b);*/
        gamePad = pad;
        //shooter.initRPMThread(Queue);
        shooter = new BallShooter();
	}
	
	public void update(GoalAlign alignment) {
    	if (gamePad.buttons.BUTTON_A.isOn()) {
    		//shoot(DistanceSensorData); 
    		//shooter.getMotor().getSpark().set(1);
    		//shooter.shoot(100);
    		shooter.shoot();
    		
    	}
    	else if (!gamePad.buttons.BUTTON_A.isOn()) {
    		shooter.reset();
    	}
    	
    	SmartDashboard.putBoolean("Shooting", gamePad.buttons.BUTTON_A.isOn());
	}
	
	public void shoot(float RawData) {
		float distance = calculateDistanceBySensor(RawData);
		//shooter.shoot(distance);
	}
	
	public float calculateDistanceBySensor(float RawData) {
		return 0;
	}
	
	public BallShooter getBallShooter() {
		return shooter;
	}
	
	public double getDistFromBottom(double hy, double y, double rectangleHeight) {
		return hy - y - rectangleHeight;
	}
}
