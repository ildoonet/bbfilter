package net.ildoo.bbfilter.filter.stark;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupStark extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterStark.class,
			FilterStark2.class
		};
	}

	public String getGroupName() {
		return "stark".toUpperCase();
	}

}
