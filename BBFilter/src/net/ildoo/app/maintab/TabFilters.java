package net.ildoo.app.maintab;

import net.ildoo.app.filterselector.FilterSelector;
import net.ildoo.bbfilter.FilterManager;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

import com.dabinci.ui.button.DTextOnBitmapButtonField;
import com.dabinci.ui.tab.DTabContent;

public class TabFilters extends DTabContent {

	public TabFilters() {
		setContents();
	}
	
	
	public void requestRefresh() {
		
	}
	
	private void setContents() {
		deleteAll();
		
		DTextOnBitmapButtonField btn = new DTextOnBitmapButtonField("CLASSIC", 
				Bitmap.getBitmapResource("sample1.jpg"));
		btn.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				UiApplication.getUiApplication().pushScreen(
						new FilterSelector(
								"CLASSIC", 
								Bitmap.getBitmapResource("sample1.jpg"), 
								FilterManager.getFilterGroup(1))
				);
			}
		});
		add(btn);
		
	}

}
