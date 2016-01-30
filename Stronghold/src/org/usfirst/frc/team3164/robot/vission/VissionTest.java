package org.usfirst.frc.team3164.robot.vission;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.vision.USBCamera;

public class VissionTest {
	
	private USBCamera camera;
	
	public VissionTest() {
		camera = new USBCamera();
	}
	
	public void update() {
		Image Result = null;
		camera.getImage(Result);
		
	}
	
	public USBCamera getCamera() {
		return camera;
	}
}
