package net.ildoo.app.maintab;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.MainScreen;

import com.dabinci.ui.DResolution;
import com.dabinci.ui.tab.DTabToolTipManager;
import com.dabinci.ui.tab.DTitleTabManager;
import com.dabinci.ui.tab.DTitleTabManager.DTitleTab;
import com.dabinci.ui.tab.TabFocusChangeListener;

public class MainTabScreen extends MainScreen implements TabFocusChangeListener{
	
	private DTabToolTipManager tooltipManager;
	
	public MainTabScreen() {
		super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		
		DTitleTabManager titleTabs = new DTitleTabManager();
		titleTabs.addTab(
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_gallery_off.png")),
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_gallery.png")), 
				new TabGallery());
		titleTabs.addTab(
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_filters_off.png")),
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_filters.png")), 
				new TabGallery());
		titleTabs.addTab(
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_store_off.png")),
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_store.png")), 
				new TabGallery());
		titleTabs.addTab(
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_settings_off.png")),
				DResolution.getBitmap(Bitmap.getBitmapResource("tab_settings.png")), 
				new TabGallery());
		titleTabs.setTab(0);
		titleTabs.setTabChangeListener(this);
		setTitle(titleTabs);
		
		tooltipManager = new DTabToolTipManager(titleTabs.getContainer());
		add(tooltipManager);
	}

	public void onTabFocusChanged(DTitleTab tab) {
		Field field = tab;
		int left = 0;
		while (field != null) {
			left += field.getLeft();
			field = field.getManager();
		}

		tooltipManager.onToolTip(false);
		tooltipManager.getToolTipField().setToolTip("aaaaaaa", left + tab.getWidth() / 2, tab.getHeight());
		tooltipManager.onToolTip(true);
	}
	
	public void onTabFocus() {
		tooltipManager.onToolTip(true);
	}
	
	public void onTabUnfocus() {
		tooltipManager.onToolTip(false);
	}
}
