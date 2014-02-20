package net.ildoo.bbfilter.filter.classic;

import net.ildoo.bbfilter.filter.Filter;

public class FilterCool extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			1.13f, -0.17f, -0.015f, 0.0f, -4.11f,
			0.15f, 0.77f, -0.06f, 0.0f, 8.44f,
			0.11f, 0f, 0.59f, 0.0f, 40.22f,
			0f, 0f, 0f, 1f, 0f
		};
	}

	public String getName() {
		return "CL2";
	}
}
