package com.dabinci.os;

import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.ui.picker.FilePicker;

public abstract class DabinciOS {
	public abstract void setPicturePicker(FilePicker fp);
	
	public void requestPermission(ApplicationPermissions permissions) {
	}
}
