package org.usfirst.frc.team3164.robot.thread;

public class WorkerThread extends BasicThread {

	private boolean isRunning;
	private boolean runOnce;
	private boolean isThreadStarted;
	private ThreadMethod method;
	
	private int index;
	
	public WorkerThread(boolean runOnce, String Name) {
		this.runOnce = runOnce;
		index = -1;
		isThreadStarted = false;
		setThreadName(Name);
		if (runOnce) {
			isRunning = false;
		}
		else {
			isRunning = true;
		}
	}
	
	public void startThread() {
		isThreadStarted = true;
		start();
	}
	
	public void setThreadMethod(ThreadMethod method) {
		this.method = method;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public void update() {
		method.run();
	}

	@Override
	public boolean isStarted() {
		return isThreadStarted;
	}
	
	@Override
	public boolean isRunning() {
		return (runOnce) ? false : isRunning;
	}

	@Override
	public boolean runOnce() {
		return runOnce;
	}
	
}
