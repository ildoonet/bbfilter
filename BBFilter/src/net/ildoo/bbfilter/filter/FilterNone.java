package net.ildoo.bbfilter.filter;

public class FilterNone extends Filter {

	protected float[] getTransformMatrix() {
		return new float[20];
	}

	public String getName() {
		return "None";
	}
}
