package net.ildoo.bbfilter.filter.whitecat;

import net.ildoo.bbfilter.filter.Filter;

public class FilterBW extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.30f, 0.7f, 0f, 0f, 35.6f/3,
			0.30f, 0.7f, 0f, 0f, 35.6f/3,
			0.30f, 0.7f, 0f, 0f, 35.6f/3,
			0f, 0f, 0f, 1f, 0f
		};
	}

	public String getName() {
		return "WC1";
	}

}
