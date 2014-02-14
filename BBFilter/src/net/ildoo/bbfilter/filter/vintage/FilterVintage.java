package net.ildoo.bbfilter.filter.vintage;

import net.ildoo.bbfilter.filter.Filter;

public class FilterVintage extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -60, 
                0, 1, 0, 0, -60, 
                0, 0, 1, 0, -60, 
                0, 0, 0, 1, 0 };
	}

	protected float getSaturation() {
		return 0.9f;
	}
	
	protected float getBrightness() {
		return 1.5f;
	}
}
