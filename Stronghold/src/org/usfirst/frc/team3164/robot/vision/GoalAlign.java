package org.usfirst.frc.team3164.robot.vision;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
public class GoalAlign {

	private /*final*/ NetworkTable grip = NetworkTable.getTable("grip");
	private String reportName;
	public double area;
	public double centerX;
	public double centerY;
	public double width;
	public double height;
	public double solidity;
	
	public GoalAlign(String ContourReportName) {
		this.reportName = ContourReportName;//GoalContours i think
	}
	
	public updateByLargestArea() {
		int largestAreaYet = 0;
		for (double blobArea : grip.getNumberArray(reportName + "/area", new double[0])) {
			if(largestAreaYet < area)largestAreaYet = blobArea;
		}
	}
	
}
