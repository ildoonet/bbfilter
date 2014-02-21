package net.ildoo.bbfilter.filter.toy;

import net.ildoo.bbfilter.gradient.Gradient;
import net.ildoo.bbfilter.gradient.Gradient.XY;
import net.ildoo.bbfilter.gradient.GradientEclipse;

public class FilterToyVignette extends FilterToy {
	protected Gradient getGradient(int width, int height) {
		Gradient eg = new GradientEclipse();
		eg.setXY(XY.create((int)(width * 0.7), (int)(height*0.7)), XY.create(0, 0));
		return eg;
	}
	
	public String getName() {
		return "T2V";
	}
}
