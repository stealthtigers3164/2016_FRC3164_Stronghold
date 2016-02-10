package org.usfirst.frc.team3164.robot.thread;

import java.util.ArrayList;

public class ThreadQueue<T extends WorkerThread> {

	private ArrayList<T> threads;
	private ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();

	public ThreadQueue() {
		threads = new ArrayList<T>();
	}

	public void update() {
		if (threads.size() > 0) {
			for (T thread : threads) {
				if (!thread.isStarted()) {
					thread.startThread();
				}
				if (thread.isFinished()) {

				}
			}
		}

		if (indicesToRemove.size() > 0) {
			for (Integer indexToRemove : indicesToRemove) {
				removeThread(indexToRemove);
			}
			indicesToRemove.clear();
		}
	}

	public int add(T Thread, boolean StartOnAdd) {
		if (StartOnAdd && !Thread.isStarted()) {
			Thread.startThread();
		}

		int index = -1;
		
		if (threads.add(Thread))
			index = threads.size() - 1;
		
		Thread.setIndex(index);
		
		return index;
	}

	public boolean removeThread(T Thread) {
		Thread.setIndex(-1);
		return threads.remove(Thread);
	}

	public boolean removeThread(int index) {
		return removeThread(getThreadByIndex(index));
	}

	public T getThreadByIndex(int index) {
		return threads.get(index);
	}

	public ArrayList<T> getThreads() {
		return threads;
	}
}
