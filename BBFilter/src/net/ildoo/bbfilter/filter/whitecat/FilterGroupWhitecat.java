package net.ildoo.bbfilter.filter.whitecat;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupWhitecat extends FilterGroup {
	public void initGroup() {
		filterList = new Class[] {
			FilterBW.class,
			FilterWhiteCat.class
		};
	}

	public String getGroupName() {
		return "white cat".toUpperCase();
	}

}
