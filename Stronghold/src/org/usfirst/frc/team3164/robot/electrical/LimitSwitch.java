package org.usfirst.frc.team3164.robot.electrical;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimitSwitch {
	private AnalogInput lin;
	
	public LimitSwitch(final int port) {
		lin = new AnalogInput(port);
	}
	
	/**
	 * Gets if the limit switch is pressed.
	 * @return true if the limit switch is pressed.
	 */
	public boolean isPressed() {
		SmartDashboard.putNumber("Limit Switch", lin.getValue());
		return lin.getValue() > 500;//TODO The NOT is because all of our limit swithches are reversed/
	}
}
