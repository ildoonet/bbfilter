package net.ildoo.app.bmpmanager;

import net.rim.device.api.system.Bitmap;

public interface BitmapManagerListener {
	public void onRequestedBitmapArrived(Bitmap bitmap);
}
