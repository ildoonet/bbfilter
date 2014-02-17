package net.ildoo.app.filterselector;

import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class FilterSelectorField extends VerticalFieldManager {
	
	private final HorizontalFieldManager thumbs;
	
	public FilterSelectorField() {
		
		thumbs = new HorizontalFieldManager();
		add(thumbs);
	}
	
	public Manager getThumbnailManager() {
		return thumbs;
	}
}
