package net.ildoo.bbfilter.filter;

public abstract class FilterGroup {
	protected Class[] filterList;
	
	public FilterGroup() {
		initGroup();
	}
	
	public abstract void initGroup();
	public abstract String getGroupName();
	
	public Class[] getFilters() {
		return filterList;
	}
}
