package net.ildoo.app;

public class TimerLogger {
	private static long time;
	
	public static void log(String tag, String msg) {
		long curr = System.currentTimeMillis();
		
		System.out.println((new StringBuffer())
				.append((curr - time)/1000.0)
				.append('\t')
				.append(tag)
				.append('\t')
				.append(msg));
		
		time = curr;
	}
}
