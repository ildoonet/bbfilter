package net.ildoo.bbfilter.filter;

public class FilterVintage extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -60, 
                0, 1, 0, 0, -60, 
                0, 0, 1, 0, -60, 
                0, 0, 0, 1, 0 };
	}

}
