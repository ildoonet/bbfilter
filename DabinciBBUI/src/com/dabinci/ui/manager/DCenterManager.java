package com.dabinci.ui.manager;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;

public class DCenterManager extends Manager {
	private int width, height;
	
	public DCenterManager(){
		super(0l);
	}
	
	public DCenterManager setWidth(int width) {
		this.width = width;
		return this;
	}

	public DCenterManager setHeight(int height) {
		this.height = height;
		return this;
	}

	protected void sublayout(int maxwidth, int maxheight) {
		setExtent(this.width, this.height);
		
		if (getFieldCount() == 0)
			return;
		
		Field f = getField(0);
		layoutChild(f, width, height);
		setPositionChild(f, 
			(getWidth() - f.getWidth()) / 2,
			(getHeight() - f.getHeight()) / 2);
	}

}
