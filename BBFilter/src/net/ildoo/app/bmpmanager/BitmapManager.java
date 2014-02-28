package net.ildoo.app.bmpmanager;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.util.ByteVector;
import net.rim.device.api.util.LongVector;

import com.dabinci.ui.DBitmapTools;
import com.dabinci.utils.cache.LRUCache;

/**
 * 
 * */
public class BitmapManager {
	private static final long PERSIST_KEY = 0x415964b0eb570b21L;
	private static final BitmapManager instance = new BitmapManager();

	public static BitmapManager getInstance() {
		return instance;
	}
	
	private final LRUCache cache = new LRUCache(10);
	
	private final PersistentObject persist;
	private LongVector list;
	
	private BitmapManager() {
		persist = PersistentStore.getPersistentObject(PERSIST_KEY);
		list = (LongVector) persist.getContents();
		if (list == null) {
			list = new LongVector();
			persist.setContents(list);
		}
	}
	
	public void requestBitmap(long key, BitmapManagerListener listener) {
		
	}
	
	public Bitmap getBitmap(long key) {
		if (list.contains(key) == false)
			return null;

		Bitmap cached = (Bitmap) cache.get(new Long(key));
		if (cached != null)
			return cached;
		
		PersistentObject pobject = PersistentStore.getPersistentObject(key);
		ByteVector bmpbyte = (ByteVector) pobject.getContents();
		
		if (bmpbyte == null) {
			list.removeElement(key);
			return null;
		}
		
		Bitmap bitmap = DBitmapTools.getBitmapFromByteVector(bmpbyte);
		cache.put(new Long(key), bitmap);
		
		return bitmap;
	}
	
	public long addBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		
		final long key = System.currentTimeMillis();
		
		PersistentObject pobject = PersistentStore.getPersistentObject(key);
		pobject.setContents(DBitmapTools.getBytesFromBitmap(bitmap));
		pobject.commit();
		
		list.addElement(key);
		persist.commit();
		
		return key;
	}
	
	public void removeBitmap(long key) {
		PersistentStore.destroyPersistentObject(key);
		list.removeElement(key);
		persist.commit();
		
		cache.remove(new Long(key));
	}
}
