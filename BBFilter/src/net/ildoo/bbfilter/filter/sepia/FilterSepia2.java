package net.ildoo.bbfilter.filter.sepia;

public class FilterSepia2 extends FilterSepia {
	protected float getBrightness() {
		return 0.9f;
	}
	
	public String getName() {
		return "SE2";
	}
	
	public String getBaseName() {
		return "SE1";
	}
}
