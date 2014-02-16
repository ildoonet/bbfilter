package com.dabinci.utils;

public class DLogger {
	private static final long FLUSH_INTERVAL = 100;
	
	private static long time;
	private static StringBuffer sb = new StringBuffer();
	private static long lastFlush = 0l;
	
	public static void tlog(String tag, String msg) {
		long curr = System.currentTimeMillis();

		sb.append((new StringBuffer())
				.append((curr - time)/1000.0)
				.append('\t')
				.append(tag)
				.append('\t')
				.append(msg))
			.append('\n');
		
		if (lastFlush + FLUSH_INTERVAL < System.currentTimeMillis()) {
			System.out.println(sb.toString());
			sb = new StringBuffer();
		}
		
		time = curr;
	}
	
	public static void log(String tag, String msg) {
		sb.append((new StringBuffer())
				.append(tag)
				.append('\t')
				.append(msg))
			.append('\n');
		
		if (lastFlush + FLUSH_INTERVAL < System.currentTimeMillis()) {
			System.out.println(sb.toString());
			sb = new StringBuffer();
		}
	}
}
