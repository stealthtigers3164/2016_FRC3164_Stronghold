package org.usfirst.frc.team3164.robot.electrical;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch {
	private DigitalInput lin;
	
	public LimitSwitch(final int port) {
		lin = new DigitalInput(port);
	}
	
	/**
	 * Gets if the limit switch is pressed.
	 * @return true if the limit switch is pressed.
	 */
	public boolean isPressed() {
		return lin.get();//TODO The NOT is because all of our limit swithches are reversed/
	}
}
