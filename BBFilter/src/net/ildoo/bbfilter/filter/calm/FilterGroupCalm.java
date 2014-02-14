package net.ildoo.bbfilter.filter.calm;

import net.ildoo.bbfilter.filter.FilterGroup;

public class FilterGroupCalm extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterCalm.class,
			FilterSunrise.class
		};
	}

	public String getGroupName() {
		return "calm".toUpperCase();
	}

}
