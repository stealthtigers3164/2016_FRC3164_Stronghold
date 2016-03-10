package org.usfirst.frc.team3164.robot.vision;

import java.util.Date;

import org.usfirst.frc.team3164.robot.electrical.motor.BasicMotor;
import org.usfirst.frc.team3164.robot.movement.DriveTrain;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GoalAlign {

	private NetworkTable grip = NetworkTable.getTable("GRIP");
	private String reportName;
	public double area;
	public double centerX;
	public double centerY;
	public double width;
	public double height;
	public double solidity;

	private double imageHeight = 240;
	private double imageWidth = 320;
	
	private long lastUpdate;
	private int timeout = 300;//Might need to change
	
	private boolean update = false;
	
	public GoalAlign(String ContourReportName) {
		this.reportName = ContourReportName;// GoalContours i think
		this.lastUpdate = new Date().getTime();
		
	}

	public <T extends BasicMotor> void update(DriveTrain<T> drive)
	{
		updateByLargestArea();
		
		SmartDashboard.putBoolean("TurningLeft", update && centerX > 0 ? (centerX < imageWidth / 2) : false);
		SmartDashboard.putBoolean("TurningRight", update && centerX > 0 ? (centerX > imageWidth / 2) : false);
		
		if (SmartDashboard.getBoolean("TurningLeft"))
			drive.setLeftPower(1);
		
		if (SmartDashboard.getBoolean("TurningRight"))
			drive.setRightPower(1);
	}
	
	public void updateByLargestArea() {

		
		try {
			double[] arrayArea = grip.getNumberArray(reportName + "/area", new double[0]);///?*******New Double might need to be taken out.
			SmartDashboard.putNumber("ARRAY LENGTH", arrayArea.length);
			if (arrayArea.length > 0) {
				update = true;
				int largestIndex = 0;
				for (int i = 0; i < arrayArea.length; i++) {
					if (arrayArea[i] > arrayArea[largestIndex])
						largestIndex = i;
				} 
				this.area = grip.getNumberArray(reportName + "/area", new double[0])[largestIndex];
				this.centerX = grip.getNumberArray(reportName + "/centerX", new double[0])[largestIndex];
				this.centerY = grip.getNumberArray(reportName + "/centerY", new double[0])[largestIndex];
				this.width = grip.getNumberArray(reportName + "/width", new double[0])[largestIndex];
				this.height = grip.getNumberArray(reportName + "/height", new double[0])[largestIndex];
				this.solidity = grip.getNumberArray(reportName + "/solidity", new double[0])[largestIndex];
				
				this.lastUpdate = new Date().getTime();
			} else {
				if((this.lastUpdate + this.timeout) <= (new Date().getTime())) {
					//Not tested, supposed to make sure it doesnt disappear for a split second.
					this.zeroVariables();
				}
				update = false;
			
			}
		} catch (Exception ex) {
			if((this.lastUpdate + this.timeout) <= (new Date().getTime())) {
				//Not tested, supposed to make sure it doesnt disappear for a split second.
				this.zeroVariables();
			}
		}
		
	}
	
	public double getHorizontalDistanceFromCenter() {
		/*return 0.5 * (imageWidth - centerX);//It might work, but trying something different*/
		//Returns positive values is box is to right of the robot
		if(centerX == 0) return 0;
		return (centerX - (imageWidth*0.5));
		/*
		If that doesnt work good try:
		return ((centerX/imageWidth*100) - 50);
		This returns a percent value, positive means box is to the right.
		
		*/
	}

	public double getVerticalDistanceFromCenter() {
		return 0.5 * (imageHeight - centerY);//Change to match a working horizontaldist
	}
	
	private void zeroVariables() {
		this.area = 0;
		this.centerX = 0;
		this.centerY = 0;
		this.width = 0;
		this.height = 0;
		this.solidity = 0;
	}
	//Log areas with distance from analog when shooting in a log file!!!!!!!!!!!!!

}
