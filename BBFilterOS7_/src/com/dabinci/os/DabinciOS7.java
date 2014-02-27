package com.dabinci.os;

import net.rim.device.api.applicationcontrol.ApplicationPermissions;

class DabinciOS7 extends DabinciOS6 {

	public void requestPermission(ApplicationPermissions permissions) {
		super.requestPermission(permissions);
		
		int permissions_list[] = {(ApplicationPermissions.PERMISSION_NFC) };

		for (int i = 0; i < permissions_list.length; i++) {
			int int_permission = permissions_list[i];
			try {
				permissions.addPermission(int_permission);
			} catch (Exception e) {
			}
		}// end for i
	}
}
