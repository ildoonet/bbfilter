package net.ildoo.bbfilter.filter;

public class FilterSunnySide extends Filter {

	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, 10, 
                0, 1, 0, 0, 10, 
                0, 0, 1, 0, -60, 
                0, 0, 0, 1, 0 };
	}

}