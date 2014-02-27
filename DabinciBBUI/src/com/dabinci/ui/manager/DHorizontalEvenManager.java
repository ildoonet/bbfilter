package com.dabinci.ui.manager;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;

public class DHorizontalEvenManager extends Manager {
	private final int margin;
	
	public DHorizontalEvenManager(int margin) {
		this(margin, 0l);
	}
	
	public DHorizontalEvenManager(int margin, long style) {
		super(style);
		this.margin = margin;
	}

	protected void sublayout(int width, int height) {
		final int count = getFieldCount();
		
		int availableWidth;
		
		if (count <= 0) {
			setExtent(width,0);
			return;
		} else if (count > 1)
			availableWidth = (width - (count - 1) * margin) / count;
		else
			availableWidth = width;
		int maxHeight = 0;
		
		for (int i = 0; i < count; i++) {
			layoutChild(getField(i), availableWidth, height);
			setPositionChild(getField(i), ( availableWidth + margin) * i, 0);
			maxHeight = Math.max(maxHeight, getField(i).getHeight());
		}
		
		setExtent(width, maxHeight);
	}
	
	protected int nextFocus(int direction, int axis) {
		if (axis == Field.AXIS_VERTICAL)
    		return -1;
    	else
    		return super.nextFocus(direction, axis);
	}
}
