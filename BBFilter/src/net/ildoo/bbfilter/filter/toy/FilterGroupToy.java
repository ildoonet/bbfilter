package net.ildoo.bbfilter.filter.toy;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupToy extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterToy.class,
			FilterToyVignette.class,
		};
	}

	public String getGroupName() {
		return "Toy".toUpperCase();
	}
}
