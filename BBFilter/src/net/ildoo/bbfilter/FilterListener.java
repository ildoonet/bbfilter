package net.ildoo.bbfilter;

import net.rim.device.api.system.Bitmap;

public interface FilterListener {
	public void onFiltered(final Bitmap bitmap);
	public void onThumbnailed(final FilteredBitmap[] thumbs);
	public void onProgressChanged(final float percentage);
	public void onFilterFail();
}
