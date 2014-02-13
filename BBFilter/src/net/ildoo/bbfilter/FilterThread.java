package net.ildoo.bbfilter;

public class FilterThread extends Thread {
	private static FilterThread worker;
	
	public static FilterThread getCurrentWorker() {
		return worker;
	}
	
	
	
	private volatile boolean isCanceled;
	
	public FilterThread() {
		if (worker != null && worker.isAlive()) {
			worker.cancel();
		}
	}
	
	public void cancel() {
		this.isCanceled = true;
	}
	
	public boolean isCanceled() {
		return isCanceled;
	}
	
}
