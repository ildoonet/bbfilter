package net.ildoo.app.bmpmanager;

import java.util.Hashtable;

import net.ildoo.app.maintab.TabGallery;
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
	private static final long PERSIST_KEY_PATH = 0x415964b0eb570b22L;
	private static final BitmapManager instance = new BitmapManager();
	
	public static BitmapManager getInstance() {
		return instance;
	}
	
	private final LRUCache cache = new LRUCache(10);
	
	private final PersistentObject persist;
	private LongVector list;

	private final PersistentObject persistPath;
	private Hashtable pathTable;
	
	private BitmapManager() {
		persist = PersistentStore.getPersistentObject(PERSIST_KEY);
		list = (LongVector) persist.getContents();
		if (list == null) {
			list = new LongVector();
			persist.setContents(list);
		}
		
		persistPath = PersistentStore.getPersistentObject(PERSIST_KEY_PATH);
		pathTable = (Hashtable) persistPath.getContents();
		if (pathTable == null) {
			pathTable = new Hashtable();
			persistPath.setContents(pathTable);
		}
	}
	
	public void requestBitmap(long key, BitmapManagerListener listener) {
		
	}
	
	public int getSize() {
		return list.size();
	}
	
	public long getKey(final int index) {
		return list.elementAt(index);
	}
	
	public Bitmap getBitmap(long key) {
		if (list.contains(key) == false)
			return null;

		Bitmap cached = (Bitmap) cache.get(new Long(key));
		if (cached instanceof Bitmap)
			return cached;
		
		PersistentObject pobject = PersistentStore.getPersistentObject(key);
		Object content = pobject.getContents();
		
		if (content instanceof ByteVector == false) {
			removeBitmap(key);
			return null;
		}
		
		ByteVector bmpbyte = (ByteVector) content;
		Bitmap bitmap = DBitmapTools.getBitmapFromByteVector(bmpbyte);
		cache.put(new Long(key), bitmap);
		
		return bitmap;
	}
	
	public long addBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		
		final long key = System.currentTimeMillis();
		
		PersistentObject pobject = PersistentStore.getPersistentObject(key);
		pobject.setContents(DBitmapTools.getByteVectorFromBitmap(bitmap));
		pobject.commit();
		
		list.addElement(key);
		persist.commit();
		
		// refresh request
		TabGallery.needRefresh = true;
		
		return key;
	}
	
	public void removeBitmap(long key) {
		PersistentStore.destroyPersistentObject(key);
		list.removeElement(key);
		persist.commit();
		
		cache.remove(new Long(key));
		
		removeBitmapPath(key);
		
		// refresh request
		TabGallery.needRefresh = true;
	}
	
	public void addBitmapPath(long key, String path) {
		pathTable.put(new Long(key), path);
		persistPath.commit();
	}
	
	public String getBitmapPath(long key) {
		return (String) pathTable.get(new Long(key));
	}
	
	public void removeBitmapPath(long key) {
		pathTable.remove(new Long(key));
		persistPath.commit();
	}
}
