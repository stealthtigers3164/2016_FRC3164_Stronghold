package org.usfirst.frc.team3164.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gamepad {
	public Joystick jstick;
	/**
	 * Controller button wrapper
	 */
	public gamepadButtons buttons;
	/**
	 * Controller stick wrapper
	 */
	public gamepadAxes sticks;
	/**
	 * Controller trigger wrapper
	 */
	public gamepadTriggers trigger;
	/**
	 * Controller tophat wrapper
	 */
	public gamepadDPad tophat;
	/**
	 * Rumble motor wrapper
	 */
	public gamepadRumble rumble;
	
	/**
	 * Controller constructor
	 * @param port The port of the controller
	 */
	public Gamepad(int port) {
		this.jstick = new Joystick(port);
		this.buttons = new gamepadButtons();
		this.sticks = new gamepadAxes();
		this.trigger = new gamepadTriggers(2, 3);
		this.tophat = new gamepadDPad(4, 5);
		this.rumble = new gamepadRumble();
	}
	
	/**
	 * Buttons on the FTC Controller
	 * @author jaxon
	 *
	 */
	public class gamepadButtons {
		public Button BUTTON_A = new Button(1);
		public Button BUTTON_B = new Button(2);
		public Button BUTTON_X = new Button(3);
		public Button BUTTON_Y = new Button(4);
		public Button BUTTON_LB = new Button(5);
		public Button BUTTON_RB = new Button(6);
		public Button BUTTON_LT = new Button(5);
		public Button BUTTON_RT = new Button(6);
		public Button BUTTON_BACK = new Button(7);
		public Button BUTTON_START = new Button(8);
		public Button LEFT_STICK_BUTTON = new Button(9);
		public Button RIGHT_STICK_BUTTON = new Button(10);
	}
	
	/**
	 * Sticks on the FTC Controller
	 * Mapped for Logitech 430
	 * @author jaxon, William Y
	 *
	 */
	public class gamepadAxes {
		public LeftRightAxis LEFT_X = new LeftRightAxis(0);
		public UpDownAxis LEFT_Y = new UpDownAxis(1, true);
		public LeftRightAxis RIGHT_X = new LeftRightAxis(4);
		public UpDownAxis RIGHT_Y = new UpDownAxis(5, true);
		
		/**
		 * Sets the deadzone to the greatest (X or Y) value for each axis of each stick
		 * Caps at 0.15 in case this is called when joysticks are being used
		 * Since it reads the raw input, only call this from robot initialization, and make sure the joysticks are not being used
		 */
		public void setDeadzones() {
			this.LEFT_X.setDeadzone();
			this.LEFT_Y.setDeadzone();
			this.RIGHT_X.setDeadzone();
			this.RIGHT_Y.setDeadzone();
		}
		
		/**
		 * Sets the deadzone of each axis of each joystick on the gamepad to the specified value
		 * Caps at 0.15 for safety, buy new gamepad if it needs to be higher 
		 * @param dz Deadzone value [0, 0.15]
		 */
		public void setDeadzones(double dz) {
			this.LEFT_X.setDeadzone(dz);
			this.LEFT_Y.setDeadzone(dz);
			this.RIGHT_X.setDeadzone(dz);
			this.RIGHT_Y.setDeadzone(dz);
		}
		
	}
	
	/**
	 * Triggers on the FTC Controller
	 * @author jaxon, William Y
	 *
	 */
	public class gamepadTriggers {
		private int leftPort;
		private int rightPort;
		public gamepadTriggers(int left, int right) {
			this.leftPort = left;
			this.rightPort = right;
			
		}
		
		/**
		 * Get the left trigger's value
		 * @return value of left trigger [0,1]
		 */
		public double getLeftVal() {
			return jstick.getRawAxis(leftPort);
		}
		
		/**
		 * Get the right trigger's value
		 * @return value of the right trigger [0,1]
		 */
		public double getRightVal() {
			return jstick.getRawAxis(rightPort);
		}
		
		/**
		 * Get the triggers acting as an axis
		 * Left trigger is negative, right trigger is positive
		 * Values cancel each other out, returns net value
		 * Ex. Both pressed down = 1 - 1 = 0
		 * @return value of the trigger [-1, 1]
		 */
		public double getAxis() {
			return ((jstick.getRawAxis(rightPort) - jstick.getRawAxis(leftPort)));
		}
		
		/**
		 * get the net direction of the axis
		 * defaults right if zero
		 * @return enum dir of axis
		 */
		public LeftRightDir getDirection() {
			return (this.getAxis() >=0 ? LeftRightDir.RIGHT : LeftRightDir.LEFT);
		}
		
		/**
		 * Treats left trigger as a button
		 * Full press requires the trigger to be => 0.9
		 * Not full press activates => 0.2
		 * @param full true if trigger needs to be pressed completely
		 * @return true is trigger exceeds a certain value
		 */
		public boolean getLeftPressed(boolean full) {
			return (this.getLeftVal() >= (full ? 0.9 : 0.2) ? true : false);
		}
		
		/**
		 * Treats right trigger as a button
		 * Full press requires the trigger to be => 0.9
		 * Not full press activates => 0.2
		 * @param full true if trigger needs to be pressed completely
		 * @return true is trigger exceeds a certain value
		 */
		public boolean getRightPressed(boolean full) {
			return (this.getRightVal() >= (full ? 0.9 : 0.2) ? true : false);
		}
	}
	
	/**
	 * Class to read an axis actuating left and right
	 * @author jaxon
	 *
	 */
	public class LeftRightAxis extends JoystickAxis {
		/**
		 * Set up an axis that can be read
		 * @param port port of axis
		 */
		public LeftRightAxis(int port) {
			super(port);
		}
		
		/**
		 * Set up an axis that can be read
		 * @param port port of axis
		 * @param rev set true to flip values (mainly for Y axis)
		 */
		public LeftRightAxis(int port, boolean rev) {
			super(port, rev);
		}
		
		/**
		 * get direction of axis
		 * defaults right if zero
		 * @return enum dir of axis
		 */
		public LeftRightDir getDirection() {
			return (this.getRaw() >=0 ? LeftRightDir.RIGHT : LeftRightDir.LEFT);
		}
		
		/**
		 * Gives the intensity of the axis (not scaled)
		 * Just the absolute value of getRaw()
		 * @return [0, 1]
		 */
		public double getIntensity() {
			return Math.abs(this.getRaw());
		}
	}
	/**
	 * Left or right?
	 * @author jaxon
	 *
	 */
	public enum LeftRightDir {
		LEFT(),
		RIGHT();
	}
	
	/**
	 * Class reading the value of an axis that goes up and down
	 */
	public class UpDownAxis extends JoystickAxis {
		/**
		 * Creates new axis reader
		 * @param port port of the axis
		 */
		public UpDownAxis(int port) {
			super(port);
		}
		
		/**
		 * Creates new axis reader
		 * @param port port of the axis
		 * @param rev set true to flip the values (for Y axis)
		 */
		public UpDownAxis(int port, boolean rev) {
			super(port, rev);
		}
		
		/**
		 * Gets the direction, up or down
		 * Defaults up
		 * @return enum value, up or down
		 */
		public UpDownDir getDirection() {
			return (this.getRaw() >=0 ? UpDownDir.UP : UpDownDir.DOWN);
		}
		
		
		
		/**
		 * Gives the intensity of the axis (not scaled)
		 * Just the absolute value of getRaw()
		 * @return [0, 1]
		 */
		public double getIntensity() {
			return Math.abs(this.getRaw());
		}
	}
	/**
	 * Up or down?
	 * @author jaxon
	 *
	 */
	public enum UpDownDir {
		UP(),
		DOWN();
	}
	
	/**
	 * Top hat wrapper
	 * @author jaxon
	 *
	 */
	public class gamepadDPad {
		private int lrPort;
		private int udPort;
		/**
		 * Sets up new top hat
		 * @param lrPort port of the left/right axis
		 * @param udPort port of the up/down axis
		 */
		public gamepadDPad(int lrPort, int udPort) {
			this.lrPort = lrPort;
			this.udPort = udPort;
		}
		/**
		 * Gets raw of up and down axis.
		 * @return -1, 0, or 1
		 */
		public int getUpDownRaw() {
			return (int) jstick.getRawAxis(udPort);
		}
		/**
		 * Gets raw of left right axis,
		 * @return -1, 0, or 1
		 */
		public int getLeftRightRaw() {
			return (int) jstick.getRawAxis(lrPort);
		}
		/**
		 * Gets direction of the tophat
		 * @return Top hat enum of direction
		 */
		public TopHatDir getDir() {
			switch(getUpDownRaw()) {
			case 1:
				switch(getLeftRightRaw()) {
				case 1:
					return TopHatDir.DOWN_RIGHT;
				case 0:
					return TopHatDir.DOWN;
				case -1:
					return TopHatDir.DOWN_LEFT;
				}
			case 0:
				switch(getLeftRightRaw()) {
				case 1:
					return TopHatDir.RIGHT;
				case 0:
					return TopHatDir.NONE;
				case -1:
					return TopHatDir.LEFT;
				}
			case -1:
				switch(getLeftRightRaw()) {
				case 1:
					return TopHatDir.UP_RIGHT;
				case 0:
					return TopHatDir.UP;
				case -1:
					return TopHatDir.UP_LEFT;
				}
			}
			return TopHatDir.NONE;
		}
	}
	/**
	 * Direction of top hat
	 * @author jaxon
	 *
	 */
	public enum TopHatDir {
		NONE(),
		UP(),
		DOWN(),
		LEFT(),
		RIGHT(),
		UP_LEFT(),
		UP_RIGHT(),
		DOWN_LEFT(),
		DOWN_RIGHT();
	}
	/**
	 * Button wrapper
	 * @author jaxon
	 *
	 */
	public class Button {
		private int port;
		/**
		 * Set up button
		 * @param port using port id
		 */
		public Button(int port) {
			this.port = port;
		}
		/**
		 * Is the button being pressed?
		 * @return true if button is pressed
		 */
		public boolean isOn() {
			return jstick.getRawButton(port);
		}
	}
	
	/**
	 * Joystick wrapper
	 * @author jaxon, William Y
	 *
	 */
	public class JoystickAxis {
		private int port;
		private double deadzone;
		private boolean reversed;
		/**
		 * Set up a joystick axis
		 * @param port the port
		 * @param dz deadzone default 0.1
		 */
		public JoystickAxis(int port) {
			this.port = port;
			this.deadzone = 0.1;
			this.reversed = false;
		}
		
		/**
		 * Set up a joystick axis
		 * @param port the port
		 * @param rev Reverse the joystick (for Y axis)
		 */
		public JoystickAxis(int port, boolean rev) {
			this.port = port;
			this.deadzone = 0.1;
			this.reversed = rev;
			
		}
		/**
		 * Get raw value of joystick (not changed)
		 * @return raw value of stick, [-1, 1]
		 */
		public double getRaw() {
			return jstick.getRawAxis(port) * (this.reversed ? -1 : 1);
		}
		
		/**
		 * Get the joystick's value scaled
		 * @return an axis value, [-1, 1]
		 */
		public double getScaled() {
			if(Math.abs(this.getRaw()) <= this.deadzone) return 0;
			return (Math.abs(this.getRaw()) - this.deadzone)/(1 - this.deadzone) * Math.signum(this.getRaw());
			
		}
		
		/**
		 * Sets the deadzone to the greatest (X or Y) value
		 * Caps at 0.15 in case this is called when joysticks are being used
		 * Since it reads the raw input, only call this from robot initialization, and make sure the joysticks are not being used
		 */
		public void setDeadzone() {
			this.deadzone = Math.min(Math.abs(this.getRaw()) + 0.01, 0.15);
		}
		
		/**
		 * Sets the deadzone to sent value
		 * Caps at 0.15, should not need to be more
		 * @param dz Deadzone value [0, 0.15]
		 */
		public void setDeadzone(double dz) {
			this.deadzone = Math.min(Math.abs(dz), 0.15);
		}
		
		/**
		 * Returns whether the axis is reversed or not
		 * @return true if reversed
		 */
		public boolean isReversed() {
			return this.reversed;
		}
	}
	
	public class gamepadRumble {
		
		/**
		 * To vibrate the left side of the joystick
		 * @param power [0,1]
		 */
		public void rumbleLeft(float power) {
			jstick.setRumble(Joystick.RumbleType.kLeftRumble, power);
		}
		
		/**
		 * To vibrate the right side of the joystick
		 * @param power [0,1]
		 */
		public void rumbleRight(float power) {
			jstick.setRumble(Joystick.RumbleType.kRightRumble, power);
		}
		
		/**
		 * To vibrate the both sides of the joystick
		 * @param power [0,1]
		 */
		public void rumble(float power) {
			this.rumbleLeft(power);
			this.rumbleRight(power);
		}
		
		/**
		 * To vibrate the both sides of the joystick
		 * @param power [0,1]
		 */
		public void stop() {
			this.rumbleLeft(0);
			this.rumbleRight(0);
		}
		
	}
}
