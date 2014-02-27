package com.dabinci.ui.button;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;

import com.dabinci.ui.DNavLockManager;
import com.dabinci.ui.DRes;

public class DToggleButtonField extends DBitmapButtonField {
	protected volatile boolean isToggled;
	protected Bitmap normalSelected;
	protected Bitmap toggledSelected;
	protected int x = 0;
	protected int y = 0;
	
	private DNavLockManager navigationManager = new DNavLockManager();

	public static DToggleButtonField newInstance(Bitmap normal, Bitmap normalSelected, Bitmap toggled, Bitmap toggledSelected, int height) {
		Bitmap normalResized = DRes.getBitmapByHeight(normal, height);
		Bitmap toggledResized = DRes.getBitmapByHeight(toggled, height);
		Bitmap normalSelectedResized = DRes.getBitmapByHeight(normalSelected, height);
		Bitmap toggledSelectedResized = DRes.getBitmapByHeight(toggledSelected, height);
		return new DToggleButtonField(normalResized, normalSelectedResized, toggledResized, toggledSelectedResized);
	}
	
	public DToggleButtonField(Bitmap normal, Bitmap normalSelected, Bitmap toggled, Bitmap toggledSelected) {
		super(normal, toggled);
		this.normalSelected = normalSelected;
		this.toggledSelected = toggledSelected;
		isToggled = false;
	}
	
	protected boolean navigationClick(int status, int time) {
		isToggled = !isToggled;
		invalidate();
		
		return super.navigationClick(status, time);
	}
	
	protected void paint(Graphics g) {
		int index = isToggled ? FOCUS : NORMAL;
		Bitmap bitmap = _bitmaps[index];
		if (bitmap == null) {
			return;
		}
		g.drawBitmap(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap, 0, 0);
	}

	protected void drawFocus(Graphics g, boolean on) {
		if (isFocus()) {
			Bitmap bitmap = null;
			if (isToggled == true) {
				bitmap = toggledSelected;
			} else {
				bitmap = normalSelected;
			}
			
			if (bitmap != null) {
				g.drawBitmap(x, y, bitmap.getWidth(), bitmap.getHeight(), bitmap, 0, 0);
			}
		} else {
			paint(g); 
		}
	}
	
	public boolean getStatus() {
		return isToggled;
	}
	
	public void setStatus(boolean isToggled) {
		if (this.isToggled == isToggled)
			return;
		
		this.isToggled = isToggled;
		invalidate();
	}

	protected int getBitmapWidth() {
		return normalSelected.getWidth();
	}
	
	protected int getBitmapHeight() {
		return normalSelected.getHeight();
	}
	
	protected boolean navigationMovement(int dx, int dy, int status, int time) {
		if (navigationManager.navigationMovement(dx, dy, status, time))
			return true;
		
		return super.navigationMovement(dx, dy, status, time);
	}
	
	public void setLockMovement(int direction, boolean lock) {
		navigationManager.setLockMovement(direction, lock);
	}
}
