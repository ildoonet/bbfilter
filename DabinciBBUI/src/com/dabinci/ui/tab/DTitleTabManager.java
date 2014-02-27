package com.dabinci.ui.tab;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DToggleButtonField;

public class DTitleTabManager extends VerticalFieldManager {
	
	private final HorizontalFieldManager tabs;
	private final VerticalFieldManager container;
	private TabFocusChangeListener listener;
	
	public DTitleTabManager() {
		super(Manager.USE_ALL_WIDTH | Field.FIELD_HCENTER);
		setBackground(BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_BG)));
		add(tabs = new HorizontalFieldManager(Manager.NO_HORIZONTAL_SCROLL | Field.FIELD_HCENTER));
		container = new VerticalFieldManager(Manager.USE_ALL_WIDTH);
	}
	
	public VerticalFieldManager getContainer() {
		return container;
	}
	
	public void addTab(String title, Bitmap icon, Bitmap icon_on, DTabContent content) {
		addTab(title, icon, icon_on, content, false);
	}
	
	public void addTab(String title, Bitmap icon, Bitmap icon_on, DTabContent content, boolean lastTab) {
		DTitleTab tab = new DTitleTab(title, icon, icon_on, content);
		if (lastTab)
			tab.setLockMovement(Manager.RIGHTWARD, true);
		
		tab.setPadding(new XYEdges(
				DRes.getPixel(3),
				DRes.getPixel(25),
				DRes.getPixel(3),
				DRes.getPixel(25)
			));
		tabs.add(tab);
	}

	public void setTab(int idx) {
		setAllTabOff();
		
		DTitleTab tab = (DTitleTab) tabs.getField(idx);
		tab.setStatus(true);
		setContents(tab.content);
	}
	
	public void setTabChangeListener(TabFocusChangeListener listener) {
		this.listener = listener;
	}
	
	private void setContents(Field field) {
		container.deleteAll();
		container.add(field);
	}
	
	private void setAllTabOff() {
		for (int i = 0; i < tabs.getFieldCount(); i++) {
			DTitleTab tab = (DTitleTab) tabs.getField(i);
			tab.setStatus(false);
		}
	}
	
	protected void onFocus(int direction) {
		super.onFocus(direction);
		listener.onTabFocus();
		
		if (direction != 0)
			for (int i = 0; i < tabs.getFieldCount(); i++) {
				DTitleTab tab = (DTitleTab) tabs.getField(i);
				if (tab.getStatus() == true && tab.isFocus() == false) {
					tab.setFocus();
					break;
				}
			}
	}
	
	protected void onUnfocus() {
		super.onUnfocus();
		listener.onTabUnfocus();
	}
	
	public class DTitleTab extends DToggleButtonField{
		private final DTabContent content;
		private final String title;
		
		public DTitleTab(String title, Bitmap icon, Bitmap icon_on, DTabContent content) {
			super(icon, icon, icon_on, icon_on);
			this.title = title;
			this.content = content;
			
			this.action = new Runnable() {
				public void run() {
					setContents(DTitleTab.this.content);
				}
			};
		}
		
		protected boolean navigationClick(int status, int time) {
			if (isToggled)
				return true;
			
			setAllTabOff();
			
			return super.navigationClick(status, time);
		}
		
		protected void onFocus(int direction) {
			super.onFocus(direction);
			if (DTitleTabManager.this.listener != null) {
				DTitleTabManager.this.listener.onTabFocusChanged(this);
			}
		}

		public String getTitle() {
			return title;
		}
	}
}
