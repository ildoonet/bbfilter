package com.dabinci.os;

import net.rim.device.api.ui.picker.FilePicker;

class DabinciOS6 extends DabinciOS {

	public void setPicturePicker(FilePicker fp) {
		fp.setView(FilePicker.VIEW_PICTURES); 
	}
	
}
