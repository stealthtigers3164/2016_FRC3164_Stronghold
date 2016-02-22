package org.usfirst.frc.team3164.robot.movement;

import org.usfirst.frc.team3164.robot.electrical.ElectricalConfig;
import org.usfirst.frc.team3164.robot.electrical.motor.SparkMotor;
import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel {
	
	private BallShooter<SparkMotor> shooter;
	private SparkMotor motor;
	private Gamepad gamePad;
	
	public FlyWheel(ThreadQueue<WorkerThread> Queue, Gamepad pad) {
        /*shooter = new BallShooter<SparkMotor>(
        		new SparkMotor(ElectricalConfig.ball_shooter_motor), 
        		ElectricalConfig.ball_shooter_encoder_channel_a, 
        		ElectricalConfig.ball_shooter_encoder_channel_b);*/
        gamePad = pad;
        //shooter.initRPMThread(Queue);
        motor = new SparkMotor(ElectricalConfig.ball_shooter_motor);
	}
	
	public void update(float DistanceSensorData) {
    	if (gamePad.buttons.BUTTON_A.isOn()) {
    		//shoot(DistanceSensorData); 
    		//shooter.getMotor().getSpark().set(1);
    		//shooter.shoot(100);
    		motor.setPower(1);
    		
    	}
    	else if (!gamePad.buttons.BUTTON_A.isOn()) {
    		motor.setPower(0);
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
	
	public BallShooter<SparkMotor> getBallShooter() {
		return shooter;
	}
}
