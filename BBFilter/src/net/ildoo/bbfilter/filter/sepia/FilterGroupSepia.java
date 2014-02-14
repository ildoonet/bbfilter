package net.ildoo.bbfilter.filter.sepia;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupSepia extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterSepia.class,
			FilterSepia2.class
		};
	}

	public String getGroupName() {
		return "sepia".toUpperCase();
	}

}
