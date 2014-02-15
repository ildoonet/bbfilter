package com.dabinci.ui.tab;

import com.dabinci.ui.tab.DTitleTabManager.DTitleTab;

public interface TabFocusChangeListener {
	public void onTabFocusChanged(final DTitleTab tab);
	public void onTabFocus();
	public void onTabUnfocus();
}
