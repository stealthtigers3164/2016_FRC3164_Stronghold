package org.usfirst.frc.team3164.robot.movement;

import org.usfirst.frc.team3164.robot.electrical.ElectricalConfig;
import org.usfirst.frc.team3164.robot.electrical.motor.SparkMotor;
import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.thread.ThreadQueue;
import org.usfirst.frc.team3164.robot.thread.WorkerThread;
import org.usfirst.frc.team3164.robot.vision.GoalAlign;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel {
	
	private SparkMotor shooter;
	private Gamepad gamePad;
	
	public FlyWheel(ThreadQueue<WorkerThread> Queue, Gamepad pad) {
        /*shooter = new BallShooter<SparkMotor>(
        		new SparkMotor(ElectricalConfig.ball_shooter_motor), 
        		ElectricalConfig.ball_shooter_encoder_channel_a, 
        		ElectricalConfig.ball_shooter_encoder_channel_b);*/
        gamePad = pad;
        //shooter.initRPMThread(Queue);
        shooter = new SparkMotor(ElectricalConfig.ball_shooter_motor);
	}
	
	public void update(GoalAlign alignment) {
		double shooterPower;
		if(SmartDashboard.getBoolean("Flywheel Testing Mode") == false) {
			shooterPower = 1;
		} else {
			shooterPower = SmartDashboard.getNumber("Flywheel Testing Power");
		}
    	if (gamePad.buttons.BUTTON_A.isOn()) {
    		//shoot(DistanceSensorData); 
    		//shooter.getMotor().getSpark().set(1);
    		//shooter.shoot(100);s
    		//shooter.setPower(getPowerFromDistance(alignment.imageHeight, alignment.centerY, alignment.height));
    		shooter.setPower(shooterPower);
    		
    	}
    	else if (!gamePad.buttons.BUTTON_A.isOn()) {
    		shooter.setPower(0);
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
	
	public SparkMotor getBallShooter() {
		return shooter;
	}
	
	public double getPowerFromDistance(double hy, double y, double rectangleHeight) {
		double distance = getPowerFromDistance(hy, y, rectangleHeight);
		SmartDashboard.putNumber("Distance from bottom of rectangle grip picture", distance);
		return 1;
	}
	
	public double getDistFromBottom(double hy, double y, double rectangleHeight) {
		return hy - y - (rectangleHeight / 2);
	}
}
