package net.ildoo.bbfilter.filter.vintage;

import net.ildoo.bbfilter.filter.Filter;

public class FilterWorn extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -60, 
                0, 1, 0, 0, -60, 
                0, 0, 1, 0, -90, 
                0, 0, 0, 1, 0 };
	}

	protected float getBrightness() {
		return 1.2f;
	}

	public String getName() {
		return "W1";
	}
}
