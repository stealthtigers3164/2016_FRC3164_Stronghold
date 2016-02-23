package org.usfirst.frc.team3164.robot.testing;

import org.usfirst.frc.team3164.robot.input.Gamepad;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class outputTesting {
	private Gamepad gamePad;
	
	public outputTesting(Gamepad Pad) {
		gamePad = Pad;
	}
	
	public void update() {
		
		gamepadTest();
		
		
		
	}
	
	private void gamepadTest() {
		if(gamePad.buttons.BUTTON_BACK.isOn()) {
    		gamePad.rumble.rumbleLeft(1);
    	} else {
    		gamePad.rumble.stop();
    	}
		SmartDashboard.putString("isXbox", gamePad1.jstick.getName());
    	SmartDashboard.putBoolean("ButtonPressed", gamePad1.jstick.getRawButton((int)SmartDashboard.getNumber("buttonPort")));
    	
	}
}
