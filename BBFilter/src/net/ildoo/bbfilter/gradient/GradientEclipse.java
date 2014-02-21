package net.ildoo.bbfilter.gradient;

import net.rim.device.api.util.MathUtilities;

public class GradientEclipse extends Gradient {
	private XY xy;
	private int max;
	
	protected float getGradient(int x, int y) {
		if (max == 0) {
			
		}
		
		// test eclipse equation
		final float eclipse = (float) MathUtilities.pow((
				(MathUtilities.pow(x - width / 2, 2.0) / MathUtilities.pow(xy.x - width / 2, 2.0)) +
				(MathUtilities.pow(y - height / 2, 2.0) / MathUtilities.pow(xy.y - height / 2, 2.0))
			), 0.7);
		
		if (eclipse < 1.0f)	// inside the eclipse
			return 1.0f;
		
		// filter function : fx = -0.005(x - 1)^2 + 1
		return Math.max(0.3f, -0.01f * (float)MathUtilities.pow(eclipse - 1, 2.7) + 1f);
	}

	public void setXY(XY xy, XY nullXy) {
		super.setXY(xy, nullXy);
		this.xy = xy;
	}
}
