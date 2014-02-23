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
		if (this.width != 0 && this.height != 0) {
			maxwidth = this.width;
			maxheight = this.height;
		}
		
		setExtent(maxwidth, maxheight);
		
		if (getFieldCount() == 0)
			return;
		
		Field f = getField(0);
		layoutChild(f, maxwidth, maxheight);
		setPositionChild(f, 
			(maxwidth - f.getWidth()) / 2,
			(maxheight - f.getHeight()) / 2);
	}

}
