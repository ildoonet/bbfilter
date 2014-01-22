package net.ildoo.bbfilter.filter;

public class FilterCalm extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.55f, 0.59f, -0.20f, 0.0f, 63.68f / 10,
			-0.2f, 1.25f, -0.18f, 0.0f, 69.12f / 10,
			-0.1f, 0.33f, 0.44f, 0.0f, 100f / 10,
			0f, 0f, 0f, 1f, 0f
		};
	}

}
