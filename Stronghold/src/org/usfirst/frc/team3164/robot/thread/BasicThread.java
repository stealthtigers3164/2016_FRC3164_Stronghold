package org.usfirst.frc.team3164.robot.thread;

import java.util.concurrent.locks.ReentrantLock;

public abstract class BasicThread extends Thread {

	public abstract void update();
	public abstract boolean isRunning();
	public abstract boolean runOnce();
	public abstract boolean isStarted();
	public abstract int getIndex();
	public abstract void setIndex(int index);
	
	private ReentrantLock threadLock = new ReentrantLock();
	private boolean hasFinished = false;
	
	@Override 
	public void run() {
		
		if (runOnce())
			update();
		else 
			while (isRunning()) 
				update();	
		hasFinished = true;
	}
		
	public boolean isFinished() {
		return hasFinished;
	}
	
	public void lockThread() {
		threadLock.lock();
	}
	
	public void unLockThread() {
		threadLock.unlock();
	}
	
	public boolean isThreadLocked() {
		return threadLock.isLocked();
	}
	
	public void setThreadName(String Name) {
		setName(Name);
	}
	
	public String getThreadName() {
		return getName();
	}
}
