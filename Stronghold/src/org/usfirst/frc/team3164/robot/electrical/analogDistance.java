package org.usfirst.frc.team3164.robot.electrical;

import edu.wpi.first.wpilibj.AnalogInput;

public class analogDistance {

	private AnalogInput distSensor;
	private double distance = 0;
	/**
	 * Analog distance sensor class, setup for MaxBotix HRLV 1013 Ultrasonic Sensor
	 * @param port analog port
	 */
	analogDistance(int port) {
		this.distSensor = new AnalogInput(port);
		
	}
	
	/**
	 * A scaled voltage value (determined by sensor not by us)
	 * @return
	 */
	public double getVoltage() {
		return this.distSensor.getVoltage();
	}
	
	/**
	 * A scaled voltage value, determined by code
	 * Oversamples, more stable
	 * @return
	 */
	public double getVoltageAvg() {
		return this.distSensor.getAverageVoltage();
	}
	
	/**
	 * Returns distance in meters
	 * @return
	 */
	public double getDistance() {
		return this.distance;
	}
}
