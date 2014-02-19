package net.ildoo.app.maintab;

import net.ildoo.app.filterselector.FilterSelector;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.filter.FilterGroup;
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
		
		final FilterGroup[] groups = FilterManager.getAllFilterGroups();

		for (int i = 0; i < groups.length; i++) {
			final FilterGroup filterGroup = groups[i];
			DTextOnBitmapButtonField btn = new DTextOnBitmapButtonField(filterGroup.getGroupName(), filterGroup.getSampleBitmap());
			btn.setChangeListener(new FieldChangeListener() {
				public void fieldChanged(Field field, int context) {
					UiApplication.getUiApplication().pushScreen(new FilterSelector(filterGroup.getGroupName(),
							filterGroup.getSampleBitmap(), filterGroup));
				}
			});
			add(btn);
		}
		
	}

}
