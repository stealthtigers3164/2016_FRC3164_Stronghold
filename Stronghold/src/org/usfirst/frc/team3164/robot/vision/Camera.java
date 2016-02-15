package org.usfirst.frc.team3164.robot.vision;

import java.util.HashMap;

import org.usfirst.frc.team3164.robot.electrical.motor.MotorSet;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Camera
{
	private HashMap<String, NetworkTable> networkTables;
	private HashMap<String, MotorSet> networkTableMotors;
	
	public Camera() {
		
	}
	
	public void Update() {
		if (networkTableMotors.size() > 0) {
			for (String CurrentKey : networkTableMotors.keySet()) {
				MotorSet Set = getMotorSetByKey(CurrentKey);
				if (Set.shouldUpdate())
					Set.updateMotors();
			}
		}
	}
	
	public void addNetworkTable(String KEY_TO_FIND_IN_MAP, String TableKey, MotorSet motors) {
		networkTables.put(KEY_TO_FIND_IN_MAP, NetworkTable.getTable(TableKey));
		networkTableMotors.put(KEY_TO_FIND_IN_MAP, motors);
	}
	
	public NetworkTable getNetworkTableByKey(String KEY_TO_FIND_IN_MAP) {
		return networkTables.get(KEY_TO_FIND_IN_MAP);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends MotorSet> T getMotorSetByKey(String KEY_TO_FIND_IN_MAP) {
		return (T)networkTableMotors.get(KEY_TO_FIND_IN_MAP);
	}
}
