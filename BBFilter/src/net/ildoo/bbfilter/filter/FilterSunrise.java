package net.ildoo.bbfilter.filter;

public class FilterSunrise extends Filter {

	protected float[] getTransformMatrix() {
		return new float[]{
			0.22f, 0.73f, -0.17f, 0f, 43.14f,
			-0.54f, 1.56f, -0.17f, 0f, 27.77f, 
			-0.51f, 0.64f, 0.61f, 0f, 24.52f,
			0f, 0f, 0f, 1f, 0f
		};
	}

}
