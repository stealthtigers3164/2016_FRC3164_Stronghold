package org.usfirst.frc.team3164.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controller {
	public Joystick jstick;
	/**
	 * Controller button wrapper
	 */
	public FTCButtons buttons;
	/**
	 * Controller stick wrapper
	 */
	public FTCAxes sticks;
	/**
	 * Controller trigger wrapper
	 */
	public FTCTriggers trigger;
	/**
	 * Controller tophat wrapper
	 */
	public TopHat tophat;
	
	/**
	 * Controller constructor
	 * @param port The port of the controller
	 */
	public Controller(int port) {
		this.jstick = new Joystick(port);
		this.buttons = new FTCButtons();
		this.sticks = new FTCAxes();
		this.trigger = new FTCTriggers(2, 3); //3
		this.tophat = new TopHat(4, 5);
	}
	
	/**
	 * Buttons on the FTC Controller
	 * @author jaxon
	 *
	 */
	public class FTCButtons {
		public Button BUTTON_A = new Button(2);
		public Button BUTTON_B = new Button(3);
		public Button BUTTON_X = new Button(1);
		public Button BUTTON_Y = new Button(4);
		public Button BUTTON_LB = new Button(5);
		public Button BUTTON_RB = new Button(6);
		public Button BUTTON_LT = new Button(7);
		public Button BUTTON_RT = new Button(8);
		public Button BUTTON_BACK = new Button(9);
		public Button BUTTON_START = new Button(10);
		public Button LEFT_STICK_BUTTON = new Button(11);
		public Button RIGHT_STICK_BUTTON = new Button(12);
	}
	
	/**
	 * Sticks on the FTC Controller
	 * @author jaxon
	 *
	 */
	public class FTCAxes {
		public LeftRightAxis LEFT_STICK_X = new LeftRightAxis(0);
		public UpDownAxis LEFT_STICK_Y = new UpDownAxis(1);
		public LeftRightAxis RIGHT_STICK_X = new LeftRightAxis(4);
		public UpDownAxis RIGHT_STICK_Y = new UpDownAxis(5);
		public LeftRightAxis RIGHT_TRIGGER = new LeftRightAxis(3);
		public UpDownAxis LEFT_TRIGGER = new UpDownAxis(2);
		
	}
	
	/**
	 * Triggers on the FTC Controller
	 * @author jaxon
	 *
	 */
	public class FTCTriggers {
		private int leftPort;
		private int rightPort;
		public FTCTriggers(int left, int right) {
			this.leftPort = left;
			this.rightPort = right;
			
		}
		/**
		 * Get the value of the triggers
		 * @return value of the triggers
		 */
		public double getLeftVal() {
			return jstick.getRawAxis(leftPort);
		}
		public double getRightVal() {
			return jstick.getRawAxis(rightPort);
		}
		public double getCombineVal() {
			return ((jstick.getRawAxis(rightPort) - jstick.getRawAxis(leftPort)));
		}
		public double getCombineValScaled() {
			double val = (jstick.getRawAxis(rightPort) - jstick.getRawAxis(leftPort));
			if(Math.abs(val)<=0.1) return 0;
			return (Math.abs(val) - 0.1)/0.9 * Math.signum(val);
		}
	}
	
	/**
	 * Class to read an axis accuating left and right
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
		 * get direction of axis
		 * @return enum dir of axis
		 */
		public LeftRightDir getDirection() {
			return getRaw() >=0 ? LeftRightDir.RIGHT : LeftRightDir.LEFT;
		}
		/**
		 * returns the intenity (absolute val) of the 
		 * @return [0, 1]
		 */
		public double getIntensity() {
			return Math.abs(getRaw());
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
		 * Gets the direction, up or down
		 * @return enum value, up or down
		 */
		public UpDownDir getDirection() {
			return getRaw() >=0 ? UpDownDir.DOWN : UpDownDir.UP;
		}
		/**
		 * gets the intensity of the axis
		 * @return [0, 1]
		 */
		public double getIntensity() {
			return Math.abs(getRaw());
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
	public class TopHat {
		private int lrPort;
		private int udPort;
		/**
		 * Sets up new top hat
		 * @param lrPort port of the left/right axis
		 * @param udPort port of the up/down axis
		 */
		public TopHat(int lrPort, int udPort) {
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
	 * @author jaxon
	 *
	 */
	public class JoystickAxis {
		private int port;
		/**
		 * Set up a joystick axis
		 * @param port the port
		 */
		public JoystickAxis(int port) {
			this.port = port;
		}
		/**
		 * Get raw value of jstick
		 * @return raw value of stick, [-1, 1]
		 */
		public double getRaw() {
			return jstick.getRawAxis(port);
			//return Math.abs(jstick.getRawAxis(port))>=0.1 ? jstick.getRawAxis(port) : 0;
		}
		
		public double getScaled() {
			if(Math.abs(jstick.getRawAxis(port))<=0.1) return 0;
			return (Math.abs(jstick.getRawAxis(port)) - 0.1)/0.9 * Math.signum(jstick.getRawAxis(port));
			
		}
	}
}
