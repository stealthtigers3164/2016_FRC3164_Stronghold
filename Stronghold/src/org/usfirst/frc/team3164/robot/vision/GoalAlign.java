package org.usfirst.frc.team3164.robot.vision;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
public class GoalAlign {

	private NetworkTable grip = NetworkTable.getTable("grip");
	private String reportName;
	public double area;
	public double centerX;
	public double centerY;
	public double width;
	public double height;
	public double solidity;
	
	private double imageHeight = 0.75;
	private double imageWidth = 0.75;
	
	public GoalAlign(String ContourReportName) {
		this.reportName = ContourReportName;//GoalContours i think
	}
	
	public void updateByLargestArea() {
		int largestIndex = 0;
		double[] arrayArea = grip.getNumberArray(reportName + "/area", new double[0]);
		for (int i = 0; i < arrayArea.length; i++) {
			if(arrayArea[i] > arrayArea[largestIndex])largestIndex = i;
		}
		this.area		= grip.getNumberArray(reportName + "/area", new double[0])[largestIndex];
		this.centerX	= grip.getNumberArray(reportName + "/centerX", new double[0])[largestIndex];
		this.centerY	= grip.getNumberArray(reportName + "/centerY", new double[0])[largestIndex];
		this.width		= grip.getNumberArray(reportName + "/width", new double[0])[largestIndex];
		this.height		= grip.getNumberArray(reportName + "/height", new double[0])[largestIndex];
		this.solidity	= grip.getNumberArray(reportName + "/solidity", new double[0])[largestIndex];
	}
	
	public double getHorizontalDistanceFromCenter() {
		return 0.5 * (imageWidth - centerX);
	}
	
	public double getVerticalDistanceFromCenter() {
		return 0.5 * (imageHeight - centerY);
	}
	
}
