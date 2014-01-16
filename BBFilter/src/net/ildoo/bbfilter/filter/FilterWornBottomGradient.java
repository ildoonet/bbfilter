package net.ildoo.bbfilter.filter;

import net.ildoo.bbfilter.gradient.Gradient;
import net.ildoo.bbfilter.gradient.Gradient.XY;
import net.ildoo.bbfilter.gradient.GradientLinear;

public class FilterWornBottomGradient extends FilterWorn {
	protected Gradient getGradient(int width, int height) {
		Gradient lg = new GradientLinear();
		lg.setXY(XY.create(0, height), XY.create(0, 0));
		return lg;
	}
}
