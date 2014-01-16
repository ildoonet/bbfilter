package net.ildoo.bbfilter.filter;

public class FilterStark extends Filter {
	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -90, 
                0, 1, 0, 0, -90, 
                0, 0, 1, 0, -90, 
                0, 0, 0, 1, 0};
	}
	
	protected float getSaturation() {
		return 0.2f;
	}
	
	protected float getBrightness() {
		return 2.0f;
	}
}
