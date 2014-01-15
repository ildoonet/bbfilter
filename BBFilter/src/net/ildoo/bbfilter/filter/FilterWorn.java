package net.ildoo.bbfilter.filter;

public class FilterWorn extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -60, 
                0, 1, 0, 0, -60, 
                0, 0, 1, 0, -90, 
                0, 0, 0, 1, 0 };
	}

}
