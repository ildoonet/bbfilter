package net.ildoo.bbfilter.filter.classic;

import net.ildoo.bbfilter.filter.Filter;

public class FilterAntique extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.77f, 0.19f, -0.03f, 0f, 41.71f/2,
			0.08f, 0.80f, -0.07f, 0f, 51.5f/2,
			0.08f, 0.20f, 0.28f, 0f, 78.81f/2,
			0f, 0f, 0f, 1f, 0f
		};
	}

}
