package com.dabinci.os;

import net.rim.device.api.ui.picker.FilePicker;

import com.dabinci.utils.DFileUtils;

class DabinciOS5 extends DabinciOS {
	public void setPicturePicker(FilePicker fp) {
		String path = "";
		if (DFileUtils.hasSdcardStorage() == true) {
			path = "file:///SDCard/BlackBerry/pictures/";
		} else if (DFileUtils.hasBuiltInStorage() == true) {
			path = "file:///store/home/user/pictures/";
		}
		
		if (path != null && path.length() > 0) {
			fp.setPath(path);
		}		
	}
}
