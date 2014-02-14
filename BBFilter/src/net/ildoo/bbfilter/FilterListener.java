package net.ildoo.bbfilter;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;

public interface FilterListener {
	public void onFiltered(final Bitmap bitmap);
	public void onThumbnailed(final FilteredBitmap[] thumbs);
	public void onBlurred(final Bitmap bitmap, Field field);
	public void onProgressChanged(final float percentage);
	public void onFilterFail();
}
