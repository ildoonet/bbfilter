package com.dabinci.ui.tab;

import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.VerticalFieldManager;

public abstract class DTabContent extends VerticalFieldManager {
	public DTabContent() {
		super(Manager.USE_ALL_WIDTH);
	}
	
	public abstract void requestRefresh();
}
