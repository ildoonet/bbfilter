/*
 * Copyright (c) 2011 Research In Motion Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dabinci.ui.button;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DNavLockManager;

public class DBitmapButtonField extends DBaseButtonField {
	protected Bitmap[] _bitmaps;
	protected static final int NORMAL = 0;
	protected static final int FOCUS = 1;
	
	private DNavLockManager navigationManager = new DNavLockManager();
	
	protected Runnable action;

	public DBitmapButtonField(Bitmap normalState) {
		this(normalState, normalState, 0);
	}

	public DBitmapButtonField(Bitmap normalState, Bitmap focusState) {
		this(normalState, focusState, 0);
	}

	public DBitmapButtonField(Bitmap normalState, Bitmap focusState, long style) {
		super(Field.FOCUSABLE | style);

		if ((normalState.getWidth() != focusState.getWidth()) || (normalState.getHeight() != focusState.getHeight())) {
			throw new IllegalArgumentException("Image sizes don't match");
		}

		_bitmaps = new Bitmap[] { normalState, focusState };
		
		setBackground(Field.VISUAL_STATE_FOCUS, BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_SELECTED_BG)));
	}

	public void setImage(Bitmap normalState) {
		_bitmaps[NORMAL] = normalState;
		invalidate();
	}

	public void setFocusImage(Bitmap focusState) {
		_bitmaps[FOCUS] = focusState;
		invalidate();
	}

	public int getPreferredWidth() {
		if (_bitmaps[NORMAL] == null) {
			return 0;
		}
		return _bitmaps[NORMAL].getWidth();
	}

	public int getPreferredHeight() {
		if (_bitmaps[NORMAL] == null) {
			return 0;
		}
		return _bitmaps[NORMAL].getHeight();
	}

	protected void layout(int width, int height) {
		if (_bitmaps[NORMAL] != null) {
			setExtent(_bitmaps[NORMAL].getWidth(), _bitmaps[NORMAL].getHeight());
		}
	}
	
	public void setAction(Runnable action) {
		this.action = action;
	}

	protected boolean navigationClick(int status, int time) {
		if (action!=null)
			action.run();
		
		return super.navigationClick(status, time);
	}

	protected void paint(Graphics g) {
		Bitmap bitmap = getIconBitmap(g);
		g.drawBitmap(0, 0, bitmap.getWidth(), bitmap.getHeight(), bitmap, 0, 0);
	}
	
	protected Bitmap getIconBitmap(Graphics g) {
		int index = g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS) ? FOCUS : NORMAL;
		Bitmap bitmap = _bitmaps[index];
		return bitmap;
	}

	protected void drawFocus(Graphics g, boolean on) {
		paintBackground(g);
		paint(g);
	}

	public void onRelease() {
		if (_bitmaps != null) {
			for (int i = 0; i < _bitmaps.length; i++) {
				_bitmaps[i] = null;
			}
		}
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
