package com.dabinci.utils;

import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

public abstract class DConfig {
	private PersistentObject persist;
	private Hashtable records;
	
	protected DConfig(final long persist_key) {
		persist = PersistentStore.getPersistentObject(persist_key);
		records = (Hashtable) persist.getContents();
		if (records == null) {
			records = new Hashtable();
			persist.setContents(records);
		}
	}
	
	public Hashtable getTable() {
		return records;
	}
	
	public void setData(Object key, Object value) {
		records.put(key, value);
		persist.commit();
	}
	
	public int getInt(final long key, final int defaultValue) {
		Object obj = records.get(new Long(key));
		
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Integer == false)
			throw new IllegalArgumentException("The value to the provided key is not integer");

		return ((Integer) obj).intValue();
	}
}
