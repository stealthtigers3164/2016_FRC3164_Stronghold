package org.usfirst.frc.team3164.robot.vision;

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

	public GoalAlign(String ContourReportName) {
		this.reportName = ContourReportName;// GoalContours i think
	}

	public void updateByLargestArea() {

		double[] arrayArea = grip.getNumberArray(reportName + "/area");
		SmartDashboard.putNumber("ARRAY LENGTH", arrayArea.length);
		if (arrayArea.length > 0) {
			int largestIndex = 0;
			for (int i = 0; i < arrayArea.length; i++) {
				if (arrayArea[i] > arrayArea[largestIndex])
					largestIndex = i;
			} 
			this.area = grip.getNumberArray(reportName + "/area"/*, new double[0]*/)[largestIndex];
			this.centerX = grip.getNumberArray(reportName + "/centerX"/*, new double[0]*/)[largestIndex];
			this.centerY = grip.getNumberArray(reportName + "/centerY"/*, new double[0]*/)[largestIndex];
			this.width = grip.getNumberArray(reportName + "/width"/*, new double[0]*/)[largestIndex];
			this.height = grip.getNumberArray(reportName + "/height", new double[0])[largestIndex];
			this.solidity = grip.getNumberArray(reportName + "/solidity", new double[0])[largestIndex];
		} else {
			/*this.area = 0;
			this.centerX = 0;
			this.centerY = 0;
			this.width = 0;
			this.height = 0;
			this.solidity = 0;*/
			//TODO Make if missing for more than two seconds
		}
	}
	
	public double getHorizontalDistanceFromCenter() {
		/*return 0.5 * (imageWidth - centerX);//It might work, but trying something different*/
		//Returns positive values is box is to right of the robot
		return (centerX - (imageWidth*0.5));
		/*
		If that doesnt work good try:
		return ((centerX/imageWidth*100) - 50);
		This returns a percent value, positive means box is to the right.
		
		*/
	}

	public double getVerticalDistanceFromCenter() {
		return 0.5 * (imageHeight - centerY);
	}

}
