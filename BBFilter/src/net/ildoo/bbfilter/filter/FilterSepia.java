package net.ildoo.bbfilter.filter;

public class FilterSepia extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] {
			0.2f, 0.51f, 0.14f, 0f, 72.5f,
			0.23f, 0.5f, 0.22f, 0f, 40.72f / 1.5f,
			0.26f, 0.44f, 0.23f, 0f, 23.38f / 1.5f,
			0f, 0f, 0f, 1f, 0f
		};
	}

}
