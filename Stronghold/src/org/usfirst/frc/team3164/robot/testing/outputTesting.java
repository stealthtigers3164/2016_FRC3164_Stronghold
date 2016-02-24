package org.usfirst.frc.team3164.robot.testing;

import org.usfirst.frc.team3164.robot.input.Gamepad;
import org.usfirst.frc.team3164.robot.electrical.ElectricalConfig;
import org.usfirst.frc.team3164.robot.electrical.LimitSwitch;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class outputTesting {
	private Gamepad gamePad;
	private AnalogInput sensorRange;
	private LimitSwitch limitSwitch;
	
	public outputTesting(Gamepad Pad) {
		gamePad = Pad;
		sensorRange = new AnalogInput(ElectricalConfig.analog_ultrasonic_port);
	}
	
	public void update() {
		
		gamepadTest();
		analogTest();
		limitSwitchTest();
		
		
	}
	
	private void gamepadTest() {
		if(gamePad.buttons.BUTTON_BACK.isOn()) {
    		gamePad.rumble.rumbleLeft(1);
    	} else {
    		gamePad.rumble.stop();
    	}
		SmartDashboard.putString("isXbox", gamePad.jstick.getName());
    	SmartDashboard.putBoolean("ButtonPressed", gamePad.jstick.getRawButton((int)SmartDashboard.getNumber("buttonPort")));
    	
	}
	
	private void analogTest() {
		SmartDashboard.putNumber("analog", sensorRange.getAverageVoltage());
	}
	
	private void limitSwitchTest() {
		SmartDashboard.putBoolean("Limit Switch Pressed", limitSwitch.isPressed());
	}
}
