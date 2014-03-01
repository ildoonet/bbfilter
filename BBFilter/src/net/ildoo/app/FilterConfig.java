package net.ildoo.app;

import com.dabinci.utils.DConfig;

public class FilterConfig extends DConfig {
	private static final long PERSIST_KEY = 0x2424b082326e7d0L;
	public static final long KEY_RESOLUTION_WIDTH = 0x1e0c2347e0da2047L;
	public static final long KEY_RESOLUTION_HEIGHT = 0x1bdeb5db1ca372a7L;
	
	public static final String DIRECTORY = "a dabinci";
	
	public static final boolean isDebugMode = true;
	
	private static FilterConfig instance;
	
	public synchronized static FilterConfig getInstance() {
		if (instance == null) {
			instance = new FilterConfig(PERSIST_KEY);
		}
		
		return instance;
	}
	
	protected FilterConfig(long persist_key) {
		super(persist_key);
	}
}
