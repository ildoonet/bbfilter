package net.ildoo.bbfilter.filter;

public class FilterStark2 extends Filter {
	protected float[] getTransformMatrix() {
		return new float[] { 
                1, 0, 0, 0, -90, 
                0, 1, 0, 0, -90, 
                0, 0, 1, 0, -90, 
                0, 0, 0, 1, 0};
	}
	
	protected float getSaturation() {
		return 0.5f;
	}
	
	protected float getBrightness() {
		return 1.5f;
	}
}
