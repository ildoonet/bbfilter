package com.dabinci.ui;

import net.rim.device.api.ui.Manager;

public class DNavLockManager {
	private boolean[] isLockMovement;
	
	public DNavLockManager() {
		isLockMovement = new boolean[]{false, false, false, false};
	}
	
	public boolean navigationMovement(int dx, int dy, int status, int time) {
		if (isLockMovement[0] == true && dy < 0)
			return true;
		if (isLockMovement[1] == true && dx > 0)
			return true;
		if (isLockMovement[2] == true && dy > 0)
			return true;
		if (isLockMovement[3] == true && dx < 0)
			return true;
		
		return false;
	}
	
	public void setLockMovement(final int direction, boolean lock) {
		switch (direction) {
		case Manager.UPWARD:
			isLockMovement[0] = lock;
			break;
		case Manager.RIGHTWARD:
			isLockMovement[1] = lock;
			break;
		case Manager.DOWNWARD:
			isLockMovement[2] = lock;
			break;
		case Manager.LEFTWARD:
			isLockMovement[3] = lock;
			break;
		default:
			break;
		}
	}
}
