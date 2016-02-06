package org.usfirst.frc.team3164.robot.comms;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;;

public class Watchcat {
	private static int timeout;//In ms
	private static Thread wdTrd;
	private static WDTask wdTsk;
	public static boolean isRobotDead = false;
	private static long last;
	private static MotorRegistry mReg;
	
	public static void init() {
		timeout = 200;
		last = new Date().getTime();
		mReg = new MotorRegistry();
		wdTsk = new WDTask();
		wdTrd = new Thread(wdTsk);
		//wdTrd.start();
	}
	
	private static class WDTask implements Runnable {
		@Override
		public void run() {
			while(true) {
				long now = new Date().getTime();
				if(last+timeout <= now) {
					killRobot();
				}
				try {
					Thread.sleep(10);
				} catch(Exception ex) {}
			}
		}
	}
	
	public static class MotorRegistry {
		private Map<UUID, BasicMotor> motors = new HashMap<UUID, BasicMotor>();
		
		public void addMotor(BasicMotor m) {
			if(!isHere(m)) {
				motors.put(UUID.randomUUID(), m);
			}
		}
		
		private boolean isHere(BasicMotor m) {
			for(BasicMotor mo : motors.values()) {
				if(m.getLoc() == mo.getLoc()) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static void killRobot() {
		if(isRobotDead)
			return;
		isRobotDead = true;
		for(BasicMotor m : mReg.motors.values()) {
			m.setDead(true);
		}
	}
	
	public static void reviveRobot() {
		if(!isRobotDead)
			return;
		isRobotDead = false;
		for(BasicMotor m : mReg.motors.values()) {
			m.setDead(false);
		}
	}
	
	public static void feed() {
		last = new Date().getTime();
		reviveRobot();
	}
	
	public static void registerMotor(BasicMotor m) {
		mReg.addMotor(m);
	}
}