package net.ildoo.app.filterlist;

import net.ildoo.app.filterselector.FilterSelectorScreen;
import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.dabinci.ui.button.DTextOnBitmapButtonField;

public class FilterListManager extends VerticalFieldManager {
	private final Bitmap bitmap;
	
	public FilterListManager(FilterGroup[] groups, Bitmap bitmap) {
		super(Manager.USE_ALL_WIDTH | Manager.VERTICAL_SCROLL);
		this.bitmap = bitmap;
		
		if (groups == null || groups.length == 0) {
			// TODO
		}
		
		for (int i = 0; i < groups.length; i++) {
			final FilterGroup filterGroup = groups[i];
			DTextOnBitmapButtonField btn = new DTextOnBitmapButtonField(filterGroup.getGroupName(), filterGroup.getTitleBitmap());
			btn.setChangeListener(new FieldChangeListener() {
				public void fieldChanged(Field field, int context) {
					Bitmap bitmap = null;
					boolean needSaveButton = false;
					if (FilterListManager.this.bitmap != null) {
						bitmap = FilterListManager.this.bitmap;
						needSaveButton = true;
					} else {
						bitmap = filterGroup.getSampleBitmap();
					}
					
					UiApplication.getUiApplication().pushScreen(new FilterSelectorScreen(getScreen(), filterGroup.getGroupName(), bitmap, null, filterGroup, needSaveButton));
				}
			});
			add(btn);
		}
	}
}
