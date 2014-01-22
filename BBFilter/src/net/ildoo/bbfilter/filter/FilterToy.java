package net.ildoo.bbfilter.filter;

public class FilterToy extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.384f, 0.682f, -0.112f, 0f, 12.85f,
			-0.583f, 1.669f, -0.11f, 0f, 4.08f,
			-0.527f, 0.626f, 0.819f, 0f, 13.16f,
			0f, 0f, 0f, 1f, 0f
		};
	}

}
