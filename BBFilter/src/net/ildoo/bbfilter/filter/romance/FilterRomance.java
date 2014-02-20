package net.ildoo.bbfilter.filter.romance;

import net.ildoo.bbfilter.filter.Filter;

public class FilterRomance extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.81f, 0.135f, -0.156f, 0f, 40f,
			-0.02f, 1.07f, -0.17f, 0f, 13.2f,
			0.01f, -0.0111f, 0.741f, 0f, 32.641f,
			0f, 0f, 0f, 1f, 0f
		};
	}

	public String getName() {
		return "R2";
	}

}
