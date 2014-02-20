package net.ildoo.app.maintab;

import net.ildoo.app.filterlist.FilterListManager;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.filter.FilterGroup;

import com.dabinci.ui.tab.DTabContent;

public class TabFilters extends DTabContent {

	public TabFilters() {
		setContents();
	}
	
	
	public void requestRefresh() {
		
	}
	
	private void setContents() {
		final FilterGroup[] groups = FilterManager.getAllFilterGroups();
		deleteAll();
		add(new FilterListManager(groups, null));
	}

}
