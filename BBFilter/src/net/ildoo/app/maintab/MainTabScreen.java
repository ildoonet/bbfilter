package net.ildoo.app.maintab;

import net.ildoo.app.FilterApp;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DRes;
import com.dabinci.ui.tab.DTabToolTipManager;
import com.dabinci.ui.tab.DTitleTabManager;
import com.dabinci.ui.tab.DTitleTabManager.DTitleTab;
import com.dabinci.ui.tab.TabFocusChangeListener;

public class MainTabScreen extends MainScreen implements TabFocusChangeListener{
	
	private DTabToolTipManager tooltipManager;
	
	public MainTabScreen() {
		super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		getMainManager().setBackground(BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_BG_GREY)));
		
		DTitleTabManager titleTabs = new DTitleTabManager();
		titleTabs.addTab(
				"Gallery",
				DRes.getBitmap(Bitmap.getBitmapResource("tab_gallery_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("tab_gallery.png")), 
				new TabGallery());
		titleTabs.addTab(
				"My Filters",
				DRes.getBitmap(Bitmap.getBitmapResource("tab_filters_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("tab_filters.png")), 
				new TabFilters());
		titleTabs.addTab(
				"Filter Store",
				DRes.getBitmap(Bitmap.getBitmapResource("tab_store_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("tab_store.png")), 
				new TabStore());
		titleTabs.addTab(
				"Settings",
				DRes.getBitmap(Bitmap.getBitmapResource("tab_settings_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("tab_settings.png")), 
				new TabSettings(), true);
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
		tooltipManager.getToolTipField().setToolTip(tab.getTitle(), left + tab.getWidth() / 2, tab.getHeight());
		tooltipManager.onToolTip(true);
	}
	
	public void onTabFocus() {
		tooltipManager.onToolTip(true);
	}
	
	public void onTabUnfocus() {
		tooltipManager.onToolTip(false);
	}
	
	public void close() {
		super.close();
		try {
			((FilterApp) UiApplication.getUiApplication()).onExit();
		} catch (Exception e) {}
	}
}
