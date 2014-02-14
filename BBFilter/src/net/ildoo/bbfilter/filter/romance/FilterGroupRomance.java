package net.ildoo.bbfilter.filter.romance;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupRomance extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterRomance.class,
			FilterClear.class
		};
	}

	public String getGroupName() {
		return "romance".toUpperCase();
	}

}
