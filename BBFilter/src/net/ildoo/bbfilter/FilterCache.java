package net.ildoo.bbfilter;

import com.dabinci.utils.cache.LRUCache;

public class FilterCache extends LRUCache {
	private static final int CACHE_SIZE = 6;
	
	private static final FilterCache instance = new FilterCache();
	
	public static FilterCache getInstance() {
		return instance;
	}
	
	private FilterCache() {
		super(CACHE_SIZE);
	}
}
