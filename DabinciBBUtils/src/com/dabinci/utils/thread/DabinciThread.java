package com.dabinci.utils.thread;

import java.util.Vector;

import com.dabinci.utils.DLogger;

public class DabinciThread extends Thread {
	private static final String TAG = "DabinciThread";
	private final Vector runnables = new Vector();
	
	public DabinciThread() {
	}

	public void addRunnable(Runnable runnable) {
		synchronized(runnables) {
			runnables.addElement(runnable);
		}
	}
	
	public void removeRunnable(Runnable runnable) {
		synchronized(runnables) {
			runnables.removeElement(runnable);
		}
	}
	
	public void run() {
		while (true) {
			Runnable runnable = null;
			synchronized (runnables) {
				if (runnables.isEmpty())
					break;
				
				runnable = (Runnable) runnables.firstElement();
				runnables.removeElementAt(0);
			} // end synchronized
			
			if (runnable != null) {
				try {
					runnable.run();
				} catch (Exception e) {
					DLogger.log(TAG, e.toString());
				}
			}
		} // end while
	}
	
}
