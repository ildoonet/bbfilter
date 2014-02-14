package net.ildoo.bbfilter.filter.sunny;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupSunnySide extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterSunnySide.class
		};
	}

	public String getGroupName() {
		return "sunnyside".toUpperCase();
	}

}
