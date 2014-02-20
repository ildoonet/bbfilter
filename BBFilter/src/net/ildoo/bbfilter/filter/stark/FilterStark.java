package net.ildoo.bbfilter.filter.stark;

import net.ildoo.bbfilter.filter.Filter;

public class FilterStark extends Filter {
	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -60, 
                0, 1, 0, 0, -60, 
                0, 0, 1, 0, -60, 
                0, 0, 0, 1, 0};
	}
	
	protected float getSaturation() {
		return 0.2f;
	}
	
	protected float getBrightness() {
		return 2.0f;
	}

	public String getName() {
		return "ST1";
	}
}
