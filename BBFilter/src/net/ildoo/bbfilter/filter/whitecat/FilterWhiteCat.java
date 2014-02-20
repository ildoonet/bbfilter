package net.ildoo.bbfilter.filter.whitecat;

import net.ildoo.bbfilter.filter.Filter;

public class FilterWhiteCat extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.58f, 0.13f, 0f, 0f, 99.53f / 2,
			0.12f, 0.6f, -0.01f, 0f, 100.66f / 2,
			0.04f, 0.18f, 0.51f, 0f, 91.42f / 2,
			0f, 0f, 0f, 1f, 0f
		};
	}

	public String getName() {
		return "WC2";
	}

}
