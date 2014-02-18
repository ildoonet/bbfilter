package net.ildoo.bbfilter;

public class FilterThread extends Thread {
	private static FilterThread worker;
	
	public static FilterThread getCurrentWorker() {
		return worker;
	}
	
	private volatile boolean isCanceled;
	
	public FilterThread() {
		this(true);
	}
	
	public FilterThread(boolean cancelPrevThread) {
		if (worker != null && worker.isAlive()) {
			worker.cancel();
		}
	}
	
	public void cancel() {
		this.isCanceled = true;
		this.interrupt();
	}
	
	public boolean isCanceled() {
		return isCanceled;
	}
	
}
