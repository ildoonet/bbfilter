package com.dabinci.ui.bitmap;

import java.lang.ref.WeakReference;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;

public abstract class DLazyBitmapField extends DBitmapButtonField {
	private WeakReference/*<Bitmap>*/ bitmap;
	
	public DLazyBitmapField(Bitmap loading, long style) {
		super(loading, loading, style);
	}

	protected Bitmap getIconBitmap(Graphics g) {
		if (bitmap != null && bitmap.get() instanceof Bitmap) {
			return (Bitmap) bitmap.get();
		}
		
		requestBitmap();
		
		return super.getIconBitmap(g);
	}
	
	public abstract void requestBitmap();
	
	public void onReqestCompleted(Bitmap bitmap) {
		Bitmap resized = new Bitmap(_bitmaps[NORMAL].getWidth(), _bitmaps[NORMAL].getHeight());
		bitmap.scaleInto(resized, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FILL);
		this.bitmap = new WeakReference(resized);
		invalidate();
	}
	
	protected void paint(Graphics g) {
		super.paint(g);
		
		if (isFocus()) {
			final int BORDER_WIDTH = DRes.getPixel(14);
			g.setColor(DColor.getColor(DColor.COLOR_SELECTED_BG));
			
			final int x = 0;
			final int y = 0;
			
			g.fillRect(x, y, BORDER_WIDTH, getHeight());
			g.fillRect(x, y, getWidth(), BORDER_WIDTH);
			g.fillRect(getWidth() - BORDER_WIDTH, y, BORDER_WIDTH, getHeight());
			g.fillRect(x, getHeight()-BORDER_WIDTH, getWidth(), BORDER_WIDTH);
		}
	}
}
