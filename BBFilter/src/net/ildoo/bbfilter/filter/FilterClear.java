package net.ildoo.bbfilter.filter;

public class FilterClear extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			1.04f, -0.16f, -0.023f, 0f, 12.05f,
			0.25f, 0.6f, -0.003f, 0f, 11f,
			0.2f, -0.23f, 0.85f, 0f, 8f,
			0f, 0f, 0f, 1f, 0f
		};
	}

}
